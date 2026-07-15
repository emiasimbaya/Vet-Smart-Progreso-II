package modulo.veterinarios;

import data.management.Veterinario;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GrafoDisponibilidad implements Serializable {
    private static final long serialVersionUID = 1L;

    private HashMap<Integer, Veterinario> veterinarios;
    private HashMap<Integer, List<AristaDisponibilidad>> relaciones;

    public GrafoDisponibilidad() {
        veterinarios = new HashMap<>();
        relaciones = new HashMap<>();
    }

    public void agregarVeterinario(Veterinario veterinario) {
        veterinarios.put(veterinario.getId(), veterinario);
        relaciones.putIfAbsent(veterinario.getId(), new ArrayList<>());
    }

    public void agregarRelacion(int idVeterinario, String especie, String fecha, String hora) {
        relaciones.putIfAbsent(idVeterinario, new ArrayList<>());
        relaciones.get(idVeterinario).add(new AristaDisponibilidad(idVeterinario, especie, fecha, hora, false));
    }

    public boolean atiendeEspecie(int idVeterinario, String especie) {
        List<AristaDisponibilidad> lista = relaciones.get(idVeterinario);
        if (lista == null) return false;
        for (AristaDisponibilidad arista : lista) {
            if (arista.getEspecie().equalsIgnoreCase(especie)) {
                return true;
            }
        }
        return false;
    }

    public boolean estaDisponible(int idVeterinario, String especie, String fecha, String hora) {
        if (!atiendeEspecie(idVeterinario, especie)) return false;
        List<AristaDisponibilidad> lista = relaciones.get(idVeterinario);
        if (lista == null) return false;
        for (AristaDisponibilidad arista : lista) {
            boolean mismoHorario = arista.getFecha().equalsIgnoreCase(fecha)
                    && arista.getHora().equalsIgnoreCase(hora);
            if (mismoHorario && arista.isOcupado()) return false;
        }
        return true;
    }

    public void ocuparHorario(int idVeterinario, String especie, String fecha, String hora) {
        relaciones.putIfAbsent(idVeterinario, new ArrayList<>());
        relaciones.get(idVeterinario).add(new AristaDisponibilidad(idVeterinario, especie, fecha, hora, true));
    }

    public String mostrarGrafo() {
        StringBuilder sb = new StringBuilder();
        for (Integer idVet : relaciones.keySet()) {
            Veterinario v = veterinarios.get(idVet);
            sb.append("Veterinario: ").append(v != null ? v.getNombre() : idVet).append("\n");
            for (AristaDisponibilidad arista : relaciones.get(idVet)) {
                sb.append("   -> Especie: ").append(arista.getEspecie())
                        .append(" | Fecha: ").append(arista.getFecha())
                        .append(" | Hora: ").append(arista.getHora())
                        .append(" | Ocupado: ").append(arista.isOcupado() ? "Sí" : "No")
                        .append("\n");
            }
        }
        return sb.length() != 0 ? sb.toString() : "El grafo no tiene relaciones registradas";
    }

    public String horariosOcupados(int idVeterinario, String fecha) {
        List<AristaDisponibilidad> lista = relaciones.get(idVeterinario);
        if (lista == null) return "No hay información de horarios para este veterinario.";
        StringBuilder sb = new StringBuilder();
        for (AristaDisponibilidad arista : lista) {
            if (arista.getFecha().equalsIgnoreCase(fecha) && arista.isOcupado()) {
                sb.append("Hora ocupada: ").append(arista.getHora()).append("\n");
            }
        }
        return sb.length() != 0 ? sb.toString() : "El veterinario está libre todo el día " + fecha;
    }
}
