package modulo.mascotas;

public class ValidarMascota {

    public static boolean camposBasicosValidos(String nombre, String especie, String edad, String peso, String dueno) {
        return nombre != null && !nombre.trim().isEmpty()
                && especie != null && !especie.trim().isEmpty()
                && edad != null && !edad.trim().isEmpty()
                && peso != null && !peso.trim().isEmpty()
                && dueno != null && !dueno.trim().isEmpty();
    }

    public static boolean edadValida(int edad) {
        return edad >= 0 && edad <= 40;
    }

    public static boolean pesoValido(double peso) {
        return peso > 0;
    }
}
