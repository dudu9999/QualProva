package br.dudu9999.com.qualprova.Telas;

import android.Manifest;
import com.android.volley.RequestQueue;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.icu.util.GregorianCalendar;
import android.provider.CalendarContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import br.dudu9999.com.qualprova.Fragmentos.*;
import br.dudu9999.com.qualprova.R;

public class TelaAdcionaProvaActivity extends AppCompatActivity {
    private Prova pv;
    private Boolean edit = false;
    private Prova prova;
    private Button btnDeletar;
    private Spinner spTipo;
    private EditText txtMateriaProva;
    private EditText txtColegioProva;
    private EditText txtTurmaProva;
    private EditText txtConteudoProva;
    private EditText txtId;
    private Button btn_enviar_prova;
    private static final String TAG = "logTelaAdciona";
    private RequestQueue mVolleyRequest;
    private Spinner spDay;
    private Spinner spMouth;
    private Spinner spYear;



    @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_adciona);

            btnDeletar = (Button) findViewById(R.id.btnDeletar);
            txtMateriaProva = (EditText) findViewById(R.id.edt_materia_prova);
            txtColegioProva = (EditText) findViewById(R.id.edt_colegio_prova);
            txtTurmaProva = (EditText) findViewById(R.id.edt_turma_prova);
            txtConteudoProva = (EditText) findViewById(R.id.edt_conteudo_prova);
            btn_enviar_prova = (Button) findViewById(R.id.btn_enviar_prova);
            txtId = (EditText) findViewById(R.id.txtId);
            spDay = (Spinner) findViewById(R.id.spDay);
            spMouth = (Spinner) findViewById(R.id.spMouth);
            spYear = (Spinner) findViewById(R.id.spYear);

            //mascara para cpf
//            SimpleMaskFormatter dataFormater = new SimpleMaskFormatter("NNNNNNNNNNNN");
//            MaskTextWatcher mtw = new MaskTextWatcher(txtDataProva, dataFormater);
//            txtDataProva.addTextChangedListener(mtw);

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
//                txtDataProva.setText(p.getDay().toString());
                spDay.setSelection(p.getDay());
                spMouth.setSelection(p.getMouth());
                spYear.setSelection(p.getYear());

                txtId.setText(p.getId());
                btnDeletar.setVisibility(View.VISIBLE);
            }

            btn_enviar_prova.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Log.d(TAG, "entrou btnenviar antes if");

                    if(   !txtMateriaProva.getText().toString().isEmpty() &&
                            !txtColegioProva.getText().toString().isEmpty() &&
                            !txtTurmaProva.getText().toString().isEmpty() &&
                            !txtConteudoProva.getText().toString().isEmpty() //&&
                            //!txtDataProva.getText().toString().isEmpty()
                      ) {


                        //Exemplo de cast
                        //p.setMateria(Integer.parseInt(txtMateriaProva.getText().toString()));
                        //p.setColegio(Double.parseDouble(etRenda.getText().toString()));




                        if(acao.equalsIgnoreCase("alterar")) {
                            p.setId(txtId.getText().toString());
                            p.setMateria(txtMateriaProva.getText().toString());
                            p.setColegio(txtColegioProva.getText().toString());
                            p.setTurmas(txtTurmaProva.getText().toString());
                            p.setConteudo(txtConteudoProva.getText().toString());
                            p.setDay(Integer.parseInt(spDay.getSelectedItem().toString()));
                            p.setMouth(Integer.parseInt(spMouth.getSelectedItem().toString()));
                            p.setYear(Integer.parseInt(spYear.getSelectedItem().toString()));

                            //abrindo e setando os valores no google agenda
                            agenda(p.getMateria(),p.getColegio(),p.getConteudo(),p.getDay(),p.getMouth(),p.getYear());

                            //Alterando através da chave(key) no firebase setando o novo valor
                            banco.child(p.getId()).setValue(p);
                            Toast.makeText(getBaseContext(),getResources().getString(R.string.toast_sucesso_alterar), Toast.LENGTH_LONG).show();
                            volta_tela_inicio();
                        } else {
                            Prova pp = new Prova();
                            pp.setId(txtId.getText().toString());
                            pp.setMateria(txtMateriaProva.getText().toString());
                            pp.setColegio(txtColegioProva.getText().toString());
                            pp.setTurmas(txtTurmaProva.getText().toString());
                            pp.setConteudo(txtConteudoProva.getText().toString());
                            pp.setDay(Integer.parseInt(spDay.getSelectedItem().toString()));
                            pp.setMouth(Integer.parseInt(spMouth.getSelectedItem().toString()));
                            pp.setYear(Integer.parseInt(spYear.getSelectedItem().toString()));

                            //abrindo e setando os valores no google agenda
                            agenda(pp.getMateria(),pp.getColegio(),pp.getConteudo(),pp.getDay(),pp.getMouth(),pp.getYear());

                            Log.d(TAG, "dataaaaaaaaaaaaa "+pp.getMateria()+pp.getColegio());

                            //Enviando para o Firebase
                            //banco.push().setValue(pp);

                            //Toast.makeText(getBaseContext(),getResources().getString(R.string.toast_sucesso_adicionar), Toast.LENGTH_LONG).show();
                        }
                       // limpar();
                    }else{
                        Toast.makeText(getBaseContext(),getResources().getString(R.string.toast_erro),Toast.LENGTH_LONG).show();
                    }
                }

            });

    }//fefha oncreate

//    public void deletaProva(View view) {
//                //https://qual-prova-firebase.firebaseio.com/Banco/Provas
//
//                StringRequest json = new StringRequest(Request.Method.DELETE,
//                        "https://qual-prova-firebase.firebaseio.com/Banco/Provas/"+pv.getId()+".json",
//                        new Response.Listener<String>() {
//                            @Override
//                            public void onResponse(String response) {
//                                finish();
//                            }
//                        }, new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(TelaAdcionaProvaActivity.this, "Tente novamente", Toast.LENGTH_SHORT).show();
//                    }
//                }
//                );
//
//                mVolleyRequest.add(json);
//            }



                                                //2017,06,27,19,00 ano,mes,dia,
    public void agenda(String materiaProva, String colegio,String descricao, int dia, int mes, int ano) {
        Permiss ();

        Intent intent = new Intent(Intent.ACTION_INSERT);
        intent.setType("vnd.android.cursor.item/event");
        intent.putExtra(CalendarContract.Events.TITLE, "Prova de "+materiaProva);
        intent.putExtra(CalendarContract.Events.EVENT_LOCATION, "Local: "+colegio);
        intent.putExtra(CalendarContract.Events.DESCRIPTION, "Conteudo: "+descricao);
        GregorianCalendar calDate = new GregorianCalendar(ano,mes,dia);
        intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME,calDate.getTimeInMillis());
        GregorianCalendar calDate2 = new GregorianCalendar(ano,mes,dia);
        intent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME,calDate2.getTimeInMillis());
        intent.putExtra(CalendarContract.EXTRA_EVENT_ALL_DAY, true);
        intent.putExtra(CalendarContract.Events.ACCESS_LEVEL, CalendarContract.Events.ACCESS_PRIVATE);
        intent.putExtra(CalendarContract.Events.AVAILABILITY, CalendarContract.Events.AVAILABILITY_BUSY);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);


        }else{
            Log.d(TAG, "dados nulos");
        }
        volta_tela_inicio();

    } // fim da agenda

        private void limpar(){
            txtMateriaProva.setText(null);
            txtColegioProva.setText(null);
            txtTurmaProva.setText(null);
            txtConteudoProva.setText(null);
        } // fim da limpar

        public void Permiss (){
            if (ContextCompat.checkSelfPermission(TelaAdcionaProvaActivity.this, Manifest.permission.WRITE_CALENDAR) != PackageManager.PERMISSION_GRANTED) {
                //Verifica se já mostramos o alerta e o usuário negou alguma vez.
                if (ActivityCompat.shouldShowRequestPermissionRationale(TelaAdcionaProvaActivity.this, android.Manifest.permission.WRITE_CALENDAR)) {
                    //Caso o usuário tenha negado a permissão anteriormente e não tenha marcado o check "nunca mais mostre este alerta"

                    //Podemos mostrar um alerta explicando para o usuário porque a permissão é importante.
                    Toast.makeText(
                            getBaseContext(),
                            "Você já negou antes essa permissão! " +
                                    "\nPara usar a agenda necessitamos dessa permissão!",
                            Toast.LENGTH_LONG).show();

                            /* Além da mensagem indicando a necessidade sobre a permissão,
                               podemos solicitar novamente a permissão */
                    ActivityCompat.requestPermissions(TelaAdcionaProvaActivity.this, new String[]{android.Manifest.permission.WRITE_CALENDAR, android.Manifest.permission.ACCESS_FINE_LOCATION}, 0);
                } else {
                    //Solicita a permissão
                    ActivityCompat.requestPermissions(TelaAdcionaProvaActivity.this, new String[]{android.Manifest.permission.WRITE_CALENDAR, android.Manifest.permission.ACCESS_FINE_LOCATION}, 0);
                }
            }
        }// Fim da Permiss

        public void volta_tela_inicio (){
            Intent vai_tela_inicial = new Intent(TelaAdcionaProvaActivity.this, TelaInicioActivity.class);
            startActivity(vai_tela_inicial);
            finish();
        } // fim da volta_tela_inicio

} //fecha classe