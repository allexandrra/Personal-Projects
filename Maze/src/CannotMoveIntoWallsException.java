
public class CannotMoveIntoWallsException extends Exception{

	private static final long serialVersionUID = 1L;

	public CannotMoveIntoWallsException() {
		super();
	}
	
	public CannotMoveIntoWallsException(String message) {
		super(message);
	}
}
