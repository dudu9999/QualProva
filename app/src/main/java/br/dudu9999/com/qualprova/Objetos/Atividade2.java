package br.dudu9999.com.qualprova.Objetos;

import android.os.Bundle;
import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
import android.view.Menu;

import br.dudu9999.com.qualprova.R;

public class Atividade2 extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_atividade2);
		
		/*NotificationManager nm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		nm.cancel(R.drawable.ic_launcher);*/
	}

}
