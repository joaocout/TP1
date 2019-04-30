package locadora;


import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import locadora.exception.*;
import locadora.interfaces.*;

public class Locacao implements Aluguel{

    private String data_aluguel; //formato: 2015-04-12 (yyyy-mm-dd)
    private String data_devolucao;
    private String hora_aluguel; //formato: 23:20 (hh:mm)
    private String hora_devolucao;
    private double preco_final;
    private boolean finalizada;
    private int protocolo;
    private Jogo game;
    private static int base_protocol = 0;

    public Locacao(Jogo game){
    	this.data_aluguel = "0000-00-00";
        this.finalizada = false;
        this.protocolo = generateProtocolo();
        this.game = game;
    }

    public void setDataAluguel(String data_aluguel){
        this.data_aluguel = data_aluguel;
    }
    public String getDataAluguel(){
        return data_aluguel;
    }
    public void setDataDevolucao(String data_devolucao){
        this.data_devolucao = data_devolucao;
    }
    public String getDataDevolucao(){
        return data_devolucao;
    }
    public void setHoraAluguel(String hora_aluguel){
        this.hora_aluguel = hora_aluguel;
    }
    public String getHoraAluguel(){
        return hora_aluguel;
    }
    public void setHoraDevolucao(String hora_devolucao){
        this.hora_devolucao = hora_devolucao;
    }
    public String getHoraDevolucao(){
        return hora_devolucao;
    }
    public void setProtocolo(){
        this.protocolo = generateProtocolo();
    }
    public int getProtocolo(){
        return protocolo;
    }
    public void setJogo(Jogo game){
        this.game = game;
    }
    public Jogo getJogo(){
        return game;
    }
    public void setFinalizada(boolean finalizada){
        this.finalizada = finalizada;
    }
    public boolean getFinalizada(){
        return finalizada;
    }

    public double PrecoFinal() {
    	try {
	        preco_final = game.getPrecoBase();
	        preco_final = preco_final*game.getPlataforma().getCoeficiente();
	        
	        int ano=0, mes=0, dia=0;
	
	        int i = 0;        
	        for(String a: data_aluguel.split("\\-")){
	            if(i==0){
	                ano = Integer.valueOf(a);
	                i++;
	            }
	            else if(i==1){
	                mes = Integer.valueOf(a);
	                i++;
	            }
	            else if(i==2){
	                dia = Integer.valueOf(a);
	                i++;
	            }
	        }
	        LocalDate d_aluguel = LocalDate.of(ano, mes, dia);
	        
	        // Caso queira apenas o valor da divida ate entao (e nao tiver devolvido ainda)
	        if(!this.finalizada){
	        	this.data_devolucao = LocalDate.now().toString();
	        }
	        
	        i = 0;
	        for(String a: data_devolucao.split("\\-")){
	            if(i==0){
	                ano = Integer.valueOf(a);
	                i++;
	            }
	            else if(i==1){
	                mes = Integer.valueOf(a);
	                i++;
	            }
	            else if(i==2){
	                dia = Integer.valueOf(a);
	                i++;
	            }
	        }
	        LocalDate d_devolucao = LocalDate.of(ano, mes, dia);
	        
	        long dias = ChronoUnit.DAYS.between(d_aluguel, d_devolucao);
	
	        if(dias == 0) dias = 1;
	        
	        preco_final = preco_final * dias;
	        return preco_final;
    	} catch(Exception ex){
    		ex.printStackTrace();
    		return 0.0f;
    	}
    }

    private int generateProtocolo(){
        base_protocol++;
        return base_protocol;
    }

    public void alugar() throws AlugarEx {
    	if(this.data_aluguel.equals("0000-00-00")){
	    	LocalDate dia = LocalDate.now();
		    LocalTime hora = LocalTime.now().withSecond(0).withNano(0);
		    data_aluguel = dia.toString();
		    hora_aluguel = hora.toString();
    	} else {
    		throw new AlugarEx("Aluguel ja realizado.");
    	}
    }

    public void devolver() throws DevolverEx {
    	if(!this.finalizada){
	        LocalDate dia = LocalDate.now();
	        LocalTime hora = LocalTime.now().withSecond(0).withNano(0);
	        data_devolucao = dia.toString();
	        hora_devolucao = hora.toString();
	        this.finalizada = true;
    	} else {
    		throw new DevolverEx("Locacao ja finalizada.");
    	}
    }
    
    public String toString(){
    	String finalizada = this.finalizada ? "Sim" : "Nao";
    	String toret = "Jogo: " + game.getTitulo() + "\nPreco atual: " + PrecoFinal() + "\nFinalizada: " + finalizada + "\n";
    	return toret;
    }
    
}