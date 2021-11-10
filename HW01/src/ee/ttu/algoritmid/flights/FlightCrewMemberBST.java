package ee.ttu.algoritmid.flights;


import java.util.ArrayList;
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
            root.getLeft().setParent(root);
        } else if (flightCrewMemberExperience >= root.getValue()) {
            root.setRight(insertNode(root.getRight(), flightCrewMember));
            root.getRight().setParent(root);
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
        CrewMemberTemp crewMemberTemp15 = new CrewMemberTemp("Kati", FlightCrewMember.Role.PILOT, 1.5);
        CrewMemberTemp crewMemberTemp652 = new CrewMemberTemp("Kati", FlightCrewMember.Role.PILOT, 4.5);
        CrewMemberTemp crewMemberTemp35 = new CrewMemberTemp("Kati", FlightCrewMember.Role.PILOT, 4.5);
        CrewMemberTemp crewMemberTemp33 = new CrewMemberTemp("Kati", FlightCrewMember.Role.PILOT, 0.6);

        FlightCrewMemberBST binarySearchTree3 = new FlightCrewMemberBST();
        binarySearchTree3.insert(crewMemberTemp15);
        binarySearchTree3.insert(crewMemberTemp652);
        binarySearchTree3.insert(crewMemberTemp35);
        binarySearchTree3.insert(crewMemberTemp33);

        System.out.println(binarySearchTree3.toString());

        binarySearchTree3.remove(crewMemberTemp15);

        System.out.println(binarySearchTree3.toString());

        binarySearchTree3.remove(crewMemberTemp33);

        System.out.println(binarySearchTree3.toString());


        CrewMemberTemp crewMemberTemp1 = new CrewMemberTemp("Kati", FlightCrewMember.Role.PILOT, 126.912);
        CrewMemberTemp crewMemberTemp2 = new CrewMemberTemp("Kati", FlightCrewMember.Role.PILOT, 114.89255);
        CrewMemberTemp crewMemberTemp3 = new CrewMemberTemp("Kati", FlightCrewMember.Role.PILOT, 129.01);
        CrewMemberTemp crewMemberTemp4 = new CrewMemberTemp("Kati", FlightCrewMember.Role.PILOT, 129.01);
        CrewMemberTemp crewMemberTemp5 = new CrewMemberTemp("Kati", FlightCrewMember.Role.PILOT, 129.01);
        CrewMemberTemp crewMemberTemp6 = new CrewMemberTemp("Kati", FlightCrewMember.Role.PILOT, 142.44);
        CrewMemberTemp crewMemberTemp7 = new CrewMemberTemp("Kati", FlightCrewMember.Role.PILOT, 120.23);
        CrewMemberTemp crewMemberTemp8 = new CrewMemberTemp("Kati", FlightCrewMember.Role.PILOT, 121.23);

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

        List<FlightCrewMemberNode> nodes = binarySearchTree.inorderTraversal(binarySearchTree.rootNode, new ArrayList<>());
        for (FlightCrewMemberNode node: nodes) {
            System.out.println(node.getValue());
        }

        System.out.println(binarySearchTree.findElementGreaterAtLeastByK1(binarySearchTree.rootNode, 5, 10, 115.23).getValue());
        System.out.println(binarySearchTree.findElementLessAtLeastByK1(binarySearchTree.rootNode, 3, Integer.MAX_VALUE, 135.5).getValue());


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

        System.out.println(binarySearchTree1.findElementLessAtLeastByK1(binarySearchTree1.rootNode, 3, Integer.MAX_VALUE, 115.1).getValue());
    }
}
