package ee.ttu.algoritmid.flights;

import java.util.ArrayList;
import java.util.List;

public class AVLTree extends FlightCrewMemberBST {

    @Override
    public FlightCrewMemberNode insertNode(FlightCrewMemberNode root, FlightCrewMember flightCrewMember) {
        double flightCrewMemberExperience = flightCrewMember.getWorkExperience();
        if (root == null) {
            return new FlightCrewMemberNode(flightCrewMember);
        } else if (flightCrewMemberExperience < root.getValue()) {
            root.setLeft(insertNode(root.getLeft(), flightCrewMember));
            root.getLeft().setParent(root);
        } else if (flightCrewMemberExperience >= root.getValue()) {
            root.setRight(insertNode(root.getRight(), flightCrewMember));
            root.getRight().setParent(root);
        }
        root.updateNode();
        return balance(root);
    }

    @Override
    public void remove(FlightCrewMemberNode node) {
        if (node != null) {
            FlightCrewMemberNode leftChild = node.getLeft();
            FlightCrewMemberNode rightChild = node.getRight();
            FlightCrewMemberNode flightCrewMemberNode;
            if (leftChild == null && rightChild == null) {
                flightCrewMemberNode = removeNodeWithNoChildren(node);
            } else if (leftChild != null && rightChild != null) {
                flightCrewMemberNode = removeNodeWithTwoChildren(node);
            } else {
                flightCrewMemberNode = removeNodeWithOneChild(node);
            }
            balance(flightCrewMemberNode);
        }
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
