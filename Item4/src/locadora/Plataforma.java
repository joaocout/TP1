package locadora;


import java.util.ArrayList;

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
    
    public ArrayList<Jogo> getJogos(){
    	return jogos;
    }
    
    public void addJogo(Jogo j){
    	try{
    		jogos.add(j);
    	} catch (Exception ex){
    		System.out.println("Erro: " + ex.getMessage());
    	}
    }
    public void removeJogo(String nome){
    	try{
	        for(int i=0; i<jogos.size(); i++){
	            if(jogos.get(i).getTitulo().toLowerCase().equals(nome.toLowerCase()))
	                jogos.remove(i);
	        }
    	} catch (Exception ex){
    		System.out.println("Erro: " + ex.getMessage());
    	}
    }
    
    @Override
    public String toString(){
    	return "Plataforma: " + this.getNome() + "\nQuantidade de jogos: " + this.jogos.size();
    }
    
}