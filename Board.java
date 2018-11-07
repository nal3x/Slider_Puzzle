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
                // i + j?
            }
        }
        for (byte b : board)
            System.out.print(b);
        System.out.println();
        System.out.println("Dimension is " + dimension());
    }

    public int dimension() { // board dimension n
        return n;

    }
    public int hamming() { // number of blocks out of place
        return -1;

    }
    public int manhattan() { // sum of Manhattan distances between blocks and goal
        return -1;

    }
    public boolean isGoal() {
        return false;

    }
    public Board twin() { // a board that is obtained by exchanging any pair of blocks
        return null;

    }
    public boolean equals(Object y) { // does this board equal y?
        return false;

    }
    public Iterable<Board> neighbors() { // all neighboring boards
        return null;

    }


    public String toString() { // string representation of this board (in the output format specified below)
        return null;


        // StringBuilder s = new StringBuilder();
        // int n = dimension();
        // s.append(n + "\n");
        // for (int i = 0; i < n; i++) {
        //     for (int j = 0; j < n; j++) {
        //         s.append(String.format("%2d ", tiles[i][j]));
        //     }
        //     s.append("\n");
        // }
        // return s.toString();

    }

    public static void main(String[] args) { // unit tests

    }
}
