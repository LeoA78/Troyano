package servidor;

import cliente.Cliente;

import java.net.Socket;
import java.util.ArrayList;

public class Sesion {
    private Cliente cliente;
    private int id;
    private Socket socket;
    private static ArrayList<Sesion> listaConectados = new ArrayList<>();

    public Sesion(Cliente cliente, Socket socket, int id) {
        this.cliente = cliente;
        this.id = id;
        this.socket = socket;

    }

    public static Socket dameSocket(int id) {

        for (int i = 0; i < listaConectados.size(); i++) {
            if (listaConectados.get(i).id == id) {
                return listaConectados.get(i).socket;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "Nº=" + id + "" + cliente + "Socket " + socket;
    }

    public Socket getSocket() {
        return socket;
    }

    public static ArrayList<Sesion> getListaConectados() {
        return listaConectados;
    }

    public static void setListaConectados(ArrayList<Sesion> listaConectados) {
        Sesion.listaConectados = listaConectados;
    }
}


