package algo;

public class RunAlgo implements Runnable {
    private IAlgo myAlgo;
    private AlgoComparator myComparator;
    private int position;

    public RunAlgo(IAlgo al , AlgoComparator co, int pos) {
        myAlgo = al;
        myComparator = co;
        position = pos;
    }

    @Override
    public void run() {
        long res = myAlgo.resoudre();
        myComparator.setRes(res, position, myAlgo.getSac());
    }
}
