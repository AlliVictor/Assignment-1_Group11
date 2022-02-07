package mru.game.view;

import java.util.Scanner;

import mru.game.model.Player;

public class AppMenu 
{
	Scanner input;
	
	public AppMenu()
	{
		input = new Scanner(System.in);
	}
	/**
	 * This class will be used to show the menus and sub menus to the user
	 * It also prompts the user for the inputs and validates them 
	 */
	public char showMainMenu()
	{
		System.out.println("\nSelect one of these options:\n");
		
		System.out.println("\t(P) Play game");
		System.out.println("\t(S) Search");
		System.out.println("\t(E) Exit\n");

		System.out.print("Enter your Choice: ");
		char option = input.nextLine().toLowerCase().charAt(0);
		return option;
	}
	
	public char showSubMenu()
	{
		System.out.println("\nSelect one of these options:\n");
		
		System.out.println("\t(T) Top Player (Most number of wins)");
		System.out.println("\t(N) Looking for a Name");
		System.out.println("\t(B) Back to Main menu\n");
		
		System.out.print("Enter your Choice: ");
		char option = input.nextLine().toLowerCase().charAt(0);
		return option;
	}
	
	public char showBetmenu() {
        System.out.println("\nWhat do you want to bet on?");
        
        System.out.println("\t(P) Player Wins ");
        System.out.println("\t(B) Banker Wins");
        System.out.println("\t(T) Tie Game\n");
        
        System.out.print("Enter your Choice: ");
        char option = input.nextLine().toLowerCase().charAt(0);
        return option;
    }
	
	public String promptName()
	{
		System.out.print("Enter a name here: ");
		String name = input.nextLine().trim();
		return name;
	}
	
	public Double promptBetAmount() 
	{
    	System.out.print("How much do you want to bet? ");
    	Double val = input.nextDouble();
    	input.nextLine();
    	return val;
    }
    
    public char promptContinue() 
    {
    	System.out.print("\nDo you want to play again (y/n)? ");
    	char option = input.nextLine().toLowerCase().charAt(0);
        return option;
    }
	
	public void showPlayer(Player ply)
	{
		System.out.println(ply);
	}
}