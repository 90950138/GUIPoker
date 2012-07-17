/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package GUIPackage;

import java.util.*;
import java.lang.*;

/**
 *
 * @author senatori
 */
class VideoPoker {

    // default constant values
    private static final int defaultBalance=100;
    private static final int fiveCards=5;

    // default constant payout value and fiveCardsHand types
    private static final int[] multipliers={1,2,3,5,6,9,25,50,250};
    private static final String[] goodHandTypes={
	  "Royal Pair" , "Two Pairs" , "Three of a Kind", "Straight", "Flush",
	  "Full House", "Four of a Kind", "Straight Flush", "Royal Flush",
          "You Lose"};

    // must use only one deck
    private static final Deck deck = new Deck();

    // holding current poker hand, balance, bet
    private ArrayList<Card> fiveCardsHand;
    private int balance;
    private int bet;


    /** default constructor, set balance = defaultBalance */
    public VideoPoker()
    {
	this(defaultBalance);
    }

    /** constructor, set given balance */
    public VideoPoker(int balance)
    {
	this.balance= balance;
        deck.shuffle();
        fiveCardsHand = deck.deal(fiveCards);

    }

 //----------------------------------------------------------------------------
    //The Following Functions have been added for my MyGUI class to have access


    /** Task: Resets, reshuffles, and redeals cards into hand */
    public void resetHand()
    {
        deck.reset();
        deck.shuffle();
        fiveCardsHand= deck.deal(fiveCards);
    }

    /** Task: Replaces 1 card at a particular index in the fiveCardsHand list
     * <p> Removes card var in respective index, deal itself 1 card and then ads
     * it to the specified index
     *
     *  @param index  the index/particular card that needs to be replaced
     */
    public void replaceCard(int index)
    {
        Card c; ArrayList<Card> tmp;

        fiveCardsHand.remove(index);
        tmp= deck.deal(1);
        c= tmp.get(0);
        fiveCardsHand.add(index, c);
    }


     /** Task: Determines the cards actual rank
     * <p> Looks at the cards pip
     *
     *  @param index  the index/particular card in the ArrayList whose rank
      * needs to be determined
      * @return int -the value/rank of the card <p>
      * @return 0 -if function call fails
     */
    public int getRankValue(int index)  
    {
        //String value;   0   1   2   3   4   5   6   7   8   9    10  11  12
        String[] Rank = {"A","2","3","4","5","6","7","8","9","10","J","Q","K"};
        String Pip=(fiveCardsHand.get(index)).getRank();
        int n;
        for(n=0; n<= 12; n++)
            {
                if(Pip.equals(Rank[n]))
                {
                    return n;
                }

            }

        return 0;
    }

    /** Task: Accesses the Suite of the card
     *
     *  @param index  the index/particular card in the ArrayList whose Suite
      * needs to be determined
      * @return String -the Suite of the card
     */
    public String getHandSuite(int index)
    {
        String Suite=(fiveCardsHand.get(index)).getSuit();
        return Suite;
    }

    /** Task: Determines if current hand is a winner, and if so, what kind.
     * <p> Royal Flush, Straight Flush, Full House, Royal Pair, etc
     *
     * @param index  the integer returned from checkHands() function
     * @return String -the name of the game type i.e. "Flush", "Royal Pair"
     * @see checkHands()
     */
    public String getHandScore(int index)
    {
        return goodHandTypes[index];
    }
    /** Task: assigns amount to bet
     *
     * @param amountbet  the amount you want to bet
     */
    public void setBet(int amountbet)
    {
        bet= amountbet;
    }
    /** Task: gets gambling balance
     * @return int - account balance
     */
    public int getBalance()
    {
        return balance;
    }
 

    /** Check current fiveCardsHand using multipliers and goodHandTypes arrays
     *  Must print yourHandType (default="Sorry, you lost") at the end of function.
     *  This can be checked by testCheckHands() and main() method.
     * @return integer- the respective index for goodHandTypes array this change
     * had to be made for my GUI
     */
    public int checkHands()  //modified to return goodHandType index for GUI call
    {
	// implement this method!
        String Suite;
        String[] tmphand= new String[5];
        int[] value= new int[5];

        int i; int n; int tmp;
        //--------------------This Portion determines Card Rank------------------
        for(i=0; i<fiveCards; i++)
        {


            tmphand[i]= getHandSuite(i);
            value[i]= getRankValue(i);




        }
        //------------------This Portion sorts--------------------------------
        //Used insertion sort to sort card rank/value in int value array and
        //respective suite via tmphand string array
        for(i=0;i <fiveCards; i++)
        {
            for(n=i+1; n < 5; n++)
            {
                if(value[i]> value[n])
                {
                    tmp= value[i]; Suite= tmphand[i];
                    value[i]= value[n]; tmphand[i]= tmphand[n];
                    value[n]= tmp; tmphand[n]= Suite;

                }
            }
            //counter++
        }

        //--------------------------------------------------------------------

        //Checks if the hand of cards are of same type aka flush
        if((tmphand[0].equals(tmphand[1])) && (tmphand[0].equals(tmphand[2]))
                && (tmphand[0].equals(tmphand[3]))&&
                (tmphand[0].equals(tmphand[4])))
        {
            //ROYAL FLUSH
            if((value[0]==0) && (value[1]==9) && (value[2]==10) && (value[3]==11)
                    && (value[4]==12))
            {
                //System.out.println(goodHandTypes[8]); //prints out hand type
                balance+= bet*multipliers[8];
                return 8;
            }

            //Straight Flush
            else if(((value[4] - 1) == value[3]) && ((value[3] - 1) == value[2])
                    &&
                    ((value[2]-1) == value[1])&& ((value[1]-1)== value[0]))
            {
                //System.out.println(goodHandTypes[7]);
                balance+= bet*multipliers[7];
                return 7;
            }
            //Regular flush
            else
            {
                //System.out.println(goodHandTypes[4]);
                balance+= bet*multipliers[4];
                return 4;
            }

        }
        //----------------------------------------------------------------------
        //Straight
        else if(((value[4] - 1) == value[3]) && ((value[3] - 1) == value[2])
                    &&
                    ((value[2]-1) == value[1])&& ((value[1]-1)== value[0]))
        {
            //System.out.println(goodHandTypes[3]);
            balance+= bet*multipliers[3];
            return 3;
        }
        //----------------------------------------------------------------------
        //FOUR OF A Kind
        //checks for scenarious such as J J J J 3 or J 3 3 3 3
        else if(((value[4]==value[3]) && (value[3]==value[2]) &&
                (value[2]==value[1])) || ((value[3]==value[2]) &&
                (value[2]==value[1]) && (value[1]==value[0])) )
        {
            //System.out.println(goodHandTypes[6]);
            balance+= bet*multipliers[6];
            return 6;
        }

        //FULL HOUSE
        else if(((value[4]==value[3]) && (value[3]==value[2]) &&
                (value[1]==value[0])) || ((value[4]==value[3]) &&
                (value[2]==value[1]) && (value[1]==value[0])) )
        {
            //System.out.println(goodHandTypes[5]);
            balance+= bet*multipliers[5];
            return 5;
        }

        //THREE of a Kind
        else if(((value[4]==value[3]) && (value[3]==value[2]) ) ||
                ((value[3]==value[2]) && (value[2]==value[1])) ||
                ((value[2]==value[1]) && (value[1]==value[0]) ))
        {
            //System.out.println(goodHandTypes[2]);
            balance+= bet*multipliers[2];
            return 2;
        }
        //TWO Pair
        else if(((value[4]==value[3]) && (value[2]==value[1]) ) ||
                ((value[3]==value[2]) && (value[1]==value[0]))||
                ((value[4]==value[3]) && (value[1]==value[0]) ))
        {
            //System.out.println(goodHandTypes[1]);
            balance+= bet*multipliers[1];
            return 1;
        }
        //Royal Pair
        else if(((value[4]==value[3]) &&((value[3]> 9)||(value[3]==0))) ||
                ((value[3]==value[2]) &&((value[2]> 9)||(value[2]==0))) ||
                ((value[2]==value[1]) &&((value[1]> 9)||(value[1]==0))) ||
                ((value[1]==value[0]) &&((value[0]> 9)||(value[0]==0))))
        {
            //System.out.println(goodHandTypes[0]);
            balance+= bet*multipliers[0];
            return 0;
        }

        else
        {
            //System.out.println("You Lose");
            balance= balance - bet;
            return 9;
        }

    }


    /*************************************************
     *   add new private methods here ....
     *
     *************************************************/


    public void play()
    {

     /** The main algorithm for single player poker game
     *
     * Steps:
     * 		showPayoutTable()
     *
     * 		++
     * 		show balance, get bet
     *		verify bet value, update balance
     *		reset deck, shuffle deck,
     *		deal cards and display cards
     *		ask for replacement card positions
     *		replace cards
     *		check hands, display proper messages
     *		update balance if there is a payout
     *		if balance = O:
     *			end of program
     *		else
     *			ask if the player wants to play a new game
     *			if the answer is "no" : end of program
     *			else : showPayoutTable() if user wants to see it
     *			goto ++
     */

	// implement this method!
        //fiveCardsHand= deck.deal(fiveCards);
        //fiveCardsHand.checkHands();
    }

    /** Do not modify this. It is used to test checkHands() method
     *  checkHands() should print  your current hand type
     */
    public void testCheckHands()
    {
    	fiveCardsHand = new ArrayList<Card>();

	// set Royal Flush
	fiveCardsHand.add(new Card("A","Spades"));
	fiveCardsHand.add(new Card("10","Spades"));
	fiveCardsHand.add(new Card("Q","Spades"));
	fiveCardsHand.add(new Card("J","Spades"));
	fiveCardsHand.add(new Card("K","Spades"));
	System.out.println(fiveCardsHand);
    	checkHands();
	System.out.println("-----------------------------------");

	// set Straight Flush
	fiveCardsHand.set(0,new Card("9","Spades"));
	System.out.println(fiveCardsHand);
    	checkHands();
	System.out.println("-----------------------------------");

	// set Straight
	fiveCardsHand.set(4, new Card("8","Diamonds"));
	System.out.println(fiveCardsHand);
    	checkHands();
	System.out.println("-----------------------------------");

	// set Flush
	fiveCardsHand.set(4, new Card("5","Spades"));
	System.out.println(fiveCardsHand);
    	checkHands();
	System.out.println("-----------------------------------");

	//  "Royal Pair" , "Two Pairs" , "Three of a Kind", "Straight", "Flush	",
	 // "Full House", "Four of a Kind", "Straight Flush", "Royal Flush" };

	// set Four of a Kind
	fiveCardsHand.clear();
	fiveCardsHand.add(new Card("8","Spades"));
	fiveCardsHand.add(new Card("8","Clubs"));
	fiveCardsHand.add(new Card("Q","Spades"));
	fiveCardsHand.add(new Card("8","Diamonds"));
	fiveCardsHand.add(new Card("8","Hearts"));
	System.out.println(fiveCardsHand);
    	checkHands();
	System.out.println("-----------------------------------");

	// set Three of a Kind
	fiveCardsHand.set(4, new Card("J","Spades"));
	System.out.println(fiveCardsHand);
    	checkHands();
	System.out.println("-----------------------------------");

	// set Full House
	fiveCardsHand.set(2, new Card("J","Diamonds"));
	System.out.println(fiveCardsHand);
    	checkHands();
	System.out.println("-----------------------------------");

	// set Two Pairs
	fiveCardsHand.set(1, new Card("9","Diamonds"));
	System.out.println(fiveCardsHand);
    	checkHands();
	System.out.println("-----------------------------------");

	// set Royal Pair
	fiveCardsHand.set(0, new Card("3","Diamonds"));
	System.out.println(fiveCardsHand);
    	checkHands();
	System.out.println("-----------------------------------");

	// non Royal Pair
	fiveCardsHand.set(2, new Card("3","Spades"));
	System.out.println(fiveCardsHand);
    	checkHands();
	System.out.println("-----------------------------------");
    }

//    /* Quick testCheckHands() */
//    public static void main(String args[])
//    {
//	VideoPoker mypokergame = new VideoPoker();
//	mypokergame.testCheckHands();
//        mypokergame.showPayoutTable();
//    }
}
