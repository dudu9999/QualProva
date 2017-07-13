package br.dudu9999.com.qualprova;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {


//    private DatabaseReference provaReferencia = databaseReferencia.child("Provas");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*
        Prova prova = new Prova();
        prova.setTipo("Prova");
        prova.setMateria("Dev Android");
        prova.setData("6/6/66");
        prova.setColegio("Fundatec");
        prova.setTurmas("TI08, TI09");
        prova.setConteudo("Estude todos tipos de intent.");
        provaReferencia.child("001").setValue(prova);

        Usuario usuario = new Usuario();
        usuario.setNome("Eduardo");
        usuario.setEmail("eduardo@gmail.com");
        usuario.setColegio("Fundatec");
        usuario.setCpf(123456789);
        usuario.setTurma("TI09");
        usuario.setTipo("Aluno");
        usuario.setSenha("1234");
        usuarioReferencia.child("001").setValue(usuario);
        */
        //databaseReferencia.child("ponto").setValue("100"); //esse codigo criou um nodle no firebase
        //databaseReferencia.child("001").child("nome").setValue("Eduardo"); // adicionou 001 com nome com valor eduardo

//        usuarioReferencia.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                Log.i("FIREBASE", dataSnapshot.getValue().toString() );
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });


    }

    public void cadastraronclick(View view) {
        Intent i = new Intent(MainActivity.this, CadastroActivity.class);
        startActivity(i);
    }


    public void loginonclick(View view) {
        Intent i = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(i);
    }
}
