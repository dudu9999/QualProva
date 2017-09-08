package br.dudu9999.com.qualprova.Telas;

//Exemplo de cast
//p.setMateria(Integer.parseInt(txtMateriaProva.getText().toString()));
//p.setColegio(Double.parseDouble(etRenda.getText().toString()));

import android.Manifest;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.icu.util.GregorianCalendar;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.provider.CalendarContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import br.dudu9999.com.qualprova.Objetos.*;
import br.dudu9999.com.qualprova.R;
import br.dudu9999.com.qualprova.Objetos.MyApplication;
import br.dudu9999.com.qualprova.Objetos.Usuario;

public class TelaAdcionaProvaActivity extends AppCompatActivity {

    private Button btnDeletar;
    private Button btn_salva_prova;
    private Button btn_enviar_prova;
    private EditText txtMateriaProva;
    private EditText txtColegioProva;
    private EditText txtTurmaProva;
    private EditText txtConteudoProva;
    private EditText txtId;
    private static final String TAG = "logTelaAdciona";
    private EditText edtDay;
    private EditText edtMouth;
    private EditText edtYear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adciona);
        Log.d("TELA_ADD", "TEST3:  " + ((MyApplication)getApplication()).getUser());

        //pegando objeto da telainicio
        final String selectTipo = getIntent().getStringExtra("selectTipo");

//        if (selectTipo.equals("Professor")){
//            Intent.putExtra("selectTipo","Professor");
//        }else{
//            Intent.putExtra("selectTipo","Aluno");
//        }

        Log.d(TAG, "Dados: " + selectTipo);


        btnDeletar = (Button) findViewById(R.id.btnDeletar);
        btn_salva_prova = (Button) findViewById(R.id.btn_salvar_prova);
        btn_enviar_prova = (Button) findViewById(R.id.btn_enviar_prova);
        txtId = (EditText) findViewById(R.id.txtId);
        txtMateriaProva = (EditText) findViewById(R.id.edt_materia_prova);
        txtColegioProva = (EditText) findViewById(R.id.edt_colegio_prova);
        txtTurmaProva = (EditText) findViewById(R.id.edt_turma_prova);
        txtConteudoProva = (EditText) findViewById(R.id.edt_conteudo_prova);
        edtDay = (EditText) findViewById(R.id.edt_day);
        edtMouth = (EditText) findViewById(R.id.edt_mouth);
        edtYear = (EditText) findViewById(R.id.edt_year);

        //mascara para Dia
        SimpleMaskFormatter diaFormater = new SimpleMaskFormatter("NN");
        MaskTextWatcher mtw = new MaskTextWatcher(edtDay, diaFormater);
        edtDay.addTextChangedListener(mtw);

        //mascara para Mes
        SimpleMaskFormatter MesFormater = new SimpleMaskFormatter("NN");
        MaskTextWatcher mtw2 = new MaskTextWatcher(edtMouth, MesFormater);
        edtMouth.addTextChangedListener(mtw2);

        //mascara para Ano
        SimpleMaskFormatter anoFormater = new SimpleMaskFormatter("NNNN");
        MaskTextWatcher mtw3 = new MaskTextWatcher(edtYear, anoFormater);
        edtYear.addTextChangedListener(mtw3);

        Permiss();

        //Firebase
        FirebaseApp.initializeApp(TelaAdcionaProvaActivity.this);
        final FirebaseDatabase db = FirebaseDatabase.getInstance();
        final DatabaseReference banco = db.getReference("Banco").child("Provas");

        //pegando objeto da telainicio
        final String acao = getIntent().getStringExtra("acao");
        final Prova p = (Prova) getIntent().getSerializableExtra("prova");

        if (acao.equalsIgnoreCase("cadastrar")) {
            btnDeletar.setVisibility(View.INVISIBLE);
        }

        if (acao.equalsIgnoreCase("alterar")) {
            Permiss();

            //preenchendo os campos
            txtMateriaProva.setText(p.getMateria());
            txtColegioProva.setText(p.getColegio());
            txtTurmaProva.setText(p.getTurmas());
            txtConteudoProva.setText(p.getConteudo());
            edtDay.setText(p.getDay());
            edtMouth.setText(p.getMouth());
            edtYear.setText(p.getYear());
//              txtDataProva.setText(p.getConteudo());
//              txtDataProva.setText(p.getDay().toString());

            txtId.setText(p.getId());
            btnDeletar.setVisibility(View.VISIBLE);
        }

        btn_salva_prova.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.d(TAG, "entrou btnenviar antes if");

                if (!txtMateriaProva.getText().toString().isEmpty() && !txtColegioProva.getText().toString().isEmpty() && !txtTurmaProva.getText().toString().isEmpty() && !txtConteudoProva.getText().toString().isEmpty()//&& //&&
                    //   !txtDataProva.getText().toString().isEmpty()
                        ) {


                    if (acao.equalsIgnoreCase("alterar")) { //Clicando na lista pra alterar

                        p.setId(txtId.getText().toString());
                        p.setMateria(txtMateriaProva.getText().toString());
                        p.setColegio(txtColegioProva.getText().toString());
                        p.setTurmas(txtTurmaProva.getText().toString());
                        p.setConteudo(txtConteudoProva.getText().toString());
                        //p.setDataProva(txtDataProva.getText().toString());
                        p.setDay(edtDay.getText().toString());
                        p.setMouth(edtMouth.getText().toString());
                        p.setYear(edtYear.getText().toString());

                        //Alterando através da chave(key) no firebase setando o novo valor
                        banco.child(p.getId().toString()).setValue(p);
                        Toast.makeText(getBaseContext(), getResources().getString(R.string.toast_sucesso_alterar), Toast.LENGTH_LONG).show();

                        volta_tela_inicio();

                    } else {//Clicando no botao para inserir

                        Prova pp = new Prova();
                        pp.setId(txtId.getText().toString());
                        pp.setMateria(txtMateriaProva.getText().toString());
                        pp.setColegio(txtColegioProva.getText().toString());
                        pp.setTurmas(txtTurmaProva.getText().toString());
                        pp.setConteudo(txtConteudoProva.getText().toString());
                        pp.setDay(edtDay.getText().toString());
                        pp.setMouth(edtMouth.getText().toString());
                        pp.setYear(edtYear.getText().toString());
                        // DataProvaSave = (txtDataProva.getText().toString());

                        //Enviando para o Firebase
                        banco.push().setValue(pp);

                        Toast.makeText(getBaseContext(), getResources().getString(R.string.toast_sucesso_adicionar), Toast.LENGTH_LONG).show();
                    }
                    // limpar();
                } else {
                    Toast.makeText(getBaseContext(), getResources().getString(R.string.toast_erro), Toast.LENGTH_LONG).show();
                }
                volta_tela_inicio();
            }

        });

        btnDeletar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (acao.equalsIgnoreCase("alterar")) {
                    banco.child(String.valueOf(p.getId())).removeValue();
                    //  limpar();
                    volta_tela_inicio();

                }
            }
        });


        btn_enviar_prova.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //decrementando pra ficar mes certo (bug) //
                int mesdois = Integer.parseInt(edtMouth.getText().toString());
                mesdois--;

                // evento notificação, vibração e toque //
                NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                PendingIntent p = PendingIntent.getActivity(TelaAdcionaProvaActivity.this, 0, new Intent(TelaAdcionaProvaActivity.this, Atividade2.class), 0);

                NotificationCompat.Builder builder = new NotificationCompat.Builder(TelaAdcionaProvaActivity.this);
                builder.setTicker("Ticker Texto Notificacao");
                builder.setContentTitle("Prova de " + txtMateriaProva.getText().toString());
                //builder.setContentText("Descri��o");
                builder.setSmallIcon(R.mipmap.ic_launcher_round);
                builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher));
                builder.setContentIntent(p);

                NotificationCompat.InboxStyle style = new NotificationCompat.InboxStyle();
                String [] descs = new String[]{
                        "Local: " + txtColegioProva.getText().toString(),
                        "Conteudo: " + txtConteudoProva.getText().toString(),
                        "Dia: "+edtDay.getText().toString()+"/"+edtMouth.getText().toString()+"/"+edtYear.getText().toString(),
                        "Estude Para Prova!"};
                for(int i = 0; i < descs.length; i++){
                    style.addLine(descs[i]);
                }
                builder.setStyle(style);

                Notification n = builder.build();
                n.vibrate = new long[]{150, 300, 150, 600};
                n.flags = Notification.FLAG_AUTO_CANCEL;
                nm.notify(R.mipmap.ic_launcher, n);

                try{
                    Uri som = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                    Ringtone toque = RingtoneManager.getRingtone(TelaAdcionaProvaActivity.this, som);
                    toque.play();
                }
                catch(Exception e){}
                // FIM do evento notificação, vibração e toque //

               //pedindo a permição
                Permiss();

                //evento de adicionar no google agenda
                Intent intent = new Intent(Intent.ACTION_INSERT);
                intent.setType("vnd.android.cursor.item/event");
                GregorianCalendar calDate = new GregorianCalendar(Integer.parseInt(edtYear.getText().toString()), mesdois, Integer.parseInt(edtDay.getText().toString()));
                intent.putExtra(CalendarContract.Events.TITLE, "Prova de " + txtMateriaProva.getText().toString());
                intent.putExtra(CalendarContract.Events.EVENT_LOCATION, "Local: " + txtColegioProva.getText().toString());
                intent.putExtra(CalendarContract.Events.DESCRIPTION, "Conteudo: " + txtConteudoProva.getText().toString());
                intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, calDate.getTimeInMillis());
                intent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME, calDate.getTimeInMillis());
                intent.putExtra(CalendarContract.EXTRA_EVENT_ALL_DAY, true);
                intent.putExtra(CalendarContract.Events.ACCESS_LEVEL, CalendarContract.Events.ACCESS_PRIVATE);
                intent.putExtra(CalendarContract.Events.AVAILABILITY, CalendarContract.Events.AVAILABILITY_BUSY);
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                } else {
                    Log.d(TAG, "dados nulos");
                }
            }
        });

    }//fefha oncreate

    private void limpar() {
        txtId.setText(null);
        txtMateriaProva.setText(null);
        txtColegioProva.setText(null);
        txtTurmaProva.setText(null);
        txtConteudoProva.setText(null);
    } // fim da limpar

    public void Permiss() {
        if (ContextCompat.checkSelfPermission(TelaAdcionaProvaActivity.this, Manifest.permission.WRITE_CALENDAR) != PackageManager.PERMISSION_GRANTED) {
            //Verifica se já mostramos o alerta e o usuário negou alguma vez.
            if (ActivityCompat.shouldShowRequestPermissionRationale(TelaAdcionaProvaActivity.this, android.Manifest.permission.WRITE_CALENDAR)) {
                //Caso o usuário tenha negado a permissão anteriormente e não tenha marcado o check "nunca mais mostre este alerta"

                //Podemos mostrar um alerta explicando para o usuário porque a permissão é importante.
                Toast.makeText(getBaseContext(), "Você já negou antes essa permissão! " + "\nPara usar a agenda necessitamos dessa permissão!", Toast.LENGTH_LONG).show();

                            /* Além da mensagem indicando a necessidade sobre a permissão,
                               podemos solicitar novamente a permissão */
                ActivityCompat.requestPermissions(TelaAdcionaProvaActivity.this, new String[]{android.Manifest.permission.WRITE_CALENDAR, android.Manifest.permission.ACCESS_FINE_LOCATION}, 0);
            } else {
                //Solicita a permissão
                ActivityCompat.requestPermissions(TelaAdcionaProvaActivity.this, new String[]{android.Manifest.permission.WRITE_CALENDAR, android.Manifest.permission.ACCESS_FINE_LOCATION}, 0);
            }
        }
    }// Fim da Permiss

    public void volta_tela_inicio() {
        Intent vai_tela_inicial = new Intent(TelaAdcionaProvaActivity.this, TelaInicioActivity.class);

        startActivity(vai_tela_inicial);
        finish();
    } // fim da volta_tela_inicio
} //fecha classe