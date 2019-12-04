package cliente;

/*
 * Funcion de la clase:
 * Sirve para crear metodos estaticos necesarios para el proyecto
 *
 * Implementar:
 * Método para transferir bytes.
 *
 * */


public class Utiles {
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";
    public static final String ANSI_RESET = "\u001B[0m";


    public static void esperar(double segundos) {
        double temp = segundos * 1000;
        try {
            Thread.sleep((long) temp);
        } catch (InterruptedException e) {
            System.out.println("Error en la espera");
        }

    }


}
