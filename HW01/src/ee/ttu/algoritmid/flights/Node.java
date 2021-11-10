package ee.ttu.algoritmid.flights;


public interface Node {

    public double getValue();

    public Node getLeft();

    public Node getRight();

    public int getHeight();

    public int calculateBalance();

    public void updateNode();
}
