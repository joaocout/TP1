package locadora;

class Main{
    public static void main(String[] args){
        Plataforma ps4 = new Plataforma("PS4", 1.5);
        Jogo game = new Jogo("God of War", 20f, 12, ps4);
        Locacao loc = new Locacao("0", "0", 0, game);
        loc.setProtocolo();
        loc.alugar();

        Locacao loc1 = new Locacao("2019-04-29", "15:12", 0, game);
        loc1.setProtocolo();

        Locacao loc2 = new Locacao("2019-04-29", "15:12", 0, game);
        loc2.setProtocolo();

        Locacao loc3 = new Locacao("2019-04-29", "15:12", 0, game);
        loc3.setProtocolo();
        
        System.out.println(loc.getDataAluguel() + " " + loc.getHoraAluguel());
        System.out.println(loc1.getProtocolo());
        System.out.println(loc2.getProtocolo());
        System.out.println(loc3.getProtocolo());
        
        System.exit(0);
    }
}