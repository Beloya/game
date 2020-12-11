package game.Event;

import game.Game;

public interface PlayerEvent {

	public PlayerEventEnum eventType();
	public int keepTime();
	public void handle(Game game,PlayerEventEnum eventEnum);
}
