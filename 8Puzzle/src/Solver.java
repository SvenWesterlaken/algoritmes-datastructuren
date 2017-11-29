import edu.princeton.cs.algs4.*;

import java.util.Comparator;

/**
 * Created by Sven Westerlaken on 31-10-2017.
 */
public class Solver {

    private boolean solvable;
    private SearchNode finalSearchNode;


    public Solver(final Board initial) {
        if ( initial == null ) throw new IllegalArgumentException();

        SearchNode initSearchNode = new SearchNode(initial, 0, null);
        SearchNode twinSearchNode = new SearchNode(initial.twin(), 0, null);

        MinPQ<SearchNode> gameTree = new MinPQ<>(initSearchNode);
        gameTree.insert(initSearchNode);

        MinPQ<SearchNode> twinGameTree = new MinPQ<>(twinSearchNode);
        twinGameTree.insert(twinSearchNode);

        while(true) {

            initSearchNode = gameTree.delMin();
            twinSearchNode = twinGameTree.delMin();

            if(initSearchNode.board.isGoal()) {
                finalSearchNode = initSearchNode;
                solvable = true;
                break;
            }

            if(twinSearchNode.board.isGoal()) {
                solvable = false;
                break;
            }

            for (Board initBoard : initSearchNode.board.neighbors()) {
                if(initSearchNode.previous == null || !initBoard.equals(initSearchNode.previous.board)) {
                    gameTree.insert(new SearchNode(initBoard, initSearchNode.moves + 1, initSearchNode));
                }
            }

            for (Board twinBoard : twinSearchNode.board.neighbors()) {
                if(twinSearchNode.previous == null || !twinBoard.equals(twinSearchNode.previous.board)) {
                    twinGameTree.insert(new SearchNode(twinBoard, twinSearchNode.moves + 1, twinSearchNode));
                }
            }

        }


    }

    public boolean isSolvable() {
        return solvable;
    }

    public int moves() {
        if(solvable) {
            return finalSearchNode.moves;
        }

        return -1;

    }

    public Iterable<Board> solution() {
        if(solvable) {
           Stack<Board> solution = new Stack<>();

           SearchNode item = finalSearchNode;
           while(item != null) {
               solution.push(item.board);
               item = item.previous;
           }

           return solution;
        }

        return null;
    }

    private class SearchNode implements Comparator<SearchNode> {
        private final Board board;
        private final int manhattan;
        private final int moves;
        private final SearchNode previous;


        public SearchNode(Board b, int m, SearchNode p) {
            this.board = b;
            this.moves = m;
            this.manhattan = b.manhattan();
            this.previous = p;
        }

        public int getPriority() {
            return manhattan + moves;
        }

        @Override
        public int compare(SearchNode o1, SearchNode o2) {
            if (o1.getPriority() < o2.getPriority()) return -1;
            if (o1.getPriority() > o2.getPriority()) return 1;
            if (o1.manhattan < o2.manhattan) return -1;
            if (o1.manhattan > o2.manhattan) return 1;
            return 0;
        }
    }

    public static void main(String[] args) {

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
}
