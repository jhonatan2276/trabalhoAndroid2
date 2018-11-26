package br.edu.unidavi.trabalhoandroid.web;

import android.content.Context;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.edu.unidavi.trabalhoandroid.R;
import br.edu.unidavi.trabalhoandroid.eventbus.Carro;

public class GerenciadorWebCarros extends GerenciadorWeb {

    private static final String SERVICE_NAME = "carro";
    private String parametro;

    public GerenciadorWebCarros (Context context, String parametro) {
        super(context, SERVICE_NAME);
        this.parametro = parametro;
    }

    @Override
    public String getRequestBody() {
        Map<String,String> mapaItem = new HashMap<>();
        mapaItem.put("parametro", parametro);

        JSONObject requestJson = new JSONObject(mapaItem);

        return requestJson.toString();
    }

    @Override
    public void handleResponse(String response) {

        List<Carro> carroList = new ArrayList<>();

        try {
            JSONArray jsonArray = new JSONArray(response);

            for(int index= 0; index < jsonArray.length(); index++){
                JSONObject responseAsJSON = (JSONObject) jsonArray.get(index);
                Carro carro = new Carro();
                carro.setIdServer(responseAsJSON.getInt("id"));
                carro.setMarca(responseAsJSON.getString("marca"));
                carro.setModelo(responseAsJSON.getString("modelo"));
                carro.setAno(responseAsJSON.getString("ano"));
                carro.setPreco(responseAsJSON.getString("preco"));
                carro.setObservacoes(responseAsJSON.getString("observacoes"));
                carro.setImagem(responseAsJSON.getString("imagem"));
                carroList.add(carro);
            }

            EventBus.getDefault().post(carroList);

        } catch (JSONException e) {
            EventBus.getDefault().post(new Error(getContext().getString(R.string.msg_erro_resposta_invalida)));
        }
    }
}
