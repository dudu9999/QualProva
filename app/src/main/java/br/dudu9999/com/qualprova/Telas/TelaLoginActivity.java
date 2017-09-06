package br.dudu9999.com.qualprova.Telas;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import br.dudu9999.com.qualprova.Objetos.MyApplication;
import br.dudu9999.com.qualprova.Objetos.Usuario;
import br.dudu9999.com.qualprova.R;


public class TelaLoginActivity extends AppCompatActivity {

    private Usuario userLocal;
    private Usuario userLogado;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private EditText txtNomeLogin;
    private EditText txtEmailLogin;
    private EditText txtSenhaLogin;
    private Button btnLogin;
    private Button btnCriarConta;
    private String TAG = "Log";
    private ProgressBar progress;
    private String salvaSenha;
    private String salvaNome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //referencias
        txtNomeLogin = (EditText)    findViewById(R.id.edtNomeLogin);
        txtEmailLogin = (EditText)    findViewById(R.id.edtEmailLogin);
        txtSenhaLogin = (EditText)    findViewById(R.id.edtSenhaLogin);
        btnLogin =      (Button)      findViewById(R.id.btnLogin);
        btnCriarConta = (Button)      findViewById(R.id.btnCriarConta);
        progress =      (ProgressBar) findViewById(R.id.login_progress);

        //Pegando Referencia Firebase
        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    userLogado = new Usuario();
                    userLogado.setUID(user.getUid());
                    userLogado.setEmail(user.getEmail());
                    userLogado.setNome(salvaNome);
                    userLogado.setSenha(salvaSenha);
                    Log.d(TAG, "USUARIOLOG" + userLogado.getUID().toString() + userLogado.getEmail().toString());

                    ((MyApplication) getApplication()).setUser(userLogado);

                    Intent it = new Intent(TelaLoginActivity.this, TelaInicioActivity.class);
                    startActivity(it);
                    finish();

                } else {
                    // User is signed out
                    Log.d(TAG, "USUARIO NAO ESTA LOGADO");
                }
                // ...
            }
        };

        //Clique do botão Logar
        btnLogin.setOnClickListener(new View.OnClickListener() {
//                Usuario u = new Usuario();
//                u.setEmail(txtEmail.getText().toString());
//                u.setSenha(txtSenha.getText().toString());
            @Override
            public void onClick(final View view) {

                if (!txtEmailLogin.getText().toString().isEmpty() &&
                    !txtSenhaLogin.getText().toString().isEmpty())
                {

                    //Iniciando progress
                    progress.setVisibility(View.VISIBLE);

                    //Criando objeto usuario
                    userLocal = new Usuario();
                    userLocal.setEmail(txtEmailLogin.getText().toString());
                    userLocal.setSenha(txtSenhaLogin.getText().toString());
                    userLocal.setNome(txtNomeLogin.getText().toString());


                    mAuth.signInWithEmailAndPassword(userLocal.getEmail(), userLocal.getSenha()).addOnCompleteListener(TelaLoginActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            Log.d(TAG, "signInWithEmail:onComplete:" + task.isSuccessful());

                            if (task.isSuccessful()) {



                                ((MyApplication) getApplication()).setUser(userLocal);

                                Intent inte = new Intent(TelaLoginActivity.this, TelaSplashActivity.class);

                                //inte.putExtra("Email", mAuth.getCurrentUser().getEmail());


//                                if ((((MyApplication) getApplication()).getUser().getNome()).equals(null)) {
//                                    Toast.makeText(getBaseContext(), "Preencha seus dados no perfil!", Toast.LENGTH_LONG).show();
//                                }else{
//                                    Toast.makeText(getBaseContext(), "Entou\nSeja bem vindo.", Toast.LENGTH_LONG).show();
//                                      mBtExcluir.setVisibility(View.VISIBLE);
//                                      mBtSalvar.setVisibility(View.VISIBLE);
//                                }
                                startActivity(inte);
                                finish();


                            } else {
                                Toast.makeText(getBaseContext(), R.string.toast_sucesso_erro_ao_logar, Toast.LENGTH_SHORT).show();

                            }//fecha if

                            //Removendo progress
                            progress.setVisibility(View.INVISIBLE);
                        }//fecha onComplete
                    });//fecha listener
                } else {
                    Toast.makeText(getBaseContext(), R.string.toast_erro_dados, Toast.LENGTH_SHORT).show();
                }

            }//fecha onClick
        });//fecha listener onclick

        //Clique do botão CriarConta
        btnCriarConta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!txtSenhaLogin.getText().toString().isEmpty() &&
                        !txtSenhaLogin.getText().toString().isEmpty()) {

                    //Iniciando progress
                    progress.setVisibility(View.VISIBLE);

                    //Criando objeto usuario
                    userLocal = new Usuario();
                    userLocal.setEmail(txtEmailLogin.getText().toString());
                    userLocal.setSenha(txtSenhaLogin.getText().toString());
                    userLocal.setNome(txtNomeLogin.getText().toString());

                    mAuth.createUserWithEmailAndPassword(userLocal.getEmail(), userLocal.getSenha())
                            .addOnCompleteListener(TelaLoginActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());

                                    if (task.isSuccessful()) {

                                        //Salvando localmente(setando cliente)
                                        ((MyApplication)getApplication()).setUser(userLocal);
                                       salvaSenha = userLocal.getSenha();
                                       salvaNome  = userLocal.getNome();

                                        Toast.makeText(
                                                getBaseContext(),
                                                "Conta criada com sucesso!",
                                                Toast.LENGTH_SHORT).show();
                                        Intent it = new Intent(TelaLoginActivity.this, TelaInicioActivity.class);
                                        startActivity(it);

                                    } else {

                                        Toast.makeText(
                                                getBaseContext(),
                                                "Erro ao criar conta!",
                                                Toast.LENGTH_SHORT).show();


                                    }

                                    //Removendo progress
                                    progress.setVisibility(View.INVISIBLE);
                                }//fecha onComplete
                            });//fecha mAuth

                }else{
                    Toast.makeText(
                            getBaseContext(),
                            "Preencha os dados para criar a conta!",
                            Toast.LENGTH_SHORT).show();
                }
            }//fecha onclick
        });//fecha listener btnCriarConta

    }//fecha oncreate

        @Override
        protected void onStart() {
            super.onStart();
            //Inserindo o Listener no onStart
            mAuth.addAuthStateListener(mAuthListener);
        }// fecha onstart

            @Override
            protected void onStop() {
                super.onStop();
                //Removendo o Listener no onStop
                if (mAuthListener != null) {
                    mAuth.removeAuthStateListener(mAuthListener);
                }
            }//fecha onStop

    }//fecha TelaLogin