package br.dudu9999.com.qualprova.Objetos;

import java.io.Serializable;

/**
 * Created by Eduardo on 30/06/2017.
 */

public class Prova implements Serializable{

    private String id;
    private String materia;
    private String colegio;
    private String turmas;
    private String conteudo;
    private String day;
    private String mouth;
    private String year;

    public Prova() {
    }

    public Prova(String id, String materia, String colegio, String turmas, String conteudo, String day, String mouth, String year) {
        this.id = id;
        this.materia = materia;
        this.colegio = colegio;
        this.turmas = turmas;
        this.conteudo = conteudo;
        this.day = day;
        this.mouth = mouth;
        this.year = year;
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

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getMouth() {
        return mouth;
    }

    public void setMouth(String mouth) {
        this.mouth = mouth;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    //    public String getDataProva() {
//        return dataProva;
//    }
//
//    public void setDataProva(String dataProva) {
//        this.dataProva = dataProva;
//    }

    @Override
    public String toString(){return
                                   "Prova de: "+ materia+"\n"+
                                   "Local: "+ colegio+"\n"+
                                   "Turma: "+ turmas+"\n"+
                                   "Conteudo: "+conteudo+"\n"+
                                   "Data: "+ day+"/"+mouth+"/"+year;

                                    } //fecha toString



}//fecha classe
