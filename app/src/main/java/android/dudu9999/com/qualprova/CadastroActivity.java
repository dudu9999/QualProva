package android.dudu9999.com.qualprova;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CadastroActivity extends AppCompatActivity {

    private DatabaseReference databaseReferencia = FirebaseDatabase.getInstance().getReference();
    private DatabaseReference usuarioReferencia = databaseReferencia.child("usuarios");

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

        txtEmail = (EditText) findViewById(R.id.edtEmailCadastro);
        txtSenha = (EditText) findViewById(R.id.edtSenhaCadastro);
        txtNome = (EditText) findViewById(R.id.edtNomeCad);
        txtColegio = (EditText) findViewById(R.id.edtColegioCad);
        txtCPF = (EditText) findViewById(R.id.edtCpfCad);
        txtTipo = (EditText) findViewById(R.id.edtNomeCad);
        txtTurma = (EditText) findViewById(R.id.edtTurma);
        firebaseAuth = FirebaseAuth.getInstance();

    }

    public void cadastraroclick(View view) {
        final ProgressDialog progressDialog = ProgressDialog.show(CadastroActivity.this, "Por favor aguarde :D", "Processando...", true);
        (firebaseAuth.createUserWithEmailAndPassword(txtEmail.getText().toString(),txtSenha.getText().toString())).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
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
        Prova prova = new Prova();
        prova.setTipo("Prova");
        prova.setMateria("Dev Android");
        prova.setData("6/6/66");
        prova.setColegio("Fundatec");
        prova.setTurmas("TI08, TI09");
        prova.setConteudo("Estude todos tipos de intent.");
        provaReferencia.child("001").setValue(prova);

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