package fi.oulu.tol.sqat.tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import fi.oulu.tol.sqat.GildedRose;
import fi.oulu.tol.sqat.Item;

public class GildedRoseTest {

// Example scenarios for testing
//   Item("+5 Dexterity Vest", 10, 20));
//   Item("Aged Brie", 2, 0)); ---exception
//   Item("Elixir of the Mongoose", 5, 7));
//   Item("Sulfuras, Hand of Ragnaros", 0, 80)); -----exception
//   Item("Backstage passes to a TAFKAL80ETC concert", 15, 20)); ----exception
//   Item("Conjured Mana Cake", 3, 6));

	// "Aged Brie" actually increases in Quality the older it gets
	@Test
	public void testUpdateEndOfDay_AgedBrie_Quality_10_11() {
		// Arrange
		GildedRose store = new GildedRose();
		store.addItem(new Item("Aged Brie", 2, 10) );
		
		// Act
		store.updateEndOfDay();
		
		// Assert
		List<Item> items = store.getItems();
		Item itemBrie = items.get(0);
		assertEquals(11, itemBrie.getQuality());
		}
	
	//At the end of each day our system lowers both values for every item 
	@Test
	public void testUpdateEndOfDay_LowerQualityBy1() {
		// Arrange
		GildedRose store = new GildedRose();
		store.addItem(new Item("Elixir of the Mongoose", 5, 7));
		
		// Act
		store.updateEndOfDay();
		
		// Assert
		List<Item> items = store.getItems();
		Item itemElixir = items.get(0);
		assertEquals("Test failed", 6, itemElixir.getQuality());
	}
	
	@Test
	public void testUpdateEndOfDay_LowerSellInBy1() {
		// Arrange
		GildedRose store = new GildedRose();
		store.addItem(new Item("Elixir of the Mongoose", 5, 7));
		
		// Act
		store.updateEndOfDay();
		
		// Assert
		List<Item> items = store.getItems();
		Item itemElixir = items.get(0);
		assertEquals("Test failed", 4, itemElixir.getSellIn());
	}
     
	//Once the sell by date has passed, Quality degrades twice as fast
	@Test
	public void testUpdateEndOfDay_QualityDegradesX2() {
		// Arrange
				GildedRose store = new GildedRose();
				store.addItem(new Item("+5 Dexterity Vest", 1, 20) );
				
				// Act
				store.updateEndOfDay();
				store.updateEndOfDay();
				
				// Assert
				List<Item> items = store.getItems();
				Item itemVest = items.get(0);
				assertEquals("Test failed",17, itemVest.getQuality());
	}
	
	//The Quality of an item is never negative
	@Test
	public void testUpdateEndOfDay_QualityNeverNegative() {
		// Arrange
				GildedRose store = new GildedRose();
				store.addItem(new Item("Conjured Mana Cake", 1, 1) );
				
				// Act
				store.updateEndOfDay();
				store.updateEndOfDay();
				
				// Assert
				List<Item> items = store.getItems();
				Item itemCake = items.get(0);
				assertEquals("Test failed",0, itemCake.getQuality());
	}
	// The Quality of an item is never more than 50 
	@Test
	public void testUpdateEndOfDay_QualityNeverOver50() {
		// Arrange
				GildedRose store = new GildedRose();
				store.addItem(new Item("Aged Brie", 10, 49) );
				
				// Act
				store.updateEndOfDay();
				store.updateEndOfDay();
				
				// Assert
				List<Item> items = store.getItems();
				Item itemBrie = items.get(0);
				assertEquals("Test failed",50, itemBrie.getQuality());
				}
	
	@Test
	public void testUpdateEndOfDay_QualityNeverOver50_test2() {
		// Arrange
				GildedRose store = new GildedRose();
				store.addItem(new Item("Backstage passes to a TAFKAL80ETC concert", 4, 49) );
				
				// Act
				store.updateEndOfDay();
				store.updateEndOfDay();
				
				// Assert
				List<Item> items = store.getItems();
				Item itemBrie = items.get(0);
				assertEquals("Test failed",50, itemBrie.getQuality());
				}
	
	//"Sulfuras", being a legendary item, never has to be sold or decreases in Quality 
	@Test
	public void testUpdateEndOfDay_SulfurasQualityNeverDecreases() {
		// Arrange
				GildedRose store = new GildedRose();
				store.addItem(new Item("Sulfuras, Hand of Ragnaros", 0, 80) );
				
				// Act
				store.updateEndOfDay();
				store.updateEndOfDay();
				
				// Assert
				List<Item> items = store.getItems();
				Item itemSulfuras = items.get(0);
				assertEquals("Test failed",80, itemSulfuras.getQuality());				
	}
	
	// "Backstage passes", like aged brie, increases in Quality as it's SellIn value approaches; 
	//Quality increases by 2 when there are 10 days or less 
	@Test
	public void testUpdateEndOfDay_PassesQualityIncreasesBy2() {
		// Arrange
				GildedRose store = new GildedRose();
								
				store.addItem(new Item("Backstage passes to a TAFKAL80ETC concert", 10, 20) );
				
				// Act
				store.updateEndOfDay();
								
				// Assert
				List<Item> items = store.getItems();
				Item itemPasses = items.get(0);
				
				assertEquals("Test failed",22, itemPasses.getQuality());
	}
	
	//Backstage passes Quality increases by 3 when there are 5 days or less
	@Test
	public void testUpdateEndOfDay_PassesQualityIncreasesBy3() {
				// Arrange
				GildedRose store = new GildedRose();
								
				store.addItem(new Item("Backstage passes to a TAFKAL80ETC concert", 6, 20) );
				
				// Act
				store.updateEndOfDay();
				store.updateEndOfDay();
								
				// Assert
				List<Item> items = store.getItems();
				Item itemPasses = items.get(0);
				
				assertEquals("Test failed",25, itemPasses.getQuality());
	}	
	
	//Backstage passes Quality drops to 0 after the concert. 
	@Test
	public void testUpdateEndOfDay_PassesQualityZero() {
				// Arrange
				GildedRose store = new GildedRose();
								
				store.addItem(new Item("Backstage passes to a TAFKAL80ETC concert", 1, 20) );
				
				// Act
				store.updateEndOfDay();
				store.updateEndOfDay();
								
				// Assert
				List<Item> items = store.getItems();
				Item itemPasses = items.get(0);
				
				assertEquals("Test failed",0, itemPasses.getQuality());
	}
		
}
