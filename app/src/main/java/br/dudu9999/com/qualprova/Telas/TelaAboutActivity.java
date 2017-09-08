package br.dudu9999.com.qualprova.Telas;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import br.dudu9999.com.qualprova.R;

public class TelaAboutActivity extends AppCompatActivity {
    private Button facebook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        facebook = (Button)findViewById(R.id.Facebook);
    }

    public void Facebookclick(View view) {


        facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri1 = Uri.parse("https://www.facebook.com/ecaetanocorrea");
                Intent it1 = new Intent(Intent.ACTION_VIEW, uri1);
                startActivity(it1);
            }//fecha onClick
        });//fecha Facebook
    }

}
