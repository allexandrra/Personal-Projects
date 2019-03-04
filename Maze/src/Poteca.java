
public class Poteca extends Cell{

	//celula libera pe unde eroul poate sa treaca
	char cell;
	int nrOfPasses;
	int xCoord, yCoord;
	char orientation;
	
	public Poteca(char c, int x, int y) {
		cell = c;
		xCoord = x;
		yCoord = y;
		nrOfPasses = 0;
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

	//metoda care intoare orientarea eroului in labirint
	@Override
	public char getOrientation() {
		return orientation;
	}
}
