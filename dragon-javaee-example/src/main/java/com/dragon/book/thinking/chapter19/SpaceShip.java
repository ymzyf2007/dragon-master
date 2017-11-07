package com.dragon.book.thinking.chapter19;

public enum SpaceShip {
	
	SCOUT, CARGO, TRANSPORT, CRUISER, BATTLESHIP, MOTHERSHIP;
	
	/**
	 * 覆盖enum的toString方法与覆盖一般类的方法没有区别
	 */
	public String toString() {
		String id = name();
		String lower = id.substring(1).toLowerCase();
		return id.charAt(0) + lower;
	}
	
	public static void main(String[] args) {
		for(SpaceShip s : values()) {
			System.out.println(s);
		}
	}

}