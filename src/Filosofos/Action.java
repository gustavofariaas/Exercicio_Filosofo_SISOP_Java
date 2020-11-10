package Filosofos;

import java.util.concurrent.Semaphore;
import java.util.Random;


public class Action implements Runnable {
    private Filosofo filosofo;
    private Semaphore garfo[];
    private long duracaoJantar = 0;
    private int numeroFilosofo;

    public Action (String nome,Semaphore forks[], int nF, Filosofo f, long dJ, int n) {
        this.garfo = forks;
        this.duracaoJantar = dJ;
        this.filosofo = f;
        this.numeroFilosofo = nF;
        f.setNome(nome, n);
    }

    private void pensar() {
        try {
            System.out.println(filosofo.getNome() + " está pensando");
            Thread.sleep(2000);
            System.out.println(filosofo.getNome() + " processando pensamento");
            Thread.sleep(2000);
            System.out.println(filosofo.getNome() + " acabou de pensar");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        filosofo.somatorioVezesPensou();
    }

    private void comer() {
        try {
            System.out.println(filosofo.getNome() + " está comendo");
            Thread.sleep(1000);
            System.out.println(filosofo.getNome() + " ainda está comendo...");
            Thread.sleep(1000);
            System.out.println(filosofo.getNome() + " acabou de comer");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        filosofo.somatorioVezesComeu();
    }

    private void pegarGarfo(int i) {
        if (garfo[i].tryAcquire()){
            garfo[i].release();
            try {
                garfo[i].acquire();
                tentaComer(i);
                garfo[i].release();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void tentaComer(int i){
        Random random = new Random();
        int tempoRandomico = 0;
        int tempo = 0;
        tempoRandomico = random.nextInt(3);

        try {
            while (tempo < tempoRandomico){
                System.out.println(filosofo.getNome() + " tentará comer!");
                if (garfo[(i+1) % numeroFilosofo].tryAcquire()){
                    garfo[(i+1) % numeroFilosofo].release();
                    garfo[(i+1) % numeroFilosofo].acquire();
                    comer();
                    garfo[(i+1) % numeroFilosofo].release();
                    break;
                }
                else {
                    System.out.println(filosofo.getNome() + " ainda tentando...");
                    tempo++;

                }

                if (tempo == tempoRandomico) {
                    System.out.println(filosofo.getNome() + " não conseguiu comer :c");
                    Thread.sleep(tempoRandomico);
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        filosofo.somatorioVezesTentouComer();
    }

    @Override
    public void run() {
        int fork = filosofo.getNumero();
        long inicio = System.currentTimeMillis();
        long fim;

        while(true){
            pensar();
            pegarGarfo(fork);
            fim = System.currentTimeMillis();
            if((fim-inicio)>(duracaoJantar*1000)) {
                break;
            }
        }
    }
}
