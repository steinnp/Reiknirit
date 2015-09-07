

package D1;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.introcs.StdIn;
/**
 * Heighted quick union is a modified version of the weighted quick union implementation
 * available at http://algs4.cs.princeton.edu/code/edu/princeton/cs/algs4/WeightedQuickUnionUF.java.html
 * by authors
 *  @author Robert Sedgewick
 *  @author Kevin Wayne
 *  modified by Steinn Ellidi Petursson and Skuli Þor Arnason.
 */
public class HeightedQuickUnionUF {
    private int[] parent;   // parent[i] = parent of i
    private int[] height;     // height[i] = number of objects in subtree rooted at i
    private int count;      // number of components

    /**
     * Initializes an empty union-find data structure with N isolated components 0 through N-1.
     * @param N the number of objects
     * @throws java.lang.IllegalArgumentException if N < 0
     */
    public HeightedQuickUnionUF(int N) {
        count = N;
        parent = new int[N];
        height = new int[N];
        for (int i = 0; i < N; i++) {
            parent[i] = i;
            height[i] = 1;
        }
    }

    /**
     * Returns the number of components.
     * @return the number of components (between 1 and N)
     */
    public int count() {
        return count;
    }

    /**
     * Returns the component identifier for the component containing site <tt>p</tt>.
     * @param p the integer representing one site
     * @return the component identifier for the component containing site <tt>p</tt>
     * @throws java.lang.IndexOutOfBoundsException unless 0 <= p < N
     */
    public int find(int p) {
        validate(p);
        while (p != parent[p])
            p = parent[p];
        return p;
    }

    // validate that p is a valid index
    private void validate(int p) {
        int N = parent.length;
        if (p < 0 || p >= N) {
            throw new IndexOutOfBoundsException("index " + p + " is not between 0 and " + N);
        }
    }

    /**
     * Are the two sites <tt>p</tt> and <tt>q</tt> in the same component?
     * @param p the integer representing one site
     * @param q the integer representing the other site
     * @return <tt>true</tt> if the two sites <tt>p</tt> and <tt>q</tt>
     *    are in the same component, and <tt>false</tt> otherwise
     * @throws java.lang.IndexOutOfBoundsException unless both 0 <= p < N and 0 <= q < N
     */
    public boolean connected(int p, int q) {
        return find(p) == find(q);
    }


    /**
     * Merges the component containing site<tt>p</tt> with the component
     * containing site <tt>q</tt>.
     * @param p the integer representing one site
     * @param q the integer representing the other site
     * @throws java.lang.IndexOutOfBoundsException unless both 0 <= p < N and 0 <= q < N
     */
    public void union(int p, int q) {
        int rootP = find(p);
        int rootQ = find(q);
        if (rootP == rootQ) return;

        // make smaller root point to larger one
        if (height[rootP] < height[rootQ]) {
            parent[rootP] = rootQ;
        }
        else if(height[rootP] == height[rootQ]){
            parent[rootP] = rootQ;
            height[rootQ] += 1;
        }
        else {
            parent[rootQ] = rootP;
        }
        count--;
    }


    /**
     * Reads in a sequence of pairs of integers (between 0 and N-1) from standard input,
     * where each integer represents some object;
     * if the objects are in different components, merge the two components
     * and print the pair to standard output.
     */
    public static void main(String[] args) {
        int N = StdIn.readInt();
        HeightedQuickUnionUF uf = new HeightedQuickUnionUF(N);
        while (!StdIn.isEmpty()) {
            int p = StdIn.readInt();
            int q = StdIn.readInt();
            if (uf.connected(p, q)) continue;
            uf.union(p, q);
            StdOut.println(p + " " + q);
        }
        StdOut.println(uf.count() + " components");
    }

}