package locadora;


import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

public class Locacao{

    private String data_aluguel; //Formato: 2015-04-12 (yyyy-mm-dd)
    private String data_devolucao;
    private String hora_aluguel; //Formato: 23:20 (hh:mm)
    private String hora_devolucao;
    private double preco_final;
    private boolean finalizada;
    private int protocolo;
    private Jogo game;
    private static int base_protocol = 0;

    public Locacao(String data_aluguel, String hora_aluguel, int protocolo,
    Jogo game){
        finalizada = false;
        this.data_aluguel = data_aluguel;
        this.hora_aluguel = hora_aluguel;
        this.protocolo = protocolo;
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

    public double PrecoFinal(){
        
        preco_final = game.getPrecoBase();
        preco_final = preco_final*game.getPlataforma().getCoeficiente();
        
        int ano=0, mes=0, dia=0;

        int i = 0;        
        for(String a:data_aluguel.split("\\-")){
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
        
        i = 0;
        for(String a:data_devolucao.split("\\-")){
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

        preco_final = preco_final * dias;
        return preco_final;        
    }

    public void setFinalizada(boolean finalizada){
        this.finalizada = finalizada;
    }
    public boolean getFinalizada(){
        return finalizada;
    }

    private int generateProtocolo(){
        base_protocol++;
        return base_protocol;
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

    void alugar(){
        LocalDate dia = LocalDate.now();
        LocalTime hora = LocalTime.now().withSecond(0).withNano(0);
        data_aluguel = dia.toString();
        hora_aluguel = hora.toString();
    }

    void devolver(){
        LocalDate dia = LocalDate.now();
        LocalTime hora = LocalTime.now().withSecond(0).withNano(0);
        data_devolucao = dia.toString();
        hora_devolucao = hora.toString();
    }

    void pagar(){
        finalizada = true;
    }
    
}

