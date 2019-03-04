
public abstract class Cell {
	
	char c;
	int x;
	int y;
	int no;
	char orientation;

	//clasa abstracta ce contine metode care vor fi suprascrise in clasele ce descriu fiecare celula
	public char getCellType() { return c; }
	public int getXCoord() { return x; }
	public int getYCoord() { return y; } 
	public int getNrPasses() { return no; }
	public void increasePasses() { no++; }
	public void setOrientation(char ch) { orientation = ch; }
	public char getOrientation() { return orientation; }
	public void changeCellType(char ch) { c = ch; }
}
