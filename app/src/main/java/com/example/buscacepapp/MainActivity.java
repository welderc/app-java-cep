package com.example.buscacepapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.buscacepapp.models.CEP;
import com.example.buscacepapp.service.HTTPService;

import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    Button btnBuscaCep;
    EditText txtCep;
    TextView lblResposta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtCep = findViewById(R.id.txtCep);
        btnBuscaCep = findViewById(R.id.btnBuscaCep);
        lblResposta = findViewById(R.id.lblResposta);

        btnBuscaCep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    CEP resultado = new HTTPService(txtCep.getText().toString().trim()).execute().get();
                    lblResposta.setText(resultado.toString());
                } catch (ExecutionException | InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
