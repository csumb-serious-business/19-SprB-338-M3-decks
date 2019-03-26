/* ------------------------------------------------------------------------- *\
Authors:    C Ahangama, J Asato, M Robertson, R Talmage
Class:      CST338
Assignment: M3 Decks of Cards
Date:       3/26/2019
\* ------------------------------------------------------------------------- */

import java.util.StringJoiner;

enum Suit {clubs, diamonds, hearts, spades}

public class Assig3 {
    public static void main(String[] args) {
        // test Card
        // init & print test cards
        Card card1 = new Card();
        System.out.printf("card1: %s (test: default card)%n", card1);

        Card card2 = new Card('2', Suit.hearts);
        System.out.printf("card2: %s (test: valid card)%n", card2);

        Card card3 = new Card('X', Suit.diamonds); // X -- invalid
        System.out.printf("card3: %s (test: invalid card)%n", card3);

        card2.set('X', Suit.clubs); // X -- invalid
        System.out.printf("card2: %s (test: valid -> invalid card)%n", card2);

        card3.set('T', Suit.diamonds);
        System.out.printf("card3: %s (test: invalid -> valid card)%n", card3);

        // test Hand
        // init 5 valid cards
        Card hCard1 = new Card('A', Suit.hearts);
        Card hCard2 = new Card('2', Suit.hearts);
        Card hCard3 = new Card('3', Suit.hearts);
        Card hCard4 = new Card('4', Suit.hearts);
        Card hCard5 = new Card('5', Suit.hearts);
        Card hCardX = new Card('X', Suit.hearts); // X -- invalid

        // init hand
        Hand hand = new Hand();

        // in a loop, takeCard for those 5 cards until Hand.MAX_CARDS met
        while (hand.getNumCards() < Hand.MAX_CARDS) {
            // try to add every card in each pass,
            // since takeCard guards against overfilling
            hand.takeCard(hCard1);
            hand.takeCard(hCard2);
            hand.takeCard(hCard3);
            hand.takeCard(hCard4);
            hand.takeCard(hCard5);
        }

        // print filled hand hand
        System.out.printf("filled hand: %s%n", hand);

        Card hCardCheck = hand.inspectCard(0); /// valid
        System.out.printf("inspect: %s (valid)%n", hCardCheck);

        hCardCheck = hand.inspectCard(-1); // invalid
        System.out.printf("inspect: %s (invalid)%n", hCardCheck);

        while (hand.getNumCards() > 0) {
            Card played = hand.playCard();
            System.out.printf("played card: %s%n", played);
        }

        // print emptied hand
        System.out.printf("emptied hand: %s%n", hand);

        //TODO: MBR -- test cases
    }
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
            return "\uFFFD\uFFFD"; // �� -- invalid card
        }

        return "" + this.getValue() + this.suitToUnicode();
    }

    private char suitToUnicode() {
        // note no breaks between case statements
        // since values are returned directly
        switch (this.getSuit()) {
            case clubs:
                return '\u2663'; // ♣
            case diamonds:
                return '\u2666'; // ♦
            case hearts:
                return '\u2665'; // ♥
            case spades:
                return '\u2660'; // ♠
            default:
                return '\uFFFD'; // � -- should never happen
        }
    }
}

class Hand {
    public static int MAX_CARDS = 50; // no 'monster arrays'
    private Card[] myCards; //---------/ also called myArray in assignment desc
    private int numCards; //-----------/ count of cards

    /**
     * Creates an empty hand
     */
    public Hand() {
        this.myCards = new Card[MAX_CARDS];
        this.numCards = 0;
    }

    /**
     * adds a card to the hand, usually from another play area, like a deck.
     *
     * @param card the card to add
     * @return true if card successfully taken
     */
    public boolean takeCard(Card card) {
        if (numCards < MAX_CARDS) {
            char valueChar = card.getValue();
            Suit suitVal = card.getSuit();
            Card takenCard = new Card(valueChar, suitVal);
            myCards[numCards++] = takenCard; //copies card to myCards
            return true;//return true if success
        } else {
            return false;
        }
    }

    //   playCard(): Card       - plays a card to the table or player.
    //removes & returns card from tail of array.
    public Card playCard() {
        if (numCards >= 1) {
            numCards--;
            Card played = myCards[numCards];
            myCards[numCards] = null; // delete by removing reference
            return played;
        } else {
            return new Card('X', null);
        }
    }

    //   void resetHand(): unit    - remove all cards from the hand (use simplest way)
    //removes all cards from the hand
    public void resetHand() {
        numCards = 0;
        myCards = new Card[MAX_CARDS];
    }

    //   numCards accessor
    public int getNumCards() {
        return numCards;
    }

    //   inspectCard(k: int): Card - returns the card at a given position or a Card with errorFlag = true if k is invalid
    //accessor for an individual card
    //returns a card with errorFlag=true if k is bad
    Card inspectCard(int k) {
        int myCardsLength = myCards.length;
        if (k >= 0 && k <= numCards && k <= myCardsLength) {
            return myCards[k];//return the card at k position
        } else {
            return new Card('X', Suit.diamonds);//returns a card with errorFlag
        }
    }

    //   toString(): String   - for each card in myCards: card.toString()
    public String toString() {
        StringJoiner joiner = new StringJoiner(", ", "{ ", " }");
        if (numCards <= 0) {
            return joiner.add("empty").toString();
        }
        for (int i = 0; i < numCards; i++) {
            joiner.add(myCards[i].toString());
        }
        return joiner.toString();

    }
}

class Deck {
    //TODO: R Talmage
}