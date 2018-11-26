package br.edu.unidavi.trabalhoandroid.activitys;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;;

import br.edu.unidavi.trabalhoandroid.R;
import br.edu.unidavi.trabalhoandroid.dados.FavoritoAdapter;
import br.edu.unidavi.trabalhoandroid.dados.LocalDatabaseController;
import br.edu.unidavi.trabalhoandroid.eventbus.Favorito;

public class FavoritosActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private FavoritoAdapter favoritoAdapter;
    private ArrayList<Favorito> favoritoArrayList;
    private LocalDatabaseController db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favoritos);
    }

    public void montaLista () {
        recyclerView = findViewById(R.id.fav_recyclerFavoritos);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        db = new LocalDatabaseController(this);
        favoritoArrayList = db.getAllFavs();

        favoritoAdapter = new FavoritoAdapter(favoritoArrayList, this);
        recyclerView.setAdapter(favoritoAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();

        montaLista();
    }
}