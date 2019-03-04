package procese;

import java.lang.Math;

public class Sqrt {
	
	public static int SqrtNumber(double number) {
		
		if(number < 0) {
			number = -number;
		}
		
		return (int)Math.floor(Math.sqrt(number));
	}
}
