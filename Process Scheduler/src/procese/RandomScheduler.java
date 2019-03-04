package procese;

import java.util.Random;

public class RandomScheduler{
	
	static Random rand = new Random();
	static int rez;
	
	public static void RS(ProblemData data, HomeworkWriter out) {
		
		for(int i = 0; i < data.getNumberOfNumbers(); i++) {
			int n = rand.nextInt(data.getNumberOfEvents()) + 1;
			
			switch(data.getProcesses()[n-1].getType()){
				case "CheckPrime":
					rez = CheckPrime.CheckPrimeNumber(data.getNumbersToBeProcessed()[i]);
					break;
				case "NextPrime":
					rez = NextPrime.NextPrimeNumber(data.getNumbersToBeProcessed()[i]);
					break;
				case "Cube":
					rez = Cube.CubeNumber(data.getNumbersToBeProcessed()[i]);
					break;
				case "Factorial":
					rez = Factorial.FactorialNumber(data.getNumbersToBeProcessed()[i]);
					break;
				case "Fibonacci":
					rez = Fibonacci.FibonacciNumber(data.getNumbersToBeProcessed()[i]);
					break;
				case "Square":
					rez = Square.SquareNumber(data.getNumbersToBeProcessed()[i]);
					break;
				case "Sqrt":
					rez = Sqrt.SqrtNumber(data.getNumbersToBeProcessed()[i]);
					break;
				default:
					System.out.println("ceva nu e bine");
					break;
			}
			
			out.println(Integer.toString(data.getNumbersToBeProcessed()[i]) + " " + data.getProcesses()[n-1].getType() + " " + Integer.toString(rez) + " Computed");
		}
		
	}

}
