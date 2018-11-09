import edu.princeton.cs.algs4.StdRandom;

public class Board {

    private static final byte MIN_DIMENSION = 2;
    private static final byte MAX_DIMENSION = 127;
    private final byte[] blocksArray; // board representation as 1d array of size n^2
    private final byte n;

    public Board(int[][] blocks) { // construct a board from an n-by-n array of blocks
                                   // (where blocks[i][j] = block in row i, column j)

        if (blocks.length < MIN_DIMENSION || blocks.length > MAX_DIMENSION) {
            throw new IllegalArgumentException();
        }
        for (int i = 0; i < blocks.length; i++) {
            if (blocks[i].length != blocks.length) { // checking each row for correct # of elements
                throw new IllegalArgumentException();
            }
        }
        n = (byte) blocks.length;
        blocksArray = new byte[n * n];
        for (int i = 0, k = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                blocksArray[k++] = (byte) blocks[i][j]; // converting to 1-d representation
            }
        }
    }

    private Board(Board board) { // copy constructor, used in Board.twin
        if (board == null) throw new IllegalArgumentException();
        n = board.n;
        blocksArray = new byte[n * n];
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
            if (i + 1 != blocksArray[i]) { // blocksArray[i] should be equal to i + 1
                hammingDistance++;
            }
        }
        return hammingDistance;
    }

    public int manhattan() { // sum of Manhattan distances between blocks and goal
        int manhattanSum = 0;
        for (int i = 0; i < n * n; i++) { // a pass from every block
            byte block = blocksArray[i];
            if (block == 0) { // do nothing when zero is found
            }
            else {
                // which row and column corresponds to the examined index?
                byte row = (byte) (i / n);
                byte column = (byte) (i % n);
                // goal row & col are determined by the block's value
                byte goalRow = (byte) ((blocksArray[i] - 1) / n);
                byte goalCol = (byte) ((blocksArray[i] - 1) % n);
                int manhDistance = Math.abs(goalCol - column) + Math.abs(goalRow - row);
                manhattanSum += manhDistance;
            }
        }
        return manhattanSum;
    }

    public boolean isGoal() {
        for (int i = 0; i < n * n - 1; i++) {
            if (blocksArray[i] != i + 1) {
                return false;
            }
        }
        if (blocksArray[n * n - 1] != 0) { // extra check to see if zero is the last element
            return false;
        }
        return true;
    }

    public Board twin() { // a board that is obtained by exchanging any pair of blocks
        Board twinBoard= new Board(this); // provide a Board copy using copy constructor
        // pick a block at random (except last) and exchange it with next block
        int randomBlockIndex = StdRandom.uniform(twinBoard.blocksArray.length - 1);
        byte swap = twinBoard.blocksArray[randomBlockIndex];
        twinBoard.blocksArray[randomBlockIndex] = twinBoard.blocksArray[randomBlockIndex + 1];
        twinBoard.blocksArray[randomBlockIndex + 1] = swap;
        return twinBoard;
    }

    public boolean equals(Object that) { // does this board equal y?
        if (this == that) return true; // same references => same objects
        if (that == null) return false;
        if (this.getClass() != that.getClass()) return false;
        Board thatBoard = (Board) that; // cast must succeed because of previous testi
        if (this.n != thatBoard.n) return false; // should have same dimensions
        int i = 0;
        while (i < this.n * this.n) {
            if (this.blocksArray[i] != thatBoard.blocksArray[i]) {
                return false;
            }
            i++;
        }
        return true;
    }

    public Iterable<Board> neighbors() { // all neighboring boards
        return null;

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

    public static void main(String[] args) { // unit tests

    }

    // for testing purposes only; make public :)
    private Board getGoalBoard() {
        int[][] goalArray = new int[n][n];
        for (int i = 0, k = 1; i < n; i++) {
            for (int j = 0; j < n; j++) {
                goalArray[i][j] = k++;
            }
        }
        goalArray[n-1][n-1] = 0;
        return new Board(goalArray);
    }
}
