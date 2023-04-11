package com.example.redes1appcliente;

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

    private EditText editTextOrigen, editTextDestino, editTextDatos;
    private Button buttonConectar, buttonDesconectar, buttonEnviar;
    private TextView textViewLog;
    private ClienteConnect cliente;
    public FirstFragment() {
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        cliente = new ClienteConnect("");
        binding = FragmentFirstBinding.inflate(inflater, container, false);
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
                try {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                String respuesta = cliente.sendMessage(mensaje);
                                binding.chatTextView.setText(binding.chatTextView.getText().toString()  + "\n" +  respuesta);
                                //binding.messageField.setText("");
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
                    cliente.setMacAddress(macAddress);
                    System.out.println(macAddress);
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
                                cliente.disconnect();
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
                                cliente.connect();
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

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}