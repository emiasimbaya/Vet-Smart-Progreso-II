package modulo.veterinarios;

import java.io.Serializable;

public class AristaDisponibilidad implements Serializable {
    private static final long serialVersionUID = 1L;

    private int idVeterinario;
    private String especie;
    private String fecha;
    private String hora;
    private boolean ocupado;

    public AristaDisponibilidad(int idVeterinario, String especie, String fecha, String hora, boolean ocupado) {
        this.idVeterinario = idVeterinario;
        this.especie = especie;
        this.fecha = fecha;
        this.hora = hora;
        this.ocupado = ocupado;
    }

    public int getIdVeterinario() {
        return idVeterinario;
    }

    public String getEspecie() {
        return especie;
    }

    public String getFecha() {
        return fecha;
    }

    public String getHora() {
        return hora;
    }

    public boolean isOcupado() {
        return ocupado;
    }

    public void setOcupado(boolean ocupado) {
        this.ocupado = ocupado;
    }
}
