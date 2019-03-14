package br.senai.sp.agendacontatos;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;

import java.util.List;

import br.senai.sp.dao.ContatoDAO;
import br.senai.sp.modelo.Contato;

public class MainActivity extends AppCompatActivity {

    private ListView listaContatos;
    private ImageButton btnNovo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listaContatos = findViewById(R.id.list_contatos);
        btnNovo = findViewById(R.id.btn_novo);
        btnNovo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent abrirCadastro = new Intent(MainActivity.this, CadastroContatoActivity.class);
                startActivity(abrirCadastro);
            }
        });
        registerForContextMenu(listaContatos);

        listaContatos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Contato contato = (Contato) listaContatos.getItemAtPosition(position);
                Intent cadastro = new Intent(MainActivity.this, CadastroContatoActivity.class);
                cadastro.putExtra("contato", contato);
                startActivity(cadastro);
            }
        });
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_context_contatos, menu);

        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        final ContatoDAO dao = new ContatoDAO(this);
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        final Contato contato = (Contato) listaContatos.getItemAtPosition(info.position);
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        dao.excluir(contato);
                        dao.close();
                        carregarLista();
                        break;
                    case DialogInterface.BUTTON_NEGATIVE:

                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Você tem certeza que seja excluir o contato?").setPositiveButton("Sim", dialogClickListener)
                .setNegativeButton("Não", dialogClickListener).show();



        return super.onContextItemSelected(item);
    }

    @Override
    protected void onResume() {
        carregarLista();
        super.onResume();
    }

    private void carregarLista(){
        ContatoDAO dao = new ContatoDAO(this);
        List<Contato> contatos = dao.getContatos();
        dao.close();

        ArrayAdapter<Contato> listaContatoAdapter = new ArrayAdapter<Contato>(this, android.R.layout.simple_list_item_1, contatos);

        listaContatos.setAdapter(listaContatoAdapter);

    }
}
