package game.Event;

import game.Game;
import game.RandomManager;
import game.Player.Player;

public class StartSquareEvent extends PlayerEventAdpart{

	private boolean entry;


	public StartSquareEvent(Player player, PlayerEventEnum eventEnum, boolean entry) {
		super(player, eventEnum);
		this.entry = entry;
	}


	@Override
	protected void event(Game game) {

		int randomMoney=RandomManager.getRandom().nextInt(1900)+100;
		getPlayer().addMoney(randomMoney);
		if(entry)
		System.out.println("进入起点获得资金 "+randomMoney);
		else
			System.out.println("经过起点幸运的抽到了资金 "+randomMoney);
	}

}
