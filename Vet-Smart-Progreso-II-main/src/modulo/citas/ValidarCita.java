package modulo.citas;

public class ValidarCita {

    public static boolean camposBasicosValidos(String mascota, String fecha, String hora, String motivo, String prioridad) {
        return mascota != null && !mascota.trim().isEmpty()
                && fecha != null && !fecha.trim().isEmpty()
                && hora != null && !hora.trim().isEmpty()
                && motivo != null && !motivo.trim().isEmpty()
                && prioridad != null && !prioridad.trim().isEmpty();
    }

    public static boolean prioridadValida(String prioridad) {
        if (prioridad == null) return false;
        String p = prioridad.toLowerCase();
        return p.contains("emergencia") || p.contains("prioritaria")
                || p.contains("general") || p.contains("control")
                || p.contains("alta") || p.contains("media") || p.contains("baja");
    }
}
