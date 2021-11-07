package ee.ttu.algoritmid.flights;


public class FlightCrewMemberBST {

    private FlightCrewMemberNode rootNode;

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
            root.getLeft().setParent(root);
        } else if (flightCrewMemberExperience > root.getValue()) {
            root.setRight( insertNode(root.getRight(), flightCrewMember));
            root.getRight().setParent(root);
        }
        return root;
    }

    public void remove(FlightCrewMemberNode node) {
        FlightCrewMemberNode leftChild = node.getLeft();
        FlightCrewMemberNode rightChild = node.getRight();
        if (leftChild == null && rightChild == null) {
            removeNodeWithNoChildren(node);
        } else if (leftChild != null && rightChild != null) {
            removeNodeWithTwoChildren(node);
        } else {
            removeNodeWithOneChild(node);
        }
    }

    public void removeNodeWithNoChildren(FlightCrewMemberNode node) {
        FlightCrewMemberNode parent = node.getParent();
        if (parent != null) {
            if (parent.getLeft() != null && parent.getLeft() == node) {
                parent.setLeft(null);
            } else if (parent.getRight() != null && parent.getRight() == node) {
                parent.setRight(null);
            }
        } else if (rootNode.equals(node)) {
            rootNode = null;
        }
    }

    public void removeNodeWithOneChild(FlightCrewMemberNode node) {
        FlightCrewMemberNode parent = node.getParent();
        FlightCrewMemberNode leftChild = node.getLeft();
        FlightCrewMemberNode rightChild = node.getRight();
        FlightCrewMemberNode newNode;
        if (leftChild != null) {
            newNode = leftChild;
        } else {
            newNode = rightChild;
        }
        if (parent != null) {
            if (findIfNodeIsLeftOrRight(node, parent).equals("Left")) {
                parent.setLeft(newNode);
            } else {
                parent.setRight(newNode);
            }
        } else {
            newNode.setParent(null);
            rootNode = newNode;
        }
    }

    public void removeNodeWithTwoChildren(FlightCrewMemberNode node) {
        FlightCrewMemberNode successor = findSuccessor(node);
        remove(successor);
        swapNodesData(node, successor);
    }

    private void swapNodesData(FlightCrewMemberNode node, FlightCrewMemberNode successor) {
        FlightCrewMemberNode parent = node.getParent();
        FlightCrewMemberNode left = node.getLeft();
        FlightCrewMemberNode right = node.getRight();
        if (left != null) {
            left.setParent(successor);
            successor.setLeft(left);
        }
        if (right != null) {
            right.setParent(successor);
            successor.setRight(right);
        }
        if (parent != null) {
            if (findIfNodeIsLeftOrRight(node, parent).equals("Left")) {
                parent.setLeft(successor);
            } else {
                parent.setRight(successor);
            }
            successor.setParent(parent);
        } else {
            successor.setParent(null);
            rootNode = successor;
        }
    }

    private String findIfNodeIsLeftOrRight(FlightCrewMemberNode node, FlightCrewMemberNode parent) {
        if (parent.getLeft() == node) {
            return "Left";
        }
        return "Right";
    }

    public FlightCrewMemberNode findSuccessor(FlightCrewMemberNode node) {
        if (node.getRight() != null) {
            return findMin(node.getRight());
        }
        FlightCrewMemberNode parent = node.getParent();
        while (parent != null && node == parent.getRight()) {
            node = parent;
            parent = parent.getParent();
        }
        return parent;
    }

    public FlightCrewMemberNode findMin(FlightCrewMemberNode node) {
        while (node.getLeft() != null) {
            node = node.getLeft();
        }
        return node;
    }

    public FlightCrewMemberNode findMax(FlightCrewMemberNode node) {
        while (node.getRight() != null) {
            node = node.getRight();
        }
        return node;
    }

    public FlightCrewMemberNode findElementLessAtLeastByK1(FlightCrewMemberNode node, double k1, double k2, double value) {
        FlightCrewMemberNode element = null;
        if (node == null || nodeValueIsLessThanK1AndGreaterThanK2(node, k1, k2, value)) {
            element = node;
            if (nodeHasLeft(node) && nodeValueIsLessThanK1AndGreaterThanK2(node.getLeft(), k1, k2, value)) {
                element = findElementLessAtLeastByK1(node.getLeft(), k1, k2, value);
            }
        } else if (value < node.getValue() && node.getLeft() != null) {
            element = findElementLessAtLeastByK1(node.getLeft(), k1, k2, value);
        } else if (value > node.getValue() && node.getRight() != null){
            element = findElementLessAtLeastByK1(node.getRight(), k1, k2, value);
        }
        return element;
    }

    private boolean nodeHasLeft(FlightCrewMemberNode node) {
        return node.getLeft() != null;
    }

    private boolean nodeValueIsLessThanK1AndGreaterThanK2(FlightCrewMemberNode node, double k1, double k2, Double value) {
        return value - node.getValue() >= k1 && value - node.getValue() <= k2;
    }

    public FlightCrewMemberNode findElementGreaterAtLeastByK1(FlightCrewMemberNode node, double k1, double k2, double value) {
        FlightCrewMemberNode element = null;
        if (node == null || nodeValueIsGreaterThanK1AndLessThanK2(node, k1, k2, value)) {
            element = node;
        } else if (value < node.getValue() && node.getLeft() != null && nodeValueIsGreaterThanK1AndLessThanK2(node.getLeft(), k1, k2, value)) {
            element = findElementGreaterAtLeastByK1(node.getLeft(), k1, k2, value);
        } else if (node.getRight() != null){
            element = findElementGreaterAtLeastByK1(node.getRight(), k1, k2, value);
        }
        return element;
    }

    private boolean nodeHasRight(FlightCrewMemberNode node) {
        return node.getRight() != null;
    }

    private boolean nodeValueIsGreaterThanK1AndLessThanK2(FlightCrewMemberNode node, double k1, double k2, Double value) {
        return node.getValue() - value >= k1 && node.getValue() - value <= k2;
    }

    private double calculateNodesValues(FlightCrewMemberNode node, double k1, double k2, Double value) {
        return (node.getValue() - value) + (node.getValue() - value);
    }

    public void inorderTraversal(Node root) {
        if (root != null) {
            if (root.getLeft() != null) {
                inorderTraversal(root.getLeft());
            }
            System.out.println(root.getValue());
            if (root.getRight() != null) {
                inorderTraversal(root.getRight());
            }
        }
    }

    public String toString() {
        return rootNode.printTree(new StringBuilder(), true, new StringBuilder()).toString();
    }

    public static void main(String[] args) {
        class CrewMemberTemp implements FlightCrewMember {

            private final String name;
            private final Role role;
            private final double experience;

            public CrewMemberTemp(String name, Role role, double experience) {
                this.name = name;
                this.role = role;
                this.experience = experience;
            }

            @Override
            public String getName() {
                return name;
            }

            @Override
            public Role getRole() {
                return role;
            }

            @Override
            public double getWorkExperience() {
                return experience;
            }

        }

        CrewMemberTemp crewMemberTemp1 = new CrewMemberTemp("Kati", FlightCrewMember.Role.PILOT, 3.5);
        CrewMemberTemp crewMemberTemp2 = new CrewMemberTemp("Kati", FlightCrewMember.Role.PILOT, 5.5);
        CrewMemberTemp crewMemberTemp3 = new CrewMemberTemp("Kati", FlightCrewMember.Role.PILOT, 1.5);
        CrewMemberTemp crewMemberTemp4 = new CrewMemberTemp("Kati", FlightCrewMember.Role.PILOT, 4.5);
        CrewMemberTemp crewMemberTemp5 = new CrewMemberTemp("Kati", FlightCrewMember.Role.PILOT, 12.5);
        CrewMemberTemp crewMemberTemp6 = new CrewMemberTemp("Kati", FlightCrewMember.Role.PILOT, 0.5);
        CrewMemberTemp crewMemberTemp7 = new CrewMemberTemp("Kati", FlightCrewMember.Role.PILOT, 120.5);

        FlightCrewMemberBST binarySearchTree = new FlightCrewMemberBST();
        binarySearchTree.insert(crewMemberTemp1);
        binarySearchTree.insert(crewMemberTemp2);
        binarySearchTree.insert(crewMemberTemp3);
        binarySearchTree.insert(crewMemberTemp4);
        binarySearchTree.insert(crewMemberTemp5);
        binarySearchTree.insert(crewMemberTemp6);
        binarySearchTree.insert(crewMemberTemp7);

        System.out.println(binarySearchTree.toString());

        binarySearchTree.inorderTraversal(binarySearchTree.rootNode);
    }
}
