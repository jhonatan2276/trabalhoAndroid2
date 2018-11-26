package br.edu.unidavi.trabalhoandroid.dados;

import android.content.Context;
import android.content.SharedPreferences;

public class Session {
    private final String CAMPO_USUARIO = "usuario";
    private final String CATEGORY_SESSION = "session";

    private SharedPreferences sharedPreferences;

    public Session (Context context) {
        sharedPreferences = context.getSharedPreferences(CATEGORY_SESSION, Context.MODE_PRIVATE);
    }

    public void salvaUsuarioSession (String usuarioNome) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(CAMPO_USUARIO, usuarioNome);
        editor.commit();
    }

    public String retornaUsuarioSession () {
        return sharedPreferences.getString(CAMPO_USUARIO, "");
    }
}