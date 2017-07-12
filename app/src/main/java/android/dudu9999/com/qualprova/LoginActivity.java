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

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private EditText txtEmailLogin;
    private EditText txtSenhaLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        txtEmailLogin = (EditText) findViewById(R.id.edtEmailLogin);
        txtSenhaLogin = (EditText) findViewById(R.id.edtSenhaLogin);
        firebaseAuth = FirebaseAuth.getInstance();
    }

    public void loginBtnLoginClick(View view) {
        final ProgressDialog progressDialog = ProgressDialog.show(LoginActivity.this, "Por favor aguarde :D", "Entrando...", true);
        (firebaseAuth.signInWithEmailAndPassword(txtEmailLogin.getText().toString(),txtSenhaLogin.getText().toString()))
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();

                        if (task.isSuccessful()){
                            Toast.makeText(LoginActivity.this, "Entrou :D", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(LoginActivity.this, TelaInicioActivity.class);
                            i.putExtra("Email",firebaseAuth.getCurrentUser().getEmail());

                            startActivity(i);
                        }else{
                            Log.e("ERRO", task.getException().toString());
                            Toast.makeText(LoginActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();

                        }
                    }
                });
    }
}
