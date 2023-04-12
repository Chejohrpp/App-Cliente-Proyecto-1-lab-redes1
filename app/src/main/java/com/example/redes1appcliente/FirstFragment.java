package com.example.redes1appcliente;

import static com.example.redes1appcliente.logic.RandomMacAddressGenerator.generateRandomMacAddress;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.navigation.fragment.NavHostFragment;

import com.example.redes1appcliente.connection.Cliente;
import com.example.redes1appcliente.connection.ClienteConnect;
import com.example.redes1appcliente.databinding.FragmentFirstBinding;

import java.net.InetAddress;

public class FirstFragment extends Fragment {

    private FragmentFirstBinding binding;
    private ClienteConnect cliente;
    public FirstFragment() {
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        String randomMacAddress = generateRandomMacAddress();
        cliente = new ClienteConnect(randomMacAddress);
        binding = FragmentFirstBinding.inflate(inflater, container, false);
        binding.macAddressField.setText(randomMacAddress);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.buttonFirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(FirstFragment.this)
                        .navigate(R.id.action_FirstFragment_to_SecondFragment);
            }
        });

        binding.sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mensaje = binding.messageField.getText().toString();
                System.out.println(mensaje);
                showMessageScreen("Tu: " + mensaje);
                try {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                String respuesta = cliente.sendMessage(mensaje);
                                showMessageScreen(respuesta);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });

        binding.macAddressButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    String macAddress = binding.macAddressField.getText().toString();
                    String respuesta = cliente.setMacAddress(macAddress);
                    showMessageScreen(respuesta);
                    System.out.println(respuesta);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });

        binding.disconnectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                String respuesta = cliente.disconnect();
                                showMessageScreen(respuesta);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });

        binding.connectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                String respuesta = cliente.connect();
                                showMessageScreen(respuesta);
                            } catch (Exception e) {
                                //showMessageScreen("No se pudo conectar con el servidor");
                                e.printStackTrace();
                            }
                        }
                    }).start();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });

    }

    private void showMessageScreen(String mensaje){
        binding.chatTextView.post(new Runnable() {
            @Override
            public void run() {
                binding.chatTextView.setText(binding.chatTextView.getText().toString()  + "\n" +  mensaje);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}