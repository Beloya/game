package game.Square;

import game.Player.Player;

public abstract class Square {

	private int id;
	private String name;
	private Square nextSquare;
	private Square preSquare;
	
	
	public Square(int id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public Square getNextSquare() {
		return nextSquare;
	}
	public void setNextSquare(Square nextSquare) {
		this.nextSquare = nextSquare;
	}
	public Square getPreSquare() {
		return preSquare;
	}
	public void setPreSquare(Square preSquare) {
		this.preSquare = preSquare;
	}
	
	public Square forward(Player player,boolean direction) {
		Square square=null;
		if(direction)
			square=getNextSquare();
		else
			square=getPreSquare();
		square.passEvent(player);
		return square;
	}
	
	//进入事件
	public abstract void entryEvent(Player player);
	// 路过事件
	public abstract void passEvent(Player player);
	
	public boolean isLeave() {
		return true;
	}
	public boolean isEntry() {
		return true;
	}
	public boolean repeatEntry() {
		return true;
	}
	@Override
	public String toString() {
		return "Square [id=" + id + ", name=" + name +"nextId="+nextSquare.getId()+", preId="+preSquare.getId()+"]";
	}
	
	
}
