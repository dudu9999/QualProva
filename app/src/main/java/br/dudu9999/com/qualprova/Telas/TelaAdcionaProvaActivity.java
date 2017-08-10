package br.dudu9999.com.qualprova.Telas;

import android.annotation.TargetApi;
import android.content.Intent;
import android.icu.util.Calendar;
import android.icu.util.GregorianCalendar;
import android.icu.util.TimeZone;
import android.os.Build;
import android.provider.CalendarContract;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import br.dudu9999.com.qualprova.Fragmentos.*;
import br.dudu9999.com.qualprova.R;

public class TelaAdcionaProvaActivity extends AppCompatActivity {
        private Boolean edit = false;
        private Prova prova;
        private Button mBtDeletar;
    //    private DatabaseReference databaseReferencia = FirebaseDatabase.getInstance().getReference();
    //    private DatabaseReference provaReferencia = databaseReferencia.child("provas");


        private Spinner spTippo;
        private EditText txtMateriaProva;
        private EditText txtColegioProva;
        private EditText txtTurmaProva;
        private EditText txtConteudoProva;
        private EditText txtDataProva;
        private Button btn_enviar_prova;

        private static final String TAG = "logTelaAdciona";

        private FirebaseAuth firebaseAuth;


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_adciona);

            txtMateriaProva = (EditText) findViewById(R.id.edt_materia_prova);
            txtColegioProva = (EditText) findViewById(R.id.edt_colegio_prova);
            txtTurmaProva = (EditText) findViewById(R.id.edt_turma_prova);
            txtConteudoProva = (EditText) findViewById(R.id.edt_conteudo_prova);
            txtDataProva = (EditText) findViewById(R.id.edt_data_prova);
            btn_enviar_prova = (Button) findViewById(R.id.btn_enviar_prova);

            //mascara para cpf
            SimpleMaskFormatter dataFormater = new SimpleMaskFormatter("NNNN,NN,NN,NN,NN");
            MaskTextWatcher mtw = new MaskTextWatcher(txtDataProva, dataFormater);
            txtDataProva.addTextChangedListener(mtw);

            //Firebase
            FirebaseApp.initializeApp(TelaAdcionaProvaActivity.this);
            final FirebaseDatabase db = FirebaseDatabase.getInstance();
            final DatabaseReference banco = db.getReference("Banco").child("Provas");

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

                    Log.d(TAG, "entrou btnenviar antes if");

                    if(   !txtMateriaProva.getText().toString().isEmpty() &&
                            !txtColegioProva.getText().toString().isEmpty() &&
                            !txtTurmaProva.getText().toString().isEmpty() &&
                            !txtConteudoProva.getText().toString().isEmpty() &&
                            !txtDataProva.getText().toString().isEmpty()
                      ) {


                        //Exemplo de cast
                        //p.setMateria(Integer.parseInt(txtMateriaProva.getText().toString()));
                        //p.setColegio(Double.parseDouble(etRenda.getText().toString()));




                        if(acao.equalsIgnoreCase("alterar")) {

                            p.setTipo(txtDataProva.getText().toString());
                            p.setMateria(txtMateriaProva.getText().toString());
                            p.setColegio(txtColegioProva.getText().toString());
                            p.setTurmas(txtTurmaProva.getText().toString());
                            p.setConteudo(txtConteudoProva.getText().toString());
                            p.setData(txtDataProva.getText().toString());

                            //Alterando através da chave(key) no firebase setando o novo valor
                            banco.child(p.getId()).setValue(p);
                            Toast.makeText(getBaseContext(),getResources().getString(R.string.toast_sucesso_alterar), Toast.LENGTH_LONG).show();
                            volta_tela_inicio();
                        } else {
                            Prova pp = new Prova();
                            pp.setTipo(txtDataProva.getText().toString());
                            pp.setMateria(txtMateriaProva.getText().toString());
                            pp.setColegio(txtColegioProva.getText().toString());
                            pp.setTurmas(txtTurmaProva.getText().toString());
                            pp.setConteudo(txtConteudoProva.getText().toString());
                            pp.setData(txtDataProva.getText().toString());
                            Log.d(TAG, "dataaaaaaaaaaaaa "+pp.getMateria()+pp.getColegio()+pp.getData());

                            agenda(pp.getMateria(),pp.getColegio(),pp.getData());

                            //Enviando para o Firebase
                            //banco.push().setValue(pp);
                            volta_tela_inicio();
                            //Toast.makeText(getBaseContext(),getResources().getString(R.string.toast_sucesso_adicionar), Toast.LENGTH_LONG).show();
                        }
                       // limpar();
                    }else{
                        Toast.makeText(getBaseContext(),getResources().getString(R.string.toast_erro),Toast.LENGTH_LONG).show();
                    }
                }
            });
        }//fefha oncreate

                                                //2017,06,27,19,00 ano,mes,dia,
    public void agenda(String materiaProva, String colegio, String data) {
        Calendar begin = new GregorianCalendar(TimeZone.getTimeZone(data));
        Calendar end = new GregorianCalendar(TimeZone.getTimeZone(data));
        Intent intent = new Intent(Intent.ACTION_INSERT)
                .setData(CalendarContract.Events.CONTENT_URI)
                .putExtra(CalendarContract.Events.TITLE, "Prova de "+materiaProva)
                .putExtra(CalendarContract.Events.EVENT_LOCATION, colegio)
                .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, begin)
                .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, end);
        Log.d(TAG, "dataaaaaaaaaaaaa2 data: "+data+" colegio: "+colegio+" materia: "+materiaProva);

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
            Log.d(TAG, "dataaaaaaaaaaaaa3 data: "+data+" colegio: "+colegio+" materia: "+materiaProva);

        }else{
            Log.d(TAG, "dataaaaaaaaaaaaa4 data: "+data+" colegio: "+colegio+" materia: "+materiaProva);
        }
    }

        private void limpar(){
            txtMateriaProva.setText(null);
            txtColegioProva.setText(null);
            txtTurmaProva.setText(null);
            txtConteudoProva.setText(null);
            txtDataProva.setText(null);
        }

        public void volta_tela_inicio (){
            Intent vai_tela_inicial = new Intent(TelaAdcionaProvaActivity.this, TelaInicioActivity.class);
            startActivity(vai_tela_inicial);
            finish();
        }

} //fecha classe