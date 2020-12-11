package game.Square;

import game.RandomManager;
import game.Event.BuyEvent;
import game.Event.PlayerEventEnum;
import game.Event.RentEvent;
import game.Event.SiteRaiseEvent;
import game.Player.Player;

public class SiteSquare extends Square{

	private int grade=1;
	private int buyPrice;
	private int baseRent;
	private Player ownerPlayer;
	public int getGrade() {
		return grade;
	}

	public void setGrade(int grade) {
		this.grade = grade;
	}

	public int getBuyPrice() {
		return buyPrice;
	}

	public void setBuyPrice(int buyPrice) {
		this.buyPrice = buyPrice;
	}

	public int getBaseRent() {
		return baseRent;
	}

	public void setBaseRent(int baseRent) {
		this.baseRent = baseRent;
	}


	public int rent() {
		return grade*baseRent;
	}

	public Player getOwnerPlayer() {
		return ownerPlayer;
	}

	public void setOwnerPlayer(Player ownerPlayer) {
		this.ownerPlayer = ownerPlayer;
	}

	public SiteSquare(int id, String name) {
		super(id, name);
		this.buyPrice = RandomManager.getRandom().nextInt(750)+50;
		this.baseRent = RandomManager.getRandom().nextInt(1150)+50;
	}

	public boolean hasOwner() {
		return ownerPlayer!=null;
	}
	@Override
	public void entryEvent(Player player) {
		if(!hasOwner()) {
			player.addEvent(new BuyEvent(player, PlayerEventEnum.WalkAfter, this));
			return;
		}
		if(ownerPlayer.getId()!=player.getId()) {
			player.addEvent(new RentEvent(player, PlayerEventEnum.WalkAfter, this));
			return;
		}
		player.addEvent(new SiteRaiseEvent(player, PlayerEventEnum.WalkAfter, this));

	}

	@Override
	public void passEvent(Player player) {
		
	}

	@Override
	public String toString() {
		return "SiteSquare [grade=" + grade + ", buyPrice=" + buyPrice + ", rent=" + rent()  + ", getId()=" + getId() + ", getName()=" + getName() + "]";
	}

}
