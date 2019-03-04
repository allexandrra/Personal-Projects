package procese;

public class MainClass{
	
	public static void main(String[] args){
		
		HomeworkReader file_in = new HomeworkReader(args[0]);
		ProblemData data = file_in.readData();
		file_in.close();
		HomeworkWriter file_out = new HomeworkWriter(args[1]);
			
		switch(data.getSchedulerType()) {
		
			case "RandomScheduler":
				RandomScheduler.RS(data, file_out);
				break;
			case "RoundRobinScheduler":
				RoundRobinScheduler.RRS(data.getNumberOfEvents(), data.getProcesses(), data.getNumberOfNumbers(), data.getNumbersToBeProcessed());
				break;
			case "WeightedScheduler":
				WeightedScheduler.WS();
				break;
			default:
				break;
		}
		file_out.close();
	}

}
