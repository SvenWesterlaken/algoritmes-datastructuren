import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import java.util.Iterator;

/**
 * Created by Sven Westerlaken on 15-10-2017.
 */
public class Permutation {
    public static void main(String[] args) {
        if (args.length < 1) throw new IllegalArgumentException();

        int k = Integer.parseInt(args[0]);
        In in = new In();

        RandomizedQueue<String> rq = new RandomizedQueue<>();

        while (!in.isEmpty()) {
            rq.enqueue(in.readString());
        }

        Iterator<String> iterator = rq.iterator();
        int i = 0;

        while (iterator.hasNext() && i < k) {
            StdOut.println(iterator.next());
            i++;
        }

    }
}
