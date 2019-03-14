package br.senai.sp.agendacontatos;

import android.support.design.widget.TextInputLayout;
import android.widget.EditText;

import br.senai.sp.modelo.Contato;

public class CadastroContatoHelper {

    private EditText txtNome;
    private EditText txtEndereco;
    private EditText txtTelefone;
    private EditText txtEmail;
    private EditText txtLinkedin;
    private TextInputLayout layoutTxtNome;
    private TextInputLayout layoutTxtEndereco;
    private TextInputLayout layoutTxtTelefone;
    private TextInputLayout layoutTxtEmail;
    private TextInputLayout layoutTxtLinkedIn;
    private Contato contato;

    public CadastroContatoHelper(CadastroContatoActivity activity){
        layoutTxtNome = activity.findViewById(R.id.layout_txt_nome);
        layoutTxtEndereco = activity.findViewById(R.id.layout_txt_endereco);
        layoutTxtTelefone = activity.findViewById(R.id.layout_txt_telefone);
        layoutTxtEmail = activity.findViewById(R.id.layout_txt_email);
        layoutTxtLinkedIn = activity.findViewById(R.id.layout_txt_linkedin);

        txtNome = activity.findViewById(R.id.txt_nome);
        txtEndereco = activity.findViewById(R.id .txt_endereco);
        txtTelefone = activity.findViewById(R.id.txt_telefone);
        txtEmail = activity.findViewById(R.id.txt_email);
        txtLinkedin = activity.findViewById(R.id.txt_linkedin);
        contato = new Contato();
    }

    public Contato getContato(){
        contato.setNome(txtNome.getText().toString());
        contato.setEndereco(txtEndereco.getText().toString());
        contato.setTelefone(txtTelefone.getText().toString());
        contato.setEmail(txtEmail.getText().toString());
        contato.setLinkedin(txtLinkedin.getText().toString());
        return contato;
    }

    public void preencherFormulario(Contato contato){
        txtNome.setText(contato.getNome());
        txtEndereco.setText(contato.getEndereco());
        txtTelefone.setText(contato.getTelefone());
        txtEmail.setText(contato.getEmail());
        txtLinkedin.setText(contato.getLinkedin());
        this.contato = contato;
    }

    public boolean validarVazio(){
        boolean validado = true;

        if(txtNome.getText().toString().isEmpty()){
            layoutTxtNome.setErrorEnabled(true);
            layoutTxtNome.setError("Preencha essa caixa!");
            validado = false;
        }else{
            layoutTxtNome.setErrorEnabled(false);

        }

        if(txtEndereco.getText().toString().isEmpty()){
            layoutTxtEndereco.setErrorEnabled(true);
            layoutTxtEndereco.setError("Preencha essa caixa!");
            validado = false;
        }else{
            layoutTxtEndereco.setErrorEnabled(false);

        }

        if(txtTelefone.getText().toString().isEmpty()){
            layoutTxtTelefone.setErrorEnabled(true);
            layoutTxtTelefone.setError("Preencha essa caixa!");
            validado = false;
        }else{
            layoutTxtTelefone.setErrorEnabled(false);

        }

        if(txtEmail.getText().toString().isEmpty()){
            layoutTxtEmail.setErrorEnabled(true);
            layoutTxtEmail.setError("Preencha essa caixa!");
            validado = false;
        }else{
            layoutTxtEmail.setErrorEnabled(false);

        }

        if(txtLinkedin.getText().toString().isEmpty()){
            layoutTxtLinkedIn.setErrorEnabled(true);
            layoutTxtLinkedIn.setError("Preencha essa caixa!");
            validado = false;
        }else{
            layoutTxtLinkedIn.setErrorEnabled(false);

        }

        return validado;
    }
}
