
public class Iesire extends Cell{

	//celula de iesire
	char cell;
	int nrOfPasses;
	int xCoord, yCoord;
	char orientation;
	
	public Iesire(char c, int x, int y) {
		cell = c;
		xCoord = x;
		yCoord = y;
		nrOfPasses = -1;
	}
	
	//metoda care intoarce tipul celulei
	@Override
	public char getCellType() {
		return cell;
	}

	//metoda care intoarce coordonata x a celulei
	@Override
	public int getXCoord() {
		return xCoord;
	}
	
	//metoda care intoarce coordonata y a celulei
	@Override
	public int getYCoord() {
		return yCoord;
	}
	
	//celula ce intoarce numarul de treceri prin celula
	@Override
	public int getNrPasses() {
		return nrOfPasses;
	}

	//metoda ce intoarce orientarea eroului
	@Override
	public char getOrientation() {
		return orientation;
	}
}
