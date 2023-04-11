package com.example.redes1appcliente.connection;

import com.example.redes1appcliente.Objects.Trama;

import java.io.*;
import java.net.*;

public class Cliente {
    private String macAddress;
    private InetAddress serverAddress;
    private int serverPort;
    private DatagramSocket socket;
    private boolean socketClosed;

    public Cliente(String macAddress, InetAddress serverAddress, int serverPort) throws Exception {
        this.macAddress = macAddress;
        this.serverAddress = serverAddress;
        this.serverPort = serverPort;
        this.socket = new DatagramSocket();
        this.socketClosed = false;
    }

    public void connect() throws Exception {
        // Enviar el mensaje de conexión al servidor
        sendMessage("connect " + macAddress);

        // Crear el hilo para recibir los mensajes del servidor
        Thread receiverThread = new Thread(() -> {
            try {
                while (!socketClosed) {
                    // Recibir un paquete del servidor
                    byte[] buffer = new byte[1024];
                    DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                    socket.receive(packet);

                    // Mostrar el mensaje recibido
                    String message = new String(packet.getData(), 0, packet.getLength());
                    System.out.println("Mensaje recibido: " + message);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                socket.close();
            }
        });

        receiverThread.start();
    }

    public void disconnect() throws Exception {
        // Enviar el mensaje de desconexión al servidor
        sendMessage("disconnect " + macAddress);

        // Cerrar el socket
        socketClosed = true;
    }

    public void sendMessage(String message) throws Exception {
        if (!socketClosed) {
            // Crear el paquete con el mensaje y enviarlo al servidor
            byte[] buffer = message.getBytes();
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length, serverAddress, serverPort);
            socket.send(packet);
        }
    }


    public void test() throws Exception {
        // Crear el objeto Cliente con la dirección MAC, dirección IP y puerto del servidor
        Cliente cliente = new Cliente("00:11:22:33:44:55", InetAddress.getByName("192.168.56.1"), 5000);

        // Conectar al servidor
        cliente.connect();

        // Enviar un mensaje al servidor
        cliente.sendMessage("Hola, servidor!");

        // Esperar un segundo antes de desconectar
        Thread.sleep(1000);

        // Desconectar del servidor
        cliente.disconnect();
    }

    public void enviarTrama(String origen, String destino, String datos) {
        new Thread(() -> {
            try {
                // Crear una trama con los datos del mensaje
                Trama trama = new Trama("encabezado", origen, destino, datos, "crc", 1);

                // Convertir la trama a bytes
                ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
                ObjectOutputStream objectStream = new ObjectOutputStream(byteStream);
                objectStream.writeObject(trama);
                byte[] buffer = byteStream.toByteArray();

                // Crear un paquete con los bytes y enviarlo al servidor
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length, serverAddress, serverPort);
                socket.send(packet);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

}
