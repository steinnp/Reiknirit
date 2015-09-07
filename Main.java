package d2;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.Random;
import edu.princeton.cs.algs4.StdStats;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Stopwatch;
import edu.princeton.cs.introcs.StdIn;
public class Main {
    public static void main(String[] args) {
    	
        int N = 2000;
        int N0 = 2000;
        TestingClient client = new TestingClient();
        StdOut.print("Input number of iterations: ");
        int iterations = StdIn.readInt();
        double[] elapsed_time = new double[iterations];
        
        DoubleingTest[] dt = new DoubleingTest[7];
        for(int s = 0; s < dt.length; s++){
        	dt[s] = new DoubleingTest();
        }

        //Calculates the number of pairs to be connected
        int numPairs = (int) ((N/2)*java.lang.Math.log(N));
        QuickFindUF uf = new QuickFindUF(N);
        //QuickUnionUF uf = new QuickUnionUF(N);
        //WeightedQuickUnionUF uf = new WeightedQuickUnionUF(N);
        
        for(int i = 0; i < dt.length; i++){

        	for(int k = 0; k < elapsed_time.length; k++){
        		uf = new QuickFindUF(N);
            	//uf = new QuickUnionUF(N);
        		//uf = new WeightedQuickUnionUF(N);
        		Stopwatch watch = new Stopwatch();
        		client.testQF(uf, numPairs);
        		//client.testQU(uf, numPairs);
        		//client.testWQU(uf, numPairs);
        		elapsed_time[k] = watch.elapsedTime();
        	}
        	client.stdDev(iterations, elapsed_time, dt, i);
        	Arrays.sort(elapsed_time);
        	client.maxMin(dt, elapsed_time, i);
        	client.average(elapsed_time, iterations, dt, i);
        	N *= 2;
        	numPairs = (int) ((N/2)*java.lang.Math.log(N0));
        }
        N = 2000;
        client.print(dt, N, N0);
    }

}
