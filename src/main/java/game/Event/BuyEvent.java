package game.Event;

import game.Game;
import game.Player.Player;
import game.Square.SiteSquare;

public class BuyEvent extends PlayerEventAdpart {

	private SiteSquare siteSquare;

	@Override
	protected void event(Game game) {
		int buyPrice = siteSquare.getBuyPrice();
		int money = getPlayer().money();
		if (money > buyPrice) {
			System.out.println(getPlayer().getName()+" 花费了 "+buyPrice+" 购买了 "+siteSquare.getName());
			getPlayer().reduceMoney(buyPrice);
			getPlayer().addSite(siteSquare);
			siteSquare.setOwnerPlayer(getPlayer());
		} else {
			System.out.println("资金不够");
		}

	}

	public BuyEvent(Player player, PlayerEventEnum eventEnum, SiteSquare siteSquare) {
		super(player, eventEnum);
		this.siteSquare = siteSquare;
	}

	
	
}
