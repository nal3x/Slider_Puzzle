
public class BoardTester {
    private static final int[][] TEST_BLOCKS = { { 0, 1, 2}, { 3, 4, 5}, { 6, 7, 8} };
    private static final int[][] GOAL_BLOCKS = { { 1, 2}, { 3, 0}};
    private static final int[][] OTHER_TEST_BLOCKS = { { 0, 2, 1}, { 3, 4, 5}, { 6, 7, 8} };
    // blocks for testing hamming distance (the number of blocks in the wrong position)
    private static final int[][] TWO_OUT_OF_PLACE_BLOCKS = { { 1, 3, 2}, { 4, 5, 6}, { 7, 8, 0} };
    private static final int[][] FOUR_OUT_OF_PLACE_BLOCKS = { { 4, 3, 2}, { 1, 5, 6}, { 7, 8, 0} };
    private static final int[][] ALL_OUT_OF_PLACE_BLOCKS = { { 4, 3, 2}, { 1, 6, 5}, { 0, 7, 8} };
    // blocks for testing manhattan distance (sum of vertical and horizontal distance
    // from the blocks to their goal positions)
    private static final int[][] MANHATTAN_2_BLOCKS = { { 1, 2, 3}, { 4, 0, 5}, { 7, 8, 6} };
    private static final int[][] MANHATTAN_3_BLOCKS = { { 1, 0, 3}, { 4, 2, 5}, { 7, 8, 6} };
    private static final int[][] MANHATTAN_4_BLOCKS = { { 0, 1, 3}, { 4, 2, 5}, { 7, 8, 6} };
    private static final int[][] MANHATTAN_5_BLOCKS = { { 4, 1, 3}, { 0, 2, 5}, { 7, 8, 6} };

    private static void testConstructor() {
        // TODO: make the test blocks constants
        System.out.println("***Testing Board.Board***");
        int[][] testBlocks = {{1}};
        System.out.println("Constructing a board of just one element");
        try {
            Board testBoard = new Board(testBlocks);
        } catch (IllegalArgumentException e) {
            System.out.println("Illegal argument exception was caught");
        }

        int[][] testBlocks2 = {{0, 1}};
        System.out.println("Constructing a board with " + testBlocks2.length + " rows and "
                                   + testBlocks2[0].length + " columns");
        try {
            Board testBoard = new Board(testBlocks2);
        } catch (IllegalArgumentException e) {
            System.out.println("Illegal argument exception was caught");
        }

        int[][] testBlocks3 = {{0}, {1}};
        System.out.println("Constructing a board with " + testBlocks3.length + " rows and "
                                   + testBlocks3[0].length + " columns");
        try {
            Board testBoard = new Board(testBlocks3);
        } catch (IllegalArgumentException e) {
            System.out.println("Illegal argument exception was caught");
        }

        int[][] testBlocks4 = new int[128][128];
        System.out.println("Constructing a board with " + testBlocks4.length + " rows and "
                                   + testBlocks4[0].length + " columns");
        try {
            Board testBoard = new Board(testBlocks4);
        } catch (IllegalArgumentException e) {
            System.out.println("Illegal argument exception was caught");
        }
    }

    private static void testBoardToString() {
        System.out.println("***Testing toString method***");
        System.out.println(new Board(TEST_BLOCKS));
    }

    // private static void testGoalBoard() {
    //     System.out.println("***Testing Goal Board method***");
    //     Board testBoard = new Board(TEST_BLOCKS);
    //     System.out.println("For the following board: \n" + testBoard);
    //     System.out.println("The goal board is: \n" + testBoard.getGoalBoard().toString());
    // }

    private static void testEquals() {
        Board thisBoard = new Board(TEST_BLOCKS);
        System.out.println("This board equals to null: " + thisBoard.equals(null));
        System.out.println("This board equals to a String: " + thisBoard.equals(""));
        System.out.println("This board equals to itself: " + thisBoard.equals(thisBoard));
        Board equalBoard = new Board(TEST_BLOCKS);
        System.out.println("This board equals to a same board: " + thisBoard.equals(equalBoard));
        System.out.println("This board equals to another board: " +
                                   thisBoard.equals(new Board(OTHER_TEST_BLOCKS)));
    }

    private static void testGoal(int[][] goalBlocks) {
        Board goalBoard = new Board(goalBlocks);
        System.out.println("Is goal board: " + goalBoard.isGoal());
    }

    private static void testHamming() {
        Board twoOutOfPlaceBoard = new Board(TWO_OUT_OF_PLACE_BLOCKS);
        System.out.println("Out of place blocks: " + twoOutOfPlaceBoard.hamming());
        Board fourOutOfPlaceBoard = new Board(FOUR_OUT_OF_PLACE_BLOCKS);
        System.out.println("Out of place blocks: " + fourOutOfPlaceBoard.hamming());
        Board fullOutOfPlaceBoard = new Board(ALL_OUT_OF_PLACE_BLOCKS);
        System.out.println("Out of place blocks: " + fullOutOfPlaceBoard.hamming());
    }

    private static void testManhattan() {
        System.out.println("Manhattan distance of a goal board is " +
                                   new Board(GOAL_BLOCKS).manhattan());
        Board testBoard = new Board(MANHATTAN_2_BLOCKS);
        System.out.println("Manhattan distance of \n" + testBoard + "and goal board \n" +
                                   testBoard.getGoalBoard() +"is " + testBoard.manhattan());
        testBoard = new Board(MANHATTAN_3_BLOCKS);
        System.out.println("Manhattan distance of \n" + testBoard + "and goal board \n" +
                                   testBoard.getGoalBoard() +"is " + testBoard.manhattan());
        testBoard = new Board(MANHATTAN_4_BLOCKS);
        System.out.println("Manhattan distance of \n" + testBoard + "and goal board \n" +
                                   testBoard.getGoalBoard() +"is " + testBoard.manhattan());
        testBoard = new Board(MANHATTAN_5_BLOCKS);
        System.out.println("Manhattan distance of \n" + testBoard + "and goal board \n" +
                                   testBoard.getGoalBoard() +"is " + testBoard.manhattan());
    }

    private static void testTwin(int[][] inputBlocks) {
        Board aBoard = new Board(inputBlocks);
        System.out.println("Twin of \n" + aBoard + " is \n" + aBoard.twin());
    }

    public static void testSwappedBoard(int[][] blocks) {
        Board testBoard = new Board(blocks);
        System.out.println("Initial board is\n" + testBoard);
        Board swappedBoard = testBoard.swapBoardPositions(0, 1);
        System.out.println("After swapping initial blocks\n" + swappedBoard);
        int lastBlockIndex = swappedBoard.dimension() * swappedBoard.dimension() - 1;
        swappedBoard = swappedBoard.swapBoardPositions(lastBlockIndex - 1, lastBlockIndex);
        System.out.println("After swapping last blocks\n" + swappedBoard);
        swappedBoard = swappedBoard.swapBoardPositions(0, lastBlockIndex);
        System.out.println("After swapping diagonal blocks\n" + swappedBoard);
        swappedBoard = swappedBoard.swapBoardPositions(lastBlockIndex, lastBlockIndex + 1);
        System.out.println("A new board with with out of bounds swapping\n" + swappedBoard);
    }

    public static void testNeighbors() {
        Board testBoard = new Board(TEST_BLOCKS);
        System.out.println("Board:\n" + testBoard);
        Iterable<Board> neighbors = testBoard.neighbors();
        System.out.println("Neighbors:\n");
        for (Board neighbor : neighbors) {
            System.out.println(neighbor);
        }

        testBoard = new Board(MANHATTAN_2_BLOCKS);
        System.out.println("Board:\n" + testBoard);
        neighbors = testBoard.neighbors();
        System.out.println("Neighbors:\n");
        for (Board neighbor : neighbors) {
            System.out.println(neighbor);
        }

        testBoard = new Board(ALL_OUT_OF_PLACE_BLOCKS);
        System.out.println("Board:\n" + testBoard);
        neighbors = testBoard.neighbors();
        System.out.println("Neighbors:\n");
        for (Board neighbor : neighbors) {
            System.out.println(neighbor);
        }

        testBoard = new Board(FOUR_OUT_OF_PLACE_BLOCKS);
        System.out.println("Board:\n" + testBoard);
        neighbors = testBoard.neighbors();
        System.out.println("Neighbors:\n");
        for (Board neighbor : neighbors) {
            System.out.println(neighbor);
        }


    }

    public static void main(String[] args) {
        // testConstructor();
        // testBoardToString();
        // testGoalBoard();
        // testEquals();
        // testGoal(GOAL_BLOCKS);
        // testGoal(TEST_BLOCKS);
        // testHamming();
        // testManhattan();
        // testTwin(TEST_BLOCKS);
        // testSwappedBoard(TEST_BLOCKS);
        testNeighbors();
    }


}
