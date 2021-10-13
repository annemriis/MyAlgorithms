package ee.ttu.algoritmid.subtreedifference;

public class SubtreeDifference {

    /**
     * Calculate difference between sum of all left children and sum of all right children for every node
     * @param rootNode root node of the tree. Use it to traverse the tree.
     * @return root node of the tree where for every node is computed difference of sums of it's left and right children
     */
    public Node calculateDifferences(Node rootNode) {
        Node leftNode = rootNode.getLeft();
        Node rightNode = rootNode.getRight();
        if (leftNode == null && rightNode == null) {
            return rootNode;
        }
        if (leftNode == null) {
            rootNode.setSumOfAllChildren(calculateDifferences(rightNode).getValue() + rightNode.getSumOfAllChildren());
            rootNode.setDifferenceOfLeftAndRight(-rightNode.getValue() - rightNode.getSumOfAllChildren());
        } else if (rightNode == null) {
            rootNode.setSumOfAllChildren(calculateDifferences(leftNode).getValue() + leftNode.getSumOfAllChildren());
            rootNode.setDifferenceOfLeftAndRight(leftNode.getValue() + leftNode.getSumOfAllChildren());
        } else {
            rootNode.setSumOfAllChildren(calculateDifferences(leftNode).getSumOfAllChildren()
                    + leftNode.getValue() + calculateDifferences(rightNode).getSumOfAllChildren()
                    + rightNode.getValue());
            rootNode.setDifferenceOfLeftAndRight(leftNode.getSumOfAllChildren()
                    + leftNode.getValue() - rightNode.getSumOfAllChildren()
                    - rightNode.getValue());
        }
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
