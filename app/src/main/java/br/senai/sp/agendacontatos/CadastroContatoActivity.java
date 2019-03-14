package br.senai.sp.agendacontatos;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.zip.Inflater;

import br.senai.sp.dao.ContatoDAO;
import br.senai.sp.modelo.Contato;

public class CadastroContatoActivity extends AppCompatActivity{


    CadastroContatoHelper helper;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cadastro_activity);
        helper = new CadastroContatoHelper(this);

        Intent intent = getIntent();

        Contato contato = (Contato) intent.getSerializableExtra("contato");
        if(contato != null){
            helper.preencherFormulario(contato);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater =  getMenuInflater();
        menuInflater.inflate(R.menu.menu_cadastro_contato, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.menu_salvar:
                Contato contato = helper.getContato();
                ContatoDAO dao = new ContatoDAO(this);
                if(helper.validarVazio()){
                    if(contato.getId() == 0){
                        dao.salvar(contato);
                        Toast.makeText(CadastroContatoActivity.this, "Contato salvo com sucesso!", Toast.LENGTH_LONG).show();
                    }else{
                        dao.atualizar(contato);
                        Toast.makeText(CadastroContatoActivity.this, "Contato atualizado com sucesso", Toast.LENGTH_LONG).show();
                    }
                    dao.close();
                    finish();
                }


                break;
            case R.id.menu_delete:
                Toast.makeText(CadastroContatoActivity.this, "Excluir", Toast.LENGTH_LONG).show();
                break;
            case R.id.menu_configuracoes:
                Toast.makeText(CadastroContatoActivity.this, "Configuraçoes", Toast.LENGTH_LONG).show();
        }




        return super.onOptionsItemSelected(item);
    }
}
