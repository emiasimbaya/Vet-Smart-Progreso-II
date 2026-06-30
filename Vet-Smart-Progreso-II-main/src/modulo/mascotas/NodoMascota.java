package modulo.mascotas;

import data.management.Mascota;
import java.io.Serializable;

public class NodoMascota implements Serializable {
    private static final long serialVersionUID = 1L;

    Mascota mascota;
    NodoMascota izquierdo;
    NodoMascota derecho;

    public NodoMascota(Mascota mascota) {
        this.mascota = mascota;
    }
}
