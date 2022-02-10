package mru.game.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import mru.game.controller.Card;

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

}
