package servidor;

import cliente.Utiles;

import java.io.IOException;
import java.util.Objects;
import java.util.Scanner;

public class Menu {
    private static Buscador server = null;

    public static void principal() {
        System.out.println("*********************SERVIDOR************************");
        System.out.println("1> Iniciar Servidor");
        System.out.println("2> Ver Clientes");
        System.out.println("3> Detener servidor");
        System.out.println("4> Salir");
        System.out.println("*****************************************************");
        Scanner sc = new Scanner(System.in);
        int opc;
        do {
            opc = sc.nextByte();

            switch (opc) {

                case 1: {
                    System.out.println("Iniciar Servidor");
                    server = new Buscador(true);
                    server.start();
                    Utiles.esperar(1.5);
                    principal();
                    break;
                }
                case 2: {
                    System.out.println("***** CLIENTES ONLINE ******");
                    for (int i = 0; i < Sesion.getListaConectados().size(); i++) {
                        System.out.println(Sesion.getListaConectados().get(i));
                    }
                    System.out.println("Elige un cliente para conectarte");
                    int idCliente = sc.nextInt();

                    try {
                        Conexion.iniciarFlujoDatos(Objects.requireNonNull(Sesion.dameSocket(idCliente)));
                        //La linea de arriba fue recomendada por el IDE. Solo dice que el socket del id no sea nulo.
                    } catch (IOException e) {
                        //Aca podriamos hacer que si es nulo lo quite de la lista de sesiones... VER ESO
                        e.printStackTrace();
                    }

                    try {
                        Conexion.enviarOrden("iniciar");
                        new Conexion();
                    } catch (IOException e) {
                        e.printStackTrace();
                        System.out.println("Error al iniciar!");
                    }
                    principal();
                    Utiles.esperar(1.5);
                    break;
                }
                case 3: {
                    if (server == null) {
                        System.out.println("El servidor no está iniciado");
                    } else {

                        for (int i = 0; i < Sesion.getListaConectados().size(); i++) {
                            try {
                                Conexion.iniciarFlujoDatos(Sesion.getListaConectados().get(i).getSocket());
                                Conexion.enviarOrden("adios");
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                        }
                        Buscador.setEncendido(false);
                        try {
                            server.getServerSocket().close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        System.out.println("Servidor Apagado");
                    }

                    principal();
                    break;
                }
                default: {
                    System.out.println("Opcion incorrecta, vuelve a elegir");
                    break;
                }
            }
        } while (opc <= 0 || opc >= 3);

    }


}
