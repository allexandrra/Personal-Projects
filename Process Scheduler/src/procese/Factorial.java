package procese;

public class Factorial {
	
	public static int FactorialNumber(int number) {
		
		int fact = 1;
		
		if(number < 0) {
			return 0;
		}
		
		for(int i = 1; i <= number; i++) {
			fact = (fact * i)%9973;
		}
		return fact;
	}
}
