package cliente;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.StringTokenizer;

import static cliente.Conexion.*;

public class Consola {

    private File ruta = new File(System.getProperty("user.dir"));
    private String barra = System.getProperty("file.separator");

    private boolean online = true;

    public Consola() throws IOException {

        System.out.println("INICIA CONSOLA!!!");

        while (online) {
            if (getFlujoEntrada().ready()) { //Ver que tan útil es esto
                analizarComando(getFlujoEntrada().readLine());
            }
        }
    }

    public void analizarComando(String comando) throws IOException {
        boolean existe = false;
        for (int i = 2; i < comando.length(); i++) {

            if (comando.charAt(i) == ' ') {
                StringTokenizer token = new StringTokenizer(comando, " "); //Dividimos en partes el comando
                String[] comandoCompuesto = new String[token.countTokens()]; // Creamos el array/matriz

                int index = 0;
                while (token.hasMoreElements()) { // Lo almacenamos
                    comandoCompuesto[index] = token.nextToken();
                    index++;
                }
                tratarComando(comandoCompuesto); // lo mandamos a tratar
                existe = true;
                break;
            }
        }
        if (!existe) {
            tratarComando(comando);
        }
    }

    public void tratarComando(String[] comando) throws IOException {
        switch (comando[0]) {
            case "cd": {
                cd(comando[1]);
                break;
            }
            case "rename": {
                cambiarNombre(comando[1], comando[2]);
                break;
            }
            case "mkdir": {
                crearDirectorio(comando[1]);
                break;
            }
            default: {
                System.out.println("Comando Compuesto Inexistente");
            }
        }
    }

    public void tratarComando(String comando) throws IOException {
        switch (comando) {
            case "ls": {
                ls();
                break;
            }

            case "pwd": {
                responder(ruta.toString());
                break;
            }
            case "datos": {
                responder("Conectado desde: " + getCliente().getInicioConexion().toString());
                responder("Ultima conexíon: " + getCliente().getUltimaConexion().toString());
                break;
            }

            case "!ayuda": {
                responder("Lista de Comandos:");
                responder("ls                                 " + Utiles.ANSI_GREEN + "-->" + Utiles.ANSI_RESET + "     Muestra contenido de carpeta");
                responder("pwd                                " + Utiles.ANSI_GREEN + "-->" + Utiles.ANSI_RESET + "     Muestra la ruta actual");
                responder("mkdir                              " + Utiles.ANSI_GREEN + "-->" + Utiles.ANSI_RESET + "     Crea un directorio");
                responder("rename 'archivo' 'nuevoNombre'     " + Utiles.ANSI_GREEN + "-->" + Utiles.ANSI_RESET + "     Cambia nombre del directorio");
                responder("exit                               " + Utiles.ANSI_GREEN + "-->" + Utiles.ANSI_RESET + "     Salir");
                break;
            }

            case "exit": {
                System.out.println("Cliente--> Finaliza la consola");
                // Conexion.getSc().close();
                // Conexion.getCliente().setUltimaConexion(LocalDateTime.now());
                // cerrarFlujoDatos(); De momento no es necesario cerrar el flujo de datos
                online = false;
                break;
            }

            default: {
                responder("Comando inexistente");
                break;
            }
        }
    }

    public void cd(String comando) throws IOException {

        if (comando.equals("..")) {
            ruta = new File(ruta.getParent());
        } else {
            ruta = new File(ruta + barra + comando);
        }
        responder(ruta.toString());
    }

    public void crearDirectorio(String nombre) throws IOException {
        File nuevo = new File(ruta + barra + nombre);
        boolean creado = nuevo.mkdir();
        if (!creado) {
            responder("Error> No se pudo crear el directorio");
        }

    }

    public void cambiarNombre(String archivo, String nuevoNombre) throws IOException {
        System.out.println(ruta);
        File a = new File(ruta + "/" + archivo);
        File b = new File(ruta + "/" + nuevoNombre);
        boolean correcto = a.renameTo(b);

        if (!correcto) {
            responder("Error> No se cambió el nombre");
        }

    }

    public void ls() throws IOException {

        File carpeta = new File("" + ruta);

        File[] archivos = carpeta.listFiles();
        if (archivos == null || archivos.length == 0) {
            responder("No hay elementos dentro de la carpeta actual");

            return;
        } else {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            //ESTO ES PARA QUE LA CANTIDAD DE BYTES QUEDE ORDENADA Y NO DESACOMODE TODA LA LINEA
            int mayor = 0, tester = 0;
            for (int i = 0; i < archivos.length; i++) {
                File archivo = archivos[i];
                String caracteres = "" + archivo.length();
                tester = caracteres.length();
                if (tester > mayor) {
                    mayor = tester;
                }

            }
            //....................................................................................
            for (int i = 0; i < archivos.length; i++) {
                File archivo = archivos[i];
                String talla = "" + archivo.length();
                String espacio = "";
                int resultado = mayor - talla.length();
                for (int j = 0; j < resultado; j++) {
                    espacio += " ";
                }

                responder(String.format("(%s) - %s%s - %s  -  %s",
                        archivo.isDirectory() ? Utiles.ANSI_BLUE + "Carpeta" + Utiles.ANSI_RESET : Utiles.ANSI_YELLOW + "Archivo" + Utiles.ANSI_RESET, espacio,
                        talla,
                        sdf.format(archivo.lastModified()), archivo.getName()
                ));
            }
        }
    }


}
