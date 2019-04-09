package br.senai.sp.catalogodefilmes;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.renderscript.ScriptGroup;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import br.senai.sp.dao.FilmeDAO;
import br.senai.sp.modelo.Filme;

public class CadastroActivity extends AppCompatActivity {

    public static final int GALERIA_REQUEST = 1000;
    public static final int CAMERA_REQUEST = 999;
    private  CadastroFilmeHelper helper;
    private  Button btn_camera;
    private  Button btn_galeria;
    private ImageView imgFoto;
    private String caminhoFoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);


        helper = new CadastroFilmeHelper(this);
        // associando a variavel para a view
        btn_camera = findViewById(R.id.btn_foto_camera);

        btn_galeria = findViewById(R.id.btn_foto_galeria);

        imgFoto = findViewById(R.id.image_view_filme);


        //ouvintes para os botões
        //CHAMANDO A CAMERA
        btn_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nomeAquivo = "/IMG_"+ System.currentTimeMillis() +".jpg";
                //ABRI A CAMERA
                Intent intentCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                //DANDO UM NOME A FOTO
                caminhoFoto = getExternalFilesDir(null) + nomeAquivo;
                 File arquivoFoto = new File(caminhoFoto);
                //CRIANDO CAMINHO PARA QUADAR A IMAGEM

                Log.d("NOME_ARQUIVO", nomeAquivo);


                Uri fotoUri = FileProvider.getUriForFile(
                        CadastroActivity.this,
                        BuildConfig.APPLICATION_ID + ".provider",
                        arquivoFoto);

                //PENDURANDO NA INTENT UMA SAIDA, QUE É O AQUIVO GERADO PELA CAMERA
                intentCamera.putExtra(MediaStore.EXTRA_OUTPUT, fotoUri);
                //STARTANDO A CAMERA E COLOCANDO UMA CONSTANT
                startActivityForResult(intentCamera, CAMERA_REQUEST);

            }
        });

        //CHAMANDO A GALERIA
        btn_galeria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //INTENT QUE ESTÁ ABRINDO A GALERIA PADRÃO DO CELULAR
                Intent intentGaleria = new Intent(Intent.ACTION_GET_CONTENT);
                //                                         ↑ TIPO DE AÇÃO (QUE NESSE CASO É A GALERIA)
                //SENTADO O TIPO DA IMAGEM (QUE NESSE CASO É TODOS AS TIPOS DE ESTENÇÕES)
                intentGaleria.setType("image/*");
                //STARTANDO A CAMERA E COLOCANDO UMA CONSTANT
                startActivityForResult(intentGaleria, GALERIA_REQUEST);
            }
        });


        Intent intent = getIntent();

        Filme filme = (Filme) intent.getSerializableExtra("filme");

        if(filme != null){
            helper.preencherFormulario(filme);
        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

       if(resultCode == RESULT_OK){
           try {
               switch (requestCode){
                   case GALERIA_REQUEST:
                       InputStream inputStream = getContentResolver()
                               .openInputStream(data.getData());

                       Bitmap bitmapFactory = BitmapFactory.decodeStream(inputStream);

                       imgFoto.setImageBitmap(bitmapFactory);
                       break;

                   case CAMERA_REQUEST:
                        Bitmap bitmap = BitmapFactory.decodeFile(caminhoFoto);
                        Bitmap bitmapReduzido = Bitmap.createScaledBitmap(bitmap, 300, 300, true);
                        imgFoto.setImageBitmap(bitmapReduzido);
                       break;
               }
           } catch (FileNotFoundException e) {
               e.printStackTrace();
           }
       }




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_cadastro_filmes, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        final Filme filme = helper.getFilme();
        final FilmeDAO dao = new FilmeDAO(this);

        switch (item.getItemId()){
            case R.id.menu_salvar:

                if(filme.getId() == 0){
                    dao.salvar(filme);
                    Toast.makeText(this, filme.getTitulo() + " gravado com sucesso!", Toast.LENGTH_LONG).show();
                }else{
                    dao.atualizar(filme);
                    Toast.makeText(this, filme.getTitulo() + " foi atualizado com sucesso!", Toast.LENGTH_LONG).show();
                }
                dao.close();
                finish();
                break;

            case R.id.menu_del:

                if(filme.getId() == 0){
                    Toast.makeText(this, "Filme ainda não foi criado", Toast.LENGTH_LONG).show();
                }else{

                    AlertDialog.Builder caixaDialogo = new AlertDialog.Builder(this);
                    caixaDialogo.setTitle("Exluindo " + filme.getTitulo());
                    caixaDialogo.setMessage("você tem certeza que quer excluir "+ filme.getTitulo() +"?");
                    caixaDialogo.setPositiveButton("SIM", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dao.excluir(filme);
                            Toast.makeText(CadastroActivity.this, filme.getTitulo() + " foi Excluído", Toast.LENGTH_LONG).show();
                            dao.close();
                        }
                    });
                    caixaDialogo.setNegativeButton("NÃO", null);
                    caixaDialogo.create().show();
                }


                break;
            default:

                break;
        }

        return super.onOptionsItemSelected(item);
    }
}