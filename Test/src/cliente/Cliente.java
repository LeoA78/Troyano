package cliente;

import java.io.*;
import java.time.LocalDateTime;

public class Cliente implements Serializable{
    private LocalDateTime inicioConexion;
    private LocalDateTime ultimaConexion;
    private String nombreUsuario;
    private String sistemaOperativo;
    private int id; //Ver que hacer con esta variable
    private String apodo;


    public Cliente() throws IOException {
     inicioConexion=LocalDateTime.now();
     sistemaOperativo=System.getProperty("os.name");
     nombreUsuario=System.getProperty("user.name");
    }


    public LocalDateTime getInicioConexion() {
        return inicioConexion;
    }

    public void setInicioConexion(LocalDateTime inicioConexion) {
        this.inicioConexion = inicioConexion;
    }

    public LocalDateTime getUltimaConexion() {
        return ultimaConexion;
    }

    public void setUltimaConexion(LocalDateTime ultimaConexion) {
        this.ultimaConexion = ultimaConexion;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getSistemaOperativo() {
        return sistemaOperativo;
    }

    public void setSistemaOperativo(String sistemaOperativo) {
        this.sistemaOperativo = sistemaOperativo;
    }

    public String getApodo() {
        return apodo;
    }

    public void setApodo(String apodo) {
        this.apodo = apodo;
    }

    @Override
    public String toString() {
        return "Cliente:" +
                "Conectado desde=" + inicioConexion +
                ", Nombre de Usuario='" + nombreUsuario + '\'' +
                ", Sistema Operativo='" + sistemaOperativo + '\'';
    }
}
