
package com.mycompany.parkingapp;

import java.awt.event.ActionEvent;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.swing.*;

/**
 *
 * @author Lenovo Ryzen5
 */
public class Main {

    private static final Vehiculo[] motos = new Vehiculo[100];
    private static final Vehiculo[] carros = new Vehiculo[100];

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
        botonVerMotos.addActionListener((ActionEvent e) -> mostrarListaMotos());
        botonVerCarros.addActionListener((ActionEvent e) -> mostrarListaCarros());
        panelPrincipal.add(botonVerMotos);
        panelPrincipal.add(botonVerCarros);

        ventanaPrincipal.setVisible(true);

        botonHora.addActionListener((var e) -> {
            String[] tiposPosibles = {"Carro", "Motocicleta"};
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
                    
                    if ("Motocicleta".equals(tipoSeleccionado)) {
                        int indiceMotos = obtenerIndiceDisponibleMotos();
                        
                        if (indiceMotos != -1) {
                            
                            LocalDateTime fechaYHoraLocalDateTime = LocalDateTime.now();
                            DateTimeFormatter formatoLocalDateTime = DateTimeFormatter.ofPattern("HH:mm:ss (dd-MM-yyyy)");
                            String horaEntrada = fechaYHoraLocalDateTime.format(formatoLocalDateTime);
                            
                            Vehiculo moto = new Vehiculo(placa, horaEntrada, null);
                            motos[indiceMotos] = moto;
                            JOptionPane.showMessageDialog(ventanaPrincipal,
                                    """
                                    Moto ingresada:
                                    
                                    Placa:\s""" + placa + "\nHora de Entrada: " + horaEntrada,
                                    "Ingreso Exitoso",
                                    JOptionPane.INFORMATION_MESSAGE);
                            System.out.println("Información del array motos:\n");
                            for (int i = 0; i <= indiceMotos; i++) {
                                if (motos[i] != null) {
                                    System.out.println("\nplaza[" + i + "]: " + motos[i]);
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
                            carros[indiceCarros] = carro;
                            JOptionPane.showMessageDialog(ventanaPrincipal,
                                    """
                                    Carro ingresado:
                                    
                                    Placa:\s""" + placa + "\nHora de Entrada: " + horaEntrada,
                                    "Ingreso Exitoso",
                                    JOptionPane.INFORMATION_MESSAGE);
                            System.out.println("Información del array carros:");
                            for (int i = 0; i <= indiceCarros; i++) {
                                if (carros[i] != null) {
                                    System.out.println("\nplaza[" + i + "]: " + carros[i]);
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
        for (int i = 0; i < motos.length; i++) {
            if (motos[i] == null) {
                return i;
            }
        }
        return -1;
    }

    private static int obtenerIndiceDisponibleCarros() {
        for (int i = 0; i < carros.length; i++) {
            if (carros[i] == null) {
                return i;
            }
        }
        return -1;
    }

    private static void mostrarListaMotos() {
        StringBuilder listaMotos = new StringBuilder("Lista de Motos:\n");
        for (int i = 0; i < motos.length; i++) {
            if (motos[i] != null) {
                listaMotos.append("\nPlaza[").append(i).append("]:\n");
                listaMotos.append("Placa: ").append(motos[i].getPlaca()).append("\n");
                listaMotos.append("Hora de Entrada: ").append(motos[i].getHoraEntrada()).append("\n");
            }
        }
        JOptionPane.showMessageDialog(null, listaMotos.toString(), "Motos Guardadas", JOptionPane.INFORMATION_MESSAGE);
    }

    private static void mostrarListaCarros() {
        StringBuilder listaCarros = new StringBuilder("Lista de Carros:\n");
        for (int i = 0; i < carros.length; i++) {
            if (carros[i] != null) {
                listaCarros.append("\nPlaza[").append(i).append("]:\n");
                listaCarros.append("Placa: ").append(carros[i].getPlaca()).append("\n");
                listaCarros.append("Hora de Entrada: ").append(carros[i].getHoraEntrada()).append("\n");
            }
        }
        JOptionPane.showMessageDialog(null, listaCarros.toString(), "Carros Guardados", JOptionPane.INFORMATION_MESSAGE);
    }
}
