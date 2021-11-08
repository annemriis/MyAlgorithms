package ee.ttu.algoritmid.flights;


public class FlightCrewMemberBST {

    private FlightCrewMemberNode rootNode;

    public FlightCrewMemberNode getRootNode() {
        return this.rootNode;
    }

    public boolean isEmpty() {
        return rootNode != null;
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
        while (node != null) {
            if (value - k1 <= node.getValue()) {
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

    public void inorderTraversal(FlightCrewMemberNode root) {
        if (root != null) {
            if (root.getLeft() != null) {
                inorderTraversal(root.getLeft());
            }
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

        CrewMemberTemp crewMemberTemp1 = new CrewMemberTemp("Kati", FlightCrewMember.Role.PILOT, 126.912);
        CrewMemberTemp crewMemberTemp2 = new CrewMemberTemp("Kati", FlightCrewMember.Role.PILOT, 114.89255);
        CrewMemberTemp crewMemberTemp3 = new CrewMemberTemp("Kati", FlightCrewMember.Role.PILOT, 129.01);
        CrewMemberTemp crewMemberTemp4 = new CrewMemberTemp("Kati", FlightCrewMember.Role.PILOT, 117.17);
        CrewMemberTemp crewMemberTemp5 = new CrewMemberTemp("Kati", FlightCrewMember.Role.PILOT, 149.61);
        CrewMemberTemp crewMemberTemp6 = new CrewMemberTemp("Kati", FlightCrewMember.Role.PILOT, 142.44);
        CrewMemberTemp crewMemberTemp7 = new CrewMemberTemp("Kati", FlightCrewMember.Role.PILOT, 120.54);
        CrewMemberTemp crewMemberTemp8 = new CrewMemberTemp("Kati", FlightCrewMember.Role.PILOT, 120.23);

        FlightCrewMemberBST binarySearchTree = new FlightCrewMemberBST();
        binarySearchTree.insert(crewMemberTemp1);
        binarySearchTree.insert(crewMemberTemp2);
        binarySearchTree.insert(crewMemberTemp3);
        binarySearchTree.insert(crewMemberTemp4);
        binarySearchTree.insert(crewMemberTemp5);
        binarySearchTree.insert(crewMemberTemp6);
        binarySearchTree.insert(crewMemberTemp7);
        binarySearchTree.insert(crewMemberTemp8);

        System.out.println(binarySearchTree.toString());

        binarySearchTree.inorderTraversal(binarySearchTree.rootNode);

        System.out.println(binarySearchTree.findElementGreaterAtLeastByK1(binarySearchTree.rootNode, 5, 10, 115.23).getValue());
        //System.out.println(binarySearchTree.findElementLessAtLeastByK1(binarySearchTree.rootNode, 3, Integer.MAX_VALUE, 125.5).getValue());


        CrewMemberTemp crewMemberTemp9 = new CrewMemberTemp("Kati", FlightCrewMember.Role.PILOT, 106.74079484660564);
        CrewMemberTemp crewMemberTemp10 = new CrewMemberTemp("Kati", FlightCrewMember.Role.PILOT, 115.01670613016454);
        CrewMemberTemp crewMemberTemp11 = new CrewMemberTemp("Kati", FlightCrewMember.Role.PILOT, 110.11384775145501);
        CrewMemberTemp crewMemberTemp12 = new CrewMemberTemp("Kati", FlightCrewMember.Role.PILOT, 103.1383714694357);
        CrewMemberTemp crewMemberTemp13 = new CrewMemberTemp("Kati", FlightCrewMember.Role.PILOT, 122.59641529930622);
        FlightCrewMemberBST binarySearchTree1 = new FlightCrewMemberBST();
        binarySearchTree1.insert(crewMemberTemp9);
        binarySearchTree1.insert(crewMemberTemp10);
        binarySearchTree1.insert(crewMemberTemp11);
        binarySearchTree1.insert(crewMemberTemp12);
        binarySearchTree1.insert(crewMemberTemp13);

        System.out.println(binarySearchTree1.toString());

        System.out.println(binarySearchTree1.findElementLessAtLeastByK1(binarySearchTree1.rootNode, 5, 10, 133.41630478217095).getValue());
    }
}
