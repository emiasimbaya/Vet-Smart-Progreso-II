package data.management;

import java.io.Serializable;

/*
 * Clase que representa a un cliente
 * dentro del sistema veterinario.
 */
public class Cliente implements Serializable {

    private static final long serialVersionUID = 1L;

    private String idCliente;
    private String nombre;
    private String cedula;
    private String telefono;

    public Cliente() {
    }

    public Cliente(String idCliente, String nombre,
                   String cedula, String telefono) {
        this.idCliente = idCliente;
        this.nombre = nombre;
        this.cedula = cedula;
        this.telefono = telefono;
    }

    public String getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(String idCliente) {
        this.idCliente = idCliente;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    @Override
    public String toString() {
        return "ID Cliente: " + idCliente +
                "\nNombre Cliente: " + nombre +
                "\nCedula: " + cedula +
                "\nTelefono: " + telefono;
    }
}
