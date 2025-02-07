package com.example.buscacepapp.service;

import android.os.AsyncTask;
import com.example.buscacepapp.models.CEP;
import com.google.gson.Gson;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class HTTPService extends AsyncTask<Void, Void, CEP> {
    private String cepInserido;

    public HTTPService(String cep) {
        this.cepInserido = cep;
    }
    @Override
    protected CEP doInBackground(Void... voids) {
        StringBuilder resposta = new StringBuilder();

        if (this.cepInserido != null && this.cepInserido.length() == 8) {

            try {

                URL url = new URL("https://viacep.com.br/ws/" + this.cepInserido + "/json/");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setRequestProperty("Content-type", "application/json");
                connection.setDoOutput(true);
                connection.setConnectTimeout(5000);
                connection.connect();

                Scanner scanner = new Scanner(url.openStream());

                while (scanner.hasNext()) {
                    resposta.append(scanner.next());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return new Gson().fromJson(resposta.toString(), CEP.class);
    }
}
