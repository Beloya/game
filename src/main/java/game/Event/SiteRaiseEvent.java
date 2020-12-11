package game.Event;

import game.Game;
import game.Player.Player;
import game.Square.SiteSquare;

public class SiteRaiseEvent extends PlayerEventAdpart {

	private SiteSquare siteSquare;

	public SiteRaiseEvent(Player player, PlayerEventEnum eventEnum, SiteSquare siteSquare) {
		super(player, eventEnum);
		this.siteSquare = siteSquare;
	}

	@Override
	protected void event(Game game) {
		long ownerId = siteSquare.getOwnerPlayer().getId();
		if(ownerId!=getPlayer().getId())
			return;
		int oldRent = siteSquare.rent();
		int grade = siteSquare.getGrade();
		siteSquare.setGrade(grade+1);
		int rent = siteSquare.rent();
		System.out.println(getPlayer().getName()+" 到达自己的房产 "+siteSquare+" 等级提升,租金上涨 "+(rent-oldRent));
	}

}
