package data.management;

import java.io.Serializable;

public class Cita implements Serializable, Comparable<Cita> {

    private static final long serialVersionUID = 1L;

    private String codigo;
    private Mascota mascota;
    private String fecha;
    private String hora;
    private String motivo;
    private String prioridad;
    private String estado;
    private Veterinario veterinario;

    public Cita() {
    }

    public Cita(String codigo, Mascota mascota, String fecha, String hora,
                String motivo, String prioridad, String estado, Veterinario veterinario) {
        this.codigo = codigo;
        this.mascota = mascota;
        this.fecha = fecha;
        this.hora = hora;
        this.motivo = motivo;
        this.prioridad = prioridad;
        this.estado = estado;
        this.veterinario = veterinario;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Mascota getMascota() {
        return mascota;
    }

    public void setMascota(Mascota mascota) {
        this.mascota = mascota;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public String getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(String prioridad) {
        this.prioridad = prioridad;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Veterinario getVeterinario() {
        return veterinario;
    }

    public void setVeterinario(Veterinario veterinario) {
        this.veterinario = veterinario;
    }

    public int valorPrioridad() {
        if (prioridad == null) return 4;
        String p = prioridad.toLowerCase();
        if (p.contains("emergencia")) return 1;
        if (p.contains("prioritaria") || p.contains("alta")) return 2;
        if (p.contains("general") || p.contains("media")) return 3;
        return 4;
    }

    @Override
    public int compareTo(Cita otra) {
        int comparacionPrioridad = Integer.compare(this.valorPrioridad(), otra.valorPrioridad());
        if (comparacionPrioridad != 0) return comparacionPrioridad;
        int compFecha = this.fecha.compareToIgnoreCase(otra.fecha);
        if (compFecha != 0) return compFecha;
        return this.hora.compareToIgnoreCase(otra.hora);
    }

    @Override
    public String toString() {
        String nombreMascota = mascota != null ? mascota.getNombre() : "Sin mascota";
        String nombreVet = veterinario != null ? veterinario.getNombre() : "Sin asignar";
        return "Código: " + codigo +
                "\nMascota: " + nombreMascota +
                "\nVeterinario: " + nombreVet +
                "\nFecha: " + fecha + " | Hora: " + hora +
                "\nMotivo: " + motivo +
                "\nPrioridad: " + prioridad +
                "\nEstado: " + estado +
                "\n-----------------------------------------\n";
    }
}
