package algo;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;


import sacados.Objet;
import sacados.SacADos;

public class Greedy implements IAlgo{
    
    private SacADos sac;

    public Greedy(SacADos sac)
	{
		this.sac = sac;
	}
    @Override
	public SacADos getSac() {
		return sac;
	}

	public void setSac(SacADos sac) {
		this.sac = sac;
	}
	
    @Override
	public long resoudre() {
        long startTime = System.currentTimeMillis();
        // Tri les objets possibles selon leur rapport 
        List<Objet> objetPossiblesTriee = this.sac.getObjetsPossibles();
        quickSort(objetPossiblesTriee, 0, objetPossiblesTriee.size() - 1);

        // Tant que c'est possible ajouter les objets de meilleur rapport dans le sac
        Iterator<Objet> it = objetPossiblesTriee.iterator();
        while(it.hasNext() && this.sac.getPoidsReel() < this.sac.getPoidsMaximal()) {
            Objet o = it.next();
            if(this.sac.getPoidsReel()+o.getPoids() <= this.sac.getPoidsMaximal()){
                this.sac.add(o);
            }
        }
        return System.currentTimeMillis() - startTime;
    }

    /**
     * Implémentation de l'algorithme de tri rapide Quicksort par récursivité
     * pour trier une liste d'Objets par rapport (décroissant)
     *
     * @param list           la liste d'objets à trier
     * @param indexDebutList l'indice minimal
     * @param indexFinList   l'indice maximal
     */
    private void quickSort(List<Objet> list, int indexDebutList, int indexFinList) {
        if(indexDebutList < indexFinList) {
            int pivot = (indexFinList - indexDebutList) / 2 + indexDebutList;
            pivot = repartir(list, indexDebutList, indexFinList, pivot);
            quickSort(list, indexDebutList, pivot - 1);
            quickSort(list, pivot + 1, indexFinList);
        }
    }

    /**
     * Permet de répartir les objets autour du pivot
     *
     * @param list           la liste d'objets à répartir
     * @param indexDebutList l'indice minimal
     * @param indexFinList   l'indice maximal
     * @param pivot          l'indice du pivot
     */
    private int repartir(List<Objet> list, int indexDebutList, int indexFinList, int pivot) {
        Collections.swap(list, pivot, indexFinList);
        int i = indexDebutList;
        for (int j = indexDebutList; j < indexFinList; ++j) {
            if(list.get(j).getRapport() > list.get(indexFinList).getRapport()) {
                Collections.swap(list, j, i);
                i++;
            }
        }
        Collections.swap(list, indexFinList, i);
        return i;
    }

	public String toString() {
		return "Méthode Gloutone";
	}
}
