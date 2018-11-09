public class Board {

    private static final byte MIN_DIMENSION = 2;
    private static final byte MAX_DIMENSION = 127;
    private final byte[] board; // board representation as 1d array of size n^2
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
        board = new byte[n * n];
        for (int i = 0, k = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                board[k++] = (byte) blocks[i][j]; // converting to our board representation
            }
        }
    }

    public int dimension() { // board dimension n
        return n;
    }

    public int hamming() { // number of blocks out of place
        int hammingDistance = 0;
        for (int i = 0; i < n * n - 1; i++) { // run through all array positions except last
            // as we don't count the blank square when computing priorities
            if (i + 1 != board[i]) { // board[i] should be equal to i + 1
                hammingDistance++;
            }
        }
        return hammingDistance;
    }

    public int manhattan() { // sum of Manhattan distances between blocks and goal
        int manhattanSum = 0;
        for (int i = 0; i < n * n; i++) { // a pass from every block
            byte block = board[i];
            if (block == 0) { // do nothing when zero is found
            }
            else {
                // which row and column is occupied by the block?
                byte row = (byte) (i / n);
                byte column = (byte) (i % n);
                // goal row & col  are determined by the block's value
                byte goalRow = (byte) ((board[i] - 1) / n);
                byte goalCol = (byte) ((board[i] - 1) % n);
                int manhDistance = Math.abs(goalCol - column) + Math.abs(goalRow - row);
                manhattanSum += manhDistance;
            }
        }
        return manhattanSum;
    }

    public boolean isGoal() {
        for (int i = 0; i < n * n - 1; i++) {
            if (board[i] != i + 1) {
                return false;
            }
        }
        if (board[n * n - 1] != 0) {
            return false;
        }
        return true;
    }

    public Board twin() { // a board that is obtained by exchanging any pair of blocks
        return null;
    }

    public boolean equals(Object that) { // does this board equal y?
        if (this == that) return true; // same references => same objects
        if (that == null) return false;
        if (this.getClass() != that.getClass()) return false;
        Board thatBoard = (Board) that; // cast must succeed because of previous testi
        if (this.n != thatBoard.n) return false; // should have same dimensions
        int i = 0;
        while (i < this.n * this.n) {
            if (this.board[i] != thatBoard.board[i]) {
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
                s.append(String.format("%2d ", board[k++]));
            }
            s.append("\n");
        }
        return s.toString();
    }

    public static void main(String[] args) { // unit tests

    }

    // for testing purposes only; make public :)
    public Board getGoalBoard() {
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
