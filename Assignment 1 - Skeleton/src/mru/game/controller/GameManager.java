package mru.game.controller;

import mru.game.model.Player;
import mru.game.view.AppMenu;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class GameManager
{
	
	private final String FILEPATH = "res/CasinoInfo.txt";
	ArrayList<Player> players;
	AppMenu appMen;
	
	/**
	 * The class constructor
	 * @throws Exception
	 */
	public  GameManager() throws Exception
	{
		players = new ArrayList<>();
		appMen = new AppMenu();
		loadData();
		launchApplication();
		
	}

	/**
	 * Loads the game's data an information
	 * @throws Exception
	 */
	private void loadData() throws Exception
	{
		File CI = new File(FILEPATH);
		String currentLine;
		String [] splittedLine;
		
		if (CI.exists())
		{
			Scanner fileReader = new Scanner(CI);
			
			while (fileReader.hasNextLine())
			{
				currentLine = fileReader.nextLine();
				splittedLine = currentLine.split(",");
				Player p = new Player(splittedLine[0], Double.parseDouble(splittedLine[1]), Integer.parseInt(splittedLine[2]));
				players.add(p);
			}
			
			fileReader.close(); 
		}
	}
	
	/**
	 * Launches the game application
	 * @throws Exception
	 */
	private void launchApplication() throws Exception
	{
		boolean flag = true;
		
		while(flag)
		{
			char option = appMen.showMainMenu();
			
			switch(option)
			{
			case 'p':
				playGame();
				break;
			
			case 's':
				search();
				break;
				
			case 'e':
				exit();
				flag = false;
				break;
				
			default:
				System.out.println("\nInvalid input! Please try again.");
				break;
			}
		}
	}
	

	/**
	 * Runs the core game, itself
	 */
	private void playGame() 
	{
		String name = appMen.promptName();
		System.out.println("");
		printHorizontalBoundary("*");
		Player p = searchByName(name);
		
		if (p == null) 
		{
			p = new Player(name, 100, 0);
			players.add(p);
			System.out.printf("*** %-28s --- %-34s ***\n", "WELCOME " + p.getName(), "Your initial balance is " + Double.toString(p.getBalance()) +" $");
		} 
		
		else 
		{
			System.out.printf("*** %-28s --- %-34s ***\n", "WELCOME BACK " + p.getName(), "Your balance is " + Double.toString(p.getBalance()) +" $");
		}
		
		printHorizontalBoundary("*");
		boolean flag = true;
		
		while(flag)
		{
			playGameInner(p);
			char option = appMen.promptContinue();
			
			switch (option) 
			{
				case 'y':
					break;
					
				case 'n':
					flag = false;
					break;
					
				default:
					System.out.println("\nInvalid input! Please try again.");
					break;
			}	
		} 
	}
	
	/**
	 * Runs the sub core of the game
	 * @param p what that player chooses
	 */
	private void playGameInner(Player p) 
	{
		int guess = -1;
		boolean flag = false;
		char option;
		
		while(flag) 
		{
			flag = false;
			option = appMen.showBetmenu();
			
			switch (option) 
			{
				case 'p':
					guess = 1;
					break;
					
				case 'b':
					guess = 2;
					break;
					
				case 't':
					guess = 0;
					break;
					
				default:
					System.out.println("\nInvalid input! Please try again.");
					break;
			}	
		} 
		
		Double bet = appMen.promptBetAmount();
		
		if (bet > p.getBalance()) 
		{
			System.out.println("Bet more than current balance is not allowed");
		} 
		
		else 
		{
			PuntoBancoGame obj = new PuntoBancoGame();
			int outcome = obj.playGame();
			
			if (outcome == guess) 
			{
				p.setBalance(p.getBalance() + bet);
				p.setNumOfWins(p.getNumOfWins() + 1);
				printWinAndLoss(true, bet);
			} 
			
			else 
			{
				p.setBalance(p.getBalance() - bet);
				printWinAndLoss(false, bet);
			}
		}
	}

	/**
	 * Where the player can look to search fro information
	 */
	private void search() 
	{
		char option = appMen.showSubMenu();

		switch(option)
		{
		case 't':
			topPlayer();
			break;
			
		case 'n':
			searchPlayer();
			break;
			
		case 'b':
			break;
			
		default:
			System.out.println("\nInvalid input! Please try again.");
			break;
		}
	}
	

	/**
	 * Where the player can find top players for the game
	 */
	private void topPlayer() 
	{
		if (players.size() == 0) 
		{
			System.out.println("No player found in database");
			return;
		}
		
		printHeader("TOP PLAYERS");
		int highestScore = Integer.MIN_VALUE;
		
		for (Player p: players) 
		{
			if (p.getNumOfWins() > highestScore) 
			{
				highestScore = p.getNumOfWins();
			}
		}
		
		printHorizontalBoundary("=");
		printOneLine("NAME", "# WINS");
		printHorizontalBoundary("=");
		
		for (Player p: players) 
		{
			if (p.getNumOfWins() == highestScore) 
			{
				printOneLine(p.getName(), Integer.toString(p.getNumOfWins()));
				printHorizontalBoundary("-");
			}
		}	
	}

	/**
	 * Where the player can search other players and themselves
	 */
	private void searchPlayer() 
	{
		String name = appMen.promptName();
		Player foundPlayer = searchByName(name);
		
		if (foundPlayer != null) 
		{
			printPlayerInfo(foundPlayer);
		} 
		
		else 
		{
			System.out.println("No player found with name " + name);
		}
	}
	
	/**
	 * Assists the previous method
	 * @param name of the person being searched
	 * @return that players information
	 */
	private Player searchByName(String name) 
	{
		for (Player p: players) 
		{
			if (name.equals(p.getName().toLowerCase())) 
			{
				return p;
			}
		}
		
		return null;
	}

	/**
	 * Exists the game and saves all information inputed and done by user
	 * @throws FileNotFoundException
	 */
	private void exit() throws FileNotFoundException 
	{
		File CI = new File(FILEPATH);
		PrintWriter pw = new PrintWriter(CI);
		
		for (Player p : players)
		{
			pw.println(p.format());
		}
		
		System.out.println("\nSaving... \nDone! Please visit us again.");
		
		pw.close();
	}
	
	/**
	 * Prints out the header box for table
	 * @param head - the header text
	 */
	private void printHeader(String head) 
	{
		for (int i = 0; i < 30; i++) 
		{
			System.out.print(" ");
		}
		
		System.out.print("- " + head + " -");
		for (int i = 0; i < 30; i++) 
		{
			System.out.print(" ");
		}
		
		System.out.println("");
	}
	
	/**
	 * Prints the object needed for "formating"
	 * Sets its pattern
	 * @param text1 - the object it would be written in
	 * @param text2 - the object it would be written in
	 */
	private void printOneLine(String text1, String text2) 
	{
		System.out.printf("|%-36s|%-36s|\n", text1, text2);
	}
	
	/**
	 * Prints the object needed for "formating"
	 * Sets its pattern
	 * @param pName - players name
	 * @param wins - players wins
	 * @param balance - players balance
	 */
	private void printLineOfThreeTexts(String pName, String wins, String balance) 
	{
		System.out.printf("|%-24s|%-24s|%-23s|\n", pName, wins, balance);
	}
	
	/**
	 * Prints the object needed for "formating"
	 * Sets its amount
	 * @param token - the object that would be repeated
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
	 * Prints out the player info table
	 * @param p - the players info
	 */
	private void printPlayerInfo(Player p) 
	{
		printHeader("PLAYER INFO");
		printHorizontalBoundary("=");
		printLineOfThreeTexts("NAME", "# WINS", "BALANCE");
		printHorizontalBoundary("=");
		printLineOfThreeTexts(p.getName(), Integer.toString(p.getNumOfWins()),Double.toString(p.getBalance()) + " $");
		printHorizontalBoundary("-");
	}
	
	/**
	 * Prints the spaces needed for "formating"
	 * @param n - the spaces thatr would be repeted
	 */
	private void printSpaces(int n) 
	{
		for (int i = 0; i < n; i++) 
		{
			System.out.print(" ");
		}
	}
	
	/**
	 * Prints the table for who wins and who losses
	 * @param win - if there is a win
	 * @param bet - and how much was bet
	 */
	private void printWinAndLoss(boolean win, Double bet) 
	{
		printSpaces(12);
		for (int i = 0; i < 40; i++) 
		{
			System.out.print("$");
		}
		
		System.out.println();
		
		printSpaces(12);
		
		if (win) 
		{
			System.out.printf("$%-38s$\n", "\tPLAYER WON " + Double.toString(bet) + " $");
		} 
		
		else 
		{
			System.out.printf("$%-38s$\n", "\tPLAYER LOST " + Double.toString(bet) + " $");
		}
		
		printSpaces(12);
		
		for (int i = 0; i < 40; i++) 
		{
			System.out.print("$");
		}
		
		System.out.println();
	}
	
}