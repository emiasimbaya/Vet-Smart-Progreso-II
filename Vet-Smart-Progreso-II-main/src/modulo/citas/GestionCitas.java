package modulo.citas;

import data.management.Cita;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.PriorityQueue;

public class GestionCitas {

    private ArrayList<Cita> citas;
    private HashMap<String, Cita> citasPorCodigo;
    private PriorityQueue<Cita> colaPrioridad;

    public GestionCitas() {
        citas = new ArrayList<>();
        citasPorCodigo = new HashMap<>();
        colaPrioridad = new PriorityQueue<>();
    }

    public boolean agregarCita(Cita cita) {
        if (cita == null || cita.getCodigo() == null || citasPorCodigo.containsKey(cita.getCodigo())) {
            return false;
        }
        citas.add(cita);
        citasPorCodigo.put(cita.getCodigo(), cita);
        colaPrioridad.add(cita);
        return true;
    }

    public Cita buscarPorCodigo(String codigo) {
        return citasPorCodigo.get(codigo);
    }

    public boolean cancelarCita(String codigo) {
        Cita cita = citasPorCodigo.remove(codigo);
        if (cita == null) return false;
        cita.setEstado("Cancelada");
        citas.remove(cita);
        colaPrioridad.remove(cita);
        return true;
    }

    public boolean reprogramarCita(String codigo, String nuevaFecha, String nuevaHora) {
        Cita cita = buscarPorCodigo(codigo);
        if (cita == null) return false;
        colaPrioridad.remove(cita);
        cita.setFecha(nuevaFecha);
        cita.setHora(nuevaHora);
        colaPrioridad.add(cita);
        return true;
    }

    public Cita atenderSiguiente() {
        Cita cita = colaPrioridad.poll();
        if (cita != null) {
            cita.setEstado("Atendida");
            citas.remove(cita);
            citasPorCodigo.remove(cita.getCodigo());
        }
        return cita;
    }

    public String listarTodos() {
        StringBuilder sb = new StringBuilder();
        for (Cita cita : citas) {
            sb.append(cita.toString());
        }
        return sb.length() > 0 ? sb.toString() : "No hay citas pendientes";
    }

    public ArrayList<Cita> getCitas() {
        return citas;
    }
}
