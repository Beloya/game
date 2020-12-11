package game.Event;

import game.Game;
import game.Player.Player;

public abstract class PlayerEventAdpart implements PlayerEvent{

	private Player player;
	private int keepTime=1;
	private PlayerEventEnum eventEnum=PlayerEventEnum.Default;
	protected abstract void event(Game game);
	public void eventHandle(Game game) {
		keepTime--;
		event(game);
	}
	public PlayerEventEnum eventType() {
		return eventEnum;
	}
	public void handle(Game game,PlayerEventEnum eventEnum) {
		if (this.eventEnum==eventEnum) 
			eventHandle(game);
	}
	public int keepTime() {
		return keepTime;
	}
	public Player getPlayer() {
		return player;
	}
	public void setPlayer(Player player) {
		this.player = player;
	}
	public int getKeepTime() {
		return keepTime;
	}
	public void setKeepTime(int keepTime) {
		this.keepTime = keepTime;
	}
	public PlayerEventEnum getEventEnum() {
		return eventEnum;
	}
	public void setEventEnum(PlayerEventEnum eventEnum) {
		this.eventEnum = eventEnum;
	}
	public PlayerEventAdpart(Player player, PlayerEventEnum eventEnum) {
		super();
		this.player = player;
		this.eventEnum = eventEnum;
	}
	public PlayerEventAdpart(Player player, int keepTime, PlayerEventEnum eventEnum) {
		super();
		this.player = player;
		this.keepTime = keepTime;
		this.eventEnum = eventEnum;
	}
	
}
