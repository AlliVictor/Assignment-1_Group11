package mru.game.controller;
import java.util.ArrayList;

public class PuntoBancoGame {
	private CardDeck deckOfCards;
	private ArrayList<Card> playersCards;
	private ArrayList<Card> banksCards;
	
	/**
	 * The class constructor
	 */
	public PuntoBancoGame() 
	{
		deckOfCards = new CardDeck();
		playersCards = new ArrayList<>();
		banksCards = new ArrayList<>();
	}
	
	/**
	 * Finds the rank of the card
	 * @param card - collects the rank and suit
	 * @return rank if rank is between 2 and 9, 0 if rank is greater than 10, 1 if it is neither
	 */
	private int getCardRank(Card card) 
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
	 * @return the value of the card rank mod 10
	 */
	private int getScore(ArrayList<Card> hand) 
	{
		int totalValue = 0;
		
		for (Card card: hand) 
		{
			totalValue += getCardRank(card);
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
	
	/**
	 * Uses to print out a line of text  
	 * @param text1, the line of text
	 * @param text2, the line of text
	 */
	private void printOneLine(String text1, String text2) 
	{
		System.out.printf("|%-36s|%-36s|\n", text1, text2);
	}
	
	/**
	 * Prints the status menu of the game
	 */
	private void printMenuStatus() 
	{
		//Prints header
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
			if (i >= banksCards.size() && i >= playersCards.size()) 
			{
				printOneLine("", "");
			} 
			
			else if (i >= banksCards.size()) 
			{
				printOneLine(playersCards.get(i).toString(), "");
			} 
			
			else if (i >= playersCards.size()) 
			{
				printOneLine("",  banksCards.get(i).toString());
			} 
			
			else 
			{
				printOneLine(playersCards.get(i).toString(), banksCards.get(i).toString());
			}
			
			printHorizontalBoundary("-");
		}
		
		printOneLine("PLAYER POINTS: " + getScore(playersCards), "BANKER POINTS: " + getScore(banksCards));
		printHorizontalBoundary("=");
	}
	
	/**
	 * Finds the winner of the bet
	 * @param playerScore - the players score
	 * @param bankScore - the banks score
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
	 * Starts the game
	 * @return 1 if player wins, 2 if banker wins, 0 if tie
	 */
	public int playGame() 
	{
		//If the player and the banker each get 2 cards
		for (int i = 0; i < 2; i++) 
		{
			playersCards.add(deckOfCards.getRandomCard());
			banksCards.add(deckOfCards.getRandomCard());
		}
		
		//Calculates the scores
		int playerScore = getScore(playersCards);
		int bankScore = getScore(banksCards);
		
		
		if (playerScore >= 8 || bankScore >= 8)
		{
			printMenuStatus();
			return findWinner(playerScore, bankScore);
		}
		
		//Calculates all the card values to score possibilities
		if (0 <= playerScore && playerScore <= 5) 
		{
			Card playerDraws = deckOfCards.getRandomCard();
			playersCards.add(playerDraws);
			int cardValue = getCardRank(playerDraws);
			
			if (cardValue == 2 || cardValue == 3) 
			{
				if (0 <= bankScore && bankScore <= 4) 
				{
					banksCards.add(deckOfCards.getRandomCard());
				}
			} 
			
			else if (cardValue == 4 || cardValue == 5) 
			{
				if (0 <= bankScore && bankScore <= 5) 
				{
					banksCards.add(deckOfCards.getRandomCard());
				}
			} 
			
			else if (cardValue == 6 || cardValue == 7) 
			{
				if (0 <= bankScore && bankScore <= 6) 
				{
					banksCards.add(deckOfCards.getRandomCard());
				}
			} 
			
			else if (cardValue == 8) 
			{
				if (0 <= bankScore && bankScore <= 2) 
				{
					banksCards.add(deckOfCards.getRandomCard());
				}				
			} 
			
			else  
			{
				if (0 <= bankScore && bankScore <= 3) 
				{
					banksCards.add(deckOfCards.getRandomCard());
				}
			}
		} 
		
		else 
		{
			if (0 <= bankScore && bankScore <= 5) 
			{
				banksCards.add(deckOfCards.getRandomCard());
			}
		}
		
		//Prints the menu status
		printMenuStatus();
		
		//Returns the winner for the winner, while displaying their scores
		return findWinner(getScore(playersCards), getScore(banksCards));
	}
}