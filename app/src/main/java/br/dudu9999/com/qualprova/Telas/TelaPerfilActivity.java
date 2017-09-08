    package br.dudu9999.com.qualprova.Telas;

    import android.app.ProgressDialog;
    import android.content.Intent;
    import android.content.SharedPreferences;
    import android.net.Uri;
    import android.support.design.widget.FloatingActionButton;
    import android.support.v7.app.AppCompatActivity;
    import android.os.Bundle;
    import android.util.Log;
    import android.view.View;
    import android.widget.Button;
    import android.widget.EditText;
    import android.widget.ImageView;
    import android.widget.Spinner;
    import android.widget.Toast;
    import com.github.rtoshiro.util.format.SimpleMaskFormatter;
    import com.github.rtoshiro.util.format.text.MaskTextWatcher;
    import com.google.android.gms.tasks.OnSuccessListener;
    import com.google.firebase.FirebaseApp;
    import com.google.firebase.auth.FirebaseAuth;
    import com.google.firebase.database.DataSnapshot;
    import com.google.firebase.database.DatabaseError;
    import com.google.firebase.database.DatabaseReference;
    import com.google.firebase.database.FirebaseDatabase;
    import com.google.firebase.database.ValueEventListener;
    import com.google.firebase.storage.FirebaseStorage;
    import com.google.firebase.storage.StorageReference;
    import com.google.firebase.storage.UploadTask;
    import java.util.ArrayList;

    import br.dudu9999.com.qualprova.Objetos.ConfiguracaoFireBase;
    import br.dudu9999.com.qualprova.Objetos.MyApplication;
    import br.dudu9999.com.qualprova.Objetos.Prova;
    import br.dudu9999.com.qualprova.R;
    import br.dudu9999.com.qualprova.Objetos.Usuario;

    public class TelaPerfilActivity extends AppCompatActivity {

        //widgets
        private static final int GALLERY_INTENT = 2;
        private ImageView select_image_C;
        private ProgressDialog mProgressDialog;
        private EditText txtEmail;
        private EditText txtSenha;
        private EditText txtNome;
        private EditText txtColegio;
        private EditText txtCPF;
        private EditText txtTurma;
        private Spinner spTipo;
        private Button btnCadastrar;
        private FirebaseAuth firebaseAuth;
        private Usuario userLocal;
        private Usuario userLogado;
        private Prova provaLocal;
        private StorageReference mStorage;
        private String TAG = "Log";
        private DatabaseReference bd;
        private FloatingActionButton fab;


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_perfil);

            fab = (FloatingActionButton) findViewById(R.id.fab);
            select_image_C = (ImageView) findViewById(R.id.select_image);
            txtEmail =       (EditText)  findViewById(R.id.edtEmailCadastro);
            txtSenha =       (EditText)  findViewById(R.id.edtSenhaCadastro);
            txtNome =        (EditText)  findViewById(R.id.edtNomeCad);
            txtColegio =     (EditText)  findViewById(R.id.edtColegioCad);
            txtCPF =         (EditText)  findViewById(R.id.edtCpfCad);
            spTipo =        (Spinner)   findViewById(R.id.spTipo);
            txtTurma =       (EditText)  findViewById(R.id.edtTurma);
            btnCadastrar =   (Button)    findViewById(R.id.btnCadastrar);

            //mascara para cpf
            SimpleMaskFormatter cpfFormater = new SimpleMaskFormatter("NNN.NNN.NNN-NN");
            MaskTextWatcher mtw = new MaskTextWatcher(txtCPF, cpfFormater);
            txtCPF.addTextChangedListener(mtw);


            //buscando o usuario
            userLocal = ((MyApplication)getApplication()).getUser();
//            Log.d(TAG, "USUARIOLOCA" + userLocal.toString());

            //firebase
            FirebaseApp.initializeApp(getBaseContext());
            final FirebaseDatabase db = FirebaseDatabase.getInstance();

            //carlos
            bd = ConfiguracaoFireBase.getFirebase().child("Banco").child("Usuarios");
            final ArrayList<Usuario> usu = new ArrayList<>();

            //mstorage pra foto
            mStorage = FirebaseStorage.getInstance().getReference();
            mProgressDialog = new ProgressDialog(this);
            select_image_C.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_PICK);
                    intent.setType("image/*");
                    startActivityForResult(intent, GALLERY_INTENT);
                }
            });

            btnCadastrar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Usuario u = new Usuario();

                    u.setUID(userLocal.getUID());

                    userLocal.setEmail(txtEmail.getText().toString());
                    u.setEmail(txtEmail.getText().toString());
                    u.setEmail(userLocal.getEmail().toString());

                    userLocal.setSenha(txtSenha.getText().toString());
                    u.setSenha(txtSenha.getText().toString());
                    u.setSenha(userLocal.getSenha().toString());


                    userLocal.setNome(txtNome.getText().toString());
                    u.setNome(txtNome.getText().toString());
                    u.setNome(userLocal.getNome().toString());

                    userLocal.setColegio(txtColegio.getText().toString());
                    u.setColegio(txtColegio.getText().toString());
                    u.setColegio(userLocal.getColegio().toString());

                    userLocal.setCpf(txtCPF.getText().toString());
                    u.setCpf(txtCPF.getText().toString());
                    u.setCpf(userLocal.getCpf().toString());

                    userLocal.setTurma(txtTurma.getText().toString());
                    u.setTurma(txtTurma.getText().toString());
                    u.setTurma(userLocal.getTurma().toString());

                    userLocal.setTipo(spTipo.getSelectedItem().toString());
                    u.setTipo(spTipo.getSelectedItem().toString());
                    u.setTipo(userLocal.getTipo().toString());

                    if(usu.isEmpty()) {
                        salvarUsuario(u);
                        Toast.makeText(TelaPerfilActivity.this, "Perfil cadastrado com sucesso", Toast.LENGTH_LONG).show();
                    }else{
                        u.setUID(usu.get(0).getUID());
                        salvarUsuarioAlterar(u);
                        Toast.makeText(TelaPerfilActivity.this, "Perfil alterado com sucesso", Toast.LENGTH_LONG).show();
                    }

                    SharedPreferences.Editor editor = getSharedPreferences(MyApplication.MY_PREFS_NAME, MODE_PRIVATE).edit();
                    editor.putString("UID", u.getUID());
                    editor.apply();

                    Intent i = new Intent(TelaPerfilActivity.this, TelaInicioActivity.class);
                    if ((userLocal.getTipo().toString()).equals("Professor")){
                        i.putExtra("selectTipo","Professor");
                    }else{
                        i.putExtra("selectTipo","Aluno");
                    }

                    Intent iTelaCad = new Intent(TelaPerfilActivity.this, TelaInicioActivity.class);
                    Bundle bundleParametros = new Bundle();
                    bundleParametros.putString("usuariop", userLocal.getNome());
                    iTelaCad.putExtras(bundleParametros);

                    //Salvando localmente(setando cliente)
                    ((MyApplication)getApplication()).setUser(u);

                    limpar();

                    startActivity(i);

                }
            });//fim do btncadastrar

            bd.orderByChild("email").equalTo( ( (MyApplication)getApplication()).getUser().getEmail().toString()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    usu.clear();
                    for(DataSnapshot data: dataSnapshot.getChildren()){
                        Usuario u = data.getValue(Usuario.class);

                        u.setUID(data.getKey()); //Colocando key manualmente no objeto
                        usu.add(u);
                    }
                    if(!usu.isEmpty()) {

                        Log.d("USUARIOS", "USUARIO: " + usu.toString());
                        txtEmail.setText(usu.get(0).getEmail());
                        txtNome.setText(usu.get(0).getNome());
                        txtSenha.setText(usu.get(0).getSenha());
                        txtColegio.setText(usu.get(0).getColegio());
                        txtCPF.setText(usu.get(0).getCpf());
                        txtTurma.setText(usu.get(0).getTurma());
                        Log.d("USUARIOS", "USUARIO: "+"sp1" + spTipo);
                        Log.d("USUARIOS", "USUARIO: "+"sp2" + spTipo.toString());
                        Log.d("USUARIOS", "USUARIO: "+"sp3" + spTipo.getTextAlignment());
                        Log.d("USUARIOS", "USUARIO: "+"sp4" + spTipo.getTextDirection());


                        if (spTipo.equals("Professor")){
                            spTipo.setSelection(2);
                            Log.d("USUARIOS", "USUARIO: "+"sp5 " + usu.toString());

                        }else{
                            Log.d("USUARIOS", "USUARIO: "+"sp6 " + usu.toString());

                            spTipo.setSelection(1);
                        }

                        Log.d("USUARIOS", "USUARIO: "+"sp7 " + usu.toString());

                        Log.d("USUARIOS", "USUARIO: "+"sp8 " + spTipo.getSelectedItem().toString());

                    }//fecha if
                }//fecha on data change
                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });//fim bd.addValueEventListener

        }//fecha oncreate

        private boolean salvarUsuarioAlterar (Usuario u) {
            try {
                //Alterando através da chave(key) no firebase setando o novo valor
                bd.child(u.getUID()).setValue(u);
                Log.d("USUARIOS", "USUARIO: "+"Perfil Alterado " + u.toString());
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }

        private boolean salvarUsuario (Usuario u) {
            try {
                bd.push().setValue(u);
                Log.d("USUARIOS", "USUARIO: "+"Perfil criado " + u.toString());
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }

        public void limpar(){
            txtNome.setText(null);
            txtEmail.setText(null);
            txtColegio.setText(null);
            txtCPF.setText(null);
            txtTurma.setText(null);
            spTipo.setSelection(0);
            txtSenha.setText(null);
        }

        @Override//Envio de imagem pela galeria.
        protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode, resultCode, data);
            if (requestCode == GALLERY_INTENT && resultCode == RESULT_OK){
                mProgressDialog.setMessage("Enviando...");
                mProgressDialog.show();
                Uri uri = data.getData();

                StorageReference filepath = mStorage.child("Photos").child(uri.getLastPathSegment());
                filepath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Toast.makeText(TelaPerfilActivity.this, "Imagem enviada", Toast.LENGTH_LONG).show();
                        mProgressDialog.dismiss();
                    }
                });
            }
        }

    }