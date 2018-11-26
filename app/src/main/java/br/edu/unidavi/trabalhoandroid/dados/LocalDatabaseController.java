package br.edu.unidavi.trabalhoandroid.dados;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.edu.unidavi.trabalhoandroid.eventbus.Favorito;

public class LocalDatabaseController extends SQLiteOpenHelper {

    private static final String DB_NAME = "Favorito.db";
    private static final int DB_VERSION = 5;

    public String teste;

    public LocalDatabaseController(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE favoritos(" +
                "_id integer primary key autoincrement," +
                "idServer integer," +
                "marca text," +
                "modelo text," +
                "ano text," +
                "imagem text," +
                "preco boolean" +
                ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table carros");
        onCreate(db);
    }

    public ArrayList<Favorito> getAllFavs () {
        String[] colunas = {"_id", "idServer", "marca", "modelo", "ano", "imagem", "preco"};

        Cursor cursor = getWritableDatabase().query("favoritos", colunas, null, null, null, null, null, null);

        ArrayList<Favorito> listFavorito = new ArrayList<Favorito>();

        while (cursor.moveToNext()) {
            Favorito favorito = new Favorito();
            favorito.setId(cursor.getInt(0));
            favorito.setIdServer(cursor.getInt(1));
            favorito.setMarca(cursor.getString(2));
            favorito.setModelo(cursor.getString(3));
            favorito.setAno(cursor.getString(4));
            favorito.setImagem(cursor.getString(5));
            favorito.setPreco(cursor.getString(6));

            listFavorito.add(favorito);
        }
        cursor.close();
        return listFavorito;
    }

    public void inserirFavorito(int idServer, String marca, String modelo, String ano, String imagem, String preco) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("idServer", idServer);
        values.put("marca", marca);
        values.put("modelo", modelo);
        values.put("ano", ano);
        values.put("imagem", imagem);
        values.put("preco", preco);
        db.insert("favoritos", null, values);
    }

    public void deletaFavorito(Favorito favorito) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete("favoritos", "_id=" + favorito.getId(),
                null);
    }
}
