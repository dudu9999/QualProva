package br.dudu9999.com.qualprova.Telas;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import br.dudu9999.com.qualprova.Fragmentos.MyApplication;
import br.dudu9999.com.qualprova.Fragmentos.Prova;
import br.dudu9999.com.qualprova.R;
import br.dudu9999.com.qualprova.Fragmentos.Usuario;

public class CadastroActivity extends AppCompatActivity {

    //widgets
    private static final int GALLERY_INTENT = 2;
    private ImageView select_image_C;
    private ProgressDialog mProgressDialog;
    private EditText txtEmail;
    private EditText txtSenha;
    private EditText txtNome;
    private EditText txtColegio;
    private EditText txtCPF;
    private EditText txtTurma;
    private Spinner txtTipo;
    private Button btnCadastrar;
    private FirebaseAuth firebaseAuth;
    private Usuario userLocal;
    private Prova provaLocal;
    private StorageReference mStorage;
    private String TAG = "TELACADASTRO";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        select_image_C = (ImageView) findViewById(R.id.select_image);
        txtEmail =       (EditText)  findViewById(R.id.edtEmailCadastro);
        txtSenha =       (EditText)  findViewById(R.id.edtSenhaCadastro);
        txtNome =        (EditText)  findViewById(R.id.edtNomeCad);
        txtColegio =     (EditText)  findViewById(R.id.edtColegioCad);
        txtCPF =         (EditText)  findViewById(R.id.edtCpfCad);
        txtTipo =        (Spinner)   findViewById(R.id.edtTipo);
        txtTurma =       (EditText)  findViewById(R.id.edtTurma);
        btnCadastrar =   (Button)    findViewById(R.id.btnCadastrar);

        //mascara para cpf
        SimpleMaskFormatter cpfFormater = new SimpleMaskFormatter("NNN.NNN.NNN-NN");
        MaskTextWatcher mtw = new MaskTextWatcher(txtCPF, cpfFormater);
        txtCPF.addTextChangedListener(mtw);


        //buscando o usuario
        userLocal = ((MyApplication)getApplication()).getUser();
        //Log.d(TAG, "USUARIOLOCA"+ userLocal.toString());

        //firebase

        FirebaseApp.initializeApp(getBaseContext());
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        final DatabaseReference bd = db.getReference("banco");


//        private DatabaseReference databaseReferencia = FirebaseDatabase.getInstance().getReference();
//        private DatabaseReference usuarioReferencia = databaseReferencia.child("usuarios");
        mStorage = FirebaseStorage.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();


        mProgressDialog = new ProgressDialog(this);

        select_image_C.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, GALLERY_INTENT);
            }
        });

        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Usuario u = new Usuario();

//                txtEmail = userLocal. setEmail();
//                txtSenha = userLocal.setSenha();

                u.setUID(userLocal.getUID());
                u.setEmail(userLocal.getEmail());
                u.setEmail(txtEmail.getText().toString());
                u.setSenha(txtSenha.getText().toString());
                u.setNome(txtNome.getText().toString());
                u.setColegio(txtColegio.getText().toString());
                u.setCpf(txtCPF.getText().toString());
                u.setTurma(txtTurma.getText().toString());
                u.setTipo(txtTipo.getSelectedItem().toString());

                bd.child("Usuarios").child(u.getUID()).setValue(u);

                SharedPreferences.Editor editor = getSharedPreferences(MyApplication.MY_PREFS_NAME, MODE_PRIVATE).edit();
                editor.putString("UID", u.getUID());
                editor.apply();







                //Salvando localmente(setando cliente)
                ((MyApplication)getApplication()).setUser(u);

               // limpar();

                Toast.makeText(CadastroActivity.this, "Cadastro Efetuado com sucesso!", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(CadastroActivity.this ,PerfilActivity.class);
                startActivity(i);





            }
        });

    }//fecha oncreate

    public void limpar(){
        txtNome.setText(null);
        txtEmail.setText(null);
        txtColegio.setText(null);
        txtCPF.setText(null);
        txtTurma.setText(null);
        txtTipo.setSelection(0);
        txtSenha.setText(null);
    }

    @Override//Envio de imagem pela galeria.
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GALLERY_INTENT && resultCode == RESULT_OK){
            mProgressDialog.setMessage("Enviando...");
            mProgressDialog.show();
            Uri uri = data.getData();

            StorageReference filepath = mStorage.child("Photos").child(uri.getLastPathSegment());
            filepath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(CadastroActivity.this, "Imagem enviada", Toast.LENGTH_LONG).show();
                    mProgressDialog.dismiss();
                }
            });
        }
    }
}