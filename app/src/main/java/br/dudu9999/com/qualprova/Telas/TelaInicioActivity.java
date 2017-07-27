package br.dudu9999.com.qualprova.Telas;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
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
import br.dudu9999.com.qualprova.Fragmentos.Usuario;
import br.dudu9999.com.qualprova.Telas.LoginActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

import br.dudu9999.com.qualprova.Fragmentos.Prova;
import br.dudu9999.com.qualprova.R;
import br.dudu9999.com.qualprova.Telas.AdcionaActivity;
import br.dudu9999.com.qualprova.Telas.PerfilActivity;
import butterknife.BindView;
import butterknife.ButterKnife;

public class TelaInicioActivity extends AppCompatActivity {
    private Usuario userLocal;
    private Usuario userLogado;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private RequestQueue mVolleyQueue;

    private ListView lvProvas;

    private ArrayAdapter<Prova> adapter;
    private ArrayList<Prova> provas;

    //Drawer
    private Drawer result = null;

    //Toolbar
    private Toolbar toolbar;

    @BindView(R.id.lista_prova)
     ListView mListView;

    @BindView(R.id.refresh)
     SwipeRefreshLayout mRefresh;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_inicio);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        lvProvas = (ListView) findViewById(R.id.lista_prova);

        provas = new ArrayList<>();
        adapter = new ArrayAdapter<Prova>(
                TelaInicioActivity.this,android.R.layout.simple_list_item_1,provas
        );

        lvProvas.setAdapter(adapter);

        //Firebase
        mAuth = FirebaseAuth.getInstance();
        FirebaseApp.initializeApp(TelaInicioActivity.this);
        final FirebaseDatabase db = FirebaseDatabase.getInstance();
        final DatabaseReference banco = db.getReference("Provas");
        Usuario usuario = new Usuario();

//        if( mAuth.getCurrentUser() == null){
//            //user nao esta logado
//            finish();
//            Intent Isair = new Intent(getApplicationContext(), LoginActivity.class);
//            startActivity(Isair);
//        }
//
//        if( usuario != null){
//            finish();
//            Intent Isair = new Intent(getApplicationContext(), LoginActivity.class);
//            startActivity(Isair);
//        }
//


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Irineu", Snackbar.LENGTH_INDEFINITE).setAction("Action", null).show();
                Intent addprov = new Intent(TelaInicioActivity.this, AdcionaActivity.class);
                startActivity(addprov);
            }
        });

        //Inicio AccountHeader
//####################### SÓ O CABEÇALHO #######################
        AccountHeader headerResult = new AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(R.drawable.fundo_1 )
                .addProfiles(
                new ProfileDrawerItem().withName("Thiago").withEmail("thiago_boladão@boladão.ui")
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
                        new SecondaryDrawerItem().withName("Home").withIdentifier(0).withIcon(R.mipmap.ic_home),
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
                                Intent Iperfil = new Intent(TelaInicioActivity.this, PerfilActivity.class);
                                startActivity(Iperfil);
                                Toast.makeText(getBaseContext(),"Você clicou no menu Home",Toast.LENGTH_LONG).show();
                                break;

                            case 10:
                                Intent Ihome = new Intent(TelaInicioActivity.this, PerfilActivity.class);
                                                                 startActivity(Ihome);
                                Toast.makeText(getBaseContext(),"Você clicou no menu Perfil",Toast.LENGTH_LONG).show();
                                break;

                            case 20:
                                Intent Isobre = new Intent(TelaInicioActivity.this, AboutActivity.class);
                                startActivity(Isobre);
                                Toast.makeText(getBaseContext(),"Você clicou no menu Sobre",Toast.LENGTH_LONG).show();
                                break;

                            case 30:
                                mAuth.signOut();
                                finish();
                                Intent Isair = new Intent(getApplicationContext(), LoginActivity.class);
                                startActivity(Isair);
//                                Isair.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                                Isair.putExtra("SAIR", true);

                                //System.exit(0);
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

        mVolleyQueue = Volley.newRequestQueue(this);
        ButterKnife.bind(this);
        mRefresh.setEnabled(true);
        mRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                //lerProvas();

            }
        });

            mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Prova prova = ((ArrayAdapter<Prova>)parent.getAdapter()).getItem(position);
                Intent intent = new Intent(TelaInicioActivity.this, AdcionaActivity.class);
                intent.putExtra("tipo", prova.getTipo());
                intent.putExtra("materia", prova.getMateria());
                intent.putExtra("data", prova.getData());
                intent.putExtra("colegio", prova.getColegio());
                intent.putExtra("turmas", prova.getTurmas());
                intent.putExtra("conteudo", prova.getConteudo());
                intent.putExtra("key", prova.getId());
                startActivity(intent);
            }
        });

        //lerProvas();

    }//fecha oncreate

    @Override
    protected void onStart() {
        super.onStart();
        //lerProvas();
    }//fecha onstart


;
    /*public void lerProvas() {
        StringRequest req = new StringRequest("https://qual-prova-firebase.firebaseio.com/Provas.json",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject( response );
                            jsonObject.toString();

                            Iterator<?> keys = jsonObject.keys();

                            while( keys.hasNext() ) {
                                String key = (String)keys.next();
                                if ( jsonObject.get(key) instanceof JSONObject ) {
                                    String id = ((JSONObject) jsonObject.get(key)).get("id").toString();
                                    String tipo = ((JSONObject) jsonObject.get(key)).get("tipo").toString();
                                    String materia = ((JSONObject) jsonObject.get(key)).get("materia").toString();
                                    String data = ((JSONObject) jsonObject.get(key)).get("data").toString();
                                    String colegio = ((JSONObject) jsonObject.get(key)).get("colegio").toString();
                                    String turmas = ((JSONObject) jsonObject.get(key)).get("turmas").toString();
                                    String conteudo = ((JSONObject) jsonObject.get(key)).get("conteudo").toString();
                                    Prova prov1 = new Prova(id, tipo, materia, data, colegio,turmas,conteudo );
                                    provas.add(0, prov1);
                                }
                            }
                            ArrayAdapter<Prova> adapter = new ArrayAdapter<Prova>(
                                    TelaInicioActivity.this, android.R.layout.simple_list_item_1,provas);
                            mListView.setAdapter(adapter);
                            mRefresh.setRefreshing(false);
                            YoYo.with(Techniques.FadeIn).duration(1000)
                                    .playOn(mRefresh);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(TelaInicioActivity.this, "Erro",Toast.LENGTH_SHORT).show();
            }
        });
        mVolleyQueue.add(req);
    }*///fecha ler prova

 //   @Override
//    protected void onResume() {
//        if(getIntent().getBooleanExtra("SAIR", false)){
//            finish();
//        }
//        super.onResume();
//    }


}// fecha class
