package br.edu.unidavi.trabalhoandroid.dados;

import android.content.Context;
import android.content.Intent;
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

public class CarroAdapter extends RecyclerView.Adapter<EstruturaItemLista> {

    public List<Carro> carroLista;
    private Context context;

    private ArrayList<Favorito> favoritoArrayList;
    private LocalDatabaseController db;

    public CarroAdapter (List<Carro> carroLista, Context context) {
        this.carroLista = carroLista;
        this.context = context;
    }

    @Override
    public EstruturaItemLista onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.estrutura_item_lista, parent,false);
        EstruturaItemLista estruturaItemLista = new EstruturaItemLista(view);

        return estruturaItemLista;
    }

    @Override
    public void onBindViewHolder(EstruturaItemLista estruturaItemLista, int posicao) {
        final Carro carro = carroLista.get(posicao);

        db = new LocalDatabaseController(context);

        favoritoArrayList = db.getAllFavs();

        estruturaItemLista.marca.setText(carro.getMarca());
        estruturaItemLista.modelo.setText(carro.getModelo());
        estruturaItemLista.ano.setText(carro.getAno());
        estruturaItemLista.preco.setText(carro.getPreco());

        for (int i = 0; i < favoritoArrayList.size(); i++) {
            if (carro.getIdServer() == favoritoArrayList.get(i).getIdServer()) {
                estruturaItemLista.iconeFavorito.setVisibility(View.VISIBLE);
                break;
            } else {
                estruturaItemLista.iconeFavorito.setVisibility(View.GONE);
            }
        }

        //IMAGEM
        Picasso.with(context)
                .load(carro.getImagem())
                .into(estruturaItemLista.imagem);

        //AO CLICAR NO ITEM
        estruturaItemLista.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Carro carroDet = new Carro();
                carroDet.setIdServer(carro.getIdServer());
                carroDet.setMarca(carro.getMarca());
                carroDet.setModelo(carro.getModelo());
                carroDet.setAno(carro.getAno());
                carroDet.setPreco(carro.getPreco());
                carroDet.setObservacoes(carro.getObservacoes());
                carroDet.setImagem(carro.getImagem());
                EventBus.getDefault().postSticky(carroDet);

                final Intent carroDetalhe;
                carroDetalhe =  new Intent(context, CarroDetalheActivity.class);
                context.startActivity(carroDetalhe);
            }
        });
    }

    @Override
    public int getItemCount() {
        return carroLista.size();
    }
}