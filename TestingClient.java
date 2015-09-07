package d2;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Random;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdStats;

public class TestingClient {
    public void testQF(QuickFindUF uf, int numPairs){
    	int value1 = 0;
    	int value2 = 0;
    	Random rand = new Random();
    	for(int i = 0; i < numPairs; i++){
    		value1 = rand.nextInt(uf.count());
    		value2 = rand.nextInt(uf.count());
    	uf.union(value1 ,value2);
    	}
    }
    
    public void testQU(QuickUnionUF uf, int numPairs){
    	int value1 = 0;
    	int value2 = 0;
    	Random rand = new Random();
    	for(int i = 0; i < numPairs; i++){
    		value1 = rand.nextInt(uf.count());
    		value2 = rand.nextInt(uf.count());
    	uf.union(value1 ,value2);
    	}
    }
    
    public void testWQU(WeightedQuickUnionUF uf, int numPairs){
    	int value1 = 0;
    	int value2 = 0;
    	Random rand = new Random();
    	for(int i = 0; i < numPairs; i++){
    		value1 = rand.nextInt(uf.count());
    		value2 = rand.nextInt(uf.count());
    	uf.union(value1 ,value2);
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
    public void print(DoubleingTest[] dt, double N, double N0){
    	int size = 0;
        for(int i = 0; i < dt.length; i++ ){
        	StdOut.print("Size: " + N + " ");
        	StdOut.print("Time: " + round(dt[i].getMedTime(), 4) + " ");
        	if(i != dt.length-1){
        		StdOut.print("Ratio:" + round((dt[i+1].getMedTime() / dt[i].getMedTime()), 4) +" ");
        	}
        	StdOut.print("StdDev: " + round((dt[i].getStdDev()), 4) + " ");
        	StdOut.print("Max value: " + round(dt[i].getMax(), 4) + " ");
        	StdOut.print("Min value: " + round(dt[i].getMin(), 4) + " ");
        	StdOut.println();
        	N *= 2;
        	size = (int) ((N/2)*java.lang.Math.log(N0));
        }
    }
    /*This function returns the standard deviation in the array elapsed_time*/
    public void stdDev(int iterations, double[] elapsed_time, DoubleingTest[] dt, int i){
    	if(iterations == 1){
    		dt[i].setStdDev(0.0);
    	}else{
    		dt[i].setStdDev(StdStats.stddev(elapsed_time));
    	}
    }
    /*This function returns the maximum and minimum values of time in 
     * the sorted array elapsed_time*/
    public void maxMin(DoubleingTest[] dt, double[] elapsed_time, int i){
    	dt[i].setMax(elapsed_time[elapsed_time.length-1]);
    	dt[i].setMin(elapsed_time[0]);
    }
    /*
     * Here we compute the average of the time it takes to finish 1/2N*ln(N0) union 
     * operations for a given value N. The user inputs how many iterations he wants,
     * which are in turn used to compute the average.*/
    public void average(double[] elapsed_time, int iterations, DoubleingTest[] dt, int i){
    	double med = 0;
     	for(int s = 0; s < elapsed_time.length; s++){
    		med += elapsed_time[s];
    	}
    	med = med / iterations;
    	dt[i].setMedTime(med);
    	med = 0;
    }
}
