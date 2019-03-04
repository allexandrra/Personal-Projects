import java.util.ArrayList;
import java.util.Iterator;

public class Main {

	public static void main(String[] args){
		
		fileReader in_file = new fileReader(args[1]);
		writeFile out_file = new writeFile(args[2]);
		Maze maze = in_file.getLabyrinth();
		in_file.close();
		
		getPathOne path = new getPathOne();
		ArrayList<Cell> list = path.getPath(maze, maze.getEntrance());
		out_file.println(String.valueOf(list.size()));
		Iterator<Cell> it = list.listIterator();
		while(it.hasNext()) {
			Cell c = it.next();
			out_file.println(String.valueOf(c.getYCoord()) + " " + String.valueOf(c.getXCoord()));
		}
		out_file.close();
	}
}
