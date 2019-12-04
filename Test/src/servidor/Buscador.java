package servidor;

import cliente.Cliente;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Buscador extends Thread {
    private final int PORT = 1999;
    private Socket cliente;
    private static Boolean encendido = false;
    private ServerSocket ss = null;
    private static int contador = 1;

    public Buscador(boolean encendido) {
        Buscador.encendido = encendido; //Variable que determina si buscamos conexiones o no
    }

    public void run() {
        if (encendido) {
            try {
                ss = new ServerSocket(PORT);
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Error> No se pudo iniciar el Servidor");
            }
        }

        Cliente user = null;
        while (encendido) {
            System.out.println("Servidor--> Esperando alguna Conexión...");
            try {
                cliente = ss.accept();  // <---- ACÁ SE FRENA
            } catch (IOException e) {
                //1e.printStackTrace();
                System.out.println("Error de conexión");
            }
            if (encendido) {
                try {
                    user = (Cliente) Conexion.recibirObjeto(cliente);
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }

                int id = contador;
                Sesion sesion = new Sesion(user, cliente, id);

                Sesion.getListaConectados().add(sesion);

                assert user != null; //Solo para que el IDE no joda
                System.out.println("Servidor--> " + user.getNombreUsuario() + " se ha conectado");
                contador++;
            }
        }
    }

    public static Boolean getEncendido() {
        return encendido;
    }

    public static void setEncendido(Boolean encendido) {
        Buscador.encendido = encendido;
    }

    public ServerSocket getServerSocket() {
        return ss;
    }

}
