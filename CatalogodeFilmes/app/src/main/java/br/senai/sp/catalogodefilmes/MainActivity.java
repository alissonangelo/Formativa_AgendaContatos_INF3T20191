package br.senai.sp.catalogodefilmes;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import br.senai.sp.adapter.FilmesAdapter;
import br.senai.sp.dao.FilmeDAO;
import br.senai.sp.modelo.Filme;

public class MainActivity extends AppCompatActivity {
       private ListView listaFilmes;
       private Button btNovoFilme;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //associa o objeto ListView do java á view do layout
        listaFilmes = findViewById(R.id.list_filmes);

        //assoiando o botão do novo filme
        btNovoFilme = findViewById(R.id.bt_novo_fime);

        btNovoFilme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent abrirCadastro = new Intent(MainActivity.this, CadastroActivity.class);
                startActivity(abrirCadastro);
            }
        });

        // *********** DEFINIÇÃO DE UM MENU DE CONTEXTO PARA A LISTVIEW
        registerForContextMenu(listaFilmes);

        //********* Levando para edição
        listaFilmes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Filme filme = (Filme) listaFilmes.getItemAtPosition(position);
                Intent cadastro = new Intent(MainActivity.this, CadastroActivity.class);
                cadastro.putExtra("filme",  filme);
                startActivity(cadastro);
            }
        });
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {


        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_context_lista_filmes, menu);



//        MenuItem deletar = menu.add("Excluir");
//        MenuItem editar = menu.add("Editar");
//
//        deletar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
//            @Override
//            public boolean onMenuItemClick(MenuItem item) {
//                Toast.makeText(MainActivity.this, "DELETA", Toast.LENGTH_LONG).show();
//                return false;
//            }
//        });

        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        final FilmeDAO dao = new FilmeDAO(this);
        final AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        final Filme filme = (Filme) listaFilmes.getItemAtPosition(info.position);

        AlertDialog.Builder caixaDialogo = new AlertDialog.Builder(this);
        caixaDialogo.setTitle("Exluindo um filme");
        caixaDialogo.setMessage("você tem certeza que quer excluir "+ filme.getTitulo() +"?");
        caixaDialogo.setPositiveButton("SIM", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dao.excluir(filme);
                        Toast.makeText(MainActivity.this, filme.getTitulo() + " foi Excluído", Toast.LENGTH_LONG).show();
                        dao.close();
                        carregarFilmes();
                    }
                });
        caixaDialogo.setNegativeButton("NÃO", null);
        caixaDialogo.create().show();


        return super.onContextItemSelected(item);
    }

    @Override
    protected void onResume() {
        carregarFilmes();
        super.onResume();
    }

    @Override
    protected void onPause() {

        super.onPause();
    }

    private void carregarFilmes(){

        // ******** Vetor de filmes
        FilmeDAO dao = new FilmeDAO(this);
        List<Filme> filmes = dao.getFilmes();
        dao.close();


        // ****** Criar um adapter que carrega o vetor na list view
//        ArrayAdapter<Filme> adapterFilmes =  new ArrayAdapter<Filme>(this, android.R.layout.simple_list_item_1, filmes);

        FilmesAdapter adapterFilmes = new FilmesAdapter(this, filmes);

        // injetamos o adaptador no objeto ListView
        listaFilmes.setAdapter(adapterFilmes);
    }



}