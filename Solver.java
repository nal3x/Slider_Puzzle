import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

/******************************************************************************
 *  Attempts to find a solution to the initial board using the A* algorithm. Both initial and twin
 *  board are entered in the same priority queue. As a result, at the end of the computation the
 *  last node contains the solution. Traversing the game tree bottom-up we check if the solution
 *  came from the initial board or its twin to conclude if initial board is solvable.
 *
 ******************************************************************************/

public class Solver {
    private SearchNode examinedNode; // contains goal board at the end either from initial or twin board
    private boolean isSolvable;

    public Solver(Board initial) { // find a solution to the initial board (using the A* algorithm)
        if (initial == null) throw new IllegalArgumentException();
        MinPQ<SearchNode> priorityQueue = new MinPQ<>();
        priorityQueue.insert(new SearchNode(null, initial));
        priorityQueue.insert(new SearchNode(null, initial.twin())); // also insert twin
        while (true) {
            examinedNode = priorityQueue.delMin(); // dequeuing the node with least priority
            // System.out.println("Dequed node:\n" + examinedNode); // DEBUG
            if (examinedNode.board.isGoal())
                break;
            for (Board neighborBoard : examinedNode.board.neighbors()) {
                if (examinedNode.predecessor != null) { // node has predecessor
                    if (!neighborBoard.equals(examinedNode.predecessor.board)) // optimisation
                        priorityQueue.insert(new SearchNode(examinedNode, neighborBoard));
                } else
                    priorityQueue.insert(new SearchNode(examinedNode, neighborBoard));
            }
            // System.out.printf("******Priority Queue contents:******\n");
            // for (SearchNode node : priorityQueue) {
            //     System.out.println(node);
            // }
        }
        // check to see which board (initial or twin) led to solution
        SearchNode node = examinedNode;
        while (node.predecessor != null) {
            node = node.predecessor;
        }
        if (node.board.equals(initial))
            isSolvable = true;
    }
    public boolean isSolvable() { // is the initial board solvable?
        return isSolvable;
    }
    public int moves() { // min number of moves to solve initial board; -1 if unsolvable
        if (!isSolvable) return -1;
        return examinedNode.moves;
    }
    public Iterable<Board> solution() { // sequence of boards in a shortest solution; null if unsolvable
        if (!isSolvable) return null;
        Stack<Board> solutionBoards = new Stack<>();
        SearchNode node = examinedNode;
        while (node != null) {
            solutionBoards.push(node.board);
            node = node.predecessor;
        }
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
        private int moves;

        SearchNode(SearchNode predecessor, Board board) {
            this.predecessor = predecessor;
            this.board = board;
            if (predecessor != null)
                moves = predecessor.moves + 1;
            priority = board.manhattan() + moves; // compute and cache node priority
        }

        @Override
        public int compareTo(SearchNode otherNode) {
            // When 2 search nodes have the same Manhattan priority, we break ties by comparing
            // Manhattan distances.
            if (this.priority < otherNode.priority) return -1;
            if (this.priority > otherNode.priority) return 1;
            if (this.board.manhattan() < otherNode.board.manhattan()) return -1;
            if (this.board.manhattan() > otherNode.board.manhattan()) return 1;
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