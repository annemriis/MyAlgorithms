package ee.ttu.algoritmid.flights.bst;


import ee.ttu.algoritmid.flights.FlightCrewMember;

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

    /**
     *
     * @param node
     * @return
     */
    private FlightCrewMemberNode rotateRight(FlightCrewMemberNode node) {
        FlightCrewMemberNode left = node.getLeft();
        FlightCrewMemberNode leftRight = left.getRight();
        node.setLeft(leftRight);
        left.setRight(node);
        node.updateNode();
        left.updateNode();
        return left;
    }

    /**
     *
     * @param node
     * @return
     */
    private FlightCrewMemberNode rotateLeft(FlightCrewMemberNode node) {
        FlightCrewMemberNode right = node.getRight();
        FlightCrewMemberNode rightLeft = right.getLeft();
        node.setRight(rightLeft);
        right.setLeft(node);
        node.updateNode();
        right.updateNode();
        return right;
    }

    /**
     *
     * @param node
     * @return
     */
    private FlightCrewMemberNode balance(FlightCrewMemberNode node) {
        if (node == null) {
            return null;
        }
        int balance = node.calculateBalance();
        // Left heavy.
        if (balance < -1) {
            //
            if (node.getLeft().calculateBalance() > 0) {
                node.setLeft(rotateLeft(node.getLeft()));
            }
            node = rotateRight(node);
        }
        // Right heavy.
        if (balance > 1) {
            //
            if (node.getRight().calculateBalance() < 0) {
                node.setRight(rotateRight(node.getRight()));
            }
            node = rotateLeft(node);
        }
        return node;
    }
}
