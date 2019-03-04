package procese;

public class NextPrime {

	public static int NextPrimeNumber(int number) {
		
		boolean prim = true;
		boolean ok = true;
		int nextprim = number + 1;
		
		if(number < 2) {
			return 2;
		}
		
		while(ok == true) {
			
			for(int divizor = 2; divizor < nextprim; divizor++) {
				if(nextprim % divizor == 0) {
					prim = false;
					break;
				}
			}
			
			if(prim == false) {
				prim = true;
				nextprim++;
			}
			else {
				ok = false;
			}
		}
		
		return nextprim;
	}
}
