package algo;

import java.util.ArrayList;
import java.util.Arrays;

import sacados.SacADos;

public class AlgoComparator {
    private static int ALGO_NUMBER = 2; 
    private long [] res;
    private SacADos [] resSac;

    public AlgoComparator() {
        res = new long[ALGO_NUMBER];
        resSac = new SacADos[ALGO_NUMBER];
    }

    public void setRes(long value, int position, SacADos resSacValue) {
        res[position] = value;
        resSac[position] = new SacADos(resSacValue);
    }
    /**
     * Lance nos algorithmes dans différents threads et compare les résultats
     */
    public void compareAlgo(ArrayList<SacADos> sacs) {

        int j = 0;
        int [] resFin = new int[ALGO_NUMBER];
        Arrays.fill(resFin, 0);

        String[] algoNames = new String[ALGO_NUMBER];
        algoNames[0] = "Glouton";
        algoNames[1] = "PSE";

        for (SacADos sac : sacs) {
            System.out.println(" ---------------------------- Comparaison " + (j+1) + " avec un poids de " + sac.getPoidsMaximal() + " ----------------------------\n");
            /*for (Objet item : sac.getObjetsPossibles()) {
                System.out.println(item.toString());
            }*/
            RunAlgo [] algos = new RunAlgo[ALGO_NUMBER];
            algos[0] = new RunAlgo(new Greedy(new SacADos(sac)), this, 0);
            algos[1]  = new RunAlgo(new PSE(new SacADos(sac)), this, 1);
            Thread [] threads = new Thread[ALGO_NUMBER];
            for(int i = 0; i < ALGO_NUMBER; i ++) {
                threads[i] = new Thread(algos[i]);
                threads[i].start();
            }

            for(int i = 0; i < ALGO_NUMBER; i ++) {
                try {
                    threads[i].join();
                } catch (Exception e) {
                    System.err.println(e.getMessage());
                }
            }


            int min = 0;
            for(int i = 0; i < ALGO_NUMBER; i ++) {
                System.out.println("L'algorithme " + algoNames[i] + " s'est éxécuté en " + res[i] + " milisecondes\n " + resSac[i].toString());
                if (res[i] < res[min]) {
                    min = i;
                    res[min] = res[i];
                }
            }
            System.out.println("Pour la comparaison " + (j+1) + " le meilleur algorithme est " + algoNames[min] + " avec un temps de " + res[min] + " milisecondes\n");
            resFin[min] ++;
            j ++;
        
        }
        System.out.println("\n-------------------------- Partie résultat --------------------------\n");
        int maxFin = 0;
        for(int i = 0; i < ALGO_NUMBER; i ++) {
            System.out.println("L'algorithme " + algoNames[i] + " a été le meilleur " + resFin[i] + " fois.");
            if (resFin[i] > maxFin) {
                maxFin = i;
                resFin[maxFin] = resFin[i];
            }
        }

        System.out.println("Après " + resFin.length + " comparaisons le meilleurs algorithmes est " + algoNames[maxFin] + " où il a été le meilleur " + resFin[maxFin] + " fois.\n\n");
        
    }

    
}
