package ee.ttu.algoritmid.subtreedifference;

public class SubtreeDifference {

    /**
     * Calculate difference between sum of all left children and sum of all right children for every node
     * @param rootNode root node of the tree. Use it to traverse the tree.
     * @return root node of the tree where for every node is computed difference of sums of it's left and right children
     */
    public Node calculateDifferences(Node rootNode) {
        if (rootNode.getLeft() == null && rootNode.getRight() == null) {
            rootNode.setSumOfAllChildren(0);
            rootNode.setDifferenceOfLeftAndRight(0);
            return rootNode;
        } if (rootNode.getLeft() == null) {
            calculateDifferences(rootNode.getRight());
            rootNode.setSumOfAllChildren(rootNode.getRight().getValue() + rootNode.getSumOfAllChildren());
            rootNode.setDifferenceOfLeftAndRight(rootNode.getRight().getValue()
                    + rootNode.getRight().getSumOfAllChildren());
            return rootNode;
        } else if (rootNode.getRight() == null) {
            calculateDifferences(rootNode.getLeft());
            rootNode.setSumOfAllChildren(rootNode.getLeft().getValue() + rootNode.getLeft().getSumOfAllChildren());
            rootNode.setDifferenceOfLeftAndRight(-rootNode.getLeft().getValue()
                    - rootNode.getLeft().getSumOfAllChildren());
            return rootNode;
        }
        calculateDifferences(rootNode.getRight());
        calculateDifferences(rootNode.getLeft());
        rootNode.setSumOfAllChildren(rootNode.getLeft().getSumOfAllChildren()
                + rootNode.getLeft().getValue() + rootNode.getRight().getSumOfAllChildren()
                + rootNode.getRight().getValue());
        rootNode.setDifferenceOfLeftAndRight(rootNode.getLeft().getSumOfAllChildren()
                + rootNode.getLeft().getValue() - rootNode.getRight().getSumOfAllChildren()
                - rootNode.getRight().getValue());
        return rootNode;
    }

    public static void main(String[] args) throws Exception {
        /**
         *  Use this example to test your solution
         *                  Tree:
         *                   15
         *               /       \
         *             10         17
         *           /   \       /  \
         *         3     13     5    25
         */
        Node rootNode = new Node(15);
        Node a = new Node(10);
        Node b = new Node(17);
        Node c = new Node(3);
        Node d = new Node(13);
        Node e = new Node(5);
        Node f = new Node(25);

        rootNode.setLeft(a);
        rootNode.setRight(b);
        a.setLeft(c);
        a.setRight(d);
        b.setLeft(e);
        b.setRight(f);

        SubtreeDifference solution = new SubtreeDifference();
        solution.calculateDifferences(rootNode);

        if (rootNode.getDifferenceOfLeftAndRight() != -21 ||
                a.getDifferenceOfLeftAndRight() != -10 ||
                b.getDifferenceOfLeftAndRight() != -20 ||
                c.getDifferenceOfLeftAndRight() != 0) {
            throw new Exception("There is a mistake in your solution.");
        }

        System.out.println("Your solution should be working fine in basic cases, try to push.");

    }

}
