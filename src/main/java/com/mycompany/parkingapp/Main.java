
package com.mycompany.parkingapp;

import java.awt.*;
import java.awt.event.ActionEvent;

import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Locale;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author  Juan Pablo Avendaño Pino
 */
public class Main {

    private static final Vehiculo[] motosGuardadas = new Vehiculo[100];
    private static final Vehiculo[] carrosGuardados = new Vehiculo[100];
    private static final Vehiculo[] motosPagadas = new Vehiculo[100];
    private static final Vehiculo[] carrosPagados = new Vehiculo[100];
    private static final Mensualidad[] mensualidades = new Mensualidad[100];

    static int precioMensualidad = 50000;

    private static final String RUTA_CODIGO_QR = "C:\\Users\\Lenovo Ryzen5\\Documents\\Por hacer\\UDEA\\Logica\\Trabajos\\Proyecto\\ParkingApp\\utils/QR parking.jpg";



    public static void main(String[] args) {
        SwingUtilities.invokeLater(Main::crearInterfaz);

    }

    public static void crearInterfaz() {


        JFrame ventanaPrincipal = new JFrame("ParkingApp");
        ventanaPrincipal.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        ventanaPrincipal.setSize(200, 300);
        ventanaPrincipal.setLocationRelativeTo(null);
        JPanel panelPrincipal = new JPanel();
        JLabel etiquetaTitulo = new JLabel("Bienvenido al Parqueadero");
        JButton botonHora = new JButton("Ingresar Vehiculo");
        JButton botonMensualidad = new JButton("Ingresar Mensualidades");
        JButton botonVerMotos = new JButton("Ver Motos Guardadas");
        JButton botonVerCarros = new JButton("Ver Carros Guardados");
        JButton botonVerMensualidades = new JButton("Ver Mensualidades");

        panelPrincipal.add(etiquetaTitulo);
        panelPrincipal.add(botonHora);
        panelPrincipal.add(botonMensualidad);
        panelPrincipal.add(botonVerMotos);
        panelPrincipal.add(botonVerCarros);
        panelPrincipal.add(botonVerMensualidades);
        ventanaPrincipal.add(panelPrincipal);
        ventanaPrincipal.setVisible(true);
        ventanaPrincipal.setVisible(true);
        botonVerMotos.addActionListener((ActionEvent e) -> mostrarTablaMotosGuardadas());
        botonVerCarros.addActionListener((ActionEvent e) -> mostrarTablaCarrosGuardados());



        botonMensualidad.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String nombre = JOptionPane.showInputDialog(
                        ventanaPrincipal,
                        "Ingresa el nombre del titular de la mensualidad:",
                        "Ingresar Nombre",
                        JOptionPane.PLAIN_MESSAGE);

                if (nombre != null && !nombre.isEmpty()) {
                    String placa = JOptionPane.showInputDialog(
                            ventanaPrincipal,
                            "Ingresa la placa del vehículo:",
                            "Ingresar Placa",
                            JOptionPane.PLAIN_MESSAGE);



                   int telefono = Integer.parseInt(JOptionPane.showInputDialog(
                            ventanaPrincipal,
                            "Ingresa el telefono del vehículo:",
                            "Ingresar telefono",
                            JOptionPane.PLAIN_MESSAGE));

                    if (placa != null && !placa.isEmpty()) {


                        LocalDateTime fechaYHoraLocalDateTime = LocalDateTime.now();
                        DateTimeFormatter formatoLocalDateTime = DateTimeFormatter.ofPattern("HH:mm:ss (dd-MM-yyyy)");
                        String fechaInicio = fechaYHoraLocalDateTime.format(formatoLocalDateTime);


                        LocalDateTime fechaYHoraLocalDateTimes = LocalDateTime.now().plusDays(30);
                        DateTimeFormatter formatoLocalDateTimes = DateTimeFormatter.ofPattern("HH:mm:ss (dd-MM-yyyy)");
                        String fechaCaducidad = fechaYHoraLocalDateTimes.format(formatoLocalDateTimes);


                        Mensualidad nuevaMensualidad = new Mensualidad(nombre, placa,telefono,fechaInicio, fechaCaducidad);


                        int indice = obtenerIndiceDisponibleMensualidades();
                        if (indice != -1) {
                            int precio = precioMensualidad;
                            mostrarVentanaCodigoQR(precio);

                            mensualidades[indice] = nuevaMensualidad;
                            JOptionPane.showMessageDialog(
                                    ventanaPrincipal,
                                    "Mensualidad registrada:\nNombre: " + nombre + "\nPlaca: " + placa+"\nTelefono: "+telefono+"\nFecha de Inicio: "+fechaInicio+"\nFecha de Caducidad: "+fechaCaducidad,
                                    "Registro Exitoso",
                                    JOptionPane.INFORMATION_MESSAGE);


                            System.out.println("Información del array de mensualidades:");
                            for (int i = 0; i <= indice; i++) {
                                if (mensualidades[i] != null) {
                                    System.out.println("Índice " + i + ": " + mensualidades[i].getNombre());
                                    System.out.println("Placa: " + mensualidades[i].getPlaca());
                                    System.out.println("Fecha de Inicio: " + mensualidades[i].getFechaInicio());
                                    System.out.println("Fecha de Caducidad: " + mensualidades[i].getFechaCaducidad());
                                    System.out.println("------");
                                }
                            }
                        } else {
                            JOptionPane.showMessageDialog(
                                    ventanaPrincipal,
                                    "No hay suficiente espacio para almacenar más mensualidades.",
                                    "Error",
                                    JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(
                                ventanaPrincipal,
                                "La placa no puede estar vacía.",
                                "Error",
                                JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(
                            ventanaPrincipal,
                            "El nombre no puede estar vacío.",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }  private static int obtenerIndiceDisponibleMensualidades() {
                for (int i = 0; i < mensualidades.length; i++) {
                    if (mensualidades[i] == null) {
                        return i;
                    }
                }
                return -1;
            }
        });

        botonVerMensualidades.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarTablaMensualidades();
            }
        });



        botonHora.addActionListener((var e) -> {
            String[] tiposPosibles = {"Moto","Carro" };
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
                        int indiceMotos = obtenerPlazaDisponibleMotosGuardadas();
                        
                        if (indiceMotos != -1) {
                            
                            LocalDateTime fechaYHoraLocalDateTime = LocalDateTime.now();
                            DateTimeFormatter formatoLocalDateTime = DateTimeFormatter.ofPattern("HH:mm:ss (dd-MM-yyyy)");
                            String horaEntrada = fechaYHoraLocalDateTime.format(formatoLocalDateTime);
                            
                            Vehiculo moto = new Vehiculo(placa, horaEntrada, null,0);
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
                        int indiceCarros = obtenerPlazaDisponibleCarrosGuardados();
                        if (indiceCarros != -1) {
                            
                            LocalDateTime fechaYHoraLocalDateTime = LocalDateTime.now();
                            DateTimeFormatter formatoLocalDateTime = DateTimeFormatter.ofPattern("HH:mm:ss (dd-MM-yyyy)");
                            String horaEntrada = fechaYHoraLocalDateTime.format(formatoLocalDateTime);
                            Vehiculo carro = new Vehiculo(placa, horaEntrada, null,0);
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



    private static int obtenerPlazaDisponibleMotosGuardadas() {
        for (int i = 0; i < motosGuardadas.length; i++) {
            if (motosGuardadas[i] == null) {
                return i;
            }
        }
        return -1;
    }


    private static int obtenerPlazaDisponibleCarrosGuardados() {
        for (int i = 0; i < carrosGuardados.length; i++) {
            if (carrosGuardados[i] == null) {
                return i;
            }
        }
        return -1;
    }

    private static int obtenerIndiceMotosPagadas() {
        for (int i = 0; i < motosGuardadas.length; i++) {
            if (motosGuardadas[i] == null) {
                return i;
            }
        }
        return -1;
    }


    private static int obtenerIndiceCarrosPagados() {
        for (int i = 0; i < carrosGuardados.length; i++) {
            if (carrosGuardados[i] == null) {
                return i;
            }
        }
        return -1;
    }



    private static void mostrarTablaMotosGuardadas() {
        // Datos de las motos
        Object[][] data = new Object[motosGuardadas.length][3];

        for (int i = 0; i < motosGuardadas.length; i++) {
            if (motosGuardadas[i] != null) {
                data[i][0] = i;
                data[i][1] = motosGuardadas[i].getPlaca();
                data[i][2] = motosGuardadas[i].getHoraEntrada();
            }
        }

        String[] columnNames = {"Plaza", "Placa", "Hora de Entrada"};


        DefaultTableModel tableModel = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };


        JTable table = new JTable(tableModel);

        JButton btnPagar = new JButton("Pagar");
        btnPagar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {

                    int indexToDelete = (int) table.getValueAt(selectedRow, 0);
                    Vehiculo motoPagada = motosGuardadas[indexToDelete];
                    LocalDateTime horaEntrada = LocalDateTime.parse(motoPagada.getHoraEntrada(),
                            DateTimeFormatter.ofPattern("HH:mm:ss (dd-MM-yyyy)"));
                    LocalDateTime horaSalida = LocalDateTime.now();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss (dd-MM-yyyy)");
                    String horaSalidaStr = horaSalida.format(formatter);
                    motoPagada.setHoraSalida(horaSalidaStr);


                    motosPagadas[indexToDelete] = motoPagada;


                    motosGuardadas[indexToDelete] = null;


                    tableModel.removeRow(selectedRow);

                    // Calcular la diferencia de horas
                    long diferenciaMinutos = ChronoUnit.MINUTES.between(horaEntrada, horaSalida);

                    // Lógica para calcular el precio
                    int precio = 1000 * ((int) diferenciaMinutos / 60 + 1);

                    if (diferenciaMinutos > 5 * 60) {
                        precio = 6000;
                    }

                    motoPagada.setPrecio(precio);

                    int indiceCarros = obtenerIndiceMotosPagadas();
                    System.out.println("Información del array motosPagadas:");
                    for (int i = 0; i <= indiceCarros; i++) {
                        if (motosPagadas[i] != null) {
                            System.out.println("\nplaza[" + i + "]: " + motosPagadas[i]);
                        }
                    }





                    mostrarVentanaCodigoQR(precio);




                } else {

                    JOptionPane.showMessageDialog(null, "Selecciona una fila para eliminar.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });


        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(new JScrollPane(table));
        panel.add(btnPagar);

        JButton btnMotosPagadas = new JButton("Ver Motos Pagadas");
        btnMotosPagadas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarTablaMotosPagadas();
            }
        });

        panel.add(btnMotosPagadas);

        JOptionPane.showMessageDialog(null, panel, "Motos Guardadas", JOptionPane.INFORMATION_MESSAGE);
    }

    private static void mostrarTablaMotosPagadas() {

        Object[][] data = new Object[motosPagadas.length][5];

        for (int i = 0; i < motosPagadas.length; i++) {
            if (motosPagadas[i] != null) {
                data[i][0] = i;
                data[i][1] = motosPagadas[i].getPlaca();
                data[i][2] = motosPagadas[i].getHoraEntrada();
                data[i][3] = motosPagadas[i].getHoraSalida();
                data[i][4] = motosPagadas[i].getPrecio();
            }
        }


        String[] columnNames = {"Plaza", "Placa", "Hora de Entrada", "Hora de Salida", "Precio"};


        DefaultTableModel tableModel = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };


        JTable table = new JTable(tableModel);


        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(new JScrollPane(table));

        JOptionPane.showMessageDialog(null, panel, "Motos Pagadas", JOptionPane.INFORMATION_MESSAGE);
    }

    private static void mostrarTablaCarrosPagados() {

        Object[][] data = new Object[carrosPagados.length][5];

        for (int i = 0; i < carrosPagados.length; i++) {
            if (carrosPagados[i] != null) {
                data[i][0] = i;
                data[i][1] = carrosPagados[i].getPlaca();
                data[i][2] = carrosPagados[i].getHoraEntrada();
                data[i][3] = carrosPagados[i].getHoraSalida();
                data[i][4] = carrosPagados[i].getPrecio();
            }
        }


        String[] columnNames = {"Plaza", "Placa", "Hora de Entrada", "Hora de Salida", "Precio"};

        DefaultTableModel tableModel = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };


        JTable table = new JTable(tableModel);


        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(new JScrollPane(table));

        JOptionPane.showMessageDialog(null, panel, "Carros Pagados", JOptionPane.INFORMATION_MESSAGE);
    }

    private static void mostrarTablaCarrosGuardados() {

        Object[][] data = new Object[carrosGuardados.length][3];

        for (int i = 0; i < carrosGuardados.length; i++) {
            if (carrosGuardados[i] != null) {
                data[i][0] = i;
                data[i][1] = carrosGuardados[i].getPlaca();
                data[i][2] = carrosGuardados[i].getHoraEntrada();
            }
        }


        String[] columnNames = {"Plaza", "Placa", "Hora de Entrada"};


        DefaultTableModel tableModel = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }


        };






        JTable table = new JTable(tableModel);

        JButton btnPagar = new JButton("Pagar");
        btnPagar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {

                    int indexToDelete = (int) table.getValueAt(selectedRow, 0);
                    Vehiculo carroPagado = carrosGuardados[indexToDelete];


                    LocalDateTime horaEntrada = LocalDateTime.parse(carroPagado.getHoraEntrada(),
                    DateTimeFormatter.ofPattern("HH:mm:ss (dd-MM-yyyy)"));

                    LocalDateTime horaSalida = LocalDateTime.now();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss (dd-MM-yyyy)");
                    String horaSalidaStr = horaSalida.format(formatter);
                    carroPagado.setHoraSalida(horaSalidaStr);






                    // Calcular la diferencia de horas
                    long diferenciaMinutos = ChronoUnit.MINUTES.between(horaEntrada, horaSalida);

                    // Lógica para calcular el precio
                    int precio = 5000 * ((int) diferenciaMinutos / 60 + 1);

                    if (diferenciaMinutos > 5 * 60) {
                        precio = 11000;
                    }

                    // Agregar el precio al objeto carroPagado
                    carroPagado.setPrecio(precio);





                    carrosPagados[indexToDelete] = carroPagado;


                    carrosGuardados[indexToDelete] = null;


                    tableModel.removeRow(selectedRow);


                    int indiceCarros = obtenerIndiceCarrosPagados();
                    System.out.println("Información del array carrosPagados:");
                    for (int i = 0; i <= indiceCarros; i++) {
                        if (carrosPagados[i] != null) {
                            System.out.println("\nplaza[" + i + "]: " + carrosPagados[i]);
                        }
                    }

                    mostrarVentanaCodigoQR(precio);

                } else {
                    JOptionPane.showMessageDialog(null, "Selecciona una fila para eliminar.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });


        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(new JScrollPane(table));
        panel.add(btnPagar);

        JButton btnCarrosPagados = new JButton("Ver Carros Pagados");
        btnCarrosPagados.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarTablaCarrosPagados();
            }
        });

        panel.add(btnCarrosPagados);
        JOptionPane.showMessageDialog(null, panel, "Carros Guardados", JOptionPane.INFORMATION_MESSAGE);
    }


    private static void mostrarTablaMensualidades() {

        Object[][] data = new Object[mensualidades.length][5];

        for (int i = 0; i < mensualidades.length; i++) {
            if (mensualidades[i] != null) {
                data[i][0] = i;
                data[i][1] = mensualidades[i].getNombre();
                data[i][2] = mensualidades[i].getPlaca();
                data[i][3] = mensualidades[i].getFechaInicio();
                data[i][4] = mensualidades[i].getFechaCaducidad();

            }
        }


        String[] columnNames = {"Plaza", "Nombre", "Placa", "Fecha de Inicio", "Fecha de Caducidad"};


        DefaultTableModel tableModel = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable table = new JTable(tableModel);
        JButton btnActualizarFecha = new JButton("Actualizar Fecha");
        btnActualizarFecha.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    int indexToUpdate = (int) table.getValueAt(selectedRow, 0);
                    Mensualidad mensualidad = mensualidades[indexToUpdate];


                    LocalDateTime fechaInicio = LocalDateTime.parse(mensualidad.getFechaInicio(), DateTimeFormatter.ofPattern("HH:mm:ss (dd-MM-yyyy)"));
                    LocalDateTime nuevaFechaInicio = fechaInicio.plusMonths(1);
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss (dd-MM-yyyy)");
                    mensualidad.setFechaInicio(nuevaFechaInicio.format(formatter));



                    LocalDateTime fechaCaducidad = LocalDateTime.parse(mensualidad.getFechaCaducidad(), DateTimeFormatter.ofPattern("HH:mm:ss (dd-MM-yyyy)"));
                    LocalDateTime nuevaFechaCaducidad = fechaCaducidad.plusMonths(1);
                    DateTimeFormatter formato = DateTimeFormatter.ofPattern("HH:mm:ss (dd-MM-yyyy)");
                    mensualidad.setFechaCaducidad(nuevaFechaCaducidad.format(formato));
                    int precio = precioMensualidad;
                    mostrarVentanaCodigoQR(precio);
                    JOptionPane.showMessageDialog(null, "Fecha actualizada para " + mensualidad.getNombre() +
                            "\nNueva Fecha de Inicio: " + mensualidad.getFechaInicio()+"\nNueva Fecha de Caducidad: " + mensualidad.getFechaCaducidad(), "Actualización Exitosa", JOptionPane.INFORMATION_MESSAGE);


                    mensualidades[indexToUpdate] = mensualidad;



                    tableModel.setValueAt(mensualidad.getFechaInicio(), selectedRow, 3);
                    tableModel.setValueAt(mensualidad.getFechaCaducidad(), selectedRow, 3);

                } else {
                    JOptionPane.showMessageDialog(null, "Selecciona una fila para actualizar.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(new JScrollPane(table));
        panel.add(btnActualizarFecha);

        JOptionPane.showMessageDialog(null, panel, "Mensualidades Registradas", JOptionPane.INFORMATION_MESSAGE);
    }

    private static void mostrarVentanaCodigoQR(int precio) {
        ImageIcon imagenQR = new ImageIcon(RUTA_CODIGO_QR);
        Image imagen = imagenQR.getImage();

        // Escalar la imagen a un tamaño más pequeño
        Image imagenEscalada = imagen.getScaledInstance(400, 400, Image.SCALE_SMOOTH);

        // Crear un nuevo ImageIcon con la imagen escalada
        ImageIcon QR = new ImageIcon(imagenEscalada);

        // Crear un panel para contener la imagen y el mensaje
        JPanel panel = new JPanel(new BorderLayout());

        // Etiqueta para la imagen
        JLabel imageLabel = new JLabel(QR);

        NumberFormat numberFormat = NumberFormat.getCurrencyInstance(Locale.getDefault());
        String precioFormateado = numberFormat.format(precio);

        // Etiqueta para el mensaje
        JLabel messageLabel = new JLabel("\n                    PRECIO: " + precioFormateado);

        messageLabel.setForeground(Color.RED);
        messageLabel.setFont(new Font("Segoe UI Variable", Font.BOLD, 20));

        // Añadir etiquetas al panel
        panel.add(imageLabel, BorderLayout.NORTH);
        panel.add(messageLabel, BorderLayout.SOUTH);

        // Mostrar el panel en el JOptionPane
        JOptionPane.showMessageDialog(
                null,
                panel,
                "Transferencias",
                JOptionPane.INFORMATION_MESSAGE);
    }

}










