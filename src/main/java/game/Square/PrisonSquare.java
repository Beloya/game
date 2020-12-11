package game.Square;

import game.Event.PlayerEventEnum;
import game.Event.PrisonEvent;
import game.Player.Player;

public class PrisonSquare extends Square{

	public PrisonSquare(int id, String name) {
		super(id, name);
	}

	@Override
	public void entryEvent(Player player) {
		player.addEvent(new PrisonEvent(player, 3, PlayerEventEnum.WalkBefore));
	}

	@Override
	public void passEvent(Player player) {
		
	}
	public boolean repeatEntry() {
		return false;
	}

	@Override
	public String toString() {
		return "PrisonSquare [getId()=" + getId() + ", getName()=" + getName() + "]";
	}
	
	
}
