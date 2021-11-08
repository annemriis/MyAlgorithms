package ee.ttu.algoritmid.flights;

import java.util.LinkedList;
import java.util.Queue;

public class FlightCrewMemberNode implements Node {

    private FlightCrewMember data;
    private FlightCrewMemberNode parent;
    private FlightCrewMemberNode left;
    private FlightCrewMemberNode right;
    private int height;
    private Queue<FlightCrewMember> duplicates = new LinkedList<>();

    public FlightCrewMemberNode(FlightCrewMember data) {
        this.data = data;
    }

    public void setData(FlightCrewMember data) {
        this.data = data;
    }

    public void setParent(FlightCrewMemberNode parent) {
        this.parent = parent;
    }

    public void setLeft(FlightCrewMemberNode left) {
        this.left = left;
    }

    public void setRight(FlightCrewMemberNode right) {
        this.right = right;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setDuplicates(Queue<FlightCrewMember> duplicates) {
        this.duplicates = duplicates;
    }

    public FlightCrewMember getData() {
        return data;
    }

    @Override
    public double getValue() {
        return data.getWorkExperience();
    }

    public FlightCrewMemberNode getParent() {
        return parent;
    }

    @Override
    public FlightCrewMemberNode getLeft() {
        return left;
    }

    @Override
    public FlightCrewMemberNode getRight() {
        return right;
    }

    public int getHeight() {
        return height;
    }

    public Queue<FlightCrewMember> getDuplicates() {
        return duplicates;
    }

    public void addDuplicate(FlightCrewMember flightCrewMember) {
        duplicates.add(flightCrewMember);
    }

    public FlightCrewMemberNode getDuplicate() {
        FlightCrewMemberNode duplicate = new FlightCrewMemberNode(duplicates.remove());
        duplicate.setLeft(left);
        duplicate.setRight(right);
        duplicate.setParent(parent);
        duplicate.setDuplicates(duplicates);
        return duplicate;
    }

    /*
        This method will print out constructed tree
        !! DO NOT CHANGE THIS !!
     */
    @Override
    public StringBuilder printTree(StringBuilder prefix, boolean isTail, StringBuilder sb) {
        if(right != null) {
            right.printTree(new StringBuilder().append(prefix).append(isTail ? "│   " : "    "), false, sb);
        }

        sb.append(prefix).append(isTail ? "└── " : "┌── ").append(getValue()).append("\n");

        if(left != null) {
            left.printTree(new StringBuilder().append(prefix).append(isTail ? "    " : "│   "), true, sb);
        }

        return sb;
    }
}
