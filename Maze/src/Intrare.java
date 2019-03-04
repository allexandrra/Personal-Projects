
public class Intrare extends Cell{

	//celula de intrare
	char cell;
	int nrOfPasses;
	int xCoord, yCoord;
	char orientation;
	
	public Intrare(char c, int x, int y) {
		cell = c;
		xCoord = x;
		yCoord = y;
		nrOfPasses = 0;
	}
	
	//metoda care schimba tipul celulei. 
	//Este folosita pentru intrare care dupa ce este parasita, devine celula valida de a fi vizitata
	@Override
	public void changeCellType(char c) {
		cell = c;
	}
	
	//metoda care intoarce tipul celulei
	@Override
	public char getCellType() {
		return cell;
	}
	
	//metoda care intoarce valoarea coordonatei pe axa x
	@Override
	public int getXCoord() {
		return xCoord;
	}
	
	//metoda care intoarce tipul celulei
	@Override
	public char getOrientation() {
		return orientation;
	}
	
	//metoda care intoarce valoarea coordonatei pe axa y
	@Override
	public int getYCoord() {
		return yCoord;
	}
	
	//metoda ce intoarce numarul de treceti prin celula
	@Override
	public int getNrPasses() {
		return nrOfPasses;
	}
	
	//metoda care incrementeaza numarul de treceri
	@Override
	public void increasePasses() {
		nrOfPasses++;
	}
	
	//metoda care seteaza orientarea eroului intr-o celula
	@Override
	public void setOrientation(char ch) {
		orientation = ch;
	}
}
