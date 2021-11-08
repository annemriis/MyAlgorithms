package ee.ttu.algoritmid.flights;


public interface Node {

    public double getValue();

    public Node getLeft();

    public Node getRight();

    public int getHeight();

    public StringBuilder printTree(StringBuilder stringBuilder1, boolean bool, StringBuilder stringBuilder2);

    public int calculateBalance();

    public void updateNode();
}
