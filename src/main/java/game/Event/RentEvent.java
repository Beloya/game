package game.Event;

import game.Game;
import game.Player.Player;
import game.Square.SiteSquare;

public class RentEvent extends PlayerEventAdpart{

	private SiteSquare siteSquare;


	public RentEvent(Player player, PlayerEventEnum eventEnum, SiteSquare siteSquare) {
		super(player, eventEnum);
		this.siteSquare = siteSquare;
	}


	@Override
	protected void event(Game game) {
		int rent = siteSquare.rent();
		Player ownerPlayer = siteSquare.getOwnerPlayer();
		if(ownerPlayer==null)
			return;
		getPlayer().reduceMoney(rent);
		ownerPlayer.addMoney(rent);
		System.out.println(getPlayer().getName()+" 向 "+ownerPlayer.getName()+" 支付租金 "+rent);
	}

}
