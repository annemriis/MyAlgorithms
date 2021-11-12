package ee.ttu.algoritmid.flights.bst;

import ee.ttu.algoritmid.flights.FlightCrewMember;

public class FlightCrewMemberNode implements Node {

    private FlightCrewMember data;
    private FlightCrewMemberNode left;
    private FlightCrewMemberNode right;
    private int height;

    public FlightCrewMemberNode(FlightCrewMember data) {
        this.data = data;
    }

    public void setData(FlightCrewMember data) {
        this.data = data;
    }

    public void setLeft(FlightCrewMemberNode left) {
        this.left = left;
    }

    public void setRight(FlightCrewMemberNode right) {
        this.right = right;
    }

    @Override
    public FlightCrewMember getData() {
        return data;
    }

    @Override
    public double getValue() {
        return data.getWorkExperience();
    }

    @Override
    public FlightCrewMemberNode getLeft() {
        return left;
    }

    @Override
    public FlightCrewMemberNode getRight() {
        return right;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public int calculateBalance() {
        int leftHeight = height(left);
        int rightHeight = height(right);
        return rightHeight - leftHeight;
    }

    @Override
    public void updateNode() {
        int leftHeight = height(left);
        int rightHeight = height(right);
        height = calculateHeight(leftHeight, rightHeight);
    }

    private int calculateHeight(int leftHeight, int rightHeight) {
        if (leftHeight > rightHeight) {
            return leftHeight + 1;
        }
        return rightHeight + 1;
    }

    private int height(FlightCrewMemberNode node) {
        if (node == null) {
            return -1;
        }
        return node.getHeight();
    }
}
