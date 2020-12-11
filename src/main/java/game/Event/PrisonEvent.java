package game.Event;

import game.Game;
import game.Player.Player;
import game.Player.PlayerStatus;

public class PrisonEvent extends PlayerEventAdpart{

	public PrisonEvent(Player player,int keepTime, PlayerEventEnum eventEnum) {
		super(player,keepTime+1, eventEnum);
		player.changeStatus(PlayerStatus.prison);
		System.out.println(getPlayer().getName()+" 进入监狱");
	}

	@Override
	protected void event(Game game) {
		if(keepTime()==0) {
			getPlayer().changeStatus(PlayerStatus.normal);
			System.out.println("出狱了");
			return;
		}
		int walk = getPlayer().walk();
		System.out.println("监狱中,无法行动");
	}

}
