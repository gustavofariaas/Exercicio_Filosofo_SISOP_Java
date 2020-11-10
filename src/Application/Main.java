package Application;

import java.util.Scanner;
import java.util.concurrent.Semaphore;

import Filosofos.Action;
import Filosofos.Filosofo;

public class Main {
    
    private static Semaphore garfo[];
    private static Thread thread[];
    private static Filosofo filosofo[];
    private static int nThread = 1;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Digite a quantidade de filósofos: ");
        int nFilosofos = scanner.nextInt();

        garfo = new Semaphore[nFilosofos];
        thread = new Thread[nFilosofos];
        filosofo = new Filosofo[nFilosofos];

        System.out.println("Insira o tempo de duração do jantar em segundos: ");
        long duracaoJantar = scanner.nextLong();

        criarSemaforo(garfo,nThread);
        inicializaClasses(nFilosofos, duracaoJantar);
        inicializaThread();
        joinFilosofos(garfo, thread);
        result(garfo, filosofo);
    }

    public static void inicializaClasses(int nFilosofos, long duracaoJantar){
        for (int i = 0 ; i < garfo.length ; i++){
            filosofo[i] = new Filosofo();
            thread[i] = new Thread(new Action("Filósofo "+(i+1),garfo,nFilosofos,filosofo[i],duracaoJantar,i));
        }
    }
    public static void inicializaThread(){
        for (int i = 0 ; i < garfo.length ; i++){
            thread[i].start();
        }
    }
    
    public static void criarSemaforo(Semaphore fork[], int maxThread){
        for (int i = 0 ; i < fork.length ; i++){
            fork[i] = new Semaphore(maxThread, true);
        }
    }
    public static void joinFilosofos(Semaphore fork[], Thread filosofos[]){
       try {
        for (int i = 0 ; i < fork.length ; i++){
            filosofos[i].join();
        }
       } catch (Exception e) {
            e.printStackTrace();
       }
    }
    public static void result(Semaphore fork[], Filosofo filosofo[]){
        try {
            for(int i=0; i< garfo.length; i++) {
                filosofo[i].resultadoFinal();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
