package d2;

import d2.RandomConnections.Connection;
import edu.princeton.cs.algs4.Stopwatch;
import edu.princeton.cs.algs4.StdOut;

public class PairwiseComparison {


	public static void main(String[] args){
		
		//initial values to test, N = number of items in union
		//doubleTimes says how many times we want to double the input.
		//iterations says how many times we want to test for each
		//connection list.
		int N = 8000;
		int doubleTimes = 5;
		int iterations = 10;
		
		TestingClient client = new TestingClient();
		
		//double elapsed_timewqupc[] = new double[iterations];
		double ratio_qf_qu[] = new double[doubleTimes];
		double ratio_wqu_hqu[] = new double[doubleTimes];
		//double ratio_wqu_wqupc[] = new double[doubleTimes];
		
		//final ratio after k iterations for a specific connection list
		double finalRatio_qf_qu = 0;
		double finalRatio_wqu_hqu = 0;
		//double finalRatio_wqu_wqupc = 0;
		
		//used for calculating elapsed time in each iteration
		double elapsed_timeqf = 0;
		double elapsed_timequ = 0;
		double elapsed_timewqu = 0;
		double elapsed_timehqu = 0;
		//double elapsed_timewqupc = 0;
		
		for(int i = 0; i < doubleTimes; i++){
			
			//making a new connection list
			Connection[] connect =  RandomConnections.genConnections(N);
			
			for(int k = 0; k < iterations; k++){
				
				//setting up different quick union implementations
				QuickFindUF qf = new QuickFindUF(N);
				QuickUnionUF qu = new QuickUnionUF(N);
				WeightedQuickUnionUF wqu = new WeightedQuickUnionUF(N);
				HQU hqu = new HQU(N);
				//WQUPC wqupc = new WQUPC(N);
				
				//timing the implementations
				Stopwatch watch1 = new Stopwatch();
				client.testPairWiseQF(qf, connect);
				elapsed_timeqf = watch1.elapsedTime();
				Stopwatch watch2 = new Stopwatch();
				client.testPairWiseQU(qu, connect);
				elapsed_timequ = watch2.elapsedTime();
				Stopwatch watch3 = new Stopwatch();
				client.testPairWiseWQU(wqu, connect);
				elapsed_timewqu = watch3.elapsedTime();
				Stopwatch watch4 = new Stopwatch();
				client.testPairWiseHQU(hqu, connect);
				elapsed_timehqu = watch4.elapsedTime();
				/*Stopwatch watch5 = new Stopwatch();
				client.testPairWiseWQUPC(wqupc, connect);
				elapsed_timewqupc = watch5.elapsedTime();*/
				
				//adding to final ratios division comes in the outer for loop
				finalRatio_qf_qu += (elapsed_timeqf / elapsed_timequ);
				finalRatio_wqu_hqu += (elapsed_timewqu / elapsed_timehqu);
				//finalRatio_wqu_wqupc += (elapsed_timewqu / elapsed_timewqupc);
						
				//resetting elapsed times.
				elapsed_timeqf = 0;
				elapsed_timequ = 0;
				elapsed_timewqu = 0;
				elapsed_timehqu = 0;
				//elapsed_timewqupc = 0;
			}
			
			//final ratio calculations after performing all iterations.
			finalRatio_qf_qu /= iterations;
			finalRatio_wqu_hqu /= iterations;
			//finalRatio_wqu_wqupc /= iterations;
			
			//storing the ratios
			ratio_qf_qu[i] = finalRatio_qf_qu;
			ratio_wqu_hqu[i] = finalRatio_wqu_hqu;
			//ratio_wqu_wqupc[i] = finalRatio_wqu_wqupc;
		}
		for(int i = 0; i < doubleTimes; i++){
			StdOut.println("qf qu ratio " + client.round(ratio_qf_qu[i], 4));
			StdOut.println("wqu hqu ratio " + client.round(ratio_wqu_hqu[i], 4));
		}
	}
}
