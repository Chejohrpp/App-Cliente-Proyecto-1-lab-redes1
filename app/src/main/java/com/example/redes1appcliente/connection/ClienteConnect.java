package com.example.redes1appcliente.connection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.Socket;
import java.util.Scanner;

public class ClienteConnect {
    private static final String DIRECCION_SERVIDOR = "192.168.0.25";
    private static final int PUERTO_SERVIDOR = 1234;
    private boolean socketClosed;
    private Socket servidor;
    private String macAddress;
    public ClienteConnect(String macAddress) {
        this.socketClosed = true;
        this.macAddress = macAddress;
    }

    public String sendMessage(String message) throws Exception {
        if (!socketClosed) {
            BufferedReader entrada = new BufferedReader(new InputStreamReader(servidor.getInputStream()));

            PrintWriter salida = new PrintWriter(servidor.getOutputStream(), true);

            // Leemos la entrada del usuario
            //System.out.print("Mensaje a enviar: ");
            String mensaje = message;

            // Enviamos el mensaje al servidor
            salida.println(mensaje);

            // Leemos la respuesta del servidor
            String respuesta = entrada.readLine();

            return ("Respuesta Servidor: " + respuesta);
        }
        return "";
    }

    public void connect() throws Exception {
        servidor = new Socket(DIRECCION_SERVIDOR, PUERTO_SERVIDOR);
        System.out.println("Conectado al servidor en " + servidor.getInetAddress());
        this.socketClosed = false;

    }

    public void disconnect() throws Exception {
        // Enviar el mensaje de desconexión al servidor
        servidor.close();
        System.out.println(servidor.isClosed());
        System.out.println("SERVIDOR CERRADO");
        // Cerrar el socket
        socketClosed = true;
    }

    public void setMacAddress(String macAddress){
        this.macAddress = macAddress;
    }
}
