//purchasingAgent
//Jordan Foster

import java.util.*;
import java.io.*;

/**
 * 
 * @author CoolJFoster
 * models a purchasing agent for an individual supply for a restaurant 
 */
public class purchasingAgent 
{
	//constant
	final private int NUM_DAYS=100;
	
	//input file
	private File inputFile;
		
	//scanner
	private Scanner fileScan;
	
	//initial variables
	private int initialInventory;
	private int currInventory;
	private int invCheckOne;
	private double percentage;
	private int avgDays;
	private int avgBuy;
	private int invCheckTwo;
	private int checkTwoBuy;
	private int defaultBuy;

	
	//purchases
	private int numPurchases=0;
	private double purchPrices=0;
	
	//total inventory
	private int totalInventory;
	
	//temporal avg
	private double tempAvg;
	
	//prices by day
	private double[] prices=new double[NUM_DAYS];
	
	//end of day inventories
	private double endDayInv=0;
	
	/**
	 * constructor with file name given
	 * @param fileName
	 * @throws FileNotFoundException
	 */
	purchasingAgent(String fileName) throws FileNotFoundException
	{
		this.inputFile= new File(fileName);
		this.fileScan=new Scanner(inputFile);
	}
	
	
	//
	//
	//methods
	//
	//
	
	
	/**
	 * set the file name if one is not given initially
	 * @param fileName
	 */
	void setFileName(String fileName)
	{
		this.inputFile= new File(fileName);
	}
	
	/**
	 * sets initial values of the agent
	 */
	 void setup()
	{
		int firstInv=this.fileScan.nextInt();
		
		this.initialInventory=firstInv;
		this.currInventory=firstInv;
		this.totalInventory=firstInv;
		
		this.invCheckOne=this.fileScan.nextInt();
		
		this.percentage=this.fileScan.nextDouble();
		
		this.avgDays=this.fileScan.nextInt();
		
		this.avgBuy=this.fileScan.nextInt();
		
		this.invCheckTwo=this.fileScan.nextInt();
		
		this.checkTwoBuy=this.fileScan.nextInt();
		
		this.defaultBuy=this.fileScan.nextInt();
		
	}
	 
	 
	 /**
	  * purchasing inventory for an item
	  * @param amnt
	  * @param price
	  */
	 private void purchaseItem(int amnt, double price)
	 {
		 //update current inventory
		 this.currInventory+=amnt;
		 
		 //update total inventory
		 this.totalInventory+=amnt;
		 
		 //update total purchase prices
		 this.purchPrices+=(price);
		 
		 //update number of purchases
		 this.numPurchases++;
		 
	 }
	 


	 /**
	  * 
	  * @return total number of items purchased
	  */
	 private int getTotalItemsPurchased()
	 {
		 return this.totalInventory-this.initialInventory;
	 }
	 

	 /**
	  * read through 100 days of activity and determine purchasing 
	  */
	 void printPrices()
	 {
		 //variables for the amount used and amount purchased, used as a temporary place holder
		 //and updated daily
		 int amntUsed;
		 int amntPurchased;
		 
		 //read through each of the 100 days
		 for(int i=0;i<NUM_DAYS;i++)	
		 {
			 //set amntPurchased to 0
			 amntPurchased=0;
			 
			 
			//print read from file and print day number
			 System.out.printf("Day #: %d\n", this.fileScan.nextInt());
			 
			 //read next double and update the array of daily prices
			 this.prices[i]=this.fileScan.nextDouble();
			 
			 //read next int and update the amount used
			 amntUsed=this.fileScan.nextInt();
			 
			 //subtract the amount used from the current inventory
			 subtractUsed(amntUsed);
			 
			 //print the starting inventory for the day
			 System.out.printf("Starting Inventory: %d\n",this.currInventory);
			 
			 //determine items purchased for the day
			 amntPurchased=purchasing(i);
			 
			 //print items purchased and ending inventory for the day
			 System.out.printf("Bought %3d item at $%4.2f\n",amntPurchased,this.prices[i]);
			 System.out.printf("Ending Inventory: %3d\n",this.currInventory);
			 System.out.println();
			 
			 //update total end of day inventory 
			 this.endDayInv+=this.currInventory;
		 }
		 
		 //print final averages and total amount of items purchased
		 System.out.printf( "Average Daily Inventory: %-5.2f\n",calcAvgInv());
		 System.out.printf("Average Purchase Price: $%-5.2f\n",CalcAvgPurchPrice());
		 System.out.printf("Total Items Purchased: %-5d",getTotalItemsPurchased());
	 }
	 
	 /**
	  * 
	  * @param day
	  * @return temporal avg
	  */
	 private double calcAvg(int day)
	 {
		 //if it is the first day, the average is the original price
		 if(day==0)
		 {
			 this.tempAvg=this.prices[0];
			 return this.prices[0];
		 }
		 
		 //if not yet at the amount of days set by the parameters, use data from the days available
		 else if(day<this.avgDays)
		 {
			this.tempAvg=(this.tempAvg+((this.prices[day]-this.tempAvg)/(day+1)));
			return this.tempAvg;
		 }
		 
		 //calculate temporal average using the amount of days given in the original parameters
		 else
		 {
			this.tempAvg=(this.tempAvg+((this.prices[day]-this.tempAvg)/(this.avgDays)));
			return this.tempAvg;
		 }
		
	 }
	 /**
	  * updates the current inventory
	  * @param amntUsed
	  */
	 private void subtractUsed(int amntUsed)
	 {
		 //current inventory cannot be below 0
		 if(this.currInventory-amntUsed<0) 
		 {
			 this.currInventory=0;
		 }
		 
		 //update inventory
		 else
		 {
			 this.currInventory-=amntUsed;
		 }
	 }
	 
	 /**
	  * 
	  * @return average purchase price
	  */
	 private double CalcAvgPurchPrice()
	 {
		 return this.purchPrices/this.numPurchases;
	 }
	 
	 /**
	  * 
	  * @return average daily inventory
	  */
	 private double calcAvgInv()
	 {
		 return this.endDayInv/NUM_DAYS;
	 }
	 
	 /**
	  * 
	  * @param day
	  * @param dayAvgPrice
	  * @return amount purchased 
	  */
	 private int purchasing(int day)
	 {
		 int purchased=0;
		 
		//calculate the temporal average price for a given day
		 double dayAvgPrice=calcAvg(day);
		 
		 //if inventory is below a certain point and priced a certain percentage lower than the temporal 
		 //average, purchase a certain amount of the item
		 if((this.currInventory<this.invCheckOne) && (this.prices[day]<(this.percentage*dayAvgPrice)))
		 {
			 purchaseItem(avgBuy, this.prices[day]);
			 purchased+=avgBuy;
		 }
		 //if the inventory is below another certain point, purchase a certain amount
		 else if(currInventory<invCheckTwo)
		 {
			 purchaseItem(checkTwoBuy,this.prices[day]);
			 purchased+=checkTwoBuy;
		 }
		 //purchase a certai amount of the item by default if no other conditions are met
		 else
		 {
			 purchaseItem(defaultBuy,this.prices[day]);
			 purchased+=defaultBuy;
		 }
		 
		 return purchased;
	 }
	 
}
