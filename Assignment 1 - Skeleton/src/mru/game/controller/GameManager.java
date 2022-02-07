package mru.game.controller;

import mru.game.model.Player;
import mru.game.view.AppMenu;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class GameManager
{
	
	private final String FILEPATH = "res/CasinoInfo.txt";
	ArrayList<Player> players;
	AppMenu appMen;
	
	public  GameManager() throws Exception
	{
		players = new ArrayList<>();
		appMen = new AppMenu();
		loadData();
		launchApplication();
		
	}

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
		
		do {
			playGameInner(p);
			char option = appMen.promptContinue();
			switch (option) {
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
		
		while (flag);
	}
	
	private void playGameInner(Player p) 
	{
		int guess = -1;
		boolean flag = false;
		char option;
		do {
			flag = false;
			option = appMen.showBetmenu();
			switch (option) {
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
		
		while (flag);
		
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
				printWinLoss(true, bet);
			} 
			
			else 
			{
				p.setBalance(p.getBalance() - bet);
				printWinLoss(false, bet);
			}
		}
	}

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

	private void topPlayer() 
	{
		if (players.size() == 0) 
		{
			System.out.println("No player found in database");
			return;
		}
		
		printHeader("TOP PLAYERS");
		int maxScore = Integer.MIN_VALUE;
		
		for (Player p: players) 
		{
			if (p.getNumOfWins() > maxScore) 
			{
				maxScore = p.getNumOfWins();
			}
		}
		
		printHorizontalBoundary("=");
		printOneLine("NAME", "# WINS");
		printHorizontalBoundary("=");
		
		for (Player p: players) 
		{
			if (p.getNumOfWins() == maxScore) 
			{
				printOneLine(p.getName(), Integer.toString(p.getNumOfWins()));
				printHorizontalBoundary("-");
			}
		}	
	}

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
	
	private void printOneLine(String text1, String text2) 
	{
		System.out.printf("|%-36s|%-36s|\n", text1, text2);
	}
	
	private void printOneLineThreeText(String pName, String wins, String balance) 
	{
		System.out.printf("|%-24s|%-24s|%-23s|\n", pName, wins, balance);
	}
	
	private void printHorizontalBoundary(String token) 
	{
		for (int i = 0; i < 75; i++) 
		{
			System.out.print(token);
		}
		
		System.out.println();
	}
	
	private void printPlayerInfo(Player p) 
	{
		printHeader("PLAYER INFO");
		printHorizontalBoundary("=");
		printOneLineThreeText("NAME", "# WINS", "BALANCE");
		printHorizontalBoundary("=");
		printOneLineThreeText(p.getName(), Integer.toString(p.getNumOfWins()),Double.toString(p.getBalance()) + " $");
		printHorizontalBoundary("-");
	}
	
	private void printSpaces(int n) 
	{
		for (int i = 0; i < n; i++) 
		{
			System.out.print(" ");
		}
	}
	private void printWinLoss(boolean win, Double bet) 
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