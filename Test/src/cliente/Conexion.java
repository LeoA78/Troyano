package cliente;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class Conexion {
    private static Socket sc = null;
    private static BufferedReader flujoEntrada = null;
    private static PrintWriter flujoSalida;
    private static Cliente cliente;
    private static boolean conectado = false;
    private final int PORT = 1999;
    private final String HOST = "192.168.0.14";


    public Conexion(Cliente cliente) {
        this.cliente = cliente;

        while (true) {
            Utiles.esperar(0.5);
            try {
                if (!conectado) {
                    sc = new Socket(HOST, PORT);

                    enviarObjecto(cliente);
                    // System.out.println("Cliente--> Conexión exitosa! " + cliente.getInicioConexion());
                    inicioFlujoDatos();
                    conectado = true;
                }

                if (flujoEntrada.ready()) {
                    String orden = flujoEntrada.readLine();

                    switch (orden) {
                        case "iniciar": {
                            System.out.println("Deberia iniciar consola");
                            new Consola();
                            break;
                        }

                        case "adios": {
                            sc.close(); //<<<< ver si es necesario
                            conectado = false;
                            System.out.println("salio");
                            break;
                        }
                    }
                }

                if (!ping()) {
                    sc.close(); //<<<< ver si es necesario
                    conectado = false;
                }

                System.out.println("Listo para servir - Esperando orden!");

            } catch (IOException e) {
                System.out.println("Cliente--> No hay conexión");
            }

        }
    }

    public void inicioFlujoDatos() {
        try {
            flujoSalida = new PrintWriter(sc.getOutputStream());
            flujoEntrada = new BufferedReader(new InputStreamReader(sc.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error al iniciar flujo de datos");
        }
    }

    public static void cerrarFlujoDatos() throws IOException {
        flujoSalida.flush();
        flujoSalida.close();
        flujoEntrada.close();
        //VER QUE TAN NECESARIO ES ESTO
    }

    public void enviarObjecto(Object objeto) throws IOException {
        OutputStream os = sc.getOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(os);
        oos.writeObject(objeto);
        oos.flush();
    }

    public static BufferedReader getFlujoEntrada() {
        return flujoEntrada;
    }

    public static PrintWriter getFlujoSalida() {
        return flujoSalida;
    }

    public static Cliente getCliente() {
        return cliente;
    }

    public static Socket getSc() {
        return sc;
    }

    public boolean ping() {
        //devuelve TRUE si se puede conectar a la IP o FALSE si no
        // VER si es Util o no...
        try {
            InetAddress in = sc.getInetAddress();
            return in.isReachable(1000);
        } catch (Exception e) {
            return false;
        }

    }

    public static void responder(String respuesta) throws IOException {
        flujoSalida.println(respuesta);
        flujoSalida.flush();
    }
}
