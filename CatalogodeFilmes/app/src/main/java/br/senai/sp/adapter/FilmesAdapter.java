package br.senai.sp.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.List;

import br.senai.sp.catalogodefilmes.R;
import br.senai.sp.conversores.Imagem;
import br.senai.sp.modelo.Filme;

public class FilmesAdapter extends BaseAdapter {
    private Context context;
    private List<Filme> filmes;

    public FilmesAdapter(Context context, List<Filme> filmes){
        this.context = context;
        this.filmes = filmes;
    }

    @Override
    public int getCount() {
        return filmes.size();
    }

    @Override
    public Object getItem(int position) {
        return filmes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return filmes.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
//        TextView xpto = new TextView(context);
//        xpto.setText(filmes.get(position).getTitulo())
//
        Filme filme = filmes.get(position);

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.lista_filmes, null);

        //Prenenchando os tribustos para a lista_filmes
        TextView txtTitulo = view.findViewById(R.id.txt_titulo_filme);
        txtTitulo.setText(filme.getTitulo());

        TextView txtDiretor = view.findViewById(R.id.txt_diretor);
        txtDiretor.setText(filme.getDiretor());

        RatingBar ratingBar = view.findViewById(R.id.rating_nota);
        ratingBar.setProgress(filme.getNota());

        ImageView imgFoto = view.findViewById(R.id.imagem_filme);
        imgFoto.setImageBitmap(Imagem.arrayToBimap(filme.getFoto()));

        return view;
    }
}