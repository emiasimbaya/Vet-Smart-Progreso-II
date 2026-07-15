package modulo.clientes;

import data.management.Cliente;
import java.util.ArrayList;
import java.util.HashMap;

public class GestionClientes {

    private ArrayList<Cliente> clientes;
    private HashMap<String, Cliente> clientesPorId;
    private HashMap<String, Cliente> clientesPorCedula;

    public GestionClientes() {
        clientes = new ArrayList<>();
        clientesPorId = new HashMap<>();
        clientesPorCedula = new HashMap<>();

        cargarCliente(new Cliente("C001", "Carlos Perez", "1723456789", "0991234567"));
        cargarCliente(new Cliente("C002", "Maria Lopez", "1712345678", "0987654321"));
        cargarCliente(new Cliente("C003", "Juan Torres", "1709876543", "0976543210"));
        cargarCliente(new Cliente("C004", "Ana Ruiz", "1756789123", "0965432109"));
        cargarCliente(new Cliente("C005", "Luis Sanchez", "1767891234", "0954321098"));
        cargarCliente(new Cliente("C006", "Sofia Mena", "1778912345", "0943210987"));
        cargarCliente(new Cliente("C007", "Pedro Castro", "1789123456", "0932109876"));
        cargarCliente(new Cliente("C008", "Daniela Flores", "1791234567", "0921098765"));
        cargarCliente(new Cliente("C009", "Andres Vega", "1701234567", "0910987654"));
        cargarCliente(new Cliente("C010", "Valeria Ortiz", "1711122233", "0998765432"));
        cargarCliente(new Cliente("C011", "Miguel Herrera", "1722233344", "0988877665"));
        cargarCliente(new Cliente("C012", "Camila Romero", "1733344455", "0977788990"));
        cargarCliente(new Cliente("C013", "Javier Morales", "1744455566", "0966677889"));
        cargarCliente(new Cliente("C014", "Fernanda Silva", "1755566677", "0955566778"));
        cargarCliente(new Cliente("C015", "Diego Paredes", "1766677788", "0944455667"));
    }

    private void cargarCliente(Cliente cliente) {
        clientes.add(cliente);
        clientesPorId.put(cliente.getIdCliente().toLowerCase(), cliente);
        clientesPorCedula.put(cliente.getCedula(), cliente);
    }

    public boolean agregarCliente(Cliente cliente) {
        if (cliente == null || cliente.getIdCliente() == null || cliente.getCedula() == null) return false;
        if (clientesPorId.containsKey(cliente.getIdCliente().toLowerCase())
                || clientesPorCedula.containsKey(cliente.getCedula())) return false;
        cargarCliente(cliente);
        return true;
    }

    public boolean actualizarDatos(String idCliente, String nombre, String cedula, String telefono) {
        Cliente cliente = buscarClientePorId(idCliente);
        if (cliente != null) {
            clientesPorCedula.remove(cliente.getCedula());
            cliente.setNombre(nombre);
            cliente.setCedula(cedula);
            cliente.setTelefono(telefono);
            clientesPorCedula.put(cedula, cliente);
            return true;
        }
        return false;
    }

    public boolean eliminarCliente(String idCliente) {
        Cliente cliente = buscarClientePorId(idCliente);
        if (cliente != null) {
            clientes.remove(cliente);
            clientesPorId.remove(idCliente.toLowerCase());
            clientesPorCedula.remove(cliente.getCedula());
            return true;
        }
        return false;
    }

    public String listarTodos() {
        StringBuilder sb = new StringBuilder();
        for (Cliente cliente : clientes) {
            sb.append(cliente.toString()).append("\n-----------------------------\n");
        }
        return sb.length() != 0 ? sb.toString() : "No hay clientes registrados";
    }

    public int buscarNombreSecuencial(String nombre) {
        for (int i = 0; i < clientes.size(); i++) {
            if (clientes.get(i).getNombre().equalsIgnoreCase(nombre)) return i;
        }
        return -1;
    }

    public int buscarId(String idCliente) {
        if (idCliente == null) return -1;
        Cliente cliente = buscarClientePorId(idCliente);
        return cliente != null ? clientes.indexOf(cliente) : -1;
    }

    public Cliente buscarClientePorId(String idCliente) {
        if (idCliente == null) return null;
        return clientesPorId.get(idCliente.toLowerCase());
    }

    public Cliente buscarClientePorCedula(String cedula) {
        return clientesPorCedula.get(cedula);
    }

    public void ordenarPorNombre() {
        Cliente aux;
        for (int i = 0; i < clientes.size() - 1; i++) {
            for (int j = 0; j < clientes.size() - 1 - i; j++) {
                if (clientes.get(j).getNombre().compareToIgnoreCase(clientes.get(j + 1).getNombre()) > 0) {
                    aux = clientes.get(j);
                    clientes.set(j, clientes.get(j + 1));
                    clientes.set(j + 1, aux);
                }
            }
        }
    }

    public Cliente getCliente(int indice) {
        return clientes.get(indice);
    }

    public ArrayList<Cliente> getClientes() {
        return clientes;
    }


    /** Metodo de recarga*/
    public void cargarListaExterna(ArrayList<Cliente> listaCargada) {
        clientes.clear();
        clientesPorId.clear();
        clientesPorCedula.clear();
        for (Cliente c : listaCargada) {
            cargarCliente(c);
        }
    }
}
