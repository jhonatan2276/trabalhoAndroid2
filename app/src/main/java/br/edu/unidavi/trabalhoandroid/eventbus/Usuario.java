package br.edu.unidavi.trabalhoandroid.eventbus;

public class Usuario {

    private String nome1;
    private String nome2;

    private String login;
    private String senha;

    public String getNome1() {
        return nome1;
    }

    public void setNome1(String nome1) {
        this.nome1 = nome1;
    }

    public String getNome2() {
        return nome2;
    }

    public void setNome2(String nome2) {
        this.nome2 = nome2;
    }

    public String getUsuario() {
        return login;
    }

    public void setUsuario(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
