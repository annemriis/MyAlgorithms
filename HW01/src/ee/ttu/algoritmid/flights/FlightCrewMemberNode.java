package ee.ttu.algoritmid.flights;

public class FlightCrewMemberNode implements Node {

    private FlightCrewMember data;
    private FlightCrewMemberNode parent;
    private FlightCrewMemberNode left;
    private FlightCrewMemberNode right;
    private int height;

    public FlightCrewMemberNode(FlightCrewMember data) {
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

    @Override
    public int getHeight() {
        return height;
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
