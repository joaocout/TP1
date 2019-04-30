package locadora;


import java.util.ArrayList;
import locadora.exception.*;

public class Cliente{

    private String nome;
    private String rg;
    private String cpf;
    private String email;
    private String telefone;
    private ArrayList<Locacao> locacoes;

    public Cliente(String nome, String rg, String cpf,
    String email, String telefone){
        this.nome = nome;
        this.rg = rg;
        this.cpf = cpf;
        this.email = email;
        this.telefone = telefone;
        locacoes = new ArrayList<Locacao>();
    }

    public void setNome (String nome){
        this.nome = nome;
    }
    public String getNome(){
        return nome;
    }
    public void setRG(String rg){
        this.rg = rg;
    }
    public String getRG(){
        return rg;
    }
    public void setCPF(String cpf){
        this.cpf = cpf;
    }
    public String getCPF(){
        return cpf;
    }
    public void setEmail(String email){
        this.email = email;
    }
    public String getEmail(){
        return email;
    }
    public void setTelefone(String telefone){
        this.telefone = telefone;
    }
    public String getTelefone(){
        return telefone;
    }
    public ArrayList<Locacao> getLocacoes(){
    	return this.locacoes;
    }
    
    public double Divida(){
        double divida = 0f;
        try{
	        for(int i=0; i<locacoes.size(); i++){
	            if(!locacoes.get(i).getFinalizada())
	                divida+=locacoes.get(i).PrecoFinal();
	        }
        } catch(Exception ex){
        	System.out.println(ex.getMessage());
        } catch(PrecoEx ex){
        	System.out.println(ex.getMessage());
        }
        return divida;
    }
    public void addLocacao(Locacao l) {
    	try{
    		locacoes.add(l);
    	} catch(Exception ex){
    		System.out.println(ex.getMessage());
    	}
    }
    public void listLocacoesAtivas(){
    	try{
	        for(int i=0; i<locacoes.size(); i++){
	            if(!locacoes.get(i).getFinalizada())
	                System.out.println(locacoes.get(i).getProtocolo());
	        }
    	} catch (Exception ex){
    		System.out.println(ex.getMessage());
    	}
    }

}