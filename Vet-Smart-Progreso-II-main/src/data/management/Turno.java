package data.management;

import java.io.Serializable;

public class Turno implements Serializable, Comparable<Turno> {

    private static final long serialVersionUID = 1L;

    private String codigo;
    private Cita cita;
    private String estado;

    public Turno(String codigo, Cita cita, String estado) {
        this.codigo = codigo;
        this.cita = cita;
        this.estado = estado;
    }

    public String getCodigo() {
        return codigo;
    }

    public Cita getCita() {
        return cita;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @Override
    public int compareTo(Turno otro) {
        return this.cita.compareTo(otro.cita);
    }

    @Override
    public String toString() {
        return "Turno: " + codigo + "\nEstado: " + estado + "\n" + cita.toString();
    }
}
