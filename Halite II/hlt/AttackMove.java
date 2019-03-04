package hlt;

public class AttackMove extends Move{

	private final long destinationId;

	public AttackMove(final Ship myShip, final Ship enemyShip) {
		super(MoveType.Attack, myShip);
		destinationId = enemyShip.getId();
	}

	public long getDestinationEnemyId(){
		return destinationId;
	}
}