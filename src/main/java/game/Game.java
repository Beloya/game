package game;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import game.Event.BankruptEvent;
import game.Event.PlayerEvent;
import game.Event.PlayerEventEnum;
import game.Player.Player;
import game.Player.PlayerStatus;
import game.Square.Square;

public class Game {

	private Map<Long, Player> players = new ConcurrentHashMap<>();
	// 行动队列
	private LinkedBlockingQueue<Player> actionQueue = new LinkedBlockingQueue<>();
	// 行动完队列
	private LinkedBlockingQueue<Player> actionedQueue = new LinkedBlockingQueue<>();
	private int round;
	private AtomicLong currentPlayerId = new AtomicLong();
	private GameMap gameMap;

	public static void main(String[] args) {
		Game game = new Game();
		game.gameMap = new GameMap();
		game.gameMap.init();
		Map<Long, Integer> playerPositions = game.gameMap.getPlayerPositions();
		Square startSquare = game.gameMap.getStartSquare();
		
		Player player = new Player(1, "A", 10000);
		player.setAuto(true);
		game.players.put(player.getId(), player);
		game.actionQueue.add(player);
		playerPositions.put(player.getId(), startSquare.getId());
		
		player = new Player(2, "B", 10000);
		player.setAuto(true);
		game.players.put(player.getId(), player);
		game.actionQueue.add(player);
		playerPositions.put(player.getId(), startSquare.getId());
		
		player = new Player(3, "C", 10000);
		player.setAuto(true);
		game.players.put(player.getId(), player);
		game.actionQueue.add(player);
		playerPositions.put(player.getId(), startSquare.getId());
		
		game.start();
	}

	public void start() {
	System.out.println(gameMap.getSquareMap());
		while (round < 200 ) {

			roundStart();
			// 将已行动转为行动
			actionQueue.addAll(actionedQueue);
			actionedQueue.clear();
			if(isEnd())
				break;
		}
		winer();
	}

	public void roundStart() {
		System.out.println("\n回合: "+round);
		while (!actionQueue.isEmpty()) {
			Player player = playerAction();
			direct(player);
			walkBeforeEventHanlde(player);
			System.out.println(player.getName() + " 投出 " + player.getWalkPoint().get());
			gameMap.walk(player);
			walkAfterEventHanlde(player);
			System.out.println(player+"\n");
		}

		// 资产检查
		playersCheck();
		round++;

	}

	private boolean isEnd() {
		boolean end = actionQueue.isEmpty()||actionQueue.size()==1;
		return end;
	}

	public void winer() {
		Player winer=null;
		Iterator<Player> iterator = players.values().iterator();
		while (iterator.hasNext()) {
			Player player = (Player) iterator.next();
			if(player.status()==PlayerStatus.bankrupt)
				continue;
			if(winer==null||winer.money()<player.money())
				winer=player;
		}
		System.out.println(winer.getName()+ " 胜利 \n"+winer);
	}
	private Player playerAction() {
		Player player = actionQueue.poll();
		actionedQueue.add(player);
		return player;
	}

	private void playersCheck() {
		Iterator<Player> iterator = players.values().iterator();
		while (iterator.hasNext()) {
			Player player = (Player) iterator.next();
			if(player.status()==PlayerStatus.bankrupt)
				continue;
			int money = player.money();
			if (money < 0) {
				BankruptEvent bankruptEvent = new BankruptEvent(player, PlayerEventEnum.WalkAfter);
				bankruptEvent.handle(this, PlayerEventEnum.WalkAfter);
			}

		}
	}

	public void direct(Player player) {
		int direct = RandomManager.getRandom().nextInt(5) + 1;
		player.direct(direct);
	}

	public void walkBeforeEventHanlde(Player player) {
		ConcurrentLinkedQueue<PlayerEvent> overEvents = new ConcurrentLinkedQueue<>();
		ConcurrentLinkedQueue<PlayerEvent> events = player.getEvents();
		while (!events.isEmpty()) {
			PlayerEvent playerEvent = events.poll();
			playerEvent.handle(this, PlayerEventEnum.WalkBefore);
			if (playerEvent.keepTime() > 0)
				overEvents.add(playerEvent);
		}
		player.setEvents(overEvents);
	}

	public void walkAfterEventHanlde(Player player) {
		ConcurrentLinkedQueue<PlayerEvent> overEvents = new ConcurrentLinkedQueue<>();
		ConcurrentLinkedQueue<PlayerEvent> events = player.getEvents();
		while (!events.isEmpty()) {
			PlayerEvent playerEvent = events.poll();
			playerEvent.handle(this, PlayerEventEnum.WalkAfter);
			if (playerEvent.keepTime() > 0)
				overEvents.add(playerEvent);
		}
		player.setEvents(overEvents);
	}

	public Map<Long, Player> getPlayers() {
		return players;
	}

	public void setPlayers(Map<Long, Player> players) {
		this.players = players;
	}

	public LinkedBlockingQueue<Player> getActionQueue() {
		return actionQueue;
	}

	public void setActionQueue(LinkedBlockingQueue<Player> actionQueue) {
		this.actionQueue = actionQueue;
	}

	public int getRound() {
		return round;
	}

	public void setRound(int round) {
		this.round = round;
	}

	public AtomicLong getCurrentPlayerId() {
		return currentPlayerId;
	}

	public void setCurrentPlayerId(AtomicLong currentPlayerId) {
		this.currentPlayerId = currentPlayerId;
	}

	public GameMap getGameMap() {
		return gameMap;
	}

	public void setGameMap(GameMap gameMap) {
		this.gameMap = gameMap;
	}

	public LinkedBlockingQueue<Player> getActionedQueue() {
		return actionedQueue;
	}

	public void setActionedQueue(LinkedBlockingQueue<Player> actionedQueue) {
		this.actionedQueue = actionedQueue;
	}

}
