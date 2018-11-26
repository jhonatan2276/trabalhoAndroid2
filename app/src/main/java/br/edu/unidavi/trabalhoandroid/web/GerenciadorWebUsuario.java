package br.edu.unidavi.trabalhoandroid.web;

import android.content.Context;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import br.edu.unidavi.trabalhoandroid.R;
import br.edu.unidavi.trabalhoandroid.eventbus.Usuario;

public class GerenciadorWebUsuario extends GerenciadorWeb {
    private static final String SERVICE_NAME = "usuario";

    private String nome;
    private String senha;

    public GerenciadorWebUsuario(Context context, String nome, String senha) {
        super(context, SERVICE_NAME);
        this.nome = nome;
        this.senha = senha;
    }

    @Override
    public String getRequestBody() {
        Map<String,String> usuarioMap = new HashMap<>();
        usuarioMap.put("nome", nome);
        usuarioMap.put("senha", senha);

        JSONObject json = new JSONObject(usuarioMap);
        String jsonString = json.toString();

        return jsonString;
    }

    @Override
    public void handleResponse(String response) {
        Usuario usuario = new Usuario();
        try {
            JSONObject responseAsJSON = new JSONObject(response);
            String nome1 = responseAsJSON.getString("nome1");
            usuario.setNome1(nome1);
            String nome2 = responseAsJSON.getString("nome2");
            usuario.setNome2(nome2);

            EventBus.getDefault().post(usuario);

        } catch (JSONException e) {
            EventBus.getDefault().post(new Error(getContext().getString(R.string.msg_erro_resposta_invalida)));
        }
    }
}