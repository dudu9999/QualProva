package br.dudu9999.com.qualprova.Telas;

import android.content.DialogInterface;
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
    private DatabaseReference databaseReferencia = FirebaseDatabase.getInstance().getReference();
    private DatabaseReference provaReferencia = databaseReferencia.child("provas");



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

        //Firebase
        FirebaseApp.initializeApp(AdcionaActivity.this);
        final FirebaseDatabase db = FirebaseDatabase.getInstance();
        final DatabaseReference banco = db.getReference("banco").child("Provas");


        btn_enviar_prova.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(
                        !txtMateriaProva.getText().toString().isEmpty() &&
                        !txtColegioProva.getText().toString().isEmpty() &&
                        !txtTurmaProva.getText().toString().isEmpty() &&
                        !txtConteudoProva.getText().toString().isEmpty() &&
                        !txtDataProva.getText().toString().isEmpty()) {

                    //Exemplo de cast
                    //p.setMateria(Integer.parseInt(txtMateriaProva.getText().toString()));
                    //p.setColegio(Double.parseDouble(etRenda.getText().toString()));


                    Prova p = new Prova();
                    p.setMateria(txtMateriaProva.getText().toString());
                    p.setColegio(txtColegioProva.getText().toString());
                    p.setTurmas(txtTurmaProva.getText().toString());
                    p.setConteudo(txtConteudoProva.getText().toString());
                    p.setData(txtDataProva.getText().toString());

                    //Alterando através da chave(key) no firebase setando o novo valor
                    banco.child(p.getId()).setValue(p);


                    //Enviando para o Firebase
                    banco.push().setValue(p);

                    limpar();

                    //Toast.makeText(AdcionaActivity.this, "Enviado com sucesso",Toast.LENGTH_SHORT).show();
                    Toast.makeText(getBaseContext(),getResources().getString(R.string.toast_sucesso_adicionar), Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(getBaseContext(),getResources().getString(R.string.toast_erro),Toast.LENGTH_LONG).show();
                    //Toast.makeText(AdcionaActivity.this, "Erro",Toast.LENGTH_SHORT).show();
                }
            }
        });



//        lvProvas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//
//                //objeto que tem a key
//                final int posSelec = i;
//
//                //Buscando objeto selecionado
//                final Prova p = provas.get(i);
//
//                AlertDialog.Builder msg = new AlertDialog.Builder(AdcionaActivity.this);
//                LayoutInflater inflater = getLayoutInflater();
//                View editarCliente = inflater.inflate(R.layout.activity_editar_cliente, null);
//                msg.setView(editarCliente);
//
////
//                final EditText txtMateriaProva = (EditText) findViewById(R.id.edt_materia_prova);
//                final EditText txtColegioProva = (EditText) findViewById(R.id.edt_colegio_prova);
//                final EditText txtTurmaProva = (EditText) findViewById(R.id.edt_turma_prova);
//                final EditText txtConteudoProva = (EditText) findViewById(R.id.edt_conteudo_prova);
//                final EditText txtDataProva = (EditText) findViewById(R.id.edt_data_prova);
//
//
//
//
//                txtMateriaProva.setText(p.getMateria().toString());
//                txtColegioProva.setText(p.getColegio().toString());
//                txtTurmaProva.setText(p.getTurmas().toString());
//                txtConteudoProva.setText(p.getConteudo().toString());
//                txtDataProva.setText(p.getData().toString());
//
//                msg.setTitle(getResources().getString(R.string.alert_titulo));
//                msg.setMessage(getResources().getString(R.string.alert_alterar_msg));
//                msg.setPositiveButton(getResources().getString(R.string.alert_botao_sim), new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//
//                        //Recebendo dados alterados
//                        p.setMateria(txtMateriaProva.getText().toString());
//                        p.setColegio(txtColegioProva.getText().toString());
//                        p.setTurmas(txtTurmaProva.getText().toString());
//                        p.setConteudo(txtConteudoProva.getText().toString());
//                        p.setData(txtDataProva.getText().toString());
//
//
//
//                        //p.setNome(etLogin.getText().toString());
//                        //p.setRG(Integer.parseInt(etRG.getText().toString()));
//                        //p.setRenda(Double.parseDouble(etRenda.getText().toString()));
//
//                        //Alterando através da chave(key) no firebase setando o novo valor
//                        banco.child(p.getId()).setValue(p);
//
//                        Toast.makeText(
//                                getBaseContext(),
//                                getResources().getString(R.string.toast_sucesso_alterar),
//                                Toast.LENGTH_LONG).show();
//                    }
//                });
//                msg.setNegativeButton(getResources().getString(R.string.alert_botao_nao), null);
//                msg.show();
//            }
//        });




    }//fefha oncreate


    private void limpar(){
        txtMateriaProva.setText(null);
        txtColegioProva.setText(null);
        txtTurmaProva.setText(null);
        txtConteudoProva.setText(null);
        txtDataProva.setText(null);
    }

} //fecha classe