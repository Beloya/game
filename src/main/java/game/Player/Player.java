package game.Player;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;

import game.Event.BankruptEvent;
import game.Event.PlayerEvent;
import game.Event.PlayerEventEnum;
import game.Square.SiteSquare;
import game.Square.Square;

public class Player {

	private long id;
	private String name;
	private AtomicInteger money = new AtomicInteger(0);

	private boolean auto;
	private AtomicInteger status = new AtomicInteger(PlayerStatus.normal.ordinal());
	private AtomicInteger walkPoint = new AtomicInteger(0);

	private ConcurrentLinkedQueue<PlayerEvent> events = new ConcurrentLinkedQueue<>();
	private Set<Square> ownerSites = new HashSet<>();

	
	
	public Player(long id, String name, int money) {
		super();
		this.id = id;
		this.name = name;
		this.money.set(money);
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public AtomicInteger getMoney() {
		return money;
	}

	public void setMoney(AtomicInteger money) {
		this.money = money;
	}

	public boolean isAuto() {
		return auto;
	}

	public void setAuto(boolean auto) {
		this.auto = auto;
	}

	public AtomicInteger getStatus() {
		return status;
	}

	public void setStatus(AtomicInteger status) {
		this.status = status;
	}

	public AtomicInteger getWalkPoint() {
		return walkPoint;
	}

	public void setWalkPoint(AtomicInteger walkPoint) {
		this.walkPoint = walkPoint;
	}

	public ConcurrentLinkedQueue<PlayerEvent> getEvents() {
		return events;
	}

	public void setEvents(ConcurrentLinkedQueue<PlayerEvent> events) {
		this.events = events;
	}

	public Set<Square> getOwnerSites() {
		return ownerSites;
	}

	public void setOwnerSites(Set<Square> ownerSites) {
		this.ownerSites = ownerSites;
	}

	public void reduceMoney(int money) {
		int oldMoney = this.money.get();
		this.money.set(oldMoney-money);
		if (this.money.get() < 0)
			addEvent(new BankruptEvent(this, PlayerEventEnum.WalkAfter));
	}

	public void addEvent(PlayerEvent playerEvent) {
		events.add(playerEvent);
	}


	public void addMoney(int money) {
		this.money.addAndGet(money);
	}

	public int money() {
		return money.get();
	}

	public int walk() {
		return walkPoint.getAndSet(0);
	}
	
	public void direct(int point) {
		walkPoint.set(point);
	}

	public PlayerStatus status() {
		PlayerStatus[] values = PlayerStatus.values();
		return values[status.get()];
	}

	public void changeStatus(PlayerStatus playerStatus) {
		status.set(playerStatus.ordinal());
	}

	public void addSite(Square square) {
		ownerSites.add(square);
	}

	public void removeSite(Square square) {
		SiteSquare siteSquare = ((SiteSquare) square);
		siteSquare.setOwnerPlayer(null);
		ownerSites.remove(siteSquare);
	}

	public void clearSite() {
		Iterator<Square> iterator = ownerSites.iterator();
		while (iterator.hasNext()) {
			Square square = iterator.next();
			SiteSquare siteSquare = ((SiteSquare) square);
			siteSquare.setOwnerPlayer(null);
		}

		ownerSites.clear();
	}

	@Override
	public String toString() {
		return "Player [id=" + id + ", name=" + name + ", money=" + money + ", auto=" + auto + ", status=" + status
				+ ", walkPoint=" + walkPoint + ", events=" + events + ", ownerSites=" + ownerSites + "]";
	}
	
	
}
