package data.management;

import java.io.Serializable;

/*
 * Clase que representa una mascota registrada en el sistema veterinario.
 */
public class Mascota implements Serializable {

    private static final long serialVersionUID = 1L;

    private int id;
    private String nombre;
    private String especie;
    private int edad;
    private double peso;
    private String raza;
    private Cliente cliente;

    public Mascota() {
    }

    public Mascota(int id, String nombre, String especie,
                   int edad, double peso,
                   String raza, Cliente cliente) {
        this.id = id;
        this.nombre = nombre;
        this.especie = especie;
        this.edad = edad;
        this.peso = peso;
        this.raza = raza;
        this.cliente = cliente;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEspecie() {
        return especie;
    }

    public void setEspecie(String especie) {
        this.especie = especie;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public String getRaza() {
        return raza;
    }

    public void setRaza(String raza) {
        this.raza = raza;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    @Override
    public String toString() {
        String datosCliente = cliente != null ? cliente.toString() : "Cliente: Sin asignar";
        return datosCliente +
                "\nID Mascota: " + id +
                "\nNombre Mascota: " + nombre +
                "\nEspecie: " + especie +
                "\nEdad: " + edad +
                "\nPeso: " + peso +
                "\nRaza: " + raza +
                "\n-----------------------------\n";
    }
}
