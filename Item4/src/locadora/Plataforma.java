package locadora;

public class Plataforma {

    private String nome; // primary key
    private double coeficiente;
    
    public Plataforma(String nome, double coeficiente){
        this.nome = nome;
        this.coeficiente = coeficiente;
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
    
    @Override
    public String toString(){
    	return "Plataforma: " + this.getNome() + " | Coeficiente: " + this.getCoeficiente();
    }
    
}