package locadora;


import java.util.ArrayList;

import locadora.Jogo;

public class Plataforma {

    private String nome;
    private double coeficiente;
    private ArrayList<Jogo> jogos;
    
    public Plataforma(String nome, double coeficiente){
        this.nome = nome;
        this.coeficiente = coeficiente;
        jogos = new ArrayList<Jogo>();
    }

    public void setNome(String nome){
        this.nome = nome;
    }
    public String getNome(){
        return nome;
    }

    public void setCoeficiente(double coeficiente){
        this.coeficiente = coeficiente;
    }
    public double getCoeficiente(){
        return coeficiente;
    }

    public void addJogo(Jogo j){
        jogos.add(j);
    }
    public void removeJogo(String nome){
        for(int i=0; i<jogos.size(); i++){
            if(jogos.get(i).getTitulo().equals(nome))
                jogos.remove(i);
        }
    }
    
}