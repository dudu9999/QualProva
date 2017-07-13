package br.dudu9999.com.qualprova;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class CadastroActivity extends AppCompatActivity {

    private DatabaseReference databaseReferencia = FirebaseDatabase.getInstance().getReference();
    private DatabaseReference usuarioReferencia = databaseReferencia.child("usuarios");
    private StorageReference mStorage;
    private static final int GALLERY_INTENT = 2;
    private ImageView select_image_C;
    private ProgressDialog mProgressDialog;
    private EditText txtEmail;
    private EditText txtSenha;
    private EditText txtNome;
    private EditText txtColegio;
    private EditText txtCPF;
    private EditText txtTurma;
    private EditText txtTipo;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);
        mStorage = FirebaseStorage.getInstance().getReference();




        select_image_C = (ImageView) findViewById(R.id.select_image);
        txtEmail = (EditText) findViewById(R.id.edtEmailCadastro);
        txtSenha = (EditText) findViewById(R.id.edtSenhaCadastro);
        txtNome = (EditText) findViewById(R.id.edtNomeCad);
        txtColegio = (EditText) findViewById(R.id.edtColegioCad);
        txtCPF = (EditText) findViewById(R.id.edtCpfCad);
        txtTipo = (EditText) findViewById(R.id.edtNomeCad);
        txtTurma = (EditText) findViewById(R.id.edtTurma);
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

    }

    @Override
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

    public void cadastraroclick(View view) {
        final ProgressDialog progressDialog = ProgressDialog.show
                (CadastroActivity.this, "Por favor aguarde :D", "Processando...", true);
        (firebaseAuth.createUserWithEmailAndPassword(txtEmail.getText().toString(),txtSenha.getText()
                .toString())).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressDialog.dismiss();

                if (task.isSuccessful()){
                    Toast.makeText(CadastroActivity.this, "Registrado com Sucesso", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(CadastroActivity.this, LoginActivity.class);
                    startActivity(i);
                }else{
                    Log.e("ERRO", task.getException().toString());
                    Toast.makeText(CadastroActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();

                }
            }
        });

        Usuario usuario = new Usuario();
        usuario.setNome(txtNome.getText().toString());
        usuario.setEmail(txtEmail.getText().toString());
        usuario.setColegio(txtColegio.getText().toString());
        usuario.setCpf(txtCPF.getText().toString());
        usuario.setTurma(txtTurma.getText().toString());
        usuario.setTipo(txtTipo.getText().toString());
        usuario.setSenha(txtSenha.getText().toString());
        usuarioReferencia.child("001").setValue(usuario);

        txtColegio = (EditText) findViewById(R.id.edtColegioCad);
        txtCPF = (EditText) findViewById(R.id.edtCpfCad);
        txtTipo = (EditText) findViewById(R.id.edtNomeCad);


    }
}
 /*


        Usuario usuario = new Usuario();
        usuario.setNome("Eduardo");
        usuario.setEmail("eduardo@gmail.com");
        usuario.setColegio("Fundatec");
        usuario.setCpf(123456789);
        usuario.setTurma("TI09");
        usuario.setTipo("Aluno");
        usuario.setSenha("1234");
        usuarioReferencia.child("001").setValue(usuario);
        */