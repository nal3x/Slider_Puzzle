import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdOut;

public class Solver {
    private int moves = -1;
    private MinPQ<SearchNode> priorityQueue;
    Queue<Board> solutionBoards;

    public Solver(Board initial) { // find a solution to the initial board (using the A* algorithm)
        // isSolvable is invoked from main client, so we know it's solvable
        priorityQueue = new MinPQ<>();
        solutionBoards = new Queue<>();
        SearchNode examinedNode = new SearchNode(null, initial, ++moves);
        priorityQueue.insert(examinedNode);
        while (true) {
            examinedNode = priorityQueue.delMin();
            solutionBoards.enqueue(examinedNode.board);
            // System.out.println("Dequed node:\n" + examinedNode); // DEBUG
            if (examinedNode.board.isGoal()) {
                break;
            } else {
                moves++;
                for (Board neighborBoard : examinedNode.board.neighbors()) {
                    if (examinedNode.predecessor == null) {
                        priorityQueue.insert(new SearchNode(examinedNode, neighborBoard, moves));
                    } else {
                        if (!neighborBoard.equals(examinedNode.predecessor.board)) {
                            priorityQueue.insert(new SearchNode(examinedNode, neighborBoard, moves));
                        }
                    }
                }
                // System.out.printf("******Priority Queue contents:******\n");
                // for (SearchNode node : priorityQueue) {
                //     System.out.println(node);
                // }
            }
        }
    }

    public boolean isSolvable() { // is the initial board solvable?
        return true;
    }
    public int moves() { // min number of moves to solve initial board; -1 if unsolvable
        return moves;
    }
    public Iterable<Board> solution() { // sequence of boards in a shortest solution; null if unsolvable
        return solutionBoards;
    }

    public static void main(String[] args) { // solve a slider puzzle
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }

    private class SearchNode implements Comparable<SearchNode> {
        private SearchNode predecessor;
        private Board board;
        private int priority;
        private int hammingPriority;
        private int moves;

        SearchNode(SearchNode predecessor, Board board, int moves) {
            this.predecessor = predecessor;
            this.board = board;
            this.moves = moves;
            this.hammingPriority = board.hamming() + this.moves;
            this.priority = board.manhattan() + this.moves; // caching of priority
        }

        @Override
        public int compareTo(SearchNode otherNode) {
            if (this.priority < otherNode.priority) return -1;
            if (this.priority > otherNode.priority) return 1;
            if (this.board.manhattan() < otherNode.board.manhattan()) return -1;
            if (this.board.manhattan() > otherNode.board.manhattan()) return 1;
            if (this.board.hamming() < otherNode.board.hamming()) return -1;
            if (this.board.hamming() > otherNode.board.hamming()) return 1;
            return 0;
        }

        @Override
        public String toString() {
            StringBuilder s = new StringBuilder();
            s.append("priority = " + priority + "\n");
            s.append("moves = " + moves + "\n");
            s.append("manhattan = " + board.manhattan() + "\n");
            s.append(board + "\n");
            return s.toString();
        }
    }
}
