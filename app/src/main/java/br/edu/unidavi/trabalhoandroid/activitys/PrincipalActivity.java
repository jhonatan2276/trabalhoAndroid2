package br.edu.unidavi.trabalhoandroid.activitys;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import br.edu.unidavi.trabalhoandroid.R;
import br.edu.unidavi.trabalhoandroid.dados.CarroAdapter;
import br.edu.unidavi.trabalhoandroid.eventbus.Carro;
import br.edu.unidavi.trabalhoandroid.eventbus.Mensagem;
import br.edu.unidavi.trabalhoandroid.web.GerenciadorWebCarros;

public class PrincipalActivity extends AppCompatActivity {
    private TextView txtUsuario1;
    private TextView txtUsuario2;
    private Button btnFavoritos;
    private ImageButton btnAtualizar;
    private RecyclerView recyclerView;
    private CarroAdapter carroAdapter;
    private ProgressDialog msgCarregando;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        txtUsuario1 = findViewById(R.id.prp_txtUsuario1);
        txtUsuario2 = findViewById(R.id.prp_txtUsuario2);
        btnAtualizar = findViewById(R.id.prp_btnAtualizar);
        btnFavoritos = findViewById(R.id.prp_btnAddFavoritos);

        btnAtualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                montaLista();
            }
        });

        btnFavoritos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent favoritos = new Intent(getApplicationContext(), FavoritosActivity.class);
                startActivity(favoritos);
            }
        });
    }

    public void montaLista () {
        carregando();

        GerenciadorWebCarros gerenciadorWebCarros = new GerenciadorWebCarros(this, "todos");
        gerenciadorWebCarros.execute();

        recyclerView = findViewById(R.id.prp_recyclerCarros);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        carroAdapter = new CarroAdapter(new ArrayList<Carro>(),this);
        recyclerView.setAdapter(carroAdapter);
    }

    public void carregando (){
        msgCarregando = new ProgressDialog(this);
        msgCarregando.setCancelable(false);
        msgCarregando.setMessage("Aguarde Recebendo Dados...");
        msgCarregando.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        msgCarregando.setProgress(0);
        msgCarregando.show();
    }

    public void fimCarregando(){
        if (msgCarregando != null && msgCarregando.isShowing()) {
            msgCarregando.cancel();
        }
    }

    public void criaAlerta(String mensagem) {
        AlertDialog.Builder alerta = new AlertDialog.Builder(this);
        alerta.setMessage(mensagem)
                .setPositiveButton("Tentar Novamente", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        montaLista();
                    }
                })
                .setNegativeButton("Sair", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                    }
                });
        AlertDialog alertDialog = alerta.create();
        alertDialog.show();
    }

    @Override
    protected void onResume() {
        super.onResume();

        montaLista();
    }

    @Override
    protected void onStart() {
        super.onStart();

        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();

        EventBus.getDefault().unregister(this);
    }

    //Evento criado para fins de teste de EventBus
    @Subscribe (sticky = true, threadMode = ThreadMode.MAIN)
    public void onEvent(Mensagem mensagem) {
        txtUsuario1.setText(mensagem.getTexto01());
        txtUsuario2.setText(mensagem.getTexto02());
    }

    @Subscribe
    public void onEvent(List<Carro> carroLista) {
        if (carroLista.size() == 0) {
            findViewById(R.id.prp_telaPadrao).setVisibility(View.VISIBLE);
            findViewById(R.id.prp_txtTitulo).setVisibility(View.GONE);
            findViewById(R.id.prp_recyclerCarros).setVisibility(View.GONE);
        } else {
            findViewById(R.id.prp_telaPadrao).setVisibility(View.GONE);
            findViewById(R.id.prp_txtTitulo).setVisibility(View.VISIBLE);
            findViewById(R.id.prp_recyclerCarros).setVisibility(View.VISIBLE);
            carroAdapter.carroLista = carroLista;
            carroAdapter.notifyDataSetChanged();
        }

        fimCarregando();

        Log.d("EVENTO ======= ", "RECEBENDO CARROS");
    }

    @Subscribe
    public void onEvent(Error error){
        fimCarregando();
        criaAlerta(error.getMessage());

        Log.d("ERRO =========== ", error.getMessage());
    }
}