package Filosofos;

public class Filosofo {
    private String nome;
    private int numero;
    private int qtyVezesComeu = 0;
    private int qtyVezesTentouComer = 0;
    private int qtyVezesPensou = 0;

    public int getNumero() {
        return numero;
    }

    public String getNome() {
        return nome;
    }
    public void setNome(String nome, int numero) {
        this.nome = nome;
        this.numero = numero;
    }

    public void somatorioVezesComeu(){
        qtyVezesComeu++;
    }

    public void somatorioVezesTentouComer(){
        qtyVezesTentouComer++;
    }

    public void somatorioVezesPensou(){
        qtyVezesPensou++;
    }

    public void resultadoFinal(){
        System.out.println("-----------");
        System.out.println(nome + " comeu " + qtyVezesComeu + " vezes");
        System.out.println(nome + " tentou comer " + qtyVezesTentouComer + " vezes");
        System.out.println(nome + " pensou " + qtyVezesPensou+ " vezes");
        System.out.println("-----------");
        
    }

    
}
