package br.senai.sp.modelo;

import java.io.Serializable;

public class Filme implements Serializable {

    private int id;
    private String titulo;
    private String diretor;
    private String dataLancamento;
    private String duracao;
    private String genero;
    private int nota;
    private byte[] foto;


    //alt + insert = gera getter e setter
    public int getId() {
        return id;
    }

    public byte[] getFoto() {
        return foto;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDiretor() {
        return diretor;
    }

    public String getDataLancamento() {
        return dataLancamento;
    }

    public String getDuracao() {
        return duracao;
    }

    public String getGenero() {
        return genero;
    }

    public int getNota() {
        return nota;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setDiretor(String diretor) {
        this.diretor = diretor;
    }

    public void setDataLancamento(String dataLancamento) {
        this.dataLancamento = dataLancamento;
    }

    public void setDuracao(String duracao) {
        this.duracao = duracao;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public void setNota(int nota) {
        this.nota = nota;
    }

    @Override
    public String toString() {
        return this.id + " - "+ this.titulo + " - " + this.dataLancamento + " - " + this.duracao;
    }
}
