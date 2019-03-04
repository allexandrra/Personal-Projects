import java.util.ArrayList;

public class getPathOne {

	ArrayList<Cell> path;
	
	public getPathOne() {
		path = new ArrayList<Cell>();
	}
	
	public Cell upDirection(int i,int j, Maze maze) throws CannotMoveIntoWallsException{
		Cell result = null;
		int min;
		
		//se verifica pe rand celulele vecine in ordinea prioritatilor si a disponibilitatilor
		//sunt mai multe cazuri posibile
		//toti vecinii sunt disponibili
		if(j+1 < maze.getWeight() && maze.getMaze()[i][j+1].getCellType() == '.') {
			min = maze.getMaze()[i][j+1].getNrPasses();
			result = maze.getMaze()[i][j+1];
			result.setOrientation('>');
			if(i-1 >= 0 && maze.getMaze()[i-1][j].getCellType() == '.' && min > maze.getMaze()[i-1][j].getNrPasses()) {
				min = maze.getMaze()[i-1][j].getNrPasses();
				result = maze.getMaze()[i-1][j];
				result.setOrientation('^');
				if(j-1 >= 0 && maze.getMaze()[i][j-1].getCellType() == '.' && min > maze.getMaze()[i][j-1].getNrPasses()) {
					result = maze.getMaze()[i][j-1];
					result.setOrientation('<');
					min = maze.getMaze()[i][j-1].getNrPasses();
					if(i+1 < maze.getHeight() && maze.getMaze()[i+1][j].getCellType() == '.' && min > maze.getMaze()[i+1][j].getNrPasses()) {
						result = maze.getMaze()[i+1][j];
						result.setOrientation('V');
					}
				}
				//toti vecinii sunt disponibili cu exceptia celulei din stanga
				else if(i+1 < maze.getHeight() && maze.getMaze()[i+1][j].getCellType() == '.' && min > maze.getMaze()[i+1][j].getNrPasses()) {
					result = maze.getMaze()[i+1][j];
					result.setOrientation('V');
				}
			}
			//toti vecinii sunt disponibili cu exceptia celui din fata
			else if(j-1 >= 0 && maze.getMaze()[i][j-1].getCellType() == '.' && min > maze.getMaze()[i][j-1].getNrPasses()) {
				result = maze.getMaze()[i][j-1];
				result.setOrientation('<');
				min = maze.getMaze()[i][j-1].getNrPasses();
				if(i+1 < maze.getHeight() && maze.getMaze()[i+1][j].getCellType() == '.' && min > maze.getMaze()[i+1][j].getNrPasses()) {
					result = maze.getMaze()[i+1][j];
					result.setOrientation('V');
				}
			}
			//vecinul din spate mai e singulul disponibil in afara de cel din fata
			else if(i+1 < maze.getHeight() && maze.getMaze()[i+1][j].getCellType() == '.' && min > maze.getMaze()[i+1][j].getNrPasses()) {
				result = maze.getMaze()[i+1][j];
				result.setOrientation('V');
			}
		}
		//fata, stanga, spate
		else if(i-1 >= 0 && maze.getMaze()[i-1][j].getCellType() == '.') {
			min = maze.getMaze()[i-1][j].getNrPasses();
			result = maze.getMaze()[i-1][j];
			result.setOrientation('^');
			if(j-1 >= 0 && maze.getMaze()[i][j-1].getCellType() == '.' && min > maze.getMaze()[i][j-1].getNrPasses()) {
				result = maze.getMaze()[i][j-1];
				result.setOrientation('<');
				min = maze.getMaze()[i][j-1].getNrPasses();
				if(i+1 < maze.getHeight() && maze.getMaze()[i+1][j].getCellType() == '.' && min > maze.getMaze()[i+1][j].getNrPasses()) {
					result = maze.getMaze()[i+1][j];
					result.setOrientation('V');
				}
			}
			//fata, spate
			else if(i+1 < maze.getHeight() && maze.getMaze()[i+1][j].getCellType() == '.' && min > maze.getMaze()[i+1][j].getNrPasses()) {
				result = maze.getMaze()[i+1][j];
				result.setOrientation('V');
			}
		}
		//stanga, spate
		else if(j-1 >= 0 && maze.getMaze()[i][j-1].getCellType() == '.') {
			result = maze.getMaze()[i][j-1];
			result.setOrientation('<');
			min = maze.getMaze()[i][j-1].getNrPasses();
			if(i+1 < maze.getHeight() && maze.getMaze()[i+1][j].getCellType() == '.' && min > maze.getMaze()[i+1][j].getNrPasses()) {
				result = maze.getMaze()[i+1][j];
				result.setOrientation('V');
			}
		}
		//doar celula din spate este disponibila
		else if(i+1 < maze.getHeight() && maze.getMaze()[i+1][j].getCellType() == '.' && maze.getMaze()[i+1][j].getNrPasses() >= 0) {
			result = maze.getMaze()[i+1][j];
			result.setOrientation('V');
		}
		maze.getMaze()[i][j].increasePasses();
		
		return result;
	}
	
	//idem ca la celula cu orientare spre N
	public Cell downDirection(int i,int j, Maze maze) throws CannotMoveIntoWallsException{
		Cell result = null;
		int min;
		
		if(j-1 >= 0 && maze.getMaze()[i][j-1].getCellType() == '.') {
			min = maze.getMaze()[i][j-1].getNrPasses();
			result = maze.getMaze()[i][j-1];
			result.setOrientation('<');
			if(i+1 < maze.getHeight() && maze.getMaze()[i+1][j].getCellType() == '.' && min > maze.getMaze()[i+1][j].getNrPasses()) {
				min = maze.getMaze()[i+1][j].getNrPasses();
				result = maze.getMaze()[i+1][j];
				result.setOrientation('V');
				if(j+1 < maze.getWeight() && maze.getMaze()[i][j+1].getCellType() == '.' && min > maze.getMaze()[i][j+1].getNrPasses()) {
					result = maze.getMaze()[i][j+1];
					result.setOrientation('>');
					min = maze.getMaze()[i][j+1].getNrPasses();
					if(i-1 >= 0 && maze.getMaze()[i-1][j].getCellType() == '.' && min > maze.getMaze()[i-1][j].getNrPasses()) {
						result = maze.getMaze()[i-1][j];
						result.setOrientation('^');
					}
				}
				else if(i-1 >= 0 && maze.getMaze()[i-1][j].getCellType() == '.' && min > maze.getMaze()[i-1][j].getNrPasses()) {
					result = maze.getMaze()[i-1][j];
					result.setOrientation('^');
				}
			}
			else if(j+1 < maze.getWeight() && maze.getMaze()[i][j+1].getCellType() == '.' && min > maze.getMaze()[i][j+1].getNrPasses()) {
				result = maze.getMaze()[i][j+1];
				result.setOrientation('>');
				min = maze.getMaze()[i][j+1].getNrPasses();
				if(i-1 >= 0 && maze.getMaze()[i-1][j].getCellType() == '.' && min > maze.getMaze()[i-1][j].getNrPasses()) {
					result = maze.getMaze()[i-1][j];
					result.setOrientation('^');
				}
			}
			else if(i-1 >= 0 && maze.getMaze()[i-1][j].getCellType() == '.' && min > maze.getMaze()[i-1][j].getNrPasses()) {
				result = maze.getMaze()[i-1][j];
				result.setOrientation('^');
			}
		}
		else if(i+1 < maze.getHeight() && maze.getMaze()[i+1][j].getCellType() == '.') {
			min = maze.getMaze()[i+1][j].getNrPasses();
			result = maze.getMaze()[i+1][j];
			result.setOrientation('V');
			if(j+1 < maze.getWeight() && maze.getMaze()[i][j+1].getCellType() == '.' && min > maze.getMaze()[i][j+1].getNrPasses()) {
				result = maze.getMaze()[i][j+1];
				result.setOrientation('>');
				min = maze.getMaze()[i][j+1].getNrPasses();
				if(i-1 >= 0 && maze.getMaze()[i-1][j].getCellType() == '.' && min > maze.getMaze()[i-1][j].getNrPasses()) {
					result = maze.getMaze()[i-1][j];
					result.setOrientation('^');
				}
			}
			else if(i-1 >= 0 && maze.getMaze()[i-1][j].getCellType() == '.' && min > maze.getMaze()[i-1][j].getNrPasses()) {
				result = maze.getMaze()[i-1][j];
				result.setOrientation('^');
			}
		}
		else if(j+1 < maze.getWeight() && maze.getMaze()[i][j+1].getCellType() == '.') {
			result = maze.getMaze()[i][j+1];
			result.setOrientation('>');
			min = maze.getMaze()[i][j+1].getNrPasses();
			if(i-1 >= 0 && maze.getMaze()[i-1][j].getCellType() == '.' && min > maze.getMaze()[i-1][j].getNrPasses()) {
				result = maze.getMaze()[i-1][j];
				result.setOrientation('^');
			}
		}
		else if(i-1 >= 0 && maze.getMaze()[i-1][j].getCellType() == '.' && maze.getMaze()[i-1][j].getNrPasses() >= 0) {
			result = maze.getMaze()[i-1][j];
			result.setOrientation('^');
		}
		maze.getMaze()[i][j].increasePasses();
		
		return result;
	}
	
	//idem ca la celula cu orientare spre N
	public Cell leftDirection(int i,int j, Maze maze) throws CannotMoveIntoWallsException{
		Cell result = null;
		int min;
		
		if(i-1 >= 0 && maze.getMaze()[i-1][j].getCellType() == '.') {
			min = maze.getMaze()[i-1][j].getNrPasses();
			result = maze.getMaze()[i-1][j];
			result.setOrientation('^');
			if(j-1 >= 0 && maze.getMaze()[i][j-1].getCellType() == '.' && min > maze.getMaze()[i][j-1].getNrPasses()) {
				min = maze.getMaze()[i][j-1].getNrPasses();
				result = maze.getMaze()[i][j-1];
				result.setOrientation('<');
				if(i+1 < maze.getHeight() && maze.getMaze()[i+1][j].getCellType() == '.' && min > maze.getMaze()[i+1][j].getNrPasses()) {
					result = maze.getMaze()[i+1][j];
					result.setOrientation('V');
					min = maze.getMaze()[i+1][j].getNrPasses();
					if(j+1 < maze.getWeight() && maze.getMaze()[i][j+1].getCellType() == '.' && min > maze.getMaze()[i][j+1].getNrPasses()) {
						result = maze.getMaze()[i][j+1];
						result.setOrientation('>');
					}
				}
				else if(j+1 < maze.getWeight() && maze.getMaze()[i][j+1].getCellType() == '.' && min > maze.getMaze()[i][j+1].getNrPasses()) {
					result = maze.getMaze()[i][j+1];
					result.setOrientation('>');
				}
			}
			else if(i+1 < maze.getHeight() && maze.getMaze()[i+1][j].getCellType() == '.' && min > maze.getMaze()[i+1][j].getNrPasses()) {
				result = maze.getMaze()[i+1][j];
				result.setOrientation('V');
				min = maze.getMaze()[i+1][j].getNrPasses();
				if(j+1 < maze.getWeight() && maze.getMaze()[i][j+1].getCellType() == '.' && min > maze.getMaze()[i][j+1].getNrPasses()) {
					result = maze.getMaze()[i][j+1];
					result.setOrientation('>');
				}
			}
			else if(j+1 < maze.getWeight() && maze.getMaze()[i][j+1].getCellType() == '.' && min > maze.getMaze()[i][j+1].getNrPasses()) {
				result = maze.getMaze()[i][j+1];
				result.setOrientation('>');
			}
		}
		else if(j-1 >= 0 && maze.getMaze()[i][j-1].getCellType() == '.') {
			min = maze.getMaze()[i][j-1].getNrPasses();
			result = maze.getMaze()[i][j-1];
			result.setOrientation('<');
			if(i+1 < maze.getHeight() && maze.getMaze()[i+1][j].getCellType() == '.' && min > maze.getMaze()[i+1][j].getNrPasses()) {
				result = maze.getMaze()[i+1][j];
				result.setOrientation('V');
				min = maze.getMaze()[i+1][j].getNrPasses();
				if(j+1 < maze.getWeight() && maze.getMaze()[i][j+1].getCellType() == '.' && min > maze.getMaze()[i][j+1].getNrPasses()) {
					result = maze.getMaze()[i][j+1];
					result.setOrientation('>');
				}
			}
			else if(j+1 < maze.getWeight() && maze.getMaze()[i][j+1].getCellType() == '.' && min > maze.getMaze()[i][j+1].getNrPasses()) {
				result = maze.getMaze()[i][j+1];
				result.setOrientation('>');
			}
		}
		else if(i+1 < maze.getHeight() && maze.getMaze()[i+1][j].getCellType() == '.') {
			result = maze.getMaze()[i+1][j];
			result.setOrientation('V');
			min = maze.getMaze()[i+1][j].getNrPasses();
			if(j+1 < maze.getWeight() && maze.getMaze()[i][j+1].getCellType() == '.' && min > maze.getMaze()[i][j+1].getNrPasses()) {
				result = maze.getMaze()[i][j+1];
				result.setOrientation('>');
			}
		}
		else if(j+1 < maze.getWeight() && maze.getMaze()[i][j+1].getCellType() == '.' && maze.getMaze()[i][j+1].getNrPasses() >= 0) {
			result = maze.getMaze()[i][j+1];
			result.setOrientation('>');
		}
		maze.getMaze()[i][j].increasePasses();
		
		return result;
	}
	
	//idem ca la celula cu orientare spre N
	public Cell rightDirection(int i,int j, Maze maze) throws CannotMoveIntoWallsException{
		Cell result = null;
		int min;
		
		if(i+1 < maze.getHeight() && maze.getMaze()[i+1][j].getCellType() == '.') {
			min = maze.getMaze()[i+1][j].getNrPasses();
			result = maze.getMaze()[i+1][j];
			result.setOrientation('V');
			if(j+1 < maze.getWeight() && maze.getMaze()[i][j+1].getCellType() == '.' && min > maze.getMaze()[i][j+1].getNrPasses()) {
				min = maze.getMaze()[i][j+1].getNrPasses();
				result = maze.getMaze()[i][j+1];
				result.setOrientation('>');
				if(i-1 >= 0 && maze.getMaze()[i-1][j].getCellType() == '.' && min > maze.getMaze()[i-1][j].getNrPasses()) {
					result = maze.getMaze()[i-1][j];
					result.setOrientation('^');
					min = maze.getMaze()[i-1][j].getNrPasses();
					if(j-1 >= 0 && maze.getMaze()[i][j-1].getCellType() == '.' && min > maze.getMaze()[i][j-1].getNrPasses()) {
						result = maze.getMaze()[i][j-1];
						result.setOrientation('<');
					}
				}
				else if(j-1 >= 0 && maze.getMaze()[i][j-1].getCellType() == '.' && min > maze.getMaze()[i][j-1].getNrPasses()) {
					result = maze.getMaze()[i][j-1];
					result.setOrientation('<');
				}
			}
			else if(i-1 >= 0 && maze.getMaze()[i-1][j].getCellType() == '.' && min > maze.getMaze()[i-1][j].getNrPasses()) {
				result = maze.getMaze()[i-1][j];
				result.setOrientation('^');
				min = maze.getMaze()[i-1][j].getNrPasses();
				if(j-1 >= 0 && maze.getMaze()[i][j-1].getCellType() == '.' && min > maze.getMaze()[i][j-1].getNrPasses()) {
					result = maze.getMaze()[i][j-1];
					result.setOrientation('<');
				}
			}
			else if(j-1 >= 0 && maze.getMaze()[i][j-1].getCellType() == '.' && min > maze.getMaze()[i][j-1].getNrPasses()) {
				result = maze.getMaze()[i][j-1];
				result.setOrientation('<');
			}
		}
		else if(j+1 < maze.getWeight() && maze.getMaze()[i][j+1].getCellType() == '.') {
			min = maze.getMaze()[i][j+1].getNrPasses();
			result = maze.getMaze()[i][j+1];
			result.setOrientation('>');
			if(i-1 >= 0 && maze.getMaze()[i-1][j].getCellType() == '.' && min > maze.getMaze()[i-1][j].getNrPasses()) {
				result = maze.getMaze()[i-1][j];
				result.setOrientation('^');
				min = maze.getMaze()[i-1][j].getNrPasses();
				if(j-1 >= 0 && maze.getMaze()[i][j-1].getCellType() == '.' && min > maze.getMaze()[i][j-1].getNrPasses()) {
					result = maze.getMaze()[i][j-1];
					result.setOrientation('<');
				}
			}
			else if(j-1 >= 0 && maze.getMaze()[i][j-1].getCellType() == '.' && min > maze.getMaze()[i][j-1].getNrPasses()) {
				result = maze.getMaze()[i][j-1];
				result.setOrientation('<');
			}
		}
		else if(i-1 >= 0 && maze.getMaze()[i-1][j].getCellType() == '.') {
			result = maze.getMaze()[i-1][j];
			result.setOrientation('^');
			min = maze.getMaze()[i-1][j].getNrPasses();
			if(j-1 >= 0 && maze.getMaze()[i][j-1].getCellType() == '.' && min > maze.getMaze()[i][j-1].getNrPasses()) {
				result = maze.getMaze()[i][j-1];
				result.setOrientation('<');
			}
		}
		else if(j-1 >= 0 && maze.getMaze()[i][j-1].getCellType() == '.' && maze.getMaze()[i][j-1].getNrPasses() >= 0) {
			result = maze.getMaze()[i][j-1];
			result.setOrientation('<');
		}
		maze.getMaze()[i][j].increasePasses();
		
		return result;
	}
	
	//metoda care intoarce vecinul cel mai propice
	public Cell getNeighbor(Maze maze, Cell cell) throws HeroOutOfGroundException{
		int i = cell.getYCoord();
		int j = cell.getXCoord();
		//se verifica ce orientare are eroul ca sa stabilim care sunt prioritatile de mers
		cellComparator comp = new cellComparator();
		//se verifica daca avem celula de iesire vecina
		Cell result = verifyOutPortal(i, j, maze);
		
		//in cazul in care conditia de mai sus nu e indeplinita, se cauta vecin
		if(result == null) {
			
			int orientation = comp.compareTo(cell.getOrientation());
			
			//eroul e orientat spre N
			if(orientation == 0)
				try {
					result = upDirection(i, j, maze);
				} catch (CannotMoveIntoWallsException e) {
					e.printStackTrace();
				}
			//eroul e orientat spre E
			if(orientation == 1)
				try {
					result = rightDirection(i, j, maze);
				} catch (CannotMoveIntoWallsException e) {
					e.printStackTrace();
				}
			//eroul e orientat spre S
			if(orientation == 2)
				try {
					result = downDirection(i, j, maze);
				} catch (CannotMoveIntoWallsException e) {
					e.printStackTrace();
				}
			//eroul e orientat spre V
			if(orientation == 3)
				try {
					result = leftDirection(i, j, maze);
				} catch (CannotMoveIntoWallsException e) {
					e.printStackTrace();
				}
			
		}
		return result;
	}
	
	//metoda ce verifica daca oricare dintre celulele vecine este cea de iesire
	public Cell verifyOutPortal(int i, int j, Maze maze) {
		if(j+1 < maze.getWeight() && maze.getMaze()[i][j+1].getCellType() == 'O') return maze.getMaze()[i][j+1];
		if(i-1 >= 0 && maze.getMaze()[i-1][j].getCellType() == 'O') return maze.getMaze()[i-1][j];
		if(j-1 >= 0 && maze.getMaze()[i][j-1].getCellType() == 'O') return maze.getMaze()[i][j-1];
		if(i+1 < maze.getHeight() && maze.getMaze()[i+1][j].getCellType() == 'O') return maze.getMaze()[i+1][j];
		return null;
	}
	
	public ArrayList<Cell> getPath(Maze maze, Cell entrance){
		Cell current = entrance;
		//pana cand celula curenta este diferita de celula de iesire, se adauga celule intr-o lista de celule.
		while(current.getNrPasses() != -1) {
			path.add(current);
			try {
				//se trece in celula vecina corespunzatoare
				current = getNeighbor(maze, current);
			} catch (HeroOutOfGroundException e) {
				e.printStackTrace();
			}
			//daca celula curenta este cea finala o adaugam in lista
			if(current.getNrPasses() == -1) path.add(current);
		}

		return path;
	}
}
