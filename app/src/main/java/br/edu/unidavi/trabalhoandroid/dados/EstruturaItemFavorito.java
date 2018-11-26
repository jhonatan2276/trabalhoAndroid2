package br.edu.unidavi.trabalhoandroid.dados;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import br.edu.unidavi.trabalhoandroid.R;

public class EstruturaItemFavorito  extends RecyclerView.ViewHolder {

    public TextView marca;
    public TextView modelo;
    public TextView ano;
    public TextView preco;
    public ImageView imagem;
    public ImageView iconeDeleta;

    public EstruturaItemFavorito (View itemView) {
        super(itemView);

        marca = itemView.findViewById(R.id.itemFav_txtMarca);
        modelo = itemView.findViewById(R.id.itemFav_txtModelo);
        ano = itemView.findViewById(R.id.itemFav_txtAno);
        preco = itemView.findViewById(R.id.itemFav_txtPreco);
        imagem = itemView.findViewById(R.id.itemFav_imagemIcone);
        iconeDeleta = itemView.findViewById(R.id.itemFav_iconeDeleta);
    }
}
