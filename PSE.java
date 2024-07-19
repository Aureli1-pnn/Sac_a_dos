package algo;

import sacados.Objet;
import sacados.SacADos;

public class PSE implements IAlgo{
	
	private SacADos sac;
	private Node bestNode;
	private float poidsMax;
    private double minVal;
	
	public PSE(SacADos sac)
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
	public long resoudre()
	{
        long startTime = System.currentTimeMillis();
		   //On initialise certaines données
        this.poidsMax = sac.getPoidsMaximal();
        this.minVal = 0;

        float maxVal = 0;

        //La valeur max si l'on met tous les objets dans le sac sans prendre en compte le poids max
        for(Objet o : sac.getObjetsPossibles())
            maxVal += o.getValeur();


        //On crée maintenant la racine de l'arbre
        Node racine = new Node();
        this.bestNode = racine;

        //On crée l'abre
        creeArbreRec(0, racine, maxVal);

        //On a maintenant la meilleure solution contenue dans this.meilleurRésultat
        //On remonte alors jusqu'à la racine en ajoutant dans le sac les objets
        sac.clearSac();
        ajouterSolutionRec(this.bestNode);

        //Le sac est trié avec la meilleure solution trouvée

        //On retourne le temps qu'a mis la solution
        return System.currentTimeMillis() - startTime;

	}
	
	/**
     * Permet de créer l'arbre binaires des solutions possibles récursivement
     *
     * @param index profondeur/objet à construire
     * @param noeudActuel noeud actuel
     * @param maxPossible correspond à la borne supérieure du noeud actuel
     */
    private void creeArbreRec(int index, Node noeudAct, double maxPossible) {

        //On ajoute l'objet suivant dans le fils gauche
    	noeudAct.setFilsGauche(sac.getObjetsPossibles().get(index), index);
        //On crée une copie (on ne rajoute rien) dans le fils droit
    	noeudAct.setFilsDroit();

        //On teste si une nouvelle meilleure solution possible est trouvée à gauche
        if(noeudAct.getFilsGauche().getValeur() >= this.minVal && noeudAct.getFilsGauche().getPoids() <= this.poidsMax) {
            this.bestNode = noeudAct.getFilsGauche();
            this.minVal = this.bestNode.getValeur();
        }

        //Si il reste encore des objets à mettre dans le sac et que le poids maximal n'est pas atteint
        if(index < sac.getObjetsPossibles().size()-1 && noeudAct.getPoids() < this.poidsMax) {
            //Le noeud gauche n'est pas concerné par le potentielMax,
            //Car s'il ne pouvait pas atteindre la borne minimale, il aurait été supprimé au noeud inférieur
            creeArbreRec(index + 1, noeudAct.getFilsGauche(), maxPossible);

            //On calcule le 'potentiel' des sous arbres, en calculant la borne maximale possible
            //Pour cela, on prend la valeur max possible - l'objet que l'on ne va pas ajouter
            double potentielMax = maxPossible - sac.getObjetsPossibles().get(index).getValeur();

            //, on continue la recherche
            if(potentielMax >= this.minVal ) {
                creeArbreRec(index + 1, noeudAct.getFilsDroit(), potentielMax);
            }
            
            //Sinon, on abandonne la recherche de ce noeud, car il n'y aura aucune solution correcte possible
            //ou meilleure que celle déjà trouvé
        }
    }
    
    /**
     * Ajoute les objets de la solution trouvée en remontant l'arbre à partir du meilleur noeud récursivement
     *
     * @param noeudGagnant noeud correspondant au meilleur résultat trouvé
     */
    private void ajouterSolutionRec(Node nodeAct) {
        int i;

        //Si l'index est -1, il n'y a aucun objet à ajouter
        if((i = nodeAct.getIndexObjet()) != -1) {
            //On ajoute l'objet
        	sac.add(sac.getObjetsPossibles().get(i));
        }

        //On remonte jusqu'à la racine en ajoutant tout les objets
        if(!nodeAct.getParent().isRacine()) {
            ajouterSolutionRec(nodeAct.getParent());
        }
    }

	public String toString() {
		return "Méthode PSE";
	}
}
