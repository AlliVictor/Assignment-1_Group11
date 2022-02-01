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

		System.out.print("Enter a choice: ");
		char option = input.nextLine().toLowerCase().charAt(0);
		return option;
	}
	
	public char showSubMenu()
	{
		System.out.println("\nSelect one of these options:\n");
		
		System.out.println("\t(T) Top Player (Most number of wins)");
		System.out.println("\t(N) Looking for a Name");
		System.out.println("\t(B) Back to Main menu\n");
		
		System.out.print("Enter a choice: ");
		char option = input.nextLine().toLowerCase().charAt(0);
		return option;
	}
	
	public String promptName()
	{
		System.out.print("Enter a name here: ");
		String name = input.nextLine().trim();
		return name;
	}
	
	public void showPlayer(Player ply)
	{
		System.out.println(ply);
	}
}