package com.example.redes1appcliente.logic;

import java.util.Random;

public class RandomMacAddressGenerator {

    public static String generateRandomMacAddress() {
        Random random = new Random();
        byte[] mac = new byte[6];
        random.nextBytes(mac);
        mac[0] = (byte) (mac[0] & (byte) 254);
        StringBuilder sb = new StringBuilder(18);
        for (byte b : mac) {
            if (sb.length() > 0) {
                sb.append(":");
            }
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
}
