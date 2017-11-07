package com.dragon.book.effective;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class 第46条_foreach循环优先于传统的for循环 {
	
	private enum Suit {
		CLUB, DIAMOND, HEART, SPADE
	}
	
	private enum Rank {
		ACE, DEUCE, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, TEN, JACK, QUEEN, KING
	}
	
	@SuppressWarnings("unused")
	private static class Card {
		private Suit suit;
		private Rank rank;
		
		public Card(Suit suit, Rank rank) {
			this.suit = suit;
			this.rank = rank;
		}
	}
	
	public static void main(String[] args) {
		Collection<Suit> suits = Arrays.asList(Suit.values());
		Collection<Rank> ranks = Arrays.asList(Rank.values());
		
		List<Card> deck = new ArrayList<Card>();
		for(Iterator<Suit> i = suits.iterator(); i.hasNext();)
			for(Iterator<Rank> j = ranks.iterator(); j.hasNext();)
				deck.add(new Card(i.next(), j.next()));	// 会报错：java.util.NoSuchElementException
		// 因为第二个循环，i.next()会一直取下一个元素
		
		// 解决方法1
//		for(Iterator<Suit> i = suits.iterator(); i.hasNext();) {
//			Suit suit = i.next();
//			for(Iterator<Rank> j = ranks.iterator(); j.hasNext();)
//				deck.add(new Card(suit, j.next()));	
//		}
		
//		// 2、用改进的for-each循环可解决这个问题
//		for(Suit suit : suits)
//			for(Rank rank : ranks)
//				deck.add(new Card(suit, rank));
	}

}