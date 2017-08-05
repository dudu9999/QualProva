package br.dudu9999.com.qualprova.Telas;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import br.dudu9999.com.qualprova.Fragmentos.*;
import br.dudu9999.com.qualprova.R;

public class AdcionaActivity extends AppCompatActivity {
        private RequestQueue mVolleyQueue;
        private Boolean edit = false;
        private Prova prova;
        private Button mBtDeletar;
        private RequestQueue mVolleyRequest;
    //    private DatabaseReference databaseReferencia = FirebaseDatabase.getInstance().getReference();
    //    private DatabaseReference provaReferencia = databaseReferencia.child("provas");


        private Spinner spTippo;
        private EditText txtMateriaProva;
        private EditText txtColegioProva;
        private EditText txtTurmaProva;
        private EditText txtConteudoProva;
        private EditText txtDataProva;
        private Button btn_enviar_prova;



        private FirebaseAuth firebaseAuth;


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_adciona);

            mVolleyRequest = Volley.newRequestQueue(this);


            txtMateriaProva = (EditText) findViewById(R.id.edt_materia_prova);
            txtColegioProva = (EditText) findViewById(R.id.edt_colegio_prova);
            txtTurmaProva = (EditText) findViewById(R.id.edt_turma_prova);
            txtConteudoProva = (EditText) findViewById(R.id.edt_conteudo_prova);
            txtDataProva = (EditText) findViewById(R.id.edt_data_prova);
            btn_enviar_prova = (Button) findViewById(R.id.btn_enviar_prova);

            //mascara para cpf
            SimpleMaskFormatter dataFormater = new SimpleMaskFormatter("NN/NN/NN");
            MaskTextWatcher mtw = new MaskTextWatcher(txtDataProva, dataFormater);
            txtDataProva.addTextChangedListener(mtw);

            //Firebase
            FirebaseApp.initializeApp(AdcionaActivity.this);
            final FirebaseDatabase db = FirebaseDatabase.getInstance();
            final DatabaseReference banco = db.getReference("banco").child("Provas");


            //pegando objeto da telainicio
            final String acao = getIntent().getStringExtra("acao");
            final Prova p = (Prova) getIntent().getSerializableExtra("prova");

            if(acao.equalsIgnoreCase("alterar")) {
                //preenchendo os campos
                txtMateriaProva.setText(p.getMateria());
                txtColegioProva.setText(p.getColegio());
                txtTurmaProva.setText(p.getTurmas());
                txtConteudoProva.setText(p.getConteudo());
                txtDataProva.setText(p.getData());
            }

            btn_enviar_prova.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if(acao.equalsIgnoreCase("cadastrar")
//                            &&
//                            !txtMateriaProva.getText().toString().isEmpty() &&
//                            !txtColegioProva.getText().toString().isEmpty() &&
//                            !txtTurmaProva.getText().toString().isEmpty() &&
//                            !txtConteudoProva.getText().toString().isEmpty() &&
//                            !txtDataProva.getText().toString().isEmpty()
                      ) {

                        //Exemplo de cast
                        //p.setMateria(Integer.parseInt(txtMateriaProva.getText().toString()));
                        //p.setColegio(Double.parseDouble(etRenda.getText().toString()));

                        Prova p = new Prova();
                        p.setTipo(txtDataProva.getText().toString());
                        p.setMateria(txtMateriaProva.getText().toString());
                        p.setColegio(txtColegioProva.getText().toString());
                        p.setTurmas(txtTurmaProva.getText().toString());
                        p.setConteudo(txtConteudoProva.getText().toString());
                        p.setData(txtDataProva.getText().toString());


                        if(acao.equalsIgnoreCase("alterar;")) {

                            //Alterando através da chave(key) no firebase setando o novo valor
                            banco.child(p.getId()).setValue(p);
                        } else {
                            //Enviando para o Firebase
                             banco.push().setValue(p);
                            volta_tela_inicio();
                        }

                       // limpar();

                        Toast.makeText(getBaseContext(),getResources().getString(R.string.toast_sucesso_adicionar), Toast.LENGTH_LONG).show();
                    }else{
                        Toast.makeText(getBaseContext(),getResources().getString(R.string.toast_erro),Toast.LENGTH_LONG).show();

                    }
                }
            });


        }//fefha oncreate


        private void limpar(){
            txtMateriaProva.setText(null);
            txtColegioProva.setText(null);
            txtTurmaProva.setText(null);
            txtConteudoProva.setText(null);
            txtDataProva.setText(null);
        }

        public void volta_tela_inicio (){
            Intent vai_tela_inicial = new Intent(AdcionaActivity.this, TelaInicioActivity.class);
            startActivity(vai_tela_inicial);
        }

} //fecha classe