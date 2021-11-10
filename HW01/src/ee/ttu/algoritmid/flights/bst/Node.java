package ee.ttu.algoritmid.flights.bst;


import ee.ttu.algoritmid.flights.FlightCrewMember;

public interface Node {

    public double getValue();

    public FlightCrewMember getData();

    public Node getLeft();

    public Node getRight();

    public int getHeight();

    public int calculateBalance();

    public void updateNode();
}
