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
				Player p = new Player(splittedLine[0], Integer.parseInt(splittedLine[1]), Integer.parseInt(splittedLine[2]));
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
			//Player ply = searchByName(name);
			//appMen.showPlayer(ply);
			String name = appMen.promptName();
            Player ply = searchByName(name);
            appMen.showPlayer(ply);
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
		
		
	}

	private Player searchByName(String name) 
	{
		//String name = appMen.promptName();
		Player ply = null;
		
		for(Player p : players)
		{
			if(p.getName().equals(name))
			{
				ply = p;
				break;
			}
		}
		
		return ply;
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
}