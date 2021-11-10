package ee.ttu.algoritmid.flights;


public class AVLTree extends FlightCrewMemberBST {

    private FlightCrewMemberNode rotateRight(FlightCrewMemberNode node) {
        FlightCrewMemberNode left = node.getLeft();
        FlightCrewMemberNode leftRight = left.getRight();

        node.setLeft(leftRight);
        if (leftRight != null) {
            leftRight.setParent(node);
        }
        left.setRight(node);
        left.setParent(node.getParent());
        node.setParent(left);
        node.updateNode();
        left.updateNode();
        return left;
    }

    private FlightCrewMemberNode rotateLeft(FlightCrewMemberNode node) {
        FlightCrewMemberNode right = node.getRight();
        FlightCrewMemberNode rightLeft = right.getLeft();

        node.setRight(rightLeft);
        if (rightLeft != null) {
            rightLeft.setParent(node);
        }
        right.setLeft(node);
        right.setParent(node.getParent());
        node.setParent(right);
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
            if (node.getLeft().calculateBalance() <= 0) {
                node = rotateRight(node);
            } else {
                node.setLeft(rotateLeft(node.getLeft()));
                node = rotateRight(node);
            }
        }
        if (balance > 1) {
            if (node.getRight().calculateBalance() >= 0) {
                node = rotateLeft(node);
            } else {
                node.setRight(rotateRight(node.getRight()));
                node = rotateLeft(node);
            }
        }
        return node;
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
        CrewMemberTemp crewMemberTemp4 = new CrewMemberTemp("Kati", FlightCrewMember.Role.PILOT, 129.01);
        CrewMemberTemp crewMemberTemp5 = new CrewMemberTemp("Kati", FlightCrewMember.Role.PILOT, 129.01);
        CrewMemberTemp crewMemberTemp6 = new CrewMemberTemp("Kati", FlightCrewMember.Role.PILOT, 142.44);
        CrewMemberTemp crewMemberTemp7 = new CrewMemberTemp("Kati", FlightCrewMember.Role.PILOT, 120.23);
        CrewMemberTemp crewMemberTemp8 = new CrewMemberTemp("Kati", FlightCrewMember.Role.PILOT, 121.23);

        AVLTree binarySearchTree = new AVLTree();
        binarySearchTree.insert(crewMemberTemp1);
        binarySearchTree.insert(crewMemberTemp2);
        binarySearchTree.insert(crewMemberTemp3);
        binarySearchTree.insert(crewMemberTemp4);
        binarySearchTree.insert(crewMemberTemp5);
        binarySearchTree.insert(crewMemberTemp6);
        binarySearchTree.insert(crewMemberTemp7);
        binarySearchTree.insert(crewMemberTemp8);

        System.out.println(binarySearchTree.toString());

        System.out.println(binarySearchTree.findElementGreaterAtLeastByK1(binarySearchTree.rootNode, 5, 10, 115.23).getValue());
        System.out.println(binarySearchTree.findElementLessAtLeastByK1(binarySearchTree.rootNode, 3, Integer.MAX_VALUE, 135.5).getValue());


        CrewMemberTemp crewMemberTemp9 = new CrewMemberTemp("Kati", FlightCrewMember.Role.PILOT, 106.74079484660564);
        CrewMemberTemp crewMemberTemp10 = new CrewMemberTemp("Kati", FlightCrewMember.Role.PILOT, 115.01670613016454);
        CrewMemberTemp crewMemberTemp11 = new CrewMemberTemp("Kati", FlightCrewMember.Role.PILOT, 110.11384775145501);
        CrewMemberTemp crewMemberTemp12 = new CrewMemberTemp("Kati", FlightCrewMember.Role.PILOT, 103.1383714694357);
        CrewMemberTemp crewMemberTemp13 = new CrewMemberTemp("Kati", FlightCrewMember.Role.PILOT, 122.59641529930622);
        AVLTree binarySearchTree1 = new AVLTree();
        binarySearchTree1.insert(crewMemberTemp9);
        binarySearchTree1.insert(crewMemberTemp10);
        binarySearchTree1.insert(crewMemberTemp11);
        binarySearchTree1.insert(crewMemberTemp12);
        binarySearchTree1.insert(crewMemberTemp13);

        System.out.println(binarySearchTree1.toString());

        System.out.println(binarySearchTree1.findElementLessAtLeastByK1(binarySearchTree1.rootNode, 3, Integer.MAX_VALUE, 115.1).getValue());
    }
}
