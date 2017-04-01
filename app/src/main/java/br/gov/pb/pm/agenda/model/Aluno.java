package br.gov.pb.pm.agenda.model;

import java.io.Serializable;

/**
 * Created by jovennan on 31/03/17.
 */

public class Aluno implements Serializable {
    private String nome;
    private String endereco;
    private String fone;
    private String email;
    private String site;
    private int ratio;

    public Aluno(String nome, String endereco, String fone, String email, String site, int ratio) {
        this.nome = nome;
        this.endereco = endereco;
        this.fone = fone;
        this.email = email;
        this.site = site;
        this.ratio = ratio;
    }

    public Aluno(String nome) {
        this.nome = nome;
    }

    public Aluno() {
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getFone() {
        return fone;
    }

    public void setFone(String fone) {
        this.fone = fone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public int getRatio() {
        return ratio;
    }

    public void setRatio(int ratio) {
        this.ratio = ratio;
    }
}
