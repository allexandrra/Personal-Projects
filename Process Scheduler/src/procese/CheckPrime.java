package procese;

public class CheckPrime {
	
	public static int CheckPrimeNumber(int number) {
		
		if(number <= 0) {
			return 0;
		}
		
		int prim = 1;
		
		for(int divizor = 2; divizor < number; divizor++) {
			if(number % divizor == 0) {
				prim = 0;
			}
		}
		
		return prim;
	}
}
