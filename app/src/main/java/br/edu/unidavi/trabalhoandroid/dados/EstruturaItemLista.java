package br.edu.unidavi.trabalhoandroid.dados;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import br.edu.unidavi.trabalhoandroid.R;

public class EstruturaItemLista extends RecyclerView.ViewHolder {

    public TextView marca;
    public TextView modelo;
    public TextView ano;
    public TextView preco;
    public ImageView imagem;
    public ImageView iconeFavorito;

    public EstruturaItemLista (View itemView) {
        super(itemView);

        marca = itemView.findViewById(R.id.item_txtMarca);
        modelo = itemView.findViewById(R.id.item_txtModelo);
        ano = itemView.findViewById(R.id.item_txtAno);
        preco = itemView.findViewById(R.id.item_txtPreco);
        imagem = itemView.findViewById(R.id.item_imagemIcone);
        iconeFavorito = itemView.findViewById(R.id.item_iconeFavorito);
    }
}