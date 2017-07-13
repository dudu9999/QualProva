package br.dudu9999.com.qualprova;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class AdcionaActivity extends AppCompatActivity {
    private RequestQueue mVolleyQueue;
    private Boolean edit = false;
    private Prova prova;
    private Button mBtDeletar;
    private RequestQueue mVolleyRequest;
    private DatabaseReference databaseReferencia = FirebaseDatabase.getInstance().getReference();
    private DatabaseReference provaReferencia = databaseReferencia.child("provas");


    private EditText txtTipoProva;
    private EditText txtMateriaProva;
    private EditText txtColegioProva;
    private EditText txtTurmaProva;
    private EditText txtConteudoProva;
    private EditText txtDataProva;
    private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adciona);
        mVolleyRequest = Volley.newRequestQueue(this);
        txtTipoProva = (EditText) findViewById(R.id.edt_tipo_prova);
        txtMateriaProva = (EditText) findViewById(R.id.edt_materia_prova);
        txtColegioProva = (EditText) findViewById(R.id.edt_colegio_prova);
        txtTurmaProva = (EditText) findViewById(R.id.edt_turma_prova);
        txtConteudoProva = (EditText) findViewById(R.id.edt_conteudo_prova);
        txtDataProva = (EditText) findViewById(R.id.edt_data_prova);



    }

    public void btnEnviarClick(View view) {
        //final ProgressDialog progressDialog = ProgressDialog.show(AdcionaActivity.this, "Por favor aguarde :D", "Processando...", true);


//        Prova prova = new Prova(txtTipoProva, txtMateriaProva, txtColegioProva, txtTurmaProva, txtConteudoProva,txtDataProva);
//        prova.setTipo(txtTipoProva.getText().toString());
//        prova.setMateria(txtMateriaProva.getText().toString());
//        prova.setColegio(txtColegioProva.getText().toString());
//        prova.setTurmas(txtTurmaProva.getText().toString());
//        prova.setConteudo(txtConteudoProva.getText().toString());
//        prova.setData(txtDataProva.getText().toString());
//        provaReferencia.child("002").setValue(prova);

//        (provaReferencia.child("002").setValue(prova)).addOnCompleteListener(new OnCompleteListener<????????>() {
//            @Override
//            public void onComplete(@NonNull Task<????????> task) {
//                progressDialog.dismiss();
//
//                if (task.isSuccessful()){
//                    Toast.makeText(AdcionaActivity.this, "Registrado com Sucesso", Toast.LENGTH_SHORT).show();
//                    Intent i = new Intent(AdcionaActivity.this, TelaInicioActivity.class);
//                    startActivity(i);
//                }else{
//                    Log.e("ERRO", task.getException().toString());
//                    Toast.makeText(AdcionaActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
//
//                }
//            }
//        });

//        Intent enviap = new Intent(AdcionaActivity.this, TelaInicioActivity.class);
//        startActivity(enviap);
///Tentando outro metodo
        try {
            JSONObject obj = new JSONObject();
            obj.put("Tipo", txtTipoProva.getText().toString());
            obj.put("Materia", txtMateriaProva.getText().toString());
            obj.put("Colegio", txtColegioProva.getText().toString());
            obj.put("Turma", txtTurmaProva.getText().toString());
            obj.put("Conteudo", txtConteudoProva.getText().toString());
            obj.put("Data", txtDataProva.getText().toString());


            JsonObjectRequest json = new JsonObjectRequest(Request.Method.POST,
                    "https://qual-prova-firebase.firebaseio.com/Provas.json", obj, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    finish();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(AdcionaActivity.this, "Tente novamente", Toast.LENGTH_SHORT).show();
                }
            }
            );

            mVolleyRequest.add(json);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
/*
        salvar de forma fixa
        Prova prova = new Prova();
        prova.setTipo("Prova");
        prova.setMateria("Dev Android");
        prova.setData("6/6/66");
        prova.setColegio("Fundatec");
        prova.setTurmas("TI08, TI09");
        prova.setConteudo("Estude todos tipos de intent.");
        provaReferencia.child("001").setValue(prova);

* */