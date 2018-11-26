package br.edu.unidavi.trabalhoandroid.dados;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import br.edu.unidavi.trabalhoandroid.R;
import br.edu.unidavi.trabalhoandroid.activitys.CarroDetalheActivity;
import br.edu.unidavi.trabalhoandroid.eventbus.Carro;
import br.edu.unidavi.trabalhoandroid.eventbus.Favorito;

public class FavoritoAdapter extends RecyclerView.Adapter<EstruturaItemFavorito> {

    public List<Favorito> favoritoLista;
    private Context context;
    private LocalDatabaseController db;

    private FavoritoAdapter teste;
    private ArrayList<Favorito> testeArrayList;

    public FavoritoAdapter(List<Favorito> favoritoLista, Context context) {
        this.favoritoLista = favoritoLista;
        this.context = context;
    }

    @Override
    public EstruturaItemFavorito onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.estrutura_item_favorito, parent,false);
        EstruturaItemFavorito estruturaItemFavorito = new EstruturaItemFavorito(view);

        return estruturaItemFavorito;
    }

    @Override
    public void onBindViewHolder(EstruturaItemFavorito estruturaItemFavorito, int posicao) {
        final Favorito favorito = favoritoLista.get(posicao);

        estruturaItemFavorito.marca.setText(favorito.getMarca());
        estruturaItemFavorito.modelo.setText(favorito.getModelo());
        estruturaItemFavorito.ano.setText(favorito.getAno());
        estruturaItemFavorito.preco.setText(favorito.getPreco());

        //IMAGEM
        Picasso.with(context)
                .load(favorito.getImagem())
                .into(estruturaItemFavorito.imagem);


        estruturaItemFavorito.iconeDeleta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db = new LocalDatabaseController(context);
                db.deletaFavorito(favorito);
            }
        });

        //AO CLICAR NO ITEM
        estruturaItemFavorito.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Carro carroDet = new Carro();
                carroDet.setIdServer(favorito.getIdServer());
                carroDet.setMarca(favorito.getMarca());
                carroDet.setModelo(favorito.getModelo());
                carroDet.setAno(favorito.getAno());
                carroDet.setPreco(favorito.getPreco());
                //carroDet.setObservacoes(favorito.getObservacoes());
                carroDet.setImagem(favorito.getImagem());
                EventBus.getDefault().postSticky(carroDet);

                final Intent carroDetalhe;
                carroDetalhe =  new Intent(context, CarroDetalheActivity.class);
                context.startActivity(carroDetalhe);
            }
        });
    }

    @Override
    public int getItemCount() {
        return favoritoLista.size();
    }
}
