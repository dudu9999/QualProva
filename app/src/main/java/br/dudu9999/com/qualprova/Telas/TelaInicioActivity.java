package br.dudu9999.com.qualprova.Telas;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import android.widget.ListView;
import android.widget.Toast;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;
import br.dudu9999.com.qualprova.Objetos.MyApplication;
import br.dudu9999.com.qualprova.Objetos.Prova;
import br.dudu9999.com.qualprova.Objetos.Usuario;
import java.util.ArrayList;
import br.dudu9999.com.qualprova.R;


public class TelaInicioActivity extends AppCompatActivity {

    private Usuario userLocal;
    private Usuario userLogado;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private ListView lista_prova;
    private ArrayAdapter<Prova> adapter;
    private ArrayList<Prova> provas;
    private FloatingActionButton fab;
    private String nomedraw = "User";
    private String TAG = "Log";
    //Drawer
    private Drawer result = null;

    //Toolbar
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_inicio);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Permiss ();
        //pegando objeto da telainicio
        final String selectTipo = getIntent().getStringExtra("selectTipo");
        final String usuariop = getIntent().getStringExtra("usuariop");

        lista_prova = (ListView) findViewById(R.id.lista_prova);
        fab = (FloatingActionButton) findViewById(R.id.fab);

//        fab.setVisibility(View.INVISIBLE);

        Log.d("TELAINICIO", "TEST3:  " + ((MyApplication)getApplication()).getUser());

//        if(String.valueOf(selectTipo).equals("Professor")) {
//            fab.setVisibility(View.VISIBLE);
//        }
//        if(String.valueOf(selectTipo).equals("Aluno")) {
//            fab.setVisibility(View.INVISIBLE);
//        }

        //if -----------------------------------------------------------
        if (((MyApplication)getApplication()).getUser().getNome() == null){
            nomedraw = usuariop;
        }else{
            nomedraw = ((MyApplication)getApplication()).getUser().getNome();
        }

        provas = new ArrayList<>();
        adapter = new ArrayAdapter<Prova>(
                TelaInicioActivity.this,android.R.layout.simple_list_item_1,provas
        );

        lista_prova.setAdapter(adapter);

        //Firebase
        mAuth = FirebaseAuth.getInstance();
        FirebaseApp.initializeApp(TelaInicioActivity.this);
        final FirebaseDatabase db = FirebaseDatabase.getInstance();
        final DatabaseReference banco = db.getReference("Banco").child("Provas");
        final Usuario usuario = new Usuario();


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addprov = new Intent(TelaInicioActivity.this, TelaAdcionaProvaActivity.class);
                addprov.putExtra("acao","cadastrar");
                Toast.makeText(TelaInicioActivity.this, "Clicou para Adicionar!", Toast.LENGTH_SHORT).show();
                startActivity(addprov);


            }
        });

        //####################### SÓ O CABEÇALHO #######################
        AccountHeader headerResult = new AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(R.drawable.fundo_verde )
                .addProfiles(
                new ProfileDrawerItem().withName(nomedraw).withEmail(((MyApplication)getApplication()).getUser().getEmail())
                        .withIcon(getResources().getDrawable(R.mipmap.ic_launcher))
                )
                .withOnAccountHeaderListener(new AccountHeader.OnAccountHeaderListener(){
                    @Override
                    public boolean onProfileChanged(View view, IProfile profile, boolean currentProfile) {
                        return false;
                    }
                })
                .build();

        //Menu
        result = new DrawerBuilder()
                .withActivity(this)
                .withTranslucentStatusBar(false)
                .withToolbar(toolbar)
                .withAccountHeader(headerResult)
                .withSavedInstance(savedInstanceState)
                .addDrawerItems(
                        new PrimaryDrawerItem().withName("Inicio").withIdentifier(0).withIcon(R.mipmap.ic_home),
                        new PrimaryDrawerItem().withName("Perfil").withIdentifier(10).withIcon(R.mipmap.ic_user),
                        new PrimaryDrawerItem().withName("Sobre").withIdentifier(20).withIcon(R.mipmap.ic_about),
                        new PrimaryDrawerItem().withName("Trocar de Conta").withIdentifier(30).withIcon(R.mipmap.ic_out),
                        new PrimaryDrawerItem().withName("Fechar App").withIdentifier(31).withIcon(R.mipmap.ic_out)
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        switch ((int)drawerItem.getIdentifier()){
                            case 0:
                                Intent Ihome  = new Intent(TelaInicioActivity.this, TelaInicioActivity.class);
                                startActivity(Ihome);
                                Toast.makeText(getBaseContext(),"Você clicou no menu Inicio",Toast.LENGTH_LONG).show();
                                break;

                            case 10:
                                Intent ICad = new Intent(TelaInicioActivity.this, TelaPerfilActivity.class);
                                startActivity(ICad);
                                Toast.makeText(getBaseContext(),"Você clicou no menu Perfil",Toast.LENGTH_LONG).show();
                                break;

                            case 20:
                                Intent Isobre = new Intent(TelaInicioActivity.this, TelaAboutActivity.class);
                                startActivity(Isobre);
                                Toast.makeText(getBaseContext(),"Você clicou no menu Sobre",Toast.LENGTH_LONG).show();
                                break;

                            case 30:
                                mAuth.signOut();
                                finish();
                                Intent Isair = new Intent(getApplicationContext(), TelaLoginActivity.class);
                                startActivity(Isair);
                                break;
                            case 31:
                                Intent intent = new Intent(Intent.ACTION_MAIN);
                                intent.addCategory(Intent.CATEGORY_HOME);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                break;
                        }
                        return false;
                    }
                }).build();


        //Traz os dados
        banco.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                YoYo.with(Techniques.DropOut).duration(3000).playOn(lista_prova);
                provas.clear();
                    for(DataSnapshot data: dataSnapshot.getChildren()){
                        Prova p = data.getValue(Prova.class);
                        p.setId(data.getKey()); //Colocando key manualmente no objeto
                        provas.add(p);
                    }

                adapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });




        lista_prova.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Prova prova = ((ArrayAdapter<Prova>)parent.getAdapter()).getItem(position);
                Intent intent = new Intent(TelaInicioActivity.this, TelaAdcionaProvaActivity.class);

                /*intent.putExtra("tipo", prova.get);
                intent.putExtra("materia", prova.getMateria());
                intent.putExtra("data", prova.getData());
                intent.putExtra("colegio", prova.getColegio());
                intent.putExtra("turmas", prova.getTurmas());
                intent.putExtra("conteudo", prova.getConteudo());*/
                intent.putExtra("key", prova.getId());
                //Prova.setId(getId);
                intent.putExtra("acao","alterar");
                intent.putExtra("prova", prova);


                Toast.makeText(TelaInicioActivity.this, "Clicou para editar!", Toast.LENGTH_SHORT).show();


                startActivity(intent);




            }
        });




    }//fecha oncreate
    public void Permiss (){
        if (ContextCompat.checkSelfPermission(TelaInicioActivity.this, Manifest.permission.WRITE_CALENDAR) != PackageManager.PERMISSION_GRANTED) {
            //Verifica se já mostramos o alerta e o usuário negou alguma vez.
            if (ActivityCompat.shouldShowRequestPermissionRationale(TelaInicioActivity.this, android.Manifest.permission.WRITE_CALENDAR)) {
                //Caso o usuário tenha negado a permissão anteriormente e não tenha marcado o check "nunca mais mostre este alerta"

                //Podemos mostrar um alerta explicando para o usuário porque a permissão é importante.
                Toast.makeText(
                        getBaseContext(),
                        "Você já negou antes essa permissão! " +
                                "\nPara usar a agenda necessitamos dessa permissão!",
                        Toast.LENGTH_LONG).show();

                            /* Além da mensagem indicando a necessidade sobre a permissão,
                               podemos solicitar novamente a permissão */
                ActivityCompat.requestPermissions(TelaInicioActivity.this, new String[]{android.Manifest.permission.WRITE_CALENDAR, android.Manifest.permission.ACCESS_FINE_LOCATION}, 0);
            } else {
                //Solicita a permissão
                ActivityCompat.requestPermissions(TelaInicioActivity.this, new String[]{android.Manifest.permission.WRITE_CALENDAR, android.Manifest.permission.ACCESS_FINE_LOCATION}, 0);
            }
        }
    }// Fim da Permiss
    @Override
    protected void onStart() {
        super.onStart();
        //lerProvas();
    }//fecha onstart



}// fecha class
