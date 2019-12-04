package cliente;

import java.io.IOException;

/*
 * Funciones de la Clase:
 * Reconocer el sistema operativo para inyectar el cliente en él.
 * Identificar el cliente
 * Generar carpeta raiz en el cliente con sus datos
 *
 * Implementar:
 * Métodos para dichas funciones arriba especificadas
 *
 * */

public class Inyector {

    public static void main(String[] args) throws IOException, InterruptedException {

        Cliente cliente = new Cliente();
        Conexion conexion = new Conexion(cliente);

    }

}
