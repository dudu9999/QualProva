package br.dudu9999.com.qualprova.Objetos;

/**
 * Created by Eduardo on 30/06/2017.
 */

public class Usuario {

    private String nome;
    private String email;
    private String turma;
    private String colegio;
    private String cpf;
    private String tipo;
    private String senha;
    private String UID;

    public Usuario() {}

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTurma() {
        return turma;
    }

    public void setTurma(String turma) {
        this.turma = turma;
    }

    public String getColegio() {
        return colegio;
    }

    public void setColegio(String colegio) {
        this.colegio = colegio;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getUID() { return UID; }

    public void setUID(String UID) { this.UID = UID; }


}//fecha classe
