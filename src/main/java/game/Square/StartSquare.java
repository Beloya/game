package game.Square;

import game.RandomManager;
import game.Event.PlayerEventEnum;
import game.Event.StartSquareEvent;
import game.Player.Player;

public class StartSquare extends Square{

	public StartSquare(int id, String name) {
		super(id, name);
	}

	@Override
	public void entryEvent(Player player) {
        player.addEvent(new StartSquareEvent(player, PlayerEventEnum.WalkAfter,true));
	}

	@Override
	public void passEvent(Player player) {
		if(RandomManager.randomRate(10))
	     	player.addEvent(new StartSquareEvent(player, PlayerEventEnum.WalkAfter,false));
	}

	@Override
	public String toString() {
		return "StartSquare [getId()=" + getId() + ", getName()=" + getName() + "]";
	}

}
