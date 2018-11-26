package br.edu.unidavi.trabalhoandroid.eventbus;

public class Favorito {

    private int id;
    private int idServer;
    private String marca;
    private String modelo;
    private String ano;
    private String imagem;
    private String preco;

    public Favorito() {}

    public Favorito(int id, int idServer, String marca, String modelo, String ano, String imagem, String preco) {
        this.id = id;
        this.idServer = idServer;
        this.marca = marca;
        this.modelo = modelo;
        this.ano = ano;
        this.imagem = imagem;
        this.preco = preco;
    }

    public int getId() {
        return id;
    }

    public int getIdServer() {
        return idServer;
    }

    public String getMarca() {
        return marca;
    }

    public String getModelo() {
        return modelo;
    }

    public String getAno() {
        return ano;
    }

    public String getImagem() {
        return imagem;
    }
    public String getPreco() {
        return preco;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setIdServer(int idServer) {
        this.idServer = idServer;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public void setAno(String ano) {
        this.ano = ano;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }

    public void setPreco(String preco) {
        this.preco = preco;
    }
}
