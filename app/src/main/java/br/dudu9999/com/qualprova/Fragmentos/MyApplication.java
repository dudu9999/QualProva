package br.dudu9999.com.qualprova.Fragmentos;

import android.app.Application;

/**
 * Created by Eduardo on 01/06/17.
 */

public class MyApplication extends Application {

    public static final String MY_PREFS_NAME = "MeusDados";
    private Prova prov = null;
    private Usuario user = null;

    @Override
    public void onCreate() {
        super.onCreate();

    }

    public Prova getProv() {
        return prov;
    }

    public void setProv(Prova prov) {
        this.prov = prov;
    }

    public Usuario getUser() {
        return user;
    }

    public void setUser(Usuario user) {
        this.user = user;
    }

    /* PADRÃO SINGLETON*/
    /*     private static MyProperties mInstance= null;

    public int someValueIWantToKeep;

    protected MyProperties(){}

    public static synchronized MyProperties getInstance(){
        if(null == mInstance){
            mInstance = new MyProperties();
        }
        return mInstance;
    } */
}