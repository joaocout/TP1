package locadora;


import java.util.ArrayList;

public class Cliente{

    private String nome;
    private String rg;
    private String cpf;
    private String email;
    private String telefone;
    private double divida;
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
    
    public double Divida(){
        divida = 0;
        for(int i=0; i<locacoes.size(); i++){
            if(!locacoes.get(i).getFinalizada())
                divida+=locacoes.get(i).PrecoFinal();
        }
        return divida;
    }

    public void addLocacao(Locacao l){
        locacoes.add(l);
    }
    public void listLocacoesAtivas(){
        for(int i=0; i<locacoes.size(); i++){
            if(!locacoes.get(i).getFinalizada())
                System.out.println(locacoes.get(i).getProtocolo());
        }
    }

}