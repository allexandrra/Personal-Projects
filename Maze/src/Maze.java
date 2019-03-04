
public class Maze {
	
	//clasa ce intoarce labirintul sub forma de matrice cu elemente Cell
	private int height;
	private int weight;
	private Cell[][] labyrinth;
	Cell entrance;
	
	public Maze(int height, int weight, Cell[][] labyrinth, Cell entrance) {
		this.height = height;
		this.weight = weight;
		this.labyrinth = labyrinth;
		this.entrance= entrance;
	}
	
	//metoda ce intoarce inaltimea labirintului
	public int getHeight() {
		return height;
	}
	
	//metoda ce intoarce latimea labirintului
	public int getWeight() {
		return weight;
	}
	
	//metoda care intoarce labirintul sub forma de matrice
	public Cell[][] getMaze(){
		return labyrinth;
	}
	
	//metoda ce intoarce intrarea in labirint
	public Cell getEntrance() {
		return entrance;
	}
}
