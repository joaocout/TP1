package locadora;

public class Cliente{

    private String nome;
    private String rg; // primary key
    private String cpf;
    private String email;
    private String telefone;

    public Cliente(String nome, String rg, String cpf,
    String email, String telefone){
        this.nome = nome;
        this.rg = rg;
        this.cpf = cpf;
        this.email = email;
        this.telefone = telefone;
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
    
    /*public double Divida(){
        double divida = 0f;
        try{
	        for(int i=0; i<locacoes.size(); i++){
	            if(!locacoes.get(i).getFinalizada())
	                divida+=locacoes.get(i).PrecoFinal();
	        }
        } catch(Exception ex){
        	System.out.println(ex.getMessage());
        }
        return divida;
    }*/
    
    @Override
    public String toString(){
    	return "Nome: " + this.getNome() + "\nRG: " + this.getRG() + "\nCPF: " + this.getCPF() 
    			+ "\nEmail: " + this.getEmail() + "\nTelefone: " + this.getTelefone();
    }
}