
package com.mycompany.parkingapp;

import java.awt.event.ActionEvent;

import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Lenovo Ryzen5
 */
public class Main {

    private static final Vehiculo[] motosGuardadas = new Vehiculo[100];
    private static final Vehiculo[] carrosGuardados = new Vehiculo[100];

    private static final Vehiculo[] motosPagadas = new Vehiculo[100];
    private static final Vehiculo[] carrosPagados = new Vehiculo[100];

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Main::crearInterfaz);

    }

    public static void crearInterfaz() {
        JFrame ventanaPrincipal = new JFrame("ParkingApp");
        ventanaPrincipal.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        ventanaPrincipal.setSize(200, 200);
        ventanaPrincipal.setLocationRelativeTo(null);

        JPanel panelPrincipal = new JPanel();
        JLabel etiquetaTitulo = new JLabel("Bienvenido al Parqueadero");

        JButton botonHora = new JButton("Ingresar Vehiculo");
        JButton botonMensualidad = new JButton("Mensualidades");
        JButton botonVerMotos = new JButton("Ver Motos");
        JButton botonVerCarros = new JButton("Ver Carros");

        panelPrincipal.add(etiquetaTitulo);
        panelPrincipal.add(botonHora);
        panelPrincipal.add(botonMensualidad);
        ventanaPrincipal.add(panelPrincipal);
        ventanaPrincipal.setVisible(true);
        botonVerMotos.addActionListener((ActionEvent e) -> mostrarTablaMotos());
        botonVerCarros.addActionListener((ActionEvent e) -> mostrarTablaCarros());
        panelPrincipal.add(botonVerMotos);
        panelPrincipal.add(botonVerCarros);

        ventanaPrincipal.setVisible(true);

        botonHora.addActionListener((var e) -> {
            String[] tiposPosibles = {"Carro", "Moto"};
            String tipoSeleccionado = (String) JOptionPane.showInputDialog(
                    ventanaPrincipal,
                    "Selecciona el tipo de vehículo:",
                    "Elegir Tipo de Vehículo",
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    tiposPosibles,
                    tiposPosibles[0]);
            
            if (tipoSeleccionado != null) {
                String placa = JOptionPane.showInputDialog(
                        ventanaPrincipal,
                        "Ingresa la matrícula del vehículo:",
                        "Ingresar Matrícula",
                        JOptionPane.PLAIN_MESSAGE);
                
                if (placa != null && !placa.isEmpty()) {
                    
                    if ("Moto".equals(tipoSeleccionado)) {
                        int indiceMotos = obtenerIndiceDisponibleMotos();
                        
                        if (indiceMotos != -1) {
                            
                            LocalDateTime fechaYHoraLocalDateTime = LocalDateTime.now();
                            DateTimeFormatter formatoLocalDateTime = DateTimeFormatter.ofPattern("HH:mm:ss (dd-MM-yyyy)");
                            String horaEntrada = fechaYHoraLocalDateTime.format(formatoLocalDateTime);
                            
                            Vehiculo moto = new Vehiculo(placa, horaEntrada, null);
                            motosGuardadas[indiceMotos] = moto;
                            JOptionPane.showMessageDialog(ventanaPrincipal,
                                    """
                                    Moto ingresada:
                                    
                                    Placa:\s""" + placa + "\nHora de Entrada: " + horaEntrada,
                                    "Ingreso Exitoso",
                                    JOptionPane.INFORMATION_MESSAGE);
                            System.out.println("Información del array motos:\n");
                            for (int i = 0; i <= indiceMotos; i++) {
                                if (motosGuardadas[i] != null) {
                                    System.out.println("\nplaza[" + i + "]: " + motosGuardadas[i]);
                                }
                            }
                        } else {
                            JOptionPane.showMessageDialog(
                                    ventanaPrincipal,
                                    "No hay suficiente espacio para almacenar más motocicletas.",
                                    "Error plazas de motos llenas",
                                    JOptionPane.ERROR_MESSAGE);
                        }
                    }
                    
                    if ("Carro".equals(tipoSeleccionado)) {
                        int indiceCarros = obtenerIndiceDisponibleCarros();
                        if (indiceCarros != -1) {
                            
                            LocalDateTime fechaYHoraLocalDateTime = LocalDateTime.now();
                            DateTimeFormatter formatoLocalDateTime = DateTimeFormatter.ofPattern("HH:mm:ss (dd-MM-yyyy)");
                            String horaEntrada = fechaYHoraLocalDateTime.format(formatoLocalDateTime);
                            Vehiculo carro = new Vehiculo(placa, horaEntrada, null);
                            carrosGuardados[indiceCarros] = carro;
                            JOptionPane.showMessageDialog(ventanaPrincipal,
                                    """
                                    Carro ingresado:
                                    
                                    Placa:\s""" + placa + "\nHora de Entrada: " + horaEntrada,
                                    "Ingreso Exitoso",
                                    JOptionPane.INFORMATION_MESSAGE);
                            System.out.println("Información del array carros:");
                            for (int i = 0; i <= indiceCarros; i++) {
                                if (carrosGuardados[i] != null) {
                                    System.out.println("\nplaza[" + i + "]: " + carrosGuardados[i]);
                                }
                            }
                        } else {
                            JOptionPane.showMessageDialog(
                                    ventanaPrincipal,
                                    "No hay suficiente espacio para almacenar más carros.",
                                    "Error",
                                    JOptionPane.ERROR_MESSAGE);
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(
                            ventanaPrincipal,
                            "La matrícula no puede estar vacía.",
                            "Error plazas de carros llenas",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    private static int obtenerIndiceDisponibleMotos() {
        for (int i = 0; i < motosGuardadas.length; i++) {
            if (motosGuardadas[i] == null) {
                return i;
            }
        }
        return -1;
    }

    private static int obtenerIndiceDisponibleCarros() {
        for (int i = 0; i < carrosGuardados.length; i++) {
            if (carrosGuardados[i] == null) {
                return i;
            }
        }
        return -1;
    }

    private static void mostrarTablaMotos() {
        // Datos de las motos
        Object[][] data = new Object[motosGuardadas.length][3]; // Supongo que tienes un array de objetos Moto con métodos getPlaca(), getHoraEntrada(), etc.

        for (int i = 0; i < motosGuardadas.length; i++) {
            if (motosGuardadas[i] != null) {
                data[i][0] = i; // Mostrar la posición del array en la columna "Plaza"
                data[i][1] = motosGuardadas[i].getPlaca();
                data[i][2] = motosGuardadas[i].getHoraEntrada();
            }
        }

        // Nombres de las columnas
        String[] columnNames = {"Plaza", "Placa", "Hora de Entrada"};

        // Crear el modelo de la tabla
        DefaultTableModel tableModel = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Todas las celdas no son editables
            }
        };

        // Crear la tabla con el modelo
        JTable table = new JTable(tableModel);

        JButton btnEliminar = new JButton("Pagar");
        btnEliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    // Obtener la información de la fila seleccionada
                    int indexToDelete = (int) table.getValueAt(selectedRow, 0);
                    Vehiculo motoPagada = motosGuardadas[indexToDelete];

                    // Agregar la hora de salida
                    LocalDateTime horaSalida = LocalDateTime.now();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss (dd-MM-yyyy)");
                    String horaSalidaStr = horaSalida.format(formatter);
                    motoPagada.setHoraSalida(horaSalidaStr);

                    // Guardar la información en el array de carrosPagados
                    motosPagadas[indexToDelete] = motoPagada;

                    // Eliminar la fila del array original
                    motosGuardadas[indexToDelete] = null;

                    // Eliminar la fila de la tabla
                    tableModel.removeRow(selectedRow);


                    int indiceCarros = obtenerIndiceDisponibleCarros();
                    System.out.println("Información del array motosPagadas:");
                    for (int i = 0; i <= indiceCarros; i++) {
                        if (motosPagadas[i] != null) {
                            System.out.println("\nplaza[" + i + "]: " + motosPagadas[i]);
                        }
                    }

                } else {
                    JOptionPane.showMessageDialog(null, "Selecciona una fila para eliminar.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Mostrar la tabla en un JOptionPane
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(new JScrollPane(table));
        panel.add(btnEliminar);
        // Mostrar la tabla en un JOptionPane

        JOptionPane.showMessageDialog(null, panel, "Motos Guardadas", JOptionPane.INFORMATION_MESSAGE);
    }

    private static void mostrarTablaCarros() {
        // Datos de los carros
        Object[][] data = new Object[carrosGuardados.length][3]; // Supongo que tienes un array de objetos Carro con métodos getPlaca(), getHoraEntrada(), etc.

        for (int i = 0; i < carrosGuardados.length; i++) {
            if (carrosGuardados[i] != null) {
                data[i][0] = i; // Mostrar la posición del array en la columna "Plaza"
                data[i][1] = carrosGuardados[i].getPlaca();
                data[i][2] = carrosGuardados[i].getHoraEntrada();
            }
        }

        // Nombres de las columnas
        String[] columnNames = {"Plaza", "Placa", "Hora de Entrada"};

        // Crear el modelo de la tabla
        DefaultTableModel tableModel = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Todas las celdas no son editables
            }


        };



        // Crear la tabla con el modelo
        JTable table = new JTable(tableModel);

        JButton btnEliminar = new JButton("Pagar");
        btnEliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    // Obtener la información de la fila seleccionada
                    int indexToDelete = (int) table.getValueAt(selectedRow, 0);
                    Vehiculo carroPagado = carrosGuardados[indexToDelete];

                    // Agregar la hora de salida
                    LocalDateTime horaSalida = LocalDateTime.now();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss (dd-MM-yyyy)");
                    String horaSalidaStr = horaSalida.format(formatter);
                    carroPagado.setHoraSalida(horaSalidaStr);

                    // Guardar la información en el array de carrosPagados
                    carrosPagados[indexToDelete] = carroPagado;

                    // Eliminar la fila del array original
                    carrosGuardados[indexToDelete] = null;

                    // Eliminar la fila de la tabla
                    tableModel.removeRow(selectedRow);


                    int indiceCarros = obtenerIndiceDisponibleCarros();
                    System.out.println("Información del array carrosPagados:");
                    for (int i = 0; i <= indiceCarros; i++) {
                        if (carrosPagados[i] != null) {
                            System.out.println("\nplaza[" + i + "]: " + carrosPagados[i]);
                        }
                    }



                } else {
                    JOptionPane.showMessageDialog(null, "Selecciona una fila para eliminar.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Mostrar la tabla en un JOptionPane
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(new JScrollPane(table));
        panel.add(btnEliminar);
        JOptionPane.showMessageDialog(null, panel, "Carros Guardados", JOptionPane.INFORMATION_MESSAGE);
    }
}
