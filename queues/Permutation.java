/**
 * Created by yufw on 2015/5/5.
 */

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class Permutation {
    public static void main(String[] args) {
        RandomizedQueue<String> q = new RandomizedQueue<String>();

//        int k = Integer.parseInt(args[0]);
//        while (!StdIn.isEmpty()) {
//            String s = StdIn.readString();
//            q.enqueue(s);
//        }
//        for (int i = 0; i < k; i++)
//            StdOut.println(q.dequeue());

        int k = Integer.parseInt(args[0]);
        for (int i = 0; i < k; i++) {
            String s = StdIn.readString();
            q.enqueue(s);
        }
        int n = k+1;
        while (!StdIn.isEmpty()) {
            String s = StdIn.readString();
            int j = StdRandom.uniform(0, n);
            if (j < k) {
                q.dequeue();
                q.enqueue(s);
            }
            n++;
        }
        for (String s : q)
            StdOut.println(s);
    }
}
