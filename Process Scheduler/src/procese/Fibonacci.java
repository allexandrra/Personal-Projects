package procese;

public class Fibonacci {
	
	public static int FibonacciNumber(int number) {
		
		if(number < 0) {
			
			return -1;
		}
		
		int f0 = 0, f1 = 1, f2 = f0 + f1;
		
		for(int i = 2; i < number; i++) {
			f0 = f1;
			f1 = f2;
			f2 = (f0 + f1)%9973;
		}
		
		return f2;
	}
}
