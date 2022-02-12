package mru.game.test;

import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;
import mru.game.controller.Card;
import mru.game.controller.CardDeck;
import mru.game.model.Player;

class test 
{
	@Test
    void testGettersCard() 
	{
        Card obj1 = new Card(10, "Suit");
        assertEquals(obj1.getRank(), 10);
        assertEquals(obj1.getSuit(), "Suit");
    }
	
	
    @Test
    void testSettersCard() 
    {
        Card obj1 = new Card (10, "My Suit");
        obj1.setRank(10);
        assertEquals(obj1.getRank(), 10);
        obj1.setSuit("New Suit");
        assertEquals(obj1.getSuit(), "New Suit", "suit was reset to New Suit");
    }
    
    
    @Test
    void testToStringCard() 
    {
         Card obj2 = new Card(5, "suit");
         assertEquals(obj2.toString(), "5 of suit");
    }
    
    
    @Test
    void testGettersPlayer() {
        Player obj1 = new Player("name",10.0,10);
        assertEquals(obj1.getName(), "name");
        assertEquals(obj1.getBalance(), 10.0);
        assertEquals(obj1.getNumOfWins(), 10);
    }
    

    @Test
    void testSettersPlayer() {
        Player obj1 = new Player ("name", 10.0, 10);
        obj1.setName("name");
        assertEquals(obj1.getName(), "name");
        obj1.setBalance(10.0);
        assertEquals(obj1.getBalance(), 10.0);
        obj1.setNumOfWins(10);
        assertEquals(obj1.getNumOfWins(), 10);
    }

    
    @Test
    void testToStringPlayer() 
    {
        Player obj2 = new Player("Name", 10.0, 10);
        assertEquals(obj2.toString(), "Name; NameBalance: 10.0Number of Wins: 10");
    }
    
    
    @Test
    void testCreateDeck() 
    {
        CardDeck obj = new CardDeck();
        ArrayList<Card> deck = obj.getDeck();
        assertEquals(deck.size(), 52);
    }
    
    
    @Test
    void testDeckShuffle() 
    {
        CardDeck obj = new CardDeck();
        ArrayList<Card> deck1 = getDeepCopy(obj.getDeck());
        obj.shuffleDeck();
        ArrayList<Card> deck2 = getDeepCopy(obj.getDeck());
        assertEquals(areDecksEqual(deck1, deck2), false);
    }
    
    
    @Test
    void testGetRandomCard() 
    {
        CardDeck obj = new CardDeck();
        Card randomCard = obj.getRandomCard();
        assertEquals(isCardInDeck(obj.getDeck(), randomCard), false);
    }
    
    
    private boolean areDecksEqual(ArrayList<Card> d1, ArrayList<Card> d2) 
    {
        if (d1.size() != d2.size()) 
        {
            return false;
        }
        
        for (int i = 0; i < d1.size(); i++) 
        {
            if (!d1.get(i).equals(d2.get(i))) 
            {
                return false;
            }
        }
        
        return true;
    }
    
    
    private boolean isCardInDeck(ArrayList<Card> deck, Card obj) 
    {
        for (Card d: deck) 
        {
            if (d.equals(obj)) 
            {
                return true;
            }
        }
        
        return false;
    }
     
    
    private ArrayList<Card> getDeepCopy(ArrayList<Card> obj) 
    {
        ArrayList<Card> res = new ArrayList<>();
        for (Card c : obj) 
        {
            res.add(new Card(c.getRank(), c.getSuit()));
        }
        return res;
    }
}