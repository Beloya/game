package game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

import game.Player.Player;
import game.Player.PlayerStatus;
import game.Square.FateSquare;
import game.Square.PrisonSquare;
import game.Square.SiteSquare;
import game.Square.Square;
import game.Square.StartSquare;

public class GameMap {

	private long id = UUID.randomUUID().getLeastSignificantBits();
	private String name;
	private Map<Integer, Square> squareMap = new HashMap<Integer, Square>();
	private Map<Long, Integer> playerPositions = new HashMap<>();
	private Square startSquare;

	private List<Square> prisionSquares = new ArrayList<Square>();

	private final static Map<Integer, String> mapInfos = new HashMap<>();
	static {
		for (int i = 1; i < 36; i++) {
			mapInfos.put(i, i + "");
		}
	}

	public void init() {
		Square square = new StartSquare(0, "起点");
		Square preSquare = null;
		startSquare = square;
		squareMap.put(square.getId(), square);
		Iterator<Entry<Integer, String>> iterator = mapInfos.entrySet().iterator();
		while (iterator.hasNext()) {
			Entry<Integer, String> entry = iterator.next();
			square.setNextSquare(new SiteSquare(entry.getKey(), entry.getValue()));
			preSquare = square;
			square = square.getNextSquare();
			square.setPreSquare(preSquare);
			squareMap.put(square.getId(), square);
			// 随机命运
			if (RandomManager.randomRate(200)) {
				square.setNextSquare(new FateSquare(entry.getKey() + 100, "命运"));
				preSquare = square;
				square = square.getNextSquare();
				square.setPreSquare(preSquare);
				squareMap.put(square.getId(), square);
			}

			// 随机监狱
			if (RandomManager.randomRate(100)) {
				square.setNextSquare(new PrisonSquare(entry.getKey() + 200, "监狱"));
				preSquare = square;
				square = square.getNextSquare();
				square.setPreSquare(preSquare);
				squareMap.put(square.getId(), square);
				prisionSquares.add(square);
			}
			// 链回起点
			square.setNextSquare(startSquare);
			startSquare.setPreSquare(square);
		}
	}

	public void walk(Player player) {

		Integer squareId = playerPositions.get(player.getId());
		Square square = squareMap.get(squareId);
		Square oldSquare = square;
		if (!leave(oldSquare, oldSquare, player))
			return;
		int walkPoint = player.walk();
		boolean direction = walkPoint > 0;
		int walkCount = Math.abs(walkPoint);
		for (int i = 0; i < walkCount; i++) {
			square = square.forward(player, direction);
		}
		entry(oldSquare, square, player);
	}

	public boolean leave(Square oldSquare, Square square, Player player) {
		PlayerStatus playerStatus = player.status();
		boolean leave = square.isLeave() && playerStatus == PlayerStatus.normal;
		return leave;
	}

	public boolean entry(Square oldSquare, Square square, Player player) {
		PlayerStatus playerStatus = player.status();
		boolean sameSquare = isSameSquare(oldSquare, square);
		boolean entry =square.isEntry() && playerStatus == PlayerStatus.normal;
		if (!entry)
			return false;
		if (!square.repeatEntry() && sameSquare)
			return false;
		square.entryEvent(player);
		playerPositions.put(player.getId(), square.getId());
		System.out.println(player.getName()+" 从 "+oldSquare+" 到达 "+square);
		return true;
	}

	public boolean isSameSquare(Square oldSquare, Square square) {
		return oldSquare.getId() == square.getId();
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

	public Map<Integer, Square> getSquareMap() {
		return squareMap;
	}

	public void setSquareMap(Map<Integer, Square> squareMap) {
		this.squareMap = squareMap;
	}

	public Map<Long, Integer> getPlayerPositions() {
		return playerPositions;
	}

	public void setPlayerPositions(Map<Long, Integer> playerPositions) {
		this.playerPositions = playerPositions;
	}

	public Square getStartSquare() {
		return startSquare;
	}

	public void setStartSquare(Square startSquare) {
		this.startSquare = startSquare;
	}

	public List<Square> getPrisionSquares() {
		return prisionSquares;
	}

	public void setPrisionSquares(List<Square> prisionSquares) {
		this.prisionSquares = prisionSquares;
	}

	public static Map<Integer, String> getMapinfos() {
		return mapInfos;
	}

}
