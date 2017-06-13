package com.teste.admin.rhuandsp2017.model;

import java.io.Serializable;

/**
 * Created by Admin on 10/06/2017.
 */

public class Time implements Serializable {

    private Integer id;
    private String nome;
    private String fundacao;
    private String cadastro;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getFundacao() {
        return fundacao;
    }

    public void setFundacao(String fundacao) {
        this.fundacao = fundacao;
    }

    public String getCadastro() {
        return cadastro;
    }

    public void setCadastro(String cadastro) {
        this.cadastro = cadastro;
    }

    @Override
    public String toString(){
        return (getCadastro()+" : "+getNome());
    }

}
