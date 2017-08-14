package br.dudu9999.com.qualprova.Fragmentos;

import android.provider.ContactsContract;

import java.io.Serializable;
import java.sql.Time;

/**
 * Created by Eduardo on 30/06/2017.
 */

public class Prova implements Serializable{

    private String id;
    private String materia;
    private String colegio;
    private String turmas;
    private String conteudo;
    private int day;
    private int mouth;
    private int year;

    public Prova(String id, String materia, String colegio, String turmas, String conteudo, int day, int mouth, int year) {
        this.id = id;
        this.materia = materia;
        this.colegio = colegio;
        this.turmas = turmas;
        this.conteudo = conteudo;
        this.day = day;
        this.mouth = mouth;
        this.year = year;
    }

    public Prova() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMateria() {
        return materia;
    }

    public void setMateria(String materia) {
        this.materia = materia;
    }

    public String getColegio() {
        return colegio;
    }

    public void setColegio(String colegio) {
        this.colegio = colegio;
    }

    public String getTurmas() {
        return turmas;
    }

    public void setTurmas(String turmas) {
        this.turmas = turmas;
    }

    public String getConteudo() {
        return conteudo;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getMouth() {
        return mouth;
    }

    public void setMouth(int mouth) {
        this.mouth = mouth;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    @Override
    public String toString(){return
                                   "Local: "+ colegio+"\n"+
                                   "Materia: "+ materia+"\n"+
                                   "Turma: "+ turmas+"\n"+
                                   "Conteudo: "+conteudo+"\n"+
                                   "Data: "+day+"/"+mouth+"/"+year;
                                    }



}//fecha classe
