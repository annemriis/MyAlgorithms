package ee.ttu.algoritmid.flights.bst;

import ee.ttu.algoritmid.flights.FlightCrewMember;

import java.util.List;

public interface BinarySearchTree {

    public FlightCrewMemberNode getRootNode();

    public void insert(FlightCrewMember flightCrewMember);

    public void remove(FlightCrewMember flightCrewMember);

    public FlightCrewMemberNode findMin(FlightCrewMemberNode node);

    public FlightCrewMemberNode findElementLessAtLeastByK1(FlightCrewMemberNode node, double k1, double k2, double value);

    public FlightCrewMemberNode findElementGreaterAtLeastByK1(FlightCrewMemberNode node, double k1, double k2, double value);

    public List<FlightCrewMemberNode> inorderTraversal(FlightCrewMemberNode root, List<FlightCrewMemberNode> inorder);
}
