/******************************************************************************
 *  Compilation:  javac QuickUnionUF.java
 *  Execution:  java QuickUnionUF < input.txt
 *  Dependencies: StdIn.java StdOut.java
 *
 *  Quick-union algorithm.
 *
 ******************************************************************************/

package d2;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.Random;
import edu.princeton.cs.algs4.StdStats;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Stopwatch;
import edu.princeton.cs.introcs.StdIn;

/**
 *  The <tt>QuickUnionUF</tt> class represents a union-find data structure.
 *  It supports the <em>union</em> and <em>find</em> operations, along with
 *  methods for determinig whether two objects are in the same component
 *  and the total number of components.
 *  <p>
 *  This implementation uses quick union.
 *  Initializing a data structure with <em>N</em> objects takes linear time.
 *  Afterwards, <em>union</em>, <em>find</em>, and <em>connected</em> take
 *  time linear time (in the worst case) and <em>count</em> takes constant
 *  time.
 *  <p>
 *  For additional documentation, see <a href="http://algs4.cs.princeton.edu/15uf">Section 1.5</a> of
 *  <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 *     
 *  @author Robert Sedgewick
 *  @author Kevin Wayne
 *  
 *  We got this algorithm from the books webpage and it was modified by us:
 *  Skúli Þór Árnason
 *  Steinn Elliði Pétursson 
 */
public class QuickUnionUF {
    private int[] parent;  // parent[i] = parent of i
    private int count;     // number of components

    /**
     * Initializes an empty union-find data structure with N isolated components 0 through N-1.
     * @param N the number of objects
     * @throws java.lang.IllegalArgumentException if N < 0
     */
    public QuickUnionUF(int N) {
        parent = new int[N];
        count = N;
        for (int i = 0; i < N; i++) {
            parent[i] = i;
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
     * @return <tt>true</tt> if the sites <tt>p</tt> and <tt>q</tt> are in the same
     *    component, and <tt>false</tt> otherwise
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
        parent[rootP] = rootQ; 
        count--;
    }
    /*This function creates random pairs using the union function in QuickUnion*/
    public void testQU(int size, double N){
    	int n = (int) N;
    	int value1 = 0;
    	int value2 = 0;
    	Random rand = new Random();
    	for(int i = 0; i < size; i++){
    		value1 = rand.nextInt(n);
    		value2 = rand.nextInt(n);
    			union(value1 ,value2);
    	}
    }

    /*
     * round function gotten from stackoverflow.com user Jonik
     * http://stackoverflow.com/questions/2808535/round-a-double-to-2-decimal-places
     */
    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
    /*This function prints the values for N, the average time, the ratio between the
     * averages of each value of N, the standard deviation for each value N, the max and min
     * values of time for each N.*/
    private void print(DoubleingTest[] dt, double N, double N0){
    	int size = 0;
        for(int i = 0; i < dt.length; i++ ){
        	StdOut.print("Size: " + N + " ");
        	StdOut.print("Time: " + round(dt[i].med_time, 4) + " ");
        	if(i != dt.length-1){
        		StdOut.print("Ratio:" + round((dt[i+1].med_time / dt[i].med_time), 4) +" ");
        	}
        	StdOut.print("StdDev: " + round((dt[i].std_dev), 4) + " ");
        	StdOut.print("Max value: " + round(dt[i].max, 4) + " ");
        	StdOut.print("Min value: " + round(dt[i].min, 4) + " ");
        	StdOut.println();
        	N *= 2;
        	size = (int) ((N/2)*java.lang.Math.log(N0));
        }
    }
    /*This function returns the standard deviation in the array elapsed_time*/
    private void stdDev(int iterations, double[] elapsed_time, DoubleingTest[] dt, int i){
    	if(iterations == 1){
    		dt[i].std_dev = 0.0;
    	}else{
    		dt[i].std_dev = StdStats.stddev(elapsed_time);
    	}
    }
    /*This function returns the maximum and minimum values of time in 
     * the sorted array elapsed_time*/
    private void maxMin(DoubleingTest[] dt, double[] elapsed_time, int i){
    	dt[i].max = elapsed_time[elapsed_time.length-1];
    	dt[i].min = elapsed_time[0];
    }
    /*
     * Here we compute the average of the time it takes to finish 1/2N*ln(N0) union 
     * operations for a given value N. The user inputs how many iterations he wants,
     * which are in turn used to compute the average.*/
    private void average(double[] elapsed_time, int iterations, DoubleingTest[] dt, int i){
    	double med = 0;
     	for(int s = 0; s < elapsed_time.length; s++){
    		med += elapsed_time[s];
    	}
    	med = med / iterations;
    	dt[i].med_time = med;
    	med = 0;
    }
    
    /**
     * Reads in a sequence of pairs of integers (between 0 and N-1) from standard input, 
     * where each integer represents some object;
     * if the objects are in different components, merge the two components
     * and print the pair to standard output.
     */
    public static void main(String[] args) {
    	
        double N = 2000;
        double N0 = 2000;
        double med = 0;
        
        StdOut.print("Input number of iterations: ");
        int iterations = StdIn.readInt();
        double[] elapsed_time = new double[iterations];
        
        DoubleingTest[] dt = new DoubleingTest[7];
        for(int s = 0; s < dt.length; s++){
        	dt[s] = new DoubleingTest();
        }

        //Calculates the number of pairs to be connected
        int size = (int) ((N/2)*java.lang.Math.log(N));
        QuickUnionUF uf = new QuickUnionUF(size);
        
        for(int i = 0; i < dt.length; i++){

        	for(int k = 0; k < elapsed_time.length; k++){
            	uf = new QuickUnionUF(size);
        		Stopwatch watch = new Stopwatch();
        		uf.testQU(size, N);
        		elapsed_time[k] = watch.elapsedTime();
        	}
        	uf.stdDev(iterations, elapsed_time, dt, i);
        	Arrays.sort(elapsed_time);
        	uf.maxMin(dt, elapsed_time, i);
        	uf.average(elapsed_time, iterations, dt, i);
        	N *= 2;
        	size = (int) ((N/2)*java.lang.Math.log(N0));
        }
        N = 2000;
        uf.print(dt, N, N0);
    }

}

/******************************************************************************
 *  Copyright 2002-2015, Robert Sedgewick and Kevin Wayne.
 *
 *  This file is part of algs4.jar, which accompanies the textbook
 *
 *      Algorithms, 4th edition by Robert Sedgewick and Kevin Wayne,
 *      Addison-Wesley Professional, 2011, ISBN 0-321-57351-X.
 *      http://algs4.cs.princeton.edu
 *
 *
 *  algs4.jar is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  algs4.jar is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with algs4.jar.  If not, see http://www.gnu.org/licenses.
 ******************************************************************************/
