package br.senai.sp.catalogodefilmes;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;

import java.io.ByteArrayOutputStream;

import br.senai.sp.conversores.Imagem;
import br.senai.sp.modelo.Filme;

public class CadastroFilmeHelper {
    private EditText txtTitle, txtDiretor, txtGenero, txtDataDeLancamento, txtDuracao;
    private Filme filme;
    private RatingBar nota;
    private ImageView imgFoto;

    public CadastroFilmeHelper(CadastroActivity activity) {
        txtTitle = activity.findViewById(R.id.txt_titulo);
        txtDiretor = activity.findViewById(R.id.txt_diretor);
        txtGenero = activity.findViewById(R.id.txt_genero);
        txtDataDeLancamento = activity.findViewById(R.id.txt_data);
        txtDuracao = activity.findViewById(R.id.txt_duracao);
        nota = activity.findViewById(R.id.nota_filme);
        imgFoto = activity.findViewById(R.id.image_view_filme);
        filme = new Filme();
    }

    public Filme getFilme() {
        filme.setDataLancamento(txtDataDeLancamento.getText().toString());
        filme.setDiretor(txtDiretor.getText().toString());
        filme.setDuracao(txtDuracao.getText().toString());
        filme.setGenero(txtGenero.getText().toString());
        filme.setTitulo(txtTitle.getText().toString());
        filme.setNota(nota.getProgress());

        //pegando a foto que é Drawable e transformando em bitmap
        BitmapDrawable bitmapDrawable = (BitmapDrawable) imgFoto.getDrawable();
        Bitmap bm = bitmapDrawable.getBitmap();
        //reduzindo a imagem para não gastar dados
        Bitmap bitmapRedizudo = Bitmap.createScaledBitmap(bm, 300, 300, true);
        //crianto o modo de saida para a imagem
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        //comprimindo a imagem e lançando o modo de saida
        bitmapRedizudo.compress(Bitmap.CompressFormat.PNG, 0, byteArrayOutputStream);
        //criando uma array para redesenhar a imagem
        byte[] fotoArray = byteArrayOutputStream.toByteArray();
        //setando a imgame para a saida
        filme.setFoto(fotoArray);

        return filme;
    }

    public void preencherFormulario(Filme filme) {
        txtTitle.setText(filme.getTitulo());
        txtDataDeLancamento.setText(filme.getDataLancamento());
        txtDiretor.setText(filme.getDiretor());
        txtDuracao.setText(filme.getDuracao());
        txtGenero.setText(filme.getGenero());
        nota.setProgress(filme.getNota());

        //criando um if para verificar se a array de bytes é difernete de null para converter
//        if(filme.getFoto() != null){
//            //tratando a array de bytes para bitmap
//            Bitmap bm = BitmapFactory.decodeByteArray(filme.getFoto(), 0, filme.getFoto().length);
//            //colocando a foto na View imagemView
//            imgFoto.setImageBitmap(bm);
//        }

        if(filme.getFoto() != null){
            imgFoto.setImageBitmap(Imagem.arrayToBimap(filme.getFoto()));
        }



        this.filme = filme;
    }
}