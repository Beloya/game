package game.Event;

import java.util.Set;
import java.util.concurrent.LinkedBlockingQueue;

import game.Game;
import game.Player.Player;
import game.Player.PlayerStatus;
import game.Square.Square;

public class BankruptEvent extends PlayerEventAdpart {

	public BankruptEvent(Player player, PlayerEventEnum eventEnum) {
		super(player, eventEnum);
	}

	@Override
	protected void event(Game game) {
		getPlayer().changeStatus(PlayerStatus.bankrupt);
		getPlayer().clearSite();

		LinkedBlockingQueue<Player> actionQueue = game.getActionQueue();
		LinkedBlockingQueue<Player> actionedQueue = game.getActionedQueue();
		actionQueue.remove(getPlayer());
		actionedQueue.remove(getPlayer());
		System.out.println(getPlayer().getName() + " 破产");
	}

}
