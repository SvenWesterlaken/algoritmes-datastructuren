import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Queue;

import java.util.Comparator;

/**
 * Created by Sven Westerlaken on 31-10-2017.
 */
public class Board {

    private final int N;
    private int[][] tiles;

    private int empty;

    public Board(int[][] blocks) {
        this.N = blocks.length;
        tiles = new int[N][N];

        for(int i = 0; i < N; i++) {
            for( int j = 0; j < N; j++) {
                if(blocks[i][j] == 0) {
                    this.empty = index(i, j);
                }

                tiles[i][j] = blocks[i][j];
            }
        }
    }

    public int dimension() {
        return N;
    }

    public int hamming() {
        int block;
        int score = 0;

        for(int i = 0; i < N; i++) {
            for( int j = 0; j < N; j++) {
                block = tiles[i][j];

                if(block != 0) {
                    score += hammingScore(block, i, j);
                }
            }
        }

        return score;
    }

    public int manhattan() {
        int block;
        int score = 0;

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                block = tiles[i][j];

                if(block != 0) {
                    score += manhattanScore(block, i, j);
                }
            }
        }

        return score;
    }

    public boolean isGoal() {
        return hamming() == 0;
    }

    public boolean equals(Object y) {
        if (y == this) return true;
        if (y == null) return false;
        if(y.getClass() != this.getClass()) return false;

        Board that = (Board) y;

        if ( that.dimension() != this.dimension() ) return false;

        for (int i=0; i < N; i++) {
            for (int j=0; j < N; j++) {
                if (that.tiles[i][j] != this.tiles[i][j]) return false;
            }
        }

        return true;
    }

    public Board twin() {
        Board twin = new Board(this.copy());

        if(tiles[0][0] == 0 || tiles[0][1] == 0) {
            twin.swap(1, 0, 1, 1);
        } else {
            twin.swap(0, 0, 0, 1 );
        }

        return twin;

    }

    public Iterable<Board> neighbors() {
        Queue<Board> queue = new Queue<>();
        Board neighbor;

        int row = row(empty);
        int col = col(empty);

        if(row > 0) {
            neighbor = new Board(this.copy());
            neighbor.swap(row, col, row - 1, col);
            queue.enqueue(neighbor);
        }

        if(col > 0) {
            neighbor = new Board(this.copy());
            neighbor.swap(row, col, row, col - 1);
            queue.enqueue(neighbor);
        }

        if(row < N - 1) {
            neighbor = new Board(this.copy());
            neighbor.swap(row, col, row + 1, col);
            queue.enqueue(neighbor);
        }

        if(col < N - 1) {
            neighbor = new Board(this.copy());
            neighbor.swap(row, col, row, col + 1);
            queue.enqueue(neighbor);
        }

        return queue;

    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(N).append("\n");
        for(int i=0; i < N; i++) {
            for( int j=0; j < N; j++) {
                sb.append(" ").append(tiles[i][j]);
            }

            sb.append("\n");
        }

        return sb.toString();

    }

    private int hammingScore(int value, int i, int j) {
        if(value != index(i, j)) return 1;
        return 0;
    }

    private int manhattanScore(int value, int i, int j) {
        int vOffset = Math.abs(row(value) - i);
        int hOffset = Math.abs(col(value) - j);

        return  vOffset + hOffset;
    }

    private int index(int i, int j) {
        return ( i * N ) + ( j + 1 );
    }

    private int row(int index) {
        return (index - 1 ) / N;
    }

    private int col(int index) {
        return (index - 1) % N;
    }

    private void swap(int row1, int col1, int row2, int col2) {
        int temp = this.tiles[row1][col1];
        int temp2 = this.tiles[row2][col2];
        this.tiles[row1][col1] = temp2;
        this.tiles[row2][col2] = temp;

        if (temp == 0) {
            this.empty = index(row2, col2);
        } else if (temp2 == 0) {
            this.empty = index(row1, col1);
        }
    }

    private int[][] copy() {
        int[][] copy = new int[N][N];

        for(int i=0; i < N; i++) {
            System.arraycopy(tiles[i], 0, copy[i], 0, N);
        }

        return copy;
    }



}
