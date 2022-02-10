package mru.game.application;

import mru.game.controller.GameManager;

public class AppDriver 
{

	public static void main(String[] args) throws Exception 
	{
		//Calls the instance of GameManager to run the whole program
		new GameManager();
	}
}