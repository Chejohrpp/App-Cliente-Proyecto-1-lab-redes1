package com.example.redes1appcliente.connection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.Socket;
import java.util.Scanner;

public class ClienteConnect {
    //private static final String DIRECCION_SERVIDOR = "192.168.137.1";
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

            return (respuesta);
        }
        return "";
    }

    public String connect() throws Exception {
        servidor = new Socket(DIRECCION_SERVIDOR, PUERTO_SERVIDOR);
        PrintWriter salida = new PrintWriter(servidor.getOutputStream(), true);

        // Crear la cadena con la dirección MAC y el comando CONNECT
        String macConnect = macAddress;

        // Enviar la cadena al servidor
        salida.println(macConnect);

        // Leer la respuesta del servidor
        BufferedReader entrada = new BufferedReader(new InputStreamReader(servidor.getInputStream()));
        String respuesta = entrada.readLine();

        if (respuesta.equals("OK")) {
            // La conexión se ha establecido correctamente
            String response = "Conectado al servidor en " + servidor.getInetAddress();
            System.out.println(response);
            this.socketClosed = false;
            return response;
        } else {
            // La conexión no se ha establecido correctamente
            throw new Exception("Error al conectar al servidor: " + respuesta);
        }
    }


    public String disconnect() throws Exception {
        // Enviar el mensaje de desconexión al servidor
        servidor.close();
        System.out.println(servidor.isClosed());
        String response = "SERVIDOR CERRADO";
        System.out.println(response);
        // Cerrar el socket
        socketClosed = true;
        return response;
    }

    public String setMacAddress(String macAddress){
        this.macAddress = macAddress;
        return ("Mac Address cambiada a -> " + macAddress);
    }
}
