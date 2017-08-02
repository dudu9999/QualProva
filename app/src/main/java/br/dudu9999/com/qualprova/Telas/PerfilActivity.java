package br.dudu9999.com.qualprova.Telas;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import br.dudu9999.com.qualprova.Fragmentos.MyApplication;
import br.dudu9999.com.qualprova.R;

public class PerfilActivity extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    private TextView tvEmail;
    private TextView tvSenha;
    private TextView tvNome;
    private TextView tvCpf;
    private TextView tvColegio;
    private TextView tvTurma;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        tvEmail = (TextView) findViewById(R.id.tvEmail);
        tvSenha = (TextView) findViewById(R.id.tvSenha);
        tvNome = (TextView) findViewById(R.id.tvNome);
        tvCpf = (TextView) findViewById(R.id.tvCpf);
        tvColegio = (TextView) findViewById(R.id.tvColegio);
        tvTurma = (TextView) findViewById(R.id.tvTurma);




        tvNome.setText(((MyApplication)getApplication()).getUser().getNome());
        tvCpf.setText(((MyApplication)getApplication()).getUser().getCpf());
        tvColegio.setText(((MyApplication)getApplication()).getUser().getColegio());
        tvTurma.setText(((MyApplication)getApplication()).getUser().getTurma());
        tvEmail.setText(((MyApplication)getApplication()).getUser().getEmail());
        tvSenha.setText(((MyApplication)getApplication()).getUser().getSenha());

        ///sairClick();
    }

    public void sairClick(View view) {

        firebaseAuth.signOut();
        Intent i = new Intent(PerfilActivity.this, LoginActivity.class);
        startActivity(i);



    if(firebaseAuth.getCurrentUser() != null){
        Log.i("verificacao", "usuario logado");

    }else{
        Log.i("verificacao", "usuario nao esta logado");
    }
    }
}
