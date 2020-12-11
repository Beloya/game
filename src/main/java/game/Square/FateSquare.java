package game.Square;

import game.Player.Player;

public class FateSquare extends Square{

	public FateSquare(int id, String name) {
		super(id, name);
	}

	@Override
	public void entryEvent(Player player) {
		
	}

	@Override
	public void passEvent(Player player) {
		
	}

	@Override
	public String toString() {
		return "FateSquare [getId()=" + getId() + ", getName()=" + getName() + "]";
	}

	
}
