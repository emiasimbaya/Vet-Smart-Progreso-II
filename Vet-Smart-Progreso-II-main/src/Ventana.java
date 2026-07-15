import data.management.Cita;
import data.management.Cliente;
import data.management.Mascota;
import data.management.Veterinario;
import modulo.clientes.GestionClientes;
import modulo.citas.GestionCitas;
import modulo.mascotas.GestionMascota;
import modulo.veterinarios.GestionVeterinarios;
import modulo.mascotas.ValidarMascota;
import modulo.citas.ValidarCita;
import persistencia.RepositorioDatos;
import java.io.IOException;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;


public class Ventana extends JFrame {
    private JPanel Ventana;
    private JTabbedPane tabbedPane1;
    private JTextField txtDueno;
    private JTextField txtNombre;
    private JTextField txtEspecie;
    private JTextField txtEdad;
    private JTextField txtPeso;
    private JTextArea txtListarMascotas;
    private JButton btnAgregar;
    private JButton btnActualizar;
    private JButton btnBuscar;
    private JButton btnEliminar;
    private JTextField TxtFecha;
    private JTextField txtMotivo;
    private JButton btnAgendar;
    private JButton btnCancelar;
    private JButton btnReprogramar;
    private JButton btnAtender;
    private JTextArea txtListarCitas;
    private JTextField txtMascota;
    private JTextField txtPrioridad;
    private JComboBox cmboEspecie;
    private JComboBox cmboPrioridad;
    private JTextField txtId;
    private JTextField txtVeterinario;
    private JComboBox cmboEspecialidad;
    private JButton btnAgregarVeterinario;
    private JButton btnEliminarVet;
    private JTextArea txtAreaListarVet;
    private JButton btnBuscarVeterinario;
    private JTextField txtHora;
    private JTextField txtCedula;
    private JTextField txtNombreCliente;
    private JTextField txtTelefono;
    private JTextArea txtListarClientes;
    private JButton btnActualizarVeterinario;
    private JButton btnAgregarCliente;
    private JButton btnEliminarCliente;
    private JButton btnBuscarCliente;
    private JButton btnActualizarCliente;
    private JTextField txtIdMascota;
    private JTextField txtRaza;
    private JTextField txtVeterinarioCita;
    private JComboBox cmboEspecialidadCita;
    private JButton btnGuardarClientes;
    private JButton btnCargarClientes;
    private JButton btnGuardarMascotas;
    private JButton btnCargarMascotas;
    private JButton button1;
    private JButton btnGuardarCitas;
    private JButton btnCargarCitas;
    private JButton btnVerDisponibilidad;
    private JButton button2;
    private JButton btnGuardarCitaS;
    private JButton btnCargarCitaS;
    private JButton btnGuardarVet;
    private JButton btnCargarVet;
    private GestionMascota sistemaMascotas;
    private GestionVeterinarios sistemaVeterinarios;
    private GestionClientes sistemaClientes;
    private GestionCitas sistemaCitas;
    private int proximoId = 16;
    private int proximoIdVet = 16;
    private int proximoIdCita = 1;

    public Ventana() {
        sistemaMascotas = new GestionMascota();
        sistemaVeterinarios = new GestionVeterinarios();
        txtAreaListarVet.setText(sistemaVeterinarios.listarTodos());
        sistemaClientes = new GestionClientes();
        txtListarClientes.setText(sistemaClientes.listarTodos());
        sistemaCitas = new GestionCitas();
        txtListarCitas.setText(sistemaCitas.listarTodos());
        txtListarMascotas.setText(sistemaMascotas.listarOrdenadasPorArbol());
        btnAgregar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String idStr = txtIdMascota.getText().trim();
                    String nombre = txtNombre.getText().trim();
                    String especie = cmboEspecie.getSelectedItem().toString();
                    String edadStr = txtEdad.getText().trim();
                    String pesoStr = txtPeso.getText().trim();
                    String duenoStr = txtDueno.getText().trim();
                    String raza = txtRaza.getText().trim();

                    if (idStr.isEmpty() || nombre.isEmpty() || duenoStr.isEmpty()
                            || edadStr.isEmpty() || pesoStr.isEmpty()) {
                        JOptionPane.showMessageDialog(null,
                                "Todos los campos son obligatorios.",
                                "Campos vacíos", JOptionPane.WARNING_MESSAGE);
                        return;
                    }

                    if (raza.isEmpty()) {
                        raza = "S/R";
                    }

                    int id = Integer.parseInt(idStr);

                    if (sistemaMascotas.buscarMascotaPorId(id) != null) {
                        JOptionPane.showMessageDialog(null,
                                "Ya existe una mascota con el ID: " + id,
                                "ID duplicado", JOptionPane.WARNING_MESSAGE);
                        return;
                    }

                    int indiceCliente = sistemaClientes.buscarNombreSecuencial(duenoStr);
                    if (indiceCliente == -1) {
                        JOptionPane.showMessageDialog(null,
                                "El cliente \"" + duenoStr + "\" no está registrado.\n"
                                        + "Primero debes agregarlo en la pestaña de Clientes.",
                                "Cliente no encontrado", JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                    Cliente clienteExistente = sistemaClientes.getCliente(indiceCliente);

                    int edad = Integer.parseInt(edadStr);
                    double peso = Double.parseDouble(pesoStr);

                    if (!ValidarMascota.edadValida(edad)) {
                        JOptionPane.showMessageDialog(null,
                                "La edad debe estar entre 0 y 40 años.",
                                "Edad inválida", JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                    if (!ValidarMascota.pesoValido(peso)) {
                        JOptionPane.showMessageDialog(null,
                                "El peso debe ser mayor a 0.",
                                "Peso inválido", JOptionPane.WARNING_MESSAGE);
                        return;
                    }

                    Mascota nuevaMascota = new Mascota(id, nombre, especie, edad, peso, raza, clienteExistente);
                    sistemaMascotas.agregarMascota(nuevaMascota);

                    JOptionPane.showMessageDialog(null, "¡Mascota agregada con éxito!");
                    limpiarCampos();
                    txtListarMascotas.setText(sistemaMascotas.listarOrdenadasPorArbol());

                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null,
                            "ID, Edad y Peso deben ser números válidos.",
                            "Error de datos", JOptionPane.ERROR_MESSAGE);
                }
            }
        });


        btnBuscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nombreBuscar = txtNombre.getText().trim();
                if (nombreBuscar.isEmpty()) {
                    JOptionPane.showMessageDialog(null,
                            "Escribe el nombre de la mascota para buscarla.",
                            "Campo vacío", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                int indice = sistemaMascotas.buscarNombreSecuencial(nombreBuscar);

                if (indice != -1) {
                    Mascota encontrada = sistemaMascotas.getMascota(indice);


                    txtIdMascota.setText(String.valueOf(encontrada.getId()));
                    txtNombre.setText(encontrada.getNombre());
                    txtEdad.setText(String.valueOf(encontrada.getEdad()));
                    txtPeso.setText(String.valueOf(encontrada.getPeso()));
                    txtDueno.setText(encontrada.getCliente().getNombre());
                    txtRaza.setText(encontrada.getRaza());

                    String especie = encontrada.getEspecie();
                    boolean especieEncontrada = false;
                    for (int i = 0; i < cmboEspecie.getItemCount(); i++) {
                        if (cmboEspecie.getItemAt(i).toString().equalsIgnoreCase(especie)) {
                            cmboEspecie.setSelectedIndex(i);
                            especieEncontrada = true;
                            break;
                        }
                    }
                    if (!especieEncontrada) {
                        cmboEspecie.addItem(especie);
                        cmboEspecie.setSelectedItem(especie);
                    }

                    JOptionPane.showMessageDialog(null,
                            "Mascota encontrada. Los campos han sido completados.",
                            "Encontrada", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null,
                            "No se encontró ninguna mascota con el nombre: " + nombreBuscar,
                            "No encontrada", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        btnActualizar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String idStr = txtIdMascota.getText().trim();
                String nombre = txtNombre.getText().trim();
                String especie = cmboEspecie.getSelectedItem().toString();
                String edadStr = txtEdad.getText().trim();
                String pesoStr = txtPeso.getText().trim();
                String duenoStr = txtDueno.getText().trim();
                String raza = txtRaza.getText().trim();

                if (idStr.isEmpty() || nombre.isEmpty() || edadStr.isEmpty()
                        || pesoStr.isEmpty() || duenoStr.isEmpty()) {
                    JOptionPane.showMessageDialog(null,
                            "Todos los campos deben estar llenos para actualizar.\n"
                                    + "Usa Buscar primero para cargar los datos de la mascota.",
                            "Campos vacíos", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                try {
                    int id = Integer.parseInt(idStr);

                    Mascota original = sistemaMascotas.buscarMascotaPorId(id);
                    if (original == null) {
                        JOptionPane.showMessageDialog(null,
                                "No se encontró una mascota con el ID: " + id + "\n"
                                        + "Usa el botón Buscar primero.",
                                "No encontrada", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    int indiceCliente = sistemaClientes.buscarNombreSecuencial(duenoStr);
                    if (indiceCliente == -1) {
                        JOptionPane.showMessageDialog(null,
                                "El cliente \"" + duenoStr + "\" no está registrado.\n"
                                        + "Primero debes agregarlo en la pestaña de Clientes.",
                                "Cliente no encontrado", JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                    Cliente clienteExistente = sistemaClientes.getCliente(indiceCliente);

                    int edad = Integer.parseInt(edadStr);
                    double peso = Double.parseDouble(pesoStr);
                    if (raza.isEmpty()) {
                        raza = original.getRaza();
                    }

                    String resumen = "¿Confirmas la actualización de la mascota?\n\n" +
                            "ID: " + id + "\n" +
                            "Nombre: " + nombre + "\n" +
                            "Especie: " + especie + "\n" +
                            "Edad: " + edad + "\n" +
                            "Peso: " + peso + "\n" +
                            "Raza: " + raza + "\n" +
                            "Dueño: " + duenoStr;

                    int confirmacion = JOptionPane.showConfirmDialog(null,
                            resumen, "Confirmar actualización",
                            JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

                    if (confirmacion == JOptionPane.YES_OPTION) {
                        boolean exito = sistemaMascotas.actualizarDatos(id, nombre, especie, edad, peso, raza, clienteExistente);

                        if (exito) {
                            JOptionPane.showMessageDialog(null, "Mascota actualizada correctamente.");
                            limpiarCampos();
                            txtListarMascotas.setText(sistemaMascotas.listarOrdenadasPorArbol());
                        } else {
                            JOptionPane.showMessageDialog(null,
                                    "No se pudo actualizar la mascota.",
                                    "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }

                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null,
                            "ID, Edad y Peso deben ser números válidos.",
                            "Error de datos", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        btnEliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String idStr = JOptionPane.showInputDialog(
                        "Ingresa el ID de la mascota que vas a eliminar:");
                if (idStr != null && !idStr.trim().isEmpty()) {
                    try {
                        int id = Integer.parseInt(idStr.trim());
                        boolean eliminado = sistemaMascotas.eliminarMascota(id);
                        if (eliminado) {
                            JOptionPane.showMessageDialog(null,
                                    "Mascota eliminada correctamente.");
                            limpiarCampos();
                            txtListarMascotas.setText(sistemaMascotas.listarOrdenadasPorArbol());
                        } else {
                            JOptionPane.showMessageDialog(null,
                                    "El ID no existe.", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null, "ID inválido.");
                    }
                }
            }
        });


        btnAgendar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String mascotaTexto = txtMascota.getText().trim();
                String fecha = TxtFecha.getText().trim();
                String hora = txtHora.getText().trim();
                String motivo = txtMotivo.getText().trim();
                String prioridad = cmboPrioridad.getSelectedItem().toString();
                String nombreVet = txtVeterinarioCita.getText().trim();
                String especialidadRequerida = cmboEspecialidadCita.getSelectedItem().toString();

                if (!ValidarCita.camposBasicosValidos(mascotaTexto, fecha, hora, motivo, prioridad)) {
                    JOptionPane.showMessageDialog(null,
                            "Por favor, completa todos los campos de la cita, incluyendo el veterinario.",
                            "Campos vacíos", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                if (!ValidarCita.prioridadValida(prioridad)) {
                    JOptionPane.showMessageDialog(null,
                            "La prioridad seleccionada no es válida.",
                            "Prioridad inválida", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                int indiceMascota = sistemaMascotas.buscarNombreSecuencial(mascotaTexto);
                if (indiceMascota == -1) {
                    JOptionPane.showMessageDialog(null,
                            "No se encontró ninguna mascota con el nombre: " + mascotaTexto,
                            "Mascota no encontrada", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                Mascota mascotaAsignada = sistemaMascotas.getMascota(indiceMascota);

                int indiceVet = sistemaVeterinarios.buscarNombreSecuencial(nombreVet);
                if (indiceVet == -1) {
                    JOptionPane.showMessageDialog(null,
                            "No se encontró ningún veterinario con el nombre: " + nombreVet,
                            "Veterinario no encontrado", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                Veterinario vetAsignado = sistemaVeterinarios.getVeterinario(indiceVet);

                if (!vetAsignado.getEspecialidad().equalsIgnoreCase(especialidadRequerida)) {
                    JOptionPane.showMessageDialog(null,
                            "El veterinario " + vetAsignado.getNombre() + " no tiene la especialidad: " + especialidadRequerida,
                            "Especialidad no compatible", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                boolean disponible = sistemaVeterinarios.estaDisponible(vetAsignado.getId(), especialidadRequerida, fecha, hora);
                if (!disponible) {
                    int respuesta = JOptionPane.showConfirmDialog(null,
                            "El veterinario " + vetAsignado.getNombre() + " ya tiene una cita en esa fecha y hora.\n" +
                                    "¿Deseas agendarla de todas formas como una excepción (urgencia, caso especial, etc.)?",
                            "Horario ocupado", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                    if (respuesta != JOptionPane.YES_OPTION) {
                        return;
                    }
                    motivo = motivo + " [EXCEPCIÓN: horario compartido]";
                }

                String codigo = "CT" + proximoIdCita;
                Cita nuevaCita = new Cita(codigo, mascotaAsignada, fecha, hora,
                        motivo, prioridad, "Pendiente", vetAsignado);

                boolean agregada = sistemaCitas.agregarCita(nuevaCita);
                if (agregada) {
                    sistemaVeterinarios.ocuparHorario(vetAsignado.getId(), especialidadRequerida, fecha, hora);
                    proximoIdCita++;
                    txtListarCitas.setText(sistemaCitas.listarTodos());
                    JOptionPane.showMessageDialog(null, "¡Cita agendada con éxito!");
                    limpiarCamposCitas();
                } else {
                    JOptionPane.showMessageDialog(null,
                            "No se pudo registrar la cita. Verifica los datos.",
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        btnAtender.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Cita atendida = sistemaCitas.atenderSiguiente();
                if (atendida == null) {
                    JOptionPane.showMessageDialog(null,
                            "No hay citas programadas en la lista de espera.",
                            "Cola vacía", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
                txtListarCitas.setText(sistemaCitas.listarTodos());
                JOptionPane.showMessageDialog(null,
                        "Cita atendida:\n" + atendida.toString());
                limpiarCamposCitas();
            }
        });

        btnCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String codigo = JOptionPane.showInputDialog("Ingresa el código de la cita a cancelar:");
                if (codigo == null || codigo.trim().isEmpty()) return;

                boolean cancelada = sistemaCitas.cancelarCita(codigo.trim());
                if (cancelada) {
                    txtListarCitas.setText(sistemaCitas.listarTodos());
                    JOptionPane.showMessageDialog(null, "Cita cancelada correctamente.");
                } else {
                    JOptionPane.showMessageDialog(null,
                            "No se encontró una cita con ese código.",
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
                limpiarCamposCitas();
            }
        });

        btnReprogramar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String codigo = JOptionPane.showInputDialog("Ingresa el código de la cita a reprogramar:");
                if (codigo == null || codigo.trim().isEmpty()) return;

                String nuevaFecha = JOptionPane.showInputDialog("Ingresa la nueva fecha:");
                String nuevaHora = JOptionPane.showInputDialog("Ingresa la nueva hora:");

                if (nuevaFecha != null && !nuevaFecha.trim().isEmpty()
                        && nuevaHora != null && !nuevaHora.trim().isEmpty()) {
                    boolean reprogramada = sistemaCitas.reprogramarCita(codigo.trim(), nuevaFecha.trim(), nuevaHora.trim());
                    if (reprogramada) {
                        txtListarCitas.setText(sistemaCitas.listarTodos());
                        JOptionPane.showMessageDialog(null, "Cita reprogramada correctamente.");
                    } else {
                        JOptionPane.showMessageDialog(null,
                                "No se encontró una cita con ese código.",
                                "Error", JOptionPane.ERROR_MESSAGE);
                    }
                    limpiarCamposCitas();
                }
            }
        });
        btnAgregarVeterinario.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String idStr = txtId.getText().trim();
                    String nombre = txtVeterinario.getText().trim();
                    String especialidad = cmboEspecialidad.getSelectedItem().toString();

                    if (idStr.isEmpty() || nombre.isEmpty()) {
                        JOptionPane.showMessageDialog(null,
                                "El ID y el nombre del veterinario son obligatorios.",
                                "Campos vacíos", JOptionPane.WARNING_MESSAGE);
                        return;
                    }

                    int id = Integer.parseInt(idStr);

                    if (sistemaVeterinarios.buscarId(id) != -1) {
                        JOptionPane.showMessageDialog(null,
                                "Ya existe un veterinario con el ID: " + id,
                                "ID duplicado", JOptionPane.WARNING_MESSAGE);
                        return;
                    }

                    Veterinario nuevoVet = new Veterinario(id, nombre, especialidad);
                    sistemaVeterinarios.agregarVeterinario(nuevoVet);
                    sistemaVeterinarios.agregarDisponibilidad(id, especialidad, "GENERAL", "GENERAL");   // <-- NUEVA LÍNEA, AQUÍ

                    JOptionPane.showMessageDialog(null, "¡Veterinario agregado con éxito!");
                    limpiarCamposVeterinario();
                    txtAreaListarVet.setText(sistemaVeterinarios.listarTodos());

                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null,
                            "El ID debe ser un número válido.",
                            "Error de datos", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        btnEliminarVet.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String idStr = txtId.getText().trim();

                if (idStr.isEmpty()) {
                    JOptionPane.showMessageDialog(null,
                            "Ingresa el ID del veterinario en el campo ID.",
                            "Campo vacío", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                try {
                    int id = Integer.parseInt(idStr);
                    boolean eliminado = sistemaVeterinarios.eliminarVeterinario(id);

                    if (eliminado) {
                        JOptionPane.showMessageDialog(null,
                                "Veterinario eliminado correctamente.");
                        limpiarCamposVeterinario();
                        txtAreaListarVet.setText(sistemaVeterinarios.listarTodos());
                    } else {
                        JOptionPane.showMessageDialog(null,
                                "El ID no existe.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "ID inválido.");
                }
            }
        });
        btnBuscarVeterinario.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nombreBuscar = txtVeterinario.getText().trim();

                if (nombreBuscar.isEmpty()) {
                    JOptionPane.showMessageDialog(null,
                            "Escribe el nombre del veterinario para buscarlo.",
                            "Campo vacío", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                int indice = sistemaVeterinarios.buscarNombreSecuencial(nombreBuscar);

                if (indice != -1) {
                    Veterinario encontrado = sistemaVeterinarios.getVeterinario(indice);

                    txtVeterinario.setText(encontrado.getNombre());

                    // Seleccionar la especialidad en el JComboBox
                    String especialidad = encontrado.getEspecialidad();
                    boolean especialidadEncontrada = false;
                    for (int i = 0; i < cmboEspecialidad.getItemCount(); i++) {
                        if (cmboEspecialidad.getItemAt(i).toString().equalsIgnoreCase(especialidad)) {
                            cmboEspecialidad.setSelectedIndex(i);
                            especialidadEncontrada = true;
                            break;
                        }
                    }
                    if (!especialidadEncontrada) {
                        cmboEspecialidad.addItem(especialidad);
                        cmboEspecialidad.setSelectedItem(especialidad);
                    }

                    JOptionPane.showMessageDialog(null,
                            "Veterinario encontrado. Los campos han sido completados.",
                            "Encontrado", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null,
                            "No se encontró ningún veterinario con el nombre: " + nombreBuscar,
                            "No encontrado", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        btnActualizarVeterinario.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nombre = txtVeterinario.getText().trim();
                String especialidad = cmboEspecialidad.getSelectedItem().toString();

                if (nombre.isEmpty()) {
                    JOptionPane.showMessageDialog(null,
                            "El nombre debe estar lleno para actualizar.\n"
                                    + "Usa Buscar primero para cargar los datos del veterinario.",
                            "Campo vacío", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                int indice = sistemaVeterinarios.buscarNombreSecuencial(nombre);
                if (indice == -1) {
                    JOptionPane.showMessageDialog(null,
                            "No se encontró un veterinario con el nombre: " + nombre + "\n"
                                    + "Usa el botón Buscar primero.",
                            "No encontrado", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Veterinario original = sistemaVeterinarios.getVeterinario(indice);
                int id = original.getId();

                String resumen = "¿Confirmas la actualización del veterinario?\n\n" +
                        "ID: " + id + "\n" +
                        "Nombre: " + nombre + "\n" +
                        "Especialidad: " + especialidad;

                int confirmacion = JOptionPane.showConfirmDialog(null,
                        resumen, "Confirmar actualización",
                        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

                if (confirmacion == JOptionPane.YES_OPTION) {
                    boolean exito = sistemaVeterinarios.actualizarDatos(id, nombre, especialidad);

                    if (exito) {
                        JOptionPane.showMessageDialog(null,
                                "Veterinario actualizado correctamente.");
                        limpiarCamposVeterinario();
                        txtAreaListarVet.setText(sistemaVeterinarios.listarTodos());
                    } else {
                        JOptionPane.showMessageDialog(null,
                                "No se pudo actualizar el veterinario.",
                                "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
        btnAgregarCliente.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nombre = txtNombreCliente.getText().trim();
                String cedula = txtCedula.getText().trim();
                String telefono = txtTelefono.getText().trim();

                if (nombre.isEmpty() || cedula.isEmpty() || telefono.isEmpty()) {
                    JOptionPane.showMessageDialog(null,
                            "Todos los campos son obligatorios.",
                            "Campos vacíos", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                if (cedula.length() != 10) {
                    JOptionPane.showMessageDialog(null,
                            "La cédula debe tener exactamente 10 dígitos.",
                            "Cédula inválida", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                if (telefono.length() != 10) {
                    JOptionPane.showMessageDialog(null,
                            "El teléfono debe tener exactamente 10 dígitos.",
                            "Teléfono inválido", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                if (sistemaClientes.buscarClientePorCedula(cedula) != null) {
                    JOptionPane.showMessageDialog(null,
                            "Ya existe un cliente con esa cédula.",
                            "Cliente duplicado", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                String nuevoId = "C" + String.format("%03d", proximoId);
                Cliente nuevoCliente = new Cliente(nuevoId, nombre, cedula, telefono);
                sistemaClientes.agregarCliente(nuevoCliente);
                proximoId++;

                JOptionPane.showMessageDialog(null, "¡Cliente agregado con éxito! ID asignado: " + nuevoId);
                limpiarCamposCliente();
                txtListarClientes.setText(sistemaClientes.listarTodos());
            }
        });
        btnActualizarCliente.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nombre = txtNombreCliente.getText().trim();
                String cedula = txtCedula.getText().trim();
                String telefono = txtTelefono.getText().trim();

                if (nombre.isEmpty() || cedula.isEmpty() || telefono.isEmpty()) {
                    JOptionPane.showMessageDialog(null,
                            "Todos los campos deben estar llenos para actualizar.\n"
                                    + "Usa Buscar primero para cargar los datos del cliente.",
                            "Campos vacíos", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                if (cedula.length() != 10) {
                    JOptionPane.showMessageDialog(null,
                            "La cédula debe tener exactamente 10 dígitos.",
                            "Cédula inválida", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                if (telefono.length() != 10) {
                    JOptionPane.showMessageDialog(null,
                            "El teléfono debe tener exactamente 10 dígitos.",
                            "Teléfono inválido", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                int indice = sistemaClientes.buscarNombreSecuencial(nombre);
                if (indice == -1) {
                    JOptionPane.showMessageDialog(null,
                            "No se encontró un cliente con el nombre: " + nombre + "\n"
                                    + "Usa el botón Buscar primero.",
                            "No encontrado", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Cliente original = sistemaClientes.getCliente(indice);
                String idCliente = original.getIdCliente();

                String resumen = "¿Confirmas la actualización del cliente?\n\n" +
                        "ID: " + idCliente + "\n" +
                        "Nombre: " + nombre + "\n" +
                        "Cédula: " + cedula + "\n" +
                        "Teléfono: " + telefono;

                int confirmacion = JOptionPane.showConfirmDialog(null,
                        resumen, "Confirmar actualización",
                        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

                if (confirmacion == JOptionPane.YES_OPTION) {
                    boolean exito = sistemaClientes.actualizarDatos(idCliente, nombre, cedula, telefono);

                    if (exito) {
                        JOptionPane.showMessageDialog(null,
                                "Cliente actualizado correctamente.");
                        limpiarCamposCliente();
                        txtListarClientes.setText(sistemaClientes.listarTodos());
                    } else {
                        JOptionPane.showMessageDialog(null,
                                "No se pudo actualizar el cliente.",
                                "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
        btnBuscarCliente.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nombreBuscar = txtNombreCliente.getText().trim();

                if (nombreBuscar.isEmpty()) {
                    JOptionPane.showMessageDialog(null,
                            "Escribe el nombre del cliente para buscarlo.",
                            "Campo vacío", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                int indice = sistemaClientes.buscarNombreSecuencial(nombreBuscar);

                if (indice != -1) {
                    Cliente encontrado = sistemaClientes.getCliente(indice);

                    txtNombreCliente.setText(encontrado.getNombre());
                    txtCedula.setText(encontrado.getCedula());
                    txtTelefono.setText(encontrado.getTelefono());

                    JOptionPane.showMessageDialog(null,
                            "Cliente encontrado. Los campos han sido completados.",
                            "Encontrado", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null,
                            "No se encontró ningún cliente con el nombre: " + nombreBuscar,
                            "No encontrado", JOptionPane.WARNING_MESSAGE);
                }
            }
        });
        btnEliminarCliente.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String idCliente = JOptionPane.showInputDialog("Ingresa el ID del cliente a eliminar (ej: C001):");

                if (idCliente == null || idCliente.trim().isEmpty()) return;

                idCliente = idCliente.trim();

                int confirmacion = JOptionPane.showConfirmDialog(null,
                        "¿Estás segura de que deseas eliminar al cliente con ID: " + idCliente + "?",
                        "Confirmar eliminación",
                        JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

                if (confirmacion == JOptionPane.YES_OPTION) {
                    boolean eliminado = sistemaClientes.eliminarCliente(idCliente);

                    if (eliminado) {
                        JOptionPane.showMessageDialog(null, "Cliente eliminado correctamente.");
                        limpiarCamposCliente();
                        txtListarClientes.setText(sistemaClientes.listarTodos());
                    } else {
                        JOptionPane.showMessageDialog(null,
                                "No se encontró un cliente con ese ID.",
                                "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        btnGuardarClientes.addActionListener(e -> {
            try {
                RepositorioDatos.guardarDatos("clientes.dat", sistemaClientes.getClientes());
                JOptionPane.showMessageDialog(null, "Clientes guardados correctamente.");
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "Error al guardar: " + ex.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnCargarClientes.addActionListener(e -> {
            try {
                Object datos = RepositorioDatos.cargarDatos("clientes.dat");
                if (datos instanceof ArrayList) {
                    sistemaClientes.cargarListaExterna((ArrayList<Cliente>) datos);
                    txtListarClientes.setText(sistemaClientes.listarTodos());
                    JOptionPane.showMessageDialog(null, "Clientes cargados correctamente.");
                }
            } catch (IOException | ClassNotFoundException ex) {
                JOptionPane.showMessageDialog(null, "No se encontró un archivo guardado previamente.",
                        "Aviso", JOptionPane.WARNING_MESSAGE);
            }
        });

        btnGuardarCitaS.addActionListener(e -> {
            try {
                RepositorioDatos.guardarDatos("citas.dat", sistemaCitas.getCitas());
                JOptionPane.showMessageDialog(null, "Citas guardadas correctamente.");
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "Error al guardar: " + ex.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnCargarCitaS.addActionListener(e -> {
            try {
                Object datos = RepositorioDatos.cargarDatos("citas.dat");
                if (datos instanceof ArrayList) {
                    sistemaCitas.cargarListaExterna((ArrayList<Cita>) datos);
                    txtListarCitas.setText(sistemaCitas.listarTodos());
                    JOptionPane.showMessageDialog(null, "Citas cargadas correctamente.");
                }
            } catch (IOException | ClassNotFoundException ex) {
                JOptionPane.showMessageDialog(null, "No se encontró un archivo guardado previamente.",
                        "Aviso", JOptionPane.WARNING_MESSAGE);
            }
        });

        btnVerDisponibilidad.addActionListener(e -> {
            String nombreVet = txtVeterinarioCita.getText().trim();
            String fecha = TxtFecha.getText().trim();
            if (nombreVet.isEmpty() || fecha.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Escribe el veterinario y la fecha primero.");
                return;
            }
            int indiceVetDisp = sistemaVeterinarios.buscarNombreSecuencial(nombreVet);
            if (indiceVetDisp == -1) {
                JOptionPane.showMessageDialog(null, "Veterinario no encontrado.");
                return;
            }
            Veterinario vDisp = sistemaVeterinarios.getVeterinario(indiceVetDisp);
            String horarios = sistemaVeterinarios.consultarHorariosOcupados(vDisp.getId(), fecha);
            JOptionPane.showMessageDialog(null, horarios, "Disponibilidad de " + vDisp.getNombre(), JOptionPane.INFORMATION_MESSAGE);
        });


        /** Mascotas*/
        btnGuardarMascotas.addActionListener(e -> {
            try {
                RepositorioDatos.guardarDatos("mascotas.dat", sistemaMascotas.getMascotas());
                JOptionPane.showMessageDialog(null, "Mascotas guardadas correctamente.");
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "Error al guardar: " + ex.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnCargarMascotas.addActionListener(e -> {
            try {
                Object datos = RepositorioDatos.cargarDatos("mascotas.dat");
                if (datos instanceof ArrayList) {
                    sistemaMascotas.cargarListaExterna((ArrayList<Mascota>) datos);
                    txtListarMascotas.setText(sistemaMascotas.listarOrdenadasPorArbol());
                    JOptionPane.showMessageDialog(null, "Mascotas cargadas correctamente.");
                }
            } catch (IOException | ClassNotFoundException ex) {
                JOptionPane.showMessageDialog(null, "No se encontró un archivo guardado previamente.",
                        "Aviso", JOptionPane.WARNING_MESSAGE);
            }
        });

        /** Veterinario*/
        btnGuardarVet.addActionListener(e -> {
            try {
                RepositorioDatos.guardarDatos("veterinarios.dat", sistemaVeterinarios.getVeterinarios());
                JOptionPane.showMessageDialog(null, "Veterinarios guardados correctamente.");
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "Error al guardar: " + ex.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnCargarVet.addActionListener(e -> {
            try {
                Object datos = RepositorioDatos.cargarDatos("veterinarios.dat");
                if (datos instanceof ArrayList) {
                    sistemaVeterinarios.cargarListaExterna((ArrayList<Veterinario>) datos);
                    txtAreaListarVet.setText(sistemaVeterinarios.listarTodos());
                    JOptionPane.showMessageDialog(null, "Veterinarios cargados correctamente.");
                }
            } catch (IOException | ClassNotFoundException ex) {
                JOptionPane.showMessageDialog(null, "No se encontró un archivo guardado previamente.",
                        "Aviso", JOptionPane.WARNING_MESSAGE);
            }
        });
    }

    private void limpiarCamposCliente() {
        txtNombreCliente.setText("");
        txtCedula.setText("");
        txtTelefono.setText("");
    }
    private void limpiarCampos() {
        txtIdMascota.setText("");
        txtNombre.setText("");
        txtEdad.setText("");
        txtPeso.setText("");
        txtDueno.setText("");
        txtRaza.setText("");
        cmboEspecie.setSelectedIndex(0);
    }

    private void limpiarCamposCitas() {
        txtMascota.setText("");
        TxtFecha.setText("");
        txtHora.setText("");
        txtMotivo.setText("");
        txtVeterinarioCita.setText("");
        cmboEspecialidadCita.setSelectedIndex(0);
        cmboPrioridad.setSelectedIndex(0);
    }
    private void limpiarCamposVeterinario() {
        txtVeterinario.setText("");
        cmboEspecialidad.setSelectedIndex(0);
        txtId.setText("");
    }
    public static void main(String[] args) {
        JFrame frame = new JFrame("Ventana");
        frame.setContentPane(new Ventana().Ventana);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
