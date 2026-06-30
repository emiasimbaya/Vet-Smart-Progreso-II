package modulo.mascotas;

import data.management.Mascota;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ArbolBinarioMascotas implements Serializable {
    private static final long serialVersionUID = 1L;

    private NodoMascota raiz;

    public void insertar(Mascota mascota) {
        raiz = insertarRecursivo(raiz, mascota);
    }

    private NodoMascota insertarRecursivo(NodoMascota actual, Mascota mascota) {
        if (actual == null) {
            return new NodoMascota(mascota);
        }

        if (mascota.getId() < actual.mascota.getId()) {
            actual.izquierdo = insertarRecursivo(actual.izquierdo, mascota);
        } else if (mascota.getId() > actual.mascota.getId()) {
            actual.derecho = insertarRecursivo(actual.derecho, mascota);
        } else {
            actual.mascota = mascota;
        }
        return actual;
    }

    public Mascota buscarPorId(int id) {
        NodoMascota nodo = buscarRecursivo(raiz, id);
        return nodo != null ? nodo.mascota : null;
    }

    private NodoMascota buscarRecursivo(NodoMascota actual, int id) {
        if (actual == null || actual.mascota.getId() == id) {
            return actual;
        }
        if (id < actual.mascota.getId()) {
            return buscarRecursivo(actual.izquierdo, id);
        }
        return buscarRecursivo(actual.derecho, id);
    }

    public List<Mascota> recorridoInorden() {
        List<Mascota> resultado = new ArrayList<>();
        inorden(raiz, resultado);
        return resultado;
    }

    private void inorden(NodoMascota actual, List<Mascota> resultado) {
        if (actual != null) {
            inorden(actual.izquierdo, resultado);
            resultado.add(actual.mascota);
            inorden(actual.derecho, resultado);
        }
    }

    public void reconstruir(List<Mascota> mascotas) {
        raiz = null;
        for (Mascota mascota : mascotas) {
            insertar(mascota);
        }
    }
}
