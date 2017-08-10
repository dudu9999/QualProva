package br.dudu9999.com.qualprova.Telas;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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
import br.dudu9999.com.qualprova.Fragmentos.MyApplication;
import br.dudu9999.com.qualprova.Fragmentos.Prova;
import br.dudu9999.com.qualprova.Fragmentos.Usuario;
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

        lista_prova = (ListView) findViewById(R.id.lista_prova);

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
                //Snackbar.make(view, "Preencha seus dados do pessoais no perfil", Snackbar.LENGTH_INDEFINITE).setAction("Action", null).show();
                Intent addprov = new Intent(TelaInicioActivity.this, TelaAdcionaProvaActivity.class);
                addprov.putExtra("acao","cadastrar");
                startActivity(addprov);
            }
        });

        //Inicio AccountHeader
        //####################### SÓ O CABEÇALHO #######################
        AccountHeader headerResult = new AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(R.drawable.fundo_verde )
                .addProfiles(
                new ProfileDrawerItem().withName(((MyApplication)getApplication()).getUser().getNome()).withEmail(((MyApplication)getApplication()).getUser().getEmail())
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
                        new SecondaryDrawerItem().withName("Inicio").withIdentifier(0).withIcon(R.mipmap.ic_home),
                        new SecondaryDrawerItem().withName("Perfil").withIdentifier(10).withIcon(R.mipmap.ic_user),
                        new SecondaryDrawerItem().withName("Sobre").withIdentifier(20).withIcon(R.mipmap.ic_about),
                        new PrimaryDrawerItem().withName("Sair").withIdentifier(30).withIcon(R.mipmap.ic_out)
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {

                        //Intent it;

                        switch ((int)drawerItem.getIdentifier()){
                            case 0:
                                Intent Ihome  = new Intent(TelaInicioActivity.this, TelaInicioActivity.class);
                                startActivity(Ihome);
                                Toast.makeText(getBaseContext(),"Você clicou no menu Inicio",Toast.LENGTH_LONG).show();
                                break;

                            case 10:
                                Intent ICad = new Intent(TelaInicioActivity.this, TelaPerfilActivity.class);
                                startActivity(ICad);


//                                SharedPreferences.Editor editor = getSharedPreferences(MyApplication.MY_PREFS_NAME, MODE_PRIVATE).edit();
//                                editor.putString("UID", u.getUID());
//                                editor.apply();

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
//                                Intent Isair = new Intent(getApplicationContext(), TelaLoginActivity.class);
//                                startActivity(Isair);
                                break;
                        }
                        return false;
                    }
                }).build();

        //Traz os dados
        banco.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

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

                /*intent.putExtra("tipo", prova.getTipo());
                intent.putExtra("materia", prova.getMateria());
                intent.putExtra("data", prova.getData());
                intent.putExtra("colegio", prova.getColegio());
                intent.putExtra("turmas", prova.getTurmas());
                intent.putExtra("conteudo", prova.getConteudo());
                intent.putExtra("key", prova.getId());*/

                intent.putExtra("acao","alterar");
                intent.putExtra("prova", prova);
                startActivity(intent);
            }
        });



    }//fecha oncreate

    @Override
    protected void onStart() {
        super.onStart();
        //lerProvas();
    }//fecha onstart



}// fecha class
