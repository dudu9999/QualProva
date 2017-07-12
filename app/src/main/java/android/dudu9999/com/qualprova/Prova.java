package android.dudu9999.com.qualprova;

/**
 * Created by Marcos on 30/06/2017.
 */

public class Prova {
    private String id;
    private String tipo;
    private String materia;
    private String data;
    private String colegio;
    private String turmas;
    private String conteudo;

    public Prova(String id, String tipo, String materia, String data, String colegio, String turmas, String conteudo) {
        this.id = id;
        this.tipo = tipo;
        this.materia = materia;
        this.data = data;
        this.colegio = colegio;
        this.turmas = turmas;
        this.conteudo = conteudo;
    }


    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getMateria() {
        return materia;
    }

    public void setMateria(String materia) {
        this.materia = materia;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
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

    public String getId() {  return id;  }

    public void setId(String id) { this.id = id;  }

    public String toString() {return id+"\n"+tipo+"\n"+materia+"\n"+data+"\n"+colegio+"\n"+turmas+"\n"+conteudo;}
}//fecha classe
