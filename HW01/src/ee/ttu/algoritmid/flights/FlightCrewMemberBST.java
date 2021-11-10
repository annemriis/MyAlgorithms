package ee.ttu.algoritmid.flights;


import java.util.List;

public class FlightCrewMemberBST {

    protected FlightCrewMemberNode rootNode;

    public FlightCrewMemberNode getRootNode() {
        return this.rootNode;
    }

    public void insert(FlightCrewMember flightCrewMember) {
        rootNode = insertNode(rootNode, flightCrewMember);
    }

    public FlightCrewMemberNode insertNode(FlightCrewMemberNode root, FlightCrewMember flightCrewMember) {
        double flightCrewMemberExperience = flightCrewMember.getWorkExperience();
        if (root == null) {
            return new FlightCrewMemberNode(flightCrewMember);
        } else if (flightCrewMemberExperience < root.getValue()) {
            root.setLeft(insertNode(root.getLeft(), flightCrewMember));
        } else if (flightCrewMemberExperience >= root.getValue()) {
            root.setRight(insertNode(root.getRight(), flightCrewMember));
        }
        return root;
    }

    public void remove(FlightCrewMember flightCrewMember) {
        rootNode = removeNode(rootNode, flightCrewMember);
    }

    public FlightCrewMemberNode removeNode(FlightCrewMemberNode root, FlightCrewMember flightCrewMember) {
        double flightCrewMemberExperience = flightCrewMember.getWorkExperience();
        if (root == null) {
            return null;
        }
        if (flightCrewMemberExperience < root.getValue()) {
            root.setLeft(removeNode(root.getLeft(), flightCrewMember));
        } else if (flightCrewMemberExperience > root.getValue()) {
            root.setRight(removeNode(root.getRight(), flightCrewMember));
        } else if (root.getLeft() == null && root.getRight() == null) {
            root = null;
        } else if (root.getLeft() == null) {
            root = root.getRight();
        } else if (root.getRight() == null) {
            root = root.getLeft();
        } else {
            removeNodeWithTwoChildren(root);
        }
        return root;
    }

    private void removeNodeWithTwoChildren(FlightCrewMemberNode node) {
        FlightCrewMemberNode successor = findSuccessor(node);
        node.setData(successor.getData());
        node.setRight(removeNode(node.getRight(), successor.getData()));
    }

    public FlightCrewMemberNode findSuccessor(FlightCrewMemberNode node) {
        if (node.getRight() != null) {
            return findMin(node.getRight());
        }
        return null;
    }

    public FlightCrewMemberNode findMin(FlightCrewMemberNode node) {
        while (node.getLeft() != null) {
            node = node.getLeft();
        }
        return node;
    }

    public FlightCrewMemberNode findElementLessAtLeastByK1(FlightCrewMemberNode node, double k1, double k2, double value) {
        FlightCrewMemberNode element = null;
        while (node != null) {
            if (value - k1 < node.getValue()) {
                node = node.getLeft();
            } else {
                if (nodeValueIsLessThanK1AndGreaterThanK2(node, k1, k2, value)) {
                    element = node;
                }
                node = node.getRight();
            }
        }
        return element;
    }

    private boolean nodeValueIsLessThanK1AndGreaterThanK2(FlightCrewMemberNode node, double k1, double k2, Double value) {
        return value - node.getValue() >= k1 && value - node.getValue() <= k2;
    }

    public FlightCrewMemberNode findElementGreaterAtLeastByK1(FlightCrewMemberNode node, double k1, double k2, double value) {
        FlightCrewMemberNode element = null;
        while (node != null) {
            if (value + k1 <= node.getValue()) {
                if (nodeValueIsGreaterThanK1AndLessThanK2(node, k1, k2, value)) {
                    element = node;
                }
                node = node.getLeft();
            } else {
                node = node.getRight();
            }
        }
        return element;
    }

    private boolean nodeValueIsGreaterThanK1AndLessThanK2(FlightCrewMemberNode node, double k1, double k2, Double value) {
        return node.getValue() - value >= k1 && node.getValue() - value <= k2;
    }

    public List<FlightCrewMemberNode> inorderTraversal(FlightCrewMemberNode root, List<FlightCrewMemberNode> inorder) {
        if (root != null) {
            if (root.getLeft() != null) {
                inorderTraversal(root.getLeft(), inorder);
            }
            inorder.add(root);
            if (root.getRight() != null) {
                inorderTraversal(root.getRight(), inorder);
            }
        }
        return inorder;
    }
}
