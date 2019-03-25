/* ------------------------------------------------------------------------- *\
Authors:    C Ahangama, J Asato, M Robertson, R Talmage
Class:      CST338
Assignment: M3 Decks of Cards
Date:       3/26/2019
\* ------------------------------------------------------------------------- */

enum Suit {clubs, diamonds, hearts, spades}

public class Assig3 {
   public static void main(String[] args) {
      // test Card
      //init & print test cards
      //      Card card1 = new Card();
      //      System.out.printf("card1: %s (test: default card)%n", card1);
      //
      //      Card card2 = new Card('2', Suit.hearts);
      //      System.out.printf("card2: %s (test: valid card)%n", card2);
      //
      //      Card card3 = new Card('X', Suit.diamonds); // X -- invalid
      //      System.out.printf("card3: %s (test: invalid card)%n", card3);
      //
      //      card2.set('X', Suit.clubs); // X -- invalid
      //      System.out.printf("card2: %s (test: valid -> invalid card)%n", card2);
      //
      //      card3.set('J', Suit.diamonds);
      //      System.out.printf("card3: %s (test: invalid -> valid card)%n", card3);

      //test Hand
      //      init 5 valid cards
      Card cardHand1 = new Card('A', Suit.hearts);
      Card cardHand2 = new Card('2', Suit.diamonds);
      Card cardHand3 = new Card('3', Suit.clubs);
      Card cardHand4 = new Card('4', Suit.spades);
      Card cardHand5 = new Card('5', Suit.spades);
      Card cardHand6 = new Card('X', Suit.diamonds); // X -- invalid

      //      init hand
      Hand hand=new Hand();
      System.out.println(hand);
      //in a loop, takeCard for those 5 cards until Hand.MAX_CARDS met
      //      print hand (toString)
      //TODO not sure how to put this in a loop
      System.out.print(hand.takeCard(cardHand1)+"\n");
      System.out.print(hand.takeCard(cardHand2)+"\n");
      System.out.print(hand.takeCard(cardHand3)+"\n");
      System.out.print(hand.takeCard(cardHand4)+"\n");
      System.out.print(hand.takeCard(cardHand5)+"\n");
      System.out.print(hand.takeCard(cardHand6)+"\n");
      System.out.println("Print hand: "+hand.toString());//print hand (toString)
      //      inspectCard (valid)
      //      inspectCard (invalid)
      for(int i=0;i<7;i++) {
         System.out.println("Inspect card: "+hand.inspectCard(i));
      }
      //in a loop, until hand is empty (numCards == 0)
      //          playCard & print that card (toString)
      while(hand.getNumCards()>0) {
         System.out.println(hand.playCard());
         System.out.println("Print hand: "+hand.toString());//print hand (toString)
      }

      //      print (empty) hand (toString)
      System.out.println("Hand is now empty: "+hand.toString());//print hand (toString)
   }
   //TODO: MBR -- test cases
}


class Card {
   private static final String VALID_VALUE_CHARS = "A23456789TJQK";
   private char value;
   private Suit suit;
   private boolean errorFlag;

   public Card() {
      this('A', Suit.spades);
   }

   public Card(char value, Suit suit) {
      set(value, suit);
   }

   private static boolean isValid(char value, Suit suit) {
      value = Character.toUpperCase(value);
      // value in valid chars AND suit is not null
      return (VALID_VALUE_CHARS.indexOf(value) != -1 && suit != null);
   }

   public boolean set(char value, Suit suit) {
      if (isValid(value, suit)) {
         this.suit = suit;
         this.value = value;
         this.errorFlag = false;

      } else {
         this.errorFlag = true;
      }
      return this.errorFlag;
   }

   /**
    * @return the Card's suit
    */
   public Suit getSuit() {
      return suit;
   }

   /**
    * @return the Card's errorFlag
    */
   public boolean getErrorFlag() {
      return errorFlag;
   }

   /**
    * @return the Card's value
    */
   public char getValue() {
      return value;
   }

   @Override
   public boolean equals(Object obj) {
      if (obj instanceof Card) {
         Card other = (Card) obj;
         return this.equals(other);
      }
      return false;
   }

   public boolean equals(Card card) {
      // errorFlag (invalid) cards can be checked also (not sure if that is OK)
      // they are technically evaluable, but not useful for the app
      return card.getSuit() == this.getSuit() &&
            card.getValue() == this.getValue() &&
            card.getErrorFlag() == this.getErrorFlag();
   }

   @Override
   public String toString() {
      if (this.getErrorFlag()) {
         return "ï¿½ï¿½"; // invalid card
      }
      String result = Character.toString(this.getValue());

      result += this.suitToUnicode();
      return result;
   }

   private char suitToUnicode() {
      // note no breaks between case statements
      // since values are returned directly
      switch (this.getSuit()) {
      case clubs:
         //return 'â™£';
         return '♣';
      case diamonds:
         //return 'â™¦';
         return '♦';
      case hearts:
         //return 'â™¥';
         return '♥';
      case spades:
         //return 'â™ ';
         return '♠';
      default:
         //return 'ï¿½'; // should never happen
         return '�'; 
      }
   }
}

class Hand {
   //### static
   public static int MAX_CARDS=50;//   public MAX_CARDS: int - 100 // no 'monster arrays'
   //### fields
   private Card[] myCards;   //   myCards: array[Card] - //also called myArray in assignment desc
   private int numCards;//   numCards: int        - count of cards

   //### methods
   //   constructor()        - default
   //Initializes myCards array to max length
   //Initializes numCards to 0
   public Hand(){
      this.myCards=new Card[MAX_CARDS];
      this.numCards=0;
   }
   //   takeCard(): bool      - gets a card from the deck, copies card to myCards (next pos), returns true if success
   //adds a card to the next available position in the myCards array
   //returns true if it worked
   public boolean takeCard(Card card) {
      if(numCards<MAX_CARDS) {
         char valueChar=card.getValue();
         Suit suitVal=card.getSuit();
         Card takenCard=new Card(valueChar,suitVal);//get a card from the deck
         myCards[numCards++]=takenCard;//copies card to myCards
         return true;//return true if success
      }
      else {
         return false;
      }
   }
   //   playCard(): Card       - plays a card to the table or player. 
   //removes & returns card from tail of array.
   public Card playCard() {
      Card playedCard=myCards[numCards];
      if(numCards>=1) {
         myCards[numCards]=null;//delete by removing reference
         numCards--;
      }
      System.out.print("Played: ");
      return playedCard;
   }
   //   void resetHand(): unit    - remove all cards from the hand (use simplest way)
   //removes all cards from the hand
   public void resetHand() {
      numCards=0;
      myCards=new Card[MAX_CARDS];
   }
   
   //   numCards accessor
   public int getNumCards(){
      return numCards;
   }

   //   inspectCard(k: int): Card - returns the card at a given position or a Card with errorFlag = true if k is invalid
   //accessor for an individual card
   //returns a card with errorFlag=true if k is bad
   Card inspectCard(int k) {
      int myCardsLength=myCards.length;
      if(k<=numCards || k<=myCardsLength) {
         return myCards[k];//return the card at k position
      }
      else {
         return new Card('X', Suit.diamonds);//returns a card with errorFlag
      }
   }
   //   toString(): String   - for each card in myCards: card.toString()
   public String toString(){
      String hand = "Hand= ";
      for(int i=0;i<numCards;i++) {
         hand+=myCards[i]+",";
      }
      return hand;
   }
}
class Deck {
   //TODO: R Talmage
}