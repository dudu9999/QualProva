package android.dudu9999.com.qualprova;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class PerfilActivity extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    private TextView tvEmail;

    // private TextView tvSenha;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        tvEmail = (TextView) findViewById(R.id.emailP);
        //tvSenha = (TextView) findViewById(R.id.senhaP);

        tvEmail.setText(getIntent().getExtras().getString("Email"));
        // tvSenha.setText(getIntent().getExtras().getString("Senha"));
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
