package com.example.redes1appcliente.Objects;

public class Trama {
    private String encabezado;
    private String macOrigen;
    private String macDestino;
    private String datos;
    private String crc;
    private int secuencia;

    public Trama(String encabezado, String macOrigen, String macDestino, String datos, String crc, int secuencia) {
        this.encabezado = encabezado;
        this.macOrigen = macOrigen;
        this.macDestino = macDestino;
        this.datos = datos;
        this.crc = crc;
        this.secuencia = secuencia;
    }

    // getters y setters para cada campo

    @Override
    public String toString() {
        return "Encabezado: " + encabezado + "\n" +
                "MAC origen: " + macOrigen + "\n" +
                "MAC destino: " + macDestino + "\n" +
                "Datos: " + datos + "\n" +
                "CRC: " + crc + "\n" +
                "Secuencia: " + secuencia;
    }
}
