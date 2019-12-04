package servidor;

import cliente.Utiles;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Conexion extends Thread {

    private static PrintWriter flujoSalida = null;
    private static BufferedReader flujoEntrada = null;

    Scanner sc = new Scanner(System.in);

    public Conexion() throws IOException {
        iniciarConexion(true);

    }

    public static Object recibirObjeto(Socket cliente) throws IOException, ClassNotFoundException {
        InputStream inp = cliente.getInputStream();
        ObjectInputStream ois = new ObjectInputStream(inp);
        Object user = ois.readObject();
        return user;
    }

    public static void iniciarFlujoDatos(Socket cliente) throws IOException {
        flujoSalida = new PrintWriter(cliente.getOutputStream());
        flujoEntrada = new BufferedReader(new InputStreamReader(cliente.getInputStream()));
    }

    public void iniciarConexion(boolean inicio) throws IOException {
        while (inicio) {

            System.out.print("Admin>");
            String ordena = sc.nextLine();
            enviarOrden(ordena);
            Utiles.esperar(0.1);

            while (flujoEntrada.ready()) {
                System.out.println(recibir());
            }

            if (ordena.equalsIgnoreCase("exit")) {
                //detenerConexion();
                inicio = false;
            }
        }
    }

    public static void detenerConexion() throws IOException {
        flujoEntrada.close();
        flujoSalida.close();
    }

    public static void enviarOrden(String orden) throws IOException {
        flujoSalida.println(orden);
        flujoSalida.flush();
    }

    public String recibir() throws IOException {
        //Probar poniendo un while aca directamente y recibir tod0 junto
        return flujoEntrada.readLine();
    }

}
