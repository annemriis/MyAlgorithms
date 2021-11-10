package ee.ttu.algoritmid.flights;


public class AVLTree extends FlightCrewMemberBST {

    @Override
    public FlightCrewMemberNode insertNode(FlightCrewMemberNode node, FlightCrewMember flightCrewMember) {
        node = super.insertNode(node, flightCrewMember);
        node.updateNode();
        return balance(node);
    }

    @Override
    public FlightCrewMemberNode removeNode(FlightCrewMemberNode node, FlightCrewMember flightCrewMember) {
        node = super.removeNode(node, flightCrewMember);
        if (node != null) {
            node.updateNode();
        }
        return balance(node);
    }

    private FlightCrewMemberNode rotateRight(FlightCrewMemberNode node) {
        FlightCrewMemberNode left = node.getLeft();
        FlightCrewMemberNode leftRight = left.getRight();
        node.setLeft(leftRight);
        left.setRight(node);
        node.updateNode();
        left.updateNode();
        return left;
    }

    private FlightCrewMemberNode rotateLeft(FlightCrewMemberNode node) {
        FlightCrewMemberNode right = node.getRight();
        FlightCrewMemberNode rightLeft = right.getLeft();
        node.setRight(rightLeft);
        right.setLeft(node);
        node.updateNode();
        right.updateNode();
        return right;
    }

    private FlightCrewMemberNode balance(FlightCrewMemberNode node) {
        if (node == null) {
            return null;
        }
        int balance = node.calculateBalance();
        if (balance < -1) {
            if (node.getLeft().calculateBalance() > 0) {
                node.setLeft(rotateLeft(node.getLeft()));
            }
            node = rotateRight(node);
        }
        if (balance > 1) {
            if (node.getRight().calculateBalance() < 0) {
                node.setRight(rotateRight(node.getRight()));
            }
            node = rotateLeft(node);
        }
        return node;
    }
}
