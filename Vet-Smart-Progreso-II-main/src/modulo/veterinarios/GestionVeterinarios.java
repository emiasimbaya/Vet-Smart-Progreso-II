package modulo.veterinarios;

import data.management.Veterinario;
import java.util.ArrayList;
import java.util.HashMap;

public class GestionVeterinarios {

    private ArrayList<Veterinario> veterinarios;
    private HashMap<Integer, Veterinario> veterinariosPorId;
    private GrafoDisponibilidad grafoDisponibilidad;

    public GestionVeterinarios() {
        veterinarios = new ArrayList<>();
        veterinariosPorId = new HashMap<>();
        grafoDisponibilidad = new GrafoDisponibilidad();
        cargarVeterinario(new Veterinario(1, "Carlos Mendoza", "MEDICINA INTERNA"));
        cargarVeterinario(new Veterinario(2, "Lucia Fernandez", "CARDIO"));
        cargarVeterinario(new Veterinario(3, "Roberto Aguirre", "ONCOLOGÍA"));
        cargarVeterinario(new Veterinario(4, "Patricia Salazar", "NEUROLOGÍA"));
        cargarVeterinario(new Veterinario(5, "Esteban Vallejo", "OFTALMOLOGÍA"));
        cargarVeterinario(new Veterinario(6, "Daniela Cordova", "CIRUGÍA"));
        cargarVeterinario(new Veterinario(7, "Andres Tapia", "EMERGENCIA"));
    }
    private void cargarVeterinario(Veterinario veterinario) {
        veterinarios.add(veterinario);
        veterinariosPorId.put(veterinario.getId(), veterinario);
        grafoDisponibilidad.agregarVeterinario(veterinario);
        grafoDisponibilidad.agregarRelacion(veterinario.getId(), veterinario.getEspecialidad(), "GENERAL", "GENERAL");
    }
    public int buscarNombreSecuencial(String nombre) {
        for (int i = 0; i < veterinarios.size(); i++) {
            if (veterinarios.get(i).getNombre().equalsIgnoreCase(nombre)) return i;
        }
        return -1;
    }

    public boolean eliminarVeterinario(int id) {
        Veterinario veterinario = veterinariosPorId.get(id);
        if (veterinario != null) {
            veterinarios.remove(veterinario);
            veterinariosPorId.remove(id);
            return true;
        }
        return false;
    }

    public boolean actualizarDatos(int id, String nombre, String especialidad) {
        Veterinario veterinario = veterinariosPorId.get(id);
        if (veterinario != null) {
            veterinario.setNombre(nombre);
            veterinario.setEspecialidad(especialidad);
            return true;
        }
        return false;
    }

    public Veterinario getVeterinario(int indice) {
        return veterinarios.get(indice);
    }

    public boolean agregarVeterinario(Veterinario v) {
        if (v == null || veterinariosPorId.containsKey(v.getId())) return false;
        veterinarios.add(v);
        veterinariosPorId.put(v.getId(), v);
        grafoDisponibilidad.agregarVeterinario(v);
        return true;
    }

    public String listarTodos() {
        StringBuilder sb = new StringBuilder();
        for (Veterinario v : veterinarios) {
            sb.append(v.toString());
        }
        return sb.length() != 0 ? sb.toString() : "No hay veterinarios registrados";
    }

    public int buscarId(int id) {
        Veterinario veterinario = veterinariosPorId.get(id);
        return veterinario != null ? veterinarios.indexOf(veterinario) : -1;
    }

    public Veterinario buscarVeterinarioPorId(int id) {
        return veterinariosPorId.get(id);
    }

    public void agregarDisponibilidad(int idVeterinario, String especie, String fecha, String hora) {
        grafoDisponibilidad.agregarRelacion(idVeterinario, especie, fecha, hora);
    }

    public boolean estaDisponible(int idVeterinario, String especie, String fecha, String hora) {
        return grafoDisponibilidad.estaDisponible(idVeterinario, especie, fecha, hora);
    }

    public void ocuparHorario(int idVeterinario, String especie, String fecha, String hora) {
        grafoDisponibilidad.ocuparHorario(idVeterinario, especie, fecha, hora);
    }

    public void cargarListaExterna(ArrayList<Veterinario> listaCargada) {
        veterinarios.clear();
        veterinariosPorId.clear();
        for (Veterinario v : listaCargada) {
            cargarVeterinario(v);
        }
    }

    public ArrayList<Veterinario> getVeterinarios() {
        return veterinarios;
    }

    public String mostrarGrafoDisponibilidad() {
        return grafoDisponibilidad.mostrarGrafo();
    }

    public String consultarHorariosOcupados(int idVeterinario, String fecha) {
        return grafoDisponibilidad.horariosOcupados(idVeterinario, fecha);
    }

}

