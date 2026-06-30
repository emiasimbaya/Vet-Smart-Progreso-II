package modulo.turnos;

import data.management.Turno;
import java.util.PriorityQueue;

public class GestionTurnos {
    private PriorityQueue<Turno> turnos;

    public GestionTurnos() {
        turnos = new PriorityQueue<>();
    }

    public void agregarTurno(Turno turno) {
        turnos.add(turno);
    }

    public Turno atenderSiguiente() {
        Turno turno = turnos.poll();
        if (turno != null) turno.setEstado("Atendido");
        return turno;
    }

    public String listarTurnos() {
        if (turnos.isEmpty()) return "No hay turnos pendientes";
        StringBuilder sb = new StringBuilder();
        for (Turno turno : turnos) {
            sb.append(turno.toString());
        }
        return sb.toString();
    }
}
