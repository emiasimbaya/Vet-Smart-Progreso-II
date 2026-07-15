package modulo.mascotas;

import data.management.Cliente;
import data.management.Mascota;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/*
 * Clase encargada de gestionar las mascotas del sistema.
 */
public class GestionMascota {

    private ArrayList<Mascota> mascotas;
    private HashMap<Integer, Mascota> mascotasPorId;
    private HashMap<String, ArrayList<Mascota>> mascotasPorCliente;
    private ArbolBinarioMascotas arbolMascotas;

    public GestionMascota() {
        mascotas = new ArrayList<>();
        mascotasPorId = new HashMap<>();
        mascotasPorCliente = new HashMap<>();
        arbolMascotas = new ArbolBinarioMascotas();

        cargarMascota(new Mascota(1, "Max", "Perro", 3, 12.5, "Labrador",
                new Cliente("C001", "Carlos Perez", "1723456789", "0991234567")));
        cargarMascota(new Mascota(2, "Luna", "Gato", 2, 4.1, "Siames",
                new Cliente("C002", "Maria Lopez", "1712345678", "0987654321")));
        cargarMascota(new Mascota(3, "Rocky", "Perro", 5, 18.0, "Bulldog",
                new Cliente("C003", "Juan Torres", "1709876543", "0976543210")));
        cargarMascota(new Mascota(4, "Milo", "Gato", 1, 3.5, "Persa",
                new Cliente("C004", "Ana Ruiz", "1756789123", "0965432109")));
        cargarMascota(new Mascota(5, "Toby", "Perro", 4, 10.2, "Beagle",
                new Cliente("C005", "Luis Sanchez", "1767891234", "0954321098")));
        cargarMascota(new Mascota(6, "Nala", "Gato", 3, 5.0, "Angora",
                new Cliente("C006", "Sofia Mena", "1778912345", "0943210987")));
        cargarMascota(new Mascota(7, "Zeus", "Perro", 6, 25.4, "Pastor Aleman",
                new Cliente("C007", "Pedro Castro", "1789123456", "0932109876")));
        cargarMascota(new Mascota(8, "Kira", "Perro", 2, 9.8, "Pug",
                new Cliente("C008", "Daniela Flores", "1791234567", "0921098765")));
        cargarMascota(new Mascota(9, "Simba", "Gato", 4, 6.3, "Criollo",
                new Cliente("C009", "Andres Vega", "1701234567", "0910987654")));
        cargarMascota(new Mascota(10, "Coco", "Perro", 1, 7.1, "French Poodle",
                new Cliente("C010", "Valeria Ortiz", "1711122233", "0998765432")));
        cargarMascota(new Mascota(11, "Thor", "Perro", 5, 20.0, "Rottweiler",
                new Cliente("C011", "Miguel Herrera", "1722233344", "0988877665")));
        cargarMascota(new Mascota(12, "Mia", "Gato", 2, 4.4, "Bengala",
                new Cliente("C012", "Camila Romero", "1733344455", "0977788990")));
        cargarMascota(new Mascota(13, "Bruno", "Perro", 7, 30.5, "Doberman",
                new Cliente("C013", "Javier Morales", "1744455566", "0966677889")));
        cargarMascota(new Mascota(14, "Pelusa", "Conejo", 1, 2.3, "Enano",
                new Cliente("C014", "Fernanda Silva", "1755566677", "0955566778")));
        cargarMascota(new Mascota(15, "Lucky", "Perro", 3, 11.7, "Golden Retriever",
                new Cliente("C015", "Diego Paredes", "1766677788", "0944455667")));
    }

    private void cargarMascota(Mascota mascota) {
        mascotas.add(mascota);
        mascotasPorId.put(mascota.getId(), mascota);
        if (mascota.getCliente() != null) {
            String idCliente = mascota.getCliente().getIdCliente();
            mascotasPorCliente.putIfAbsent(idCliente, new ArrayList<>());
            mascotasPorCliente.get(idCliente).add(mascota);
        }
        arbolMascotas.insertar(mascota);
    }

    public boolean agregarMascota(Mascota mascota) {
        if (mascota == null || mascotasPorId.containsKey(mascota.getId())) return false;
        cargarMascota(mascota);
        return true;
    }

    public boolean actualizarDatos(int id, String nombre, String especie, int edad, double peso, String raza, Cliente cliente) {
        Mascota mascota = mascotasPorId.get(id);
        if (mascota != null) {
            mascota.setNombre(nombre);
            mascota.setEspecie(especie);
            mascota.setEdad(edad);
            mascota.setPeso(peso);
            mascota.setRaza(raza);
            mascota.setCliente(cliente);
            reconstruirIndices();
            return true;
        }
        return false;
    }

    public boolean eliminarMascota(int id) {
        Mascota mascota = mascotasPorId.get(id);
        if (mascota != null) {
            mascotas.remove(mascota);
            reconstruirIndices();
            return true;
        }
        return false;
    }

    public String listarTodos() {
        StringBuilder sb = new StringBuilder();
        for (Mascota mascota : mascotas) {
            sb.append(mascota.toString());
        }
        return sb.length() != 0 ? sb.toString() : "No hay mascotas registradas";
    }

    public String listarOrdenadasPorArbol() {
        StringBuilder sb = new StringBuilder();
        for (Mascota mascota : arbolMascotas.recorridoInorden()) {
            sb.append(mascota.toString());
        }
        return sb.length() != 0 ? sb.toString() : "No hay mascotas registradas";
    }

    public int buscarNombreSecuencial(String nombre) {
        for (int i = 0; i < mascotas.size(); i++) {
            if (mascotas.get(i).getNombre().equalsIgnoreCase(nombre)) return i;
        }
        return -1;
    }

    public void ordenarPorNombre() {
        Mascota aux;
        for (int i = 0; i < mascotas.size() - 1; i++) {
            for (int j = 0; j < mascotas.size() - 1 - i; j++) {
                if (mascotas.get(j).getNombre().compareToIgnoreCase(mascotas.get(j + 1).getNombre()) > 0) {
                    aux = mascotas.get(j);
                    mascotas.set(j, mascotas.get(j + 1));
                    mascotas.set(j + 1, aux);
                }
            }
        }
    }

    public void ordenarPorId() {
        Mascota aux;
        for (int i = 0; i < mascotas.size() - 1; i++) {
            for (int j = 0; j < mascotas.size() - 1 - i; j++) {
                if (mascotas.get(j).getId() > mascotas.get(j + 1).getId()) {
                    aux = mascotas.get(j);
                    mascotas.set(j, mascotas.get(j + 1));
                    mascotas.set(j + 1, aux);
                }
            }
        }
    }

    public int buscarId(int id) {
        Mascota mascota = arbolMascotas.buscarPorId(id);
        return mascota != null ? mascotas.indexOf(mascota) : -1;
    }

    public Mascota buscarMascotaPorId(int id) {
        return mascotasPorId.get(id);
    }

    public Mascota getMascota(int indice) {
        return mascotas.get(indice);
    }

    public ArrayList<Mascota> getMascotas() {
        return mascotas;
    }

    public List<Mascota> getMascotasPorCliente(String idCliente) {
        return mascotasPorCliente.getOrDefault(idCliente, new ArrayList<>());
    }

    private void reconstruirIndices() {
        mascotasPorId.clear();
        mascotasPorCliente.clear();
        arbolMascotas.reconstruir(mascotas);
        for (Mascota mascota : mascotas) {
            mascotasPorId.put(mascota.getId(), mascota);
            if (mascota.getCliente() != null) {
                String idCliente = mascota.getCliente().getIdCliente();
                mascotasPorCliente.putIfAbsent(idCliente, new ArrayList<>());
                mascotasPorCliente.get(idCliente).add(mascota);
            }
        }
    }

    /** Metodo de recarga*/
    public void cargarListaExterna(ArrayList<Mascota> listaCargada) {
        mascotas.clear();
        mascotasPorId.clear();
        mascotasPorCliente.clear();
        arbolMascotas.reconstruir(new ArrayList<>()); // limpia el árbol
        for (Mascota m : listaCargada) {
            cargarMascota(m);
        }
    }

}
