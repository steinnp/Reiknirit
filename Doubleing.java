package d2;

public class DoubleingTest{
    private double med_time;
    private double std_dev;
    private double max;
    private double min;
	public DoubleingTest(){
		med_time = 0.0;
		std_dev = 0.0;
		max = 0.0;
		min = 0.0;
	}
	public void setMedTime(double time){
		med_time = time;
	}
	public double getMedTime(){
		return med_time;
	}
	public void setStdDev(double deviation){
		std_dev = deviation;
	}
	public double getStdDev(){
		return std_dev;
	}
	public void setMax(double a){
		max = a;
	}
	public double getMax(){
		return max;
	}
	public void setMin(double a){
		min = a;
	}
	public double getMin(){
		return min;
	}
}
