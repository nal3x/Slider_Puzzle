import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdRandom;

public class Board {

    private static final byte MIN_DIMENSION = 2;
    private static final byte MAX_DIMENSION = 127;
    private final int[] blocksArray; // we represent the puzzle board as  a 1d array of size n^2
    private final int n;
    private final int twinIndex1, twinIndex2;

    public Board(int[][] blocks) {

        if (blocks.length < MIN_DIMENSION || blocks.length > MAX_DIMENSION)
            throw new IllegalArgumentException();

        for (int i = 0; i < blocks.length; i++) {
            if (blocks[i].length != blocks.length) { // checking each row for correct # of elements
                throw new IllegalArgumentException();
            }
        }
        n = blocks.length;
        blocksArray = new int[n * n];
        for (int i = 0, k = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                blocksArray[k++] = blocks[i][j]; // converting to our 1-d representation
            }
        }
        // find and save proper block indexes for generation of twin
        int pos1, pos2;
        do {
            pos1 = StdRandom.uniform(blocksArray.length);
            pos2 = StdRandom.uniform(blocksArray.length);
        } while (pos1 == pos2 || blocksArray[pos1] == 0 || blocksArray[pos2] == 0);
        // avoid exchanging a block with zero or a block with itself
        twinIndex1 = pos1;
        twinIndex2 = pos2;
    }

    private Board(Board board) { // copy constructor, used in Board.swapBoardPositions
        if (board == null) throw new IllegalArgumentException();
        n = board.n;
        blocksArray = new int[n * n];
        twinIndex1 = board.twinIndex1;
        twinIndex2 = board.twinIndex2;
        for (int i = 0; i < board.blocksArray.length; i++) {
            blocksArray[i] = board.blocksArray[i];
        }
    }

    public int dimension() { // board dimension n
        return n;
    }

    public int hamming() { // number of blocks out of place
        int hammingDistance = 0;
        for (int i = 0; i < n * n - 1; i++) { // run through all array positions except last
            // as we don't count the blank square when computing priorities
            if (!isInGoalPosition(i)) {
                hammingDistance++;
            }
        }
        return hammingDistance;
    }

    public int manhattan() { // sum of Manhattan distances between blocks and goal
        int manhattanSum = 0;
        for (int i = 0; i < n * n; i++) { // a pass from every block
            int block = blocksArray[i];
            if (block != 0) { // do nothing when zero is found
                // which row and column corresponds to the examined index?
                int row = i / n;
                int column = i % n;
                // goal row & col are determined by the block's value
                int goalRow =  (blocksArray[i] - 1) / n;
                int goalCol =  (blocksArray[i] - 1) % n;
                int manhDistance = Math.abs(goalCol - column) + Math.abs(goalRow - row);
                manhattanSum += manhDistance;
            }
        }
        return manhattanSum;
    }

    public boolean isGoal() {
        for (int i = 0; i < n * n; i++)
            if (!isInGoalPosition(i))
                return false;
        return true;
    }

    public Board twin() { // a board that is obtained by exchanging any pair of blocks
        return swapBoardPositions(twinIndex1, twinIndex2);
    }

    @Override
    public boolean equals(Object that) { // does this board equal y?
        if (this == that) return true; // same references => same objects
        if (that == null) return false;
        if (this.getClass() != that.getClass()) return false;
        Board thatBoard = (Board) that; // cast must succeed because of previous testi
        if (this.n != thatBoard.n) return false; // should have same dimensions
        for (int i = 0; i < n * n; i++)
            if (this.blocksArray[i] != thatBoard.blocksArray[i])
                return false;
        return true;
    }

    public Iterable<Board> neighbors() { // all neighboring boards
        Stack<Board> boardNeighbors = new Stack<>();
        // a search to find index of 0 in blocksArray
        int zeroIndex = 0;
        while (blocksArray[zeroIndex] != 0) { // TODO: we do not check if we exceed array length
            zeroIndex++;
        }
        // swaping rows +-1,
        if (zeroIndex + n < blocksArray.length) { // check for available row below
            // we can exchange zero with block below
            boardNeighbors.push(swapBoardPositions(zeroIndex, zeroIndex + n));
        }
        if (zeroIndex - n >= 0) { // check for available row above
            boardNeighbors.push(swapBoardPositions(zeroIndex, zeroIndex - n));
        }
        int zeroColumn = zeroIndex % n;
        if (zeroColumn > 0) { // there exists a column on the left
            boardNeighbors.push(swapBoardPositions(zeroIndex, zeroIndex - 1));
        }
        if (zeroColumn < n - 1) { // there exists a column on the right
            boardNeighbors.push(swapBoardPositions(zeroIndex, zeroIndex + 1));
        }
        return boardNeighbors;
    }

    public String toString() { // string representation of this board (in the output format specified below)
        StringBuilder s = new StringBuilder();
        s.append(n + "\n");
        for (int i = 0, k = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                s.append(String.format("%2d ", blocksArray[k++]));
            }
            s.append("\n");
        }
        return s.toString();
    }

    private Board swapBoardPositions(int pos1, int pos2) {
        // returns a new Board with swapped blocks between pos1 & pos2 using our 1-d representation
        if (pos1 < 0 || pos1 >= blocksArray.length ||
                pos2 < 0 || pos2 >= blocksArray.length) {
            return null;
        }
        Board swapped = new Board(this); // get a copy
        int swappedBlock = swapped.blocksArray[pos1];
        swapped.blocksArray[pos1] = swapped.blocksArray[pos2];
        swapped.blocksArray[pos2] = swappedBlock;
        return swapped;
    }

    private boolean isInGoalPosition(int i) {
        // helper method to check if i'th element in blocksArray contains correct block
        if (i == blocksArray.length - 1) {
            return blocksArray[i] == 0; // last block should contain 0
        }
        else {
            return blocksArray[i] == i + 1; // in our representation blocksArray[i] should equal i+1
        }
    }
}