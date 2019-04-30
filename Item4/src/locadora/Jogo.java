package locadora;


public class Jogo{
    
    private String titulo;
    private double preco_base;
    private int qtd;
    private Plataforma p;

    public Jogo(String titulo, double preco_base, int qtd, Plataforma p){
        this.titulo = titulo;
        this.preco_base = preco_base;
        this.qtd = qtd;
        this.p = p;
    }

    public void setTitulo(String titulo){
        this.titulo = titulo;
    }
    public String getTitulo(){
        return titulo;
    }
    
    public void setPrecoBase(double preco){
        this.preco_base = preco;
    }
    public double getPrecoBase(){
        return preco_base;
    }

    public void setQtd(int qtd){
        this.qtd = qtd;
    }

    public int getQtd(){
        return qtd;
    }

    public void addQtd(){
        qtd++;
    }

    public void subQtd(){
        qtd--;
    }

    public void setPlataforma(Plataforma p){
        this.p = p;
    }
    public Plataforma getPlataforma(){
        return p;
    }
    
}