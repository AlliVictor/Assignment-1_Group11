package mru.game.controller;
import java.util.ArrayList;

public class PuntoBancoGame {
	private CardDeck deck;
	private ArrayList<Card> playerCards;
	private ArrayList<Card> bankCards;
	
	/**
	 * In this class you implement the game
	 * You should use CardDeck class here
	 * See the instructions for the game rules
	 */
	
	/**
	 * Constructor
	 */
	public PuntoBancoGame() 
	{
		deck = new CardDeck();
		playerCards = new ArrayList<>();
		bankCards = new ArrayList<>();
	}
	
	/**
	 * Find value of card
	 * @param card
	 * @return
	 */
	private int getCardValue(Card card) 
	{
		int rank = card.getRank();
		
		if (2 <= rank && rank <= 9) 
		{
			return rank;
		}
		
		if (rank >= 10) 
		{
			return 0;
		}
		
		return 1;
	}
	
	/**
	 * Based on the cards determine score
	 * @param - hand containing all cards dealt
	 * @return
	 */
	private int getScore(ArrayList<Card> hand) 
	{
		int totalValue = 0;
		for (Card card: hand) {
			totalValue += getCardValue(card);
		}
		return totalValue % 10;
		
	}
	
	/**
	 * print repeatedly
	 * @param token to print repeatedly
	 */
	private void printHorizontalBoundary(String token) 
	{
		for (int i = 0; i < 75; i++) 
		{
			System.out.print(token);
		}
		
		System.out.println();
	}
	
	private void printOneLine(String text1, String text2) 
	{
		System.out.printf("|%-36s|%-36s|\n", text1, text2);
	}
	
	/**
	 * Create status of the game
	 */
	private void printStatus() 
	{
		// print header
		for (int i = 0; i < 30; i++) 
		{
			System.out.print(" ");
		}
		System.out.print("- PUNTO BANCO -");
		
		for (int i = 0; i < 30; i++) 
		{
			System.out.print(" ");
		}
		System.out.println("");
		printHorizontalBoundary("=");
		printOneLine("PLAYER", "BANKER");
		printHorizontalBoundary("=");
		
		for (int i = 0; i < 3; i++) 
		{
			if (i >= bankCards.size() && i >= playerCards.size()) 
			{
				printOneLine("", "");
			} 
			
			else if (i >= bankCards.size()) 
			{
				printOneLine(playerCards.get(i).toString(), "");
			} 
			
			else if (i >= playerCards.size()) 
			{
				printOneLine("",  bankCards.get(i).toString());
			} 
			
			else 
			{
				printOneLine(playerCards.get(i).toString(), bankCards.get(i).toString());
			}
			
			printHorizontalBoundary("-");
		}
		printOneLine("PLAYER POINTS: " + getScore(playerCards), "BANKER POINTS: " + getScore(bankCards));
		printHorizontalBoundary("=");
	}
	
	/**
	 * find winner
	 * @param playerScore
	 * @param bankScore
	 * @return 1 if player wins, 2 if banker wins, 0 if tie
	 */
	private int findWinner(int playerScore, int bankScore) 
	{
		if (playerScore == bankScore) 
		{
			return 0;
		}
		
		if (playerScore > bankScore) 
		{
			return 1;
		}
		
		return 2;
	}
	/**
	 * Entry function to play the game
	 * @return 1 if player wins, 2 if banker wins, 0 if tie
	 */
	public int playGame() 
	{
		// player and banker each get 2 cards
		for (int i = 0; i < 2; i++) 
		{
			playerCards.add(deck.getRandomCard());
			bankCards.add(deck.getRandomCard());
		}
		
		// Compute scores
		int playerScore = getScore(playerCards);
		int bankScore = getScore(bankCards);
		
		if (playerScore >= 8 || bankScore >= 8)
		{
			printStatus();
			return findWinner(playerScore, bankScore);
		}
		
		if (0 <= playerScore && playerScore <= 5) 
		{
			Card playerDraws = deck.getRandomCard();
			playerCards.add(playerDraws);
			int cardValue = getCardValue(playerDraws);
			
			if (cardValue == 2 || cardValue == 3) 
			{
				if (0 <= bankScore && bankScore <= 4) 
				{
					bankCards.add(deck.getRandomCard());
				}
			} 
			
			else if (cardValue == 4 || cardValue == 5) 
			{
				if (0 <= bankScore && bankScore <= 5) 
				{
					bankCards.add(deck.getRandomCard());
				}
			} 
			
			else if (cardValue == 6 || cardValue == 7) 
			{
				if (0 <= bankScore && bankScore <= 6) 
				{
					bankCards.add(deck.getRandomCard());
				}
			} 
			
			else if (cardValue == 8) 
			{
				if (0 <= bankScore && bankScore <= 2) 
				{
					bankCards.add(deck.getRandomCard());
				}				
			} 
			
			else  
			{
				if (0 <= bankScore && bankScore <= 3) 
				{
					bankCards.add(deck.getRandomCard());
				}
			}
		} 
		
		else 
		{
			if (0 <= bankScore && bankScore <= 5) 
			{
				bankCards.add(deck.getRandomCard());
			}
		}
		
		printStatus();
		return findWinner(getScore(playerCards), getScore(bankCards));
	}
}