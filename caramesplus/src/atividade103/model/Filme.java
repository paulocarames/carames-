/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package atividade103.model;

public class Filme {
    private int id;
    private String nome;
    private String dataLancamento;
    private String categoria;
    private boolean assistido;    
    private int ativo;    
    
    public Filme() {
        
    }

    public Filme(int id, String nome, String dataLancamento, String categoria, boolean assistido, int ativo) {
        this.id = id;
        this.nome = nome;
        this.dataLancamento = dataLancamento;
        this.categoria = categoria;
        this.assistido = assistido;      
        this.ativo = ativo;         
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDataLancamento() {
        return dataLancamento;
    }

    public void setDataLancamento(String dataLancamento) {
        this.dataLancamento = dataLancamento;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
    
    // Exemplo: 28/11/2023 para 2023-11-28
    public static String converterParaSQL(String dataAntiga) {
        String[] partesData = dataAntiga.split("/");
        String dataNova = partesData[2] + "-" + partesData[1] + "-" + partesData[0];
        return dataNova;
    }
    
    // Exemplo: 2023-11-28 para 28/11/2023
    public static String converterParaJava(String dataAntiga) {
        String[] partesData = dataAntiga.split("-");
        String dataNova = partesData[2] + "/" + partesData[1] + "/" + partesData[0];
        return dataNova;        
    }    
    
    public boolean isAssistido() {
        return assistido;
    }

    public void setAssistido(boolean assistido) {
        this.assistido = assistido;
    }    
    
    public int getAtivo() {
        return ativo;
    }

    public void setAtivo(int ativo) {
        this.ativo = ativo;
    }
    
}