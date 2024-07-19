package algo;

import sacados.Objet;

public class Node {

    private Node filsGauche;
    private Node filsDroit;
    private final Node parent;
    private final int depth;
    private final double value;
    private final double weight;
    private int indexObjet;

    /**
     * Constructeur pour instancier la racine de l'arbre
     */
    public Node(){
        this.parent = null; // pas de parent --> racine
        this.depth = 0;
        this.weight = 0.0;
        this.value = 0.0;
    }

    /**
     * Constructeur pour instancier un nouveau noeud/branche à l'arbre
     */
    public Node(Node parent, double valeur, double poids, int index){
        this.parent = parent;
        this.depth = parent.depth + 1;
        this.weight = poids;
        this.value = valeur;
        this.indexObjet = index;
    }

    /**
     * Permet de construire le fils gauche en ajoutant un objet supplémentaire
     */
    public void setFilsGauche(Objet o, int index) {
        this.filsGauche = new Node(this, this.value + o.getValeur(), this.weight + o.getPoids(), index);
    }

    /**
     * Permet de construire le fils droit
     * Celui-ci n'est qu'une copie de son père, mais il n'a pas d'objet (indiqué par l'index)
     */
    public void setFilsDroit() {
        this.filsDroit = new Node(this, this.value, this.weight, -1);
    }

    public Node getFilsGauche() {
        return this.filsGauche;
    }

    public Node getFilsDroit() {
        return this.filsDroit;
    }

    public double getValeur() {
        return this.value;
    }

    public double getPoids() {
        return this.weight;
    }

    public int getIndexObjet() {
        return this.indexObjet;
    }

    public boolean isRacine() {
        return this.parent == null;
    }

    public Node getParent() {
        return this.parent;
    }
}
