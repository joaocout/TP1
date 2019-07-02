package locadora;


import interfaces.Aluguel;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

import exception.AlugarEx;
import exception.DevolverEx;
import exception.PrecoEx;

public class Locacao implements Aluguel{

	private int id; // primary key
    private String data_aluguel; //formato: 2015-04-12 (yyyy-mm-dd)
    private String data_devolucao;
    private String hora_aluguel; //formato: 23:20 (hh:mm)
    private String hora_devolucao;
    private double preco_final;
    private boolean finalizada;
    private Jogo game;
    private int dias;
    private Cliente cliente; // adicionado mas nao implementado ainda

    public Locacao(Jogo game, int dias, Cliente cliente){
    	this.data_aluguel = "0000-00-00";
    	this.data_devolucao = "0000-00-00";
        this.finalizada = false;
        this.game = game;
        this.dias = dias;
        this.cliente = cliente;
        try {
        	this.alugar();
        } catch(AlugarEx e) {
        	e.printStackTrace();
        }
    }
    public Locacao(String data_aluguel, String data_devolucao, String hora_aluguel, String hora_devolucao, double preco_final, boolean finalizada, Jogo game, int dias, Cliente cliente){
    	this.data_aluguel = data_aluguel;
    	this.data_devolucao = data_devolucao;
    	this.hora_aluguel = hora_aluguel;
    	this.hora_devolucao = hora_devolucao;
    	this.preco_final = preco_final;
        this.finalizada = finalizada;
        this.game = game;
        this.dias = dias;
        this.cliente = cliente;
    }
    public void setID(int id) {
    	this.id = id;
    }
    public int getID() {
    	return this.id;
    }
    public void setDias(int dias) {
    	this.dias = dias;
    }
    public int getDias() {
    	return dias;
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
    public Cliente getCliente() {
    	return this.cliente;
    }
    public void setCliente(Cliente cli) {
    	this.cliente = cli;
    }

    public double PrecoFinal() throws PrecoEx {
    	try {
    		
	        preco_final = game.getPrecoBase()*game.getPlataforma().getCoeficiente()*dias;
	        
	        int ano=0, mes=0, dia=0;
	
	        int i = 0;
	        
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
	        
	        if(!finalizada && LocalDate.now().isAfter(d_devolucao)) {
	        	long dias_atraso = ChronoUnit.DAYS.between(d_devolucao, LocalDate.now());
	        	double multa = game.getPrecoBase()*game.getPlataforma().getCoeficiente()*dias_atraso*2;
	        	//cada dia de atraso, soma o dobro do que seria somado em um dia normal
	        	preco_final += multa;
	        }
	        
	        return preco_final;
    	}
    	catch(Exception ex){
    		throw new PrecoEx("Erro ao calcular debito.");
    	}
    }

    public void alugar() throws AlugarEx {
    	if(this.data_aluguel.equals("0000-00-00")){
    		if(game.getQtd() > 0){
    			
			    data_aluguel = LocalDate.now().toString();
			    hora_aluguel = LocalTime.now().withSecond(0).withNano(0).toString();
			    data_devolucao = LocalDate.now().plusDays(dias).toString();
			    game.subQtd();
			    
    		}
    		else {
    			throw new AlugarEx("Nao ha mais unidades desse jogo.");
    		}
    	}
    	else {
    		throw new AlugarEx("Aluguel ja realizado.");
    	}
    }

    public void devolver() throws DevolverEx {
    	if(!this.finalizada){
    		if(data_aluguel.equals("0000-00-00")){
        		throw new DevolverEx("Ainda nao ha uma data de aluguel definida.");
    		} else {
    			data_devolucao = LocalDate.now().toString();
		        hora_devolucao = LocalTime.now().withSecond(0).withNano(0).toString();
		        game.addQtd();
		        this.finalizada = true;
    		}
    	}
    	else{
    		throw new DevolverEx("Locacao ja finalizada.");
    	}
    }
    
    @Override 
    public String toString(){
    	String finalizada = this.finalizada ? "Sim" : "Nao";
    	String toret;
    	double pfinal = 0;
    	String _pfinal = "0";
    	try{
    		pfinal += PrecoFinal();
    	} catch(PrecoEx ex){
    		_pfinal = ex.getMessage();
    	}
    	if(pfinal != 0f)
    		_pfinal = String.format("%.2f", pfinal);
		toret = "Protocolo: " + this.id + " | Jogo: " + game.getTitulo() + " | Plataforma: " + game.getPlataforma().getNome() + " | Preco atual: R$" + _pfinal + " | Finalizada: " + finalizada;
    	return toret;
    }
}