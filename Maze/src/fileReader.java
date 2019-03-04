
import java.io.*;
import java.util.Scanner;

public class fileReader {

	private FileReader file;
	
	public fileReader(String name){
		try {
			file = new FileReader(name);
		}
		catch(FileNotFoundException ex) {
			System.out.print("There is no file with this name");
		}
	}
	
	public Maze getLabyrinth() {
		Maze result = null;
		int i = 0, j = 0;
		
		Scanner input = new Scanner(file);
		//se citesc din fisier dimensiunile labirintului
		int height = input.nextInt();
		int weight = input.nextInt();
		
		Cell[][] labyrinth = new Cell[height][weight];
		Cell entrance = null;
		
		//ce citesc celulele care vor fi introduse in matrice
		while(input.hasNext() && i <= height) {
			String line = input.nextLine();
			for (int k = 0; k < line.length(); k++) {
				switch(line.charAt(k)) {
				//pentru fiecare caracter se pune in matrice tipul sau (simbolul), si coordonatele in sistemul XoY
				case '.':
					labyrinth[i-1][j] = new Poteca(line.charAt(k), j, i-1);
					j++;
					break;
				case '#':
					labyrinth[i-1][j] = new Perete(line.charAt(k), j, i-1);
					j++;
					break;
				case 'I':
					labyrinth[i-1][j] = new Intrare(line.charAt(k), j, i-1);
					//se seteaza orientarea initiala a eroului catre N
					labyrinth[i-1][j].setOrientation('^');
					//in cazul intrarii, odata citita i se schimba tipul in "." ceea ce inseamna ca e celula disponibila
					labyrinth[i-1][j].changeCellType('.');
					//se retine intrarea pentru a stii de unde se porneste
					entrance = labyrinth[i-1][j];
					j++;
					break;
				case 'O':
					labyrinth[i-1][j] = new Iesire(line.charAt(k), j, i-1);
					j++;
					break;
				}
			}
			j = 0;
			i++;
		}
		
		input.close();
		result = new Maze(height, weight, labyrinth, entrance);
		
		return result;
	}
	
	public void close() {
        try {
            file.close();
        } catch (IOException ex) {
            System.out.print("Did not close file");
        }
    }
}
