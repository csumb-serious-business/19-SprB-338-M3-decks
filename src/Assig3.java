/* ------------------------------------------------------------------------- *\
Authors:    C Ahangama, J Asato, M Robertson, R Talmage
Class:      CST338
Assignment: M3 Decks of Cards
Date:       3/26/2019
\* ------------------------------------------------------------------------- */

import java.util.Scanner;
import java.util.StringJoiner;

enum Suit {clubs, diamonds, hearts, spades}

public class Assig3 {
    private static Scanner scanner;

    /**
     * Prompts the user for number of players (input)
     * note: continues prompting until number is valid
     *
     * @return user provided player count for a card game
     */
    private static int askPlayerCount() {
        if (Assig3.scanner == null) {
            Assig3.scanner = new Scanner(System.in);
        }

        while (true) {
            System.out.println("How many players are joining this game?");
            int players = scanner.nextInt();
            if (0 < players && players <= 10) {
                return players;
            }
            System.out.printf("%d is not a valid number of players, try again.\n", players);
        }
    }

    private static void dealDeckIntoHands(Deck deck, Hand[] hands) {
        //deal cards among the hands until deck is empty
        while (true) {
            for (int h = 0; h < hands.length; h++) {
                if (hands[h] == null) {
                    hands[h] = new Hand();
                }

                if (deck.getTopCard() >= 0) {
                    Card dealt = deck.dealCard();
                    hands[h].takeCard(dealt);
                } else {
                    return;
                }
            }
        }
    }


    public static void main(String[] args) {
        System.out.println("==== Card Tests ================================");
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

        System.out.println("==== Hand Tests ================================");
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

        System.out.println("==== Deck Tests ================================");
        // init deck with 2 packs
        Deck deck = new Deck(2);

        // in a loop, until deck is empty
        // dealCard & print that card
        System.out.println("--- 2 packs unshuffled -------------------------");
        while (deck.getTopCard() >= 0) {
            Card dealt = deck.dealCard();
            System.out.printf("dealt card: %s%n", dealt);
        }

        // reset (init) deck with 2 packs
        deck.init(2);

        System.out.println("--- 2 packs shuffled ---------------------------");
        // shuffle deck
        deck.shuffle();

        // in a loop, until deck is empty
        // dealCard & print that card
        while (deck.getTopCard() >= 0) {
            Card dealt = deck.dealCard();
            System.out.printf("dealt card: %s%n", dealt);
        }

        System.out.println("--- 1 pack unshuffled --------------------------");
        // reset (init) deck with 1 pack
        deck.init();

        // in a loop, until deck is empty
        // dealCard & print that card
        // dealCard & print that card
        while (deck.getTopCard() >= 0) {
            Card dealt = deck.dealCard();
            System.out.printf("dealt card: %s%n", dealt);
        }

        System.out.println("--- 1 pack shuffled ----------------------------");
        // reset (init) deck
        deck.init();

        // shuffle deck
        deck.shuffle();

        // in a loop, until deck is empty
        // dealCard & print that card
        while (deck.getTopCard() >= 0) {
            Card dealt = deck.dealCard();
            System.out.printf("dealt card: %s%n", dealt);
        }

        System.out.println("==== Deck + Hand Tests =========================");
        // ask user for # of players (1-10), until valid
        int players = Assig3.askPlayerCount();

        System.out.println("--- 1 pack unshuffled --------------------------");
        //new deck with 1 pack
        Deck dhDeck = new Deck();
        Hand[] playerHands = new Hand[players];

        //deal cards among the hands until deck is empty
        dealDeckIntoHands(dhDeck, playerHands);

        //for each hand, display their contents.
        for (int h = 0; h < playerHands.length; h++) {
            System.out.printf("hand #%d: %s\n", h, playerHands[h]);
        }


        System.out.println("--- 1 pack shuffled ----------------------------");
        //reset the deck
        dhDeck.init();

        //reset each hand
        for (Hand playerHand : playerHands) {
            playerHand.resetHand();
        }

        //shuffle the deck
        dhDeck.shuffle();

        //deal cards among the hands until deck is empty
        dealDeckIntoHands(dhDeck, playerHands);

        //for each hand, display their contents.
        for (int h = 0; h < playerHands.length; h++) {
            System.out.printf("hand #%d: %s\n", h, playerHands[h]);
        }
    }
}

/**
 * Represents a single playing card with a suit and value
 */
class Card {
    public static final String VALID_VALUE_CHARS = "A23456789TJQK";
    private char value;
    private Suit suit;
    private boolean errorFlag;

    /**
     * Create a default card, an Ace of Spades
     */
    public Card() {
        this('A', Suit.spades);
    }

    /**
     * Create a card with a given value and suit
     *
     * @param value the card's value
     * @param suit  the card's suit
     */
    public Card(char value, Suit suit) {
        set(value, suit);
    }

    /**
     * Evaluates whether a given value/suit combination results in a valid card
     *
     * @param value the value to check
     * @param suit  the suit to check
     * @return true if the combination of value/suit is valid for a card
     */
    private static boolean isValid(char value, Suit suit) {
        value = Character.toUpperCase(value);
        // value in valid chars AND suit is not null
        return (VALID_VALUE_CHARS.indexOf(value) != -1 && suit != null);
    }

    /**
     * Assigns new values for this card's value and suit
     *
     * @param value the new value for this card
     * @param suit  the new suit for this card
     * @return true if the set operation was successful
     */
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

    /**
     * Checks whether this card is equivalent to another card (has the same suit and value)
     *
     * @param card the other card to evaluate against
     * @return true if the two cards are equivalent
     */
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

    /**
     * @return the corresponding Unicode character for a given suit
     */
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

/**
 * Represents a hand of playing cards held by a single player
 * It can hold several cards
 */
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
     * Adds a card to the hand, usually from another play area, like a deck.
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

    /**
     * Plays the next card from this hand,
     * removing it from the hand in the process
     *
     * @return the next card from this hand
     */
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

    /**
     * Resets this hand to its initial (empty) state
     */
    public void resetHand() {
        numCards = 0;
        myCards = new Card[MAX_CARDS];
    }

    //   numCards accessor

    /**
     * @return the number of cards currently in this hand
     */
    public int getNumCards() {
        return numCards;
    }

    /**
     * Fetches the card in a given position in the hand without removing it.
     *
     * @param k the position to fetch the card from
     * @return the card from the given position in the hand
     * -OR- an invalid card if that position is invalid or unpopulated
     */
    Card inspectCard(int k) {
        int myCardsLength = myCards.length;
        if (k >= 0 && k <= numCards && k <= myCardsLength) {
            return myCards[k];//return the card at k position
        } else {
            return new Card('X', Suit.diamonds);//returns a card with errorFlag
        }
    }

    @Override
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

/**
 * Represents the source of playing cards in a game
 */
class Deck {
    private static final int MAX_PACKS = 6;
    private static final int CARDS_PER_PACK = 52;
    public static final int MAX_CARDS = MAX_PACKS * CARDS_PER_PACK;
    private static Card[] masterpack;

    private Card[] cards;
    private int numPacks;
    private int topCard;

    /**
     * Creates a new deck using a given number of packs
     *
     * @param numPacks the number of packs within this deck
     */
    public Deck(int numPacks) {
        this.init(numPacks);
    }

    /**
     * Creates a new deck with a single pack
     */
    public Deck() {
        this.init();
    }

    /**
     * Populates the reusable master pack for decks
     * only if it is empty.
     */
    private static void allocateMasterPack() {
        if (Deck.masterpack != null) {
            return;
        }
        Deck.masterpack = new Card[CARDS_PER_PACK];
        int c = 0;

        for (char value : Card.VALID_VALUE_CHARS.toCharArray()) {
            for (Suit suit : Suit.values()) {
                masterpack[c] = new Card(value, suit);
                c++;
            }
        }
    }

    /**
     * Refreshes this deck, discarding all current cards (if any)
     * and populating it with fresh packs.
     *
     * @param numPacks the number of packs to refresh with
     */
    public void init(int numPacks) {
        // init master pack if not yet populated
        allocateMasterPack();

        // enforce pack limit
        if (numPacks > MAX_PACKS) {
            numPacks = MAX_PACKS;
            System.out.printf("Maximum number of packs exceeded, set to maximum: %d%n", numPacks);
        }

        this.numPacks = numPacks;

        int numCards = numPacks * CARDS_PER_PACK;
        this.cards = new Card[numCards];


        // for the desired number of packs, copy the master pack into packs
        for (int i = 0; i < numPacks; i++) {
            System.arraycopy(Deck.masterpack, 0,
                    this.cards, i * CARDS_PER_PACK,
                    Deck.masterpack.length);
        }

        // set the position of the top card
        this.topCard = numCards - 1; // zero-indexed
    }

    /**
     * Refreshes this deck, discarding all current cards (if any)
     * and populating it with a fresh pack.
     */
    public void init() { //reinitializes an existing Deck object with one pack
        this.init(1);
    }

    /**
     * Removes the top card of the deck and returns it
     *
     * @return the top card from the deck
     */
    public Card dealCard() { //returns the top card of the deck and removes it
        Card dealtCard = cards[topCard];
        cards[topCard] = null;
        topCard--;
        return dealtCard;
    }

    /**
     * Fetches the top card from this deck without removing it
     *
     * @return the top card in this deck
     */
    public int getTopCard() { //returns the topCard integer
        return this.topCard;
    }

    /**
     * Fetches the card at a given position within the deck
     * -OR- an invalid card if that position is not populated
     * or the position is otherwise invalid
     * <p>
     * does not remove the card from the deck
     *
     * @param k the position of the card in the deck to inspect
     * @return the card at the given position, or and invalid card if not found
     */
    public Card inspectCard(int k) { //takes an integer and accesses the deck at that index and returns a card object
        if (k >= 0 && k <= topCard) {
            return cards[k];
        } else return new Card('X', Suit.diamonds); //returns a card with errorFlag if index is out of range
    }

    /**
     * Exchanges the card in one position with the card in another position
     *
     * @param cardA the position of a card to swap
     * @param cardB the position of another card to swap
     */
    private void swap(int cardA, int cardB) { //helper function for shuffle, takes two ints and swaps the cards at those indexes
        if (cardA == cardB) {
            return;
        }

        Card tempCard = cards[cardA];
        cards[cardA] = cards[cardB];
        cards[cardB] = tempCard;
    }

    /**
     * Randomizes the order of the cards within this deck
     */
    public void shuffle() { //
        int numCards = this.topCard + 1;
        int shuffleSteps = numCards * 25;
        for (int i = 0; i < shuffleSteps; i++) {
            int cardA = (int) (Math.random() * numCards);
            int cardB = (int) (Math.random() * numCards);
            swap(cardA, cardB);
        }
    }
}

/* --- RUN OUTPUT ---------------------------------------------------------- *\
==== Card Tests ================================
card1: A♠ (test: default card)
card2: 2♥ (test: valid card)
card3: �� (test: invalid card)
card2: �� (test: valid -> invalid card)
card3: T♦ (test: invalid -> valid card)
==== Hand Tests ================================
filled hand: { A♥, 2♥, 3♥, 4♥, 5♥, A♥, 2♥, 3♥, 4♥, 5♥, A♥, 2♥, 3♥, 4♥, 5♥, A♥, 2♥, 3♥, 4♥, 5♥, A♥, 2♥, 3♥, 4♥, 5♥, A♥, 2♥, 3♥, 4♥, 5♥, A♥, 2♥, 3♥, 4♥, 5♥, A♥, 2♥, 3♥, 4♥, 5♥, A♥, 2♥, 3♥, 4♥, 5♥, A♥, 2♥, 3♥, 4♥, 5♥ }
inspect: A♥ (valid)
inspect: �� (invalid)
played card: 5♥
played card: 4♥
played card: 3♥
played card: 2♥
played card: A♥
played card: 5♥
played card: 4♥
played card: 3♥
played card: 2♥
played card: A♥
played card: 5♥
played card: 4♥
played card: 3♥
played card: 2♥
played card: A♥
played card: 5♥
played card: 4♥
played card: 3♥
played card: 2♥
played card: A♥
played card: 5♥
played card: 4♥
played card: 3♥
played card: 2♥
played card: A♥
played card: 5♥
played card: 4♥
played card: 3♥
played card: 2♥
played card: A♥
played card: 5♥
played card: 4♥
played card: 3♥
played card: 2♥
played card: A♥
played card: 5♥
played card: 4♥
played card: 3♥
played card: 2♥
played card: A♥
played card: 5♥
played card: 4♥
played card: 3♥
played card: 2♥
played card: A♥
played card: 5♥
played card: 4♥
played card: 3♥
played card: 2♥
played card: A♥
emptied hand: { empty }
==== Deck Tests ================================
--- 2 packs unshuffled -------------------------
dealt card: K♠
dealt card: K♥
dealt card: K♦
dealt card: K♣
dealt card: Q♠
dealt card: Q♥
dealt card: Q♦
dealt card: Q♣
dealt card: J♠
dealt card: J♥
dealt card: J♦
dealt card: J♣
dealt card: T♠
dealt card: T♥
dealt card: T♦
dealt card: T♣
dealt card: 9♠
dealt card: 9♥
dealt card: 9♦
dealt card: 9♣
dealt card: 8♠
dealt card: 8♥
dealt card: 8♦
dealt card: 8♣
dealt card: 7♠
dealt card: 7♥
dealt card: 7♦
dealt card: 7♣
dealt card: 6♠
dealt card: 6♥
dealt card: 6♦
dealt card: 6♣
dealt card: 5♠
dealt card: 5♥
dealt card: 5♦
dealt card: 5♣
dealt card: 4♠
dealt card: 4♥
dealt card: 4♦
dealt card: 4♣
dealt card: 3♠
dealt card: 3♥
dealt card: 3♦
dealt card: 3♣
dealt card: 2♠
dealt card: 2♥
dealt card: 2♦
dealt card: 2♣
dealt card: A♠
dealt card: A♥
dealt card: A♦
dealt card: A♣
dealt card: K♠
dealt card: K♥
dealt card: K♦
dealt card: K♣
dealt card: Q♠
dealt card: Q♥
dealt card: Q♦
dealt card: Q♣
dealt card: J♠
dealt card: J♥
dealt card: J♦
dealt card: J♣
dealt card: T♠
dealt card: T♥
dealt card: T♦
dealt card: T♣
dealt card: 9♠
dealt card: 9♥
dealt card: 9♦
dealt card: 9♣
dealt card: 8♠
dealt card: 8♥
dealt card: 8♦
dealt card: 8♣
dealt card: 7♠
dealt card: 7♥
dealt card: 7♦
dealt card: 7♣
dealt card: 6♠
dealt card: 6♥
dealt card: 6♦
dealt card: 6♣
dealt card: 5♠
dealt card: 5♥
dealt card: 5♦
dealt card: 5♣
dealt card: 4♠
dealt card: 4♥
dealt card: 4♦
dealt card: 4♣
dealt card: 3♠
dealt card: 3♥
dealt card: 3♦
dealt card: 3♣
dealt card: 2♠
dealt card: 2♥
dealt card: 2♦
dealt card: 2♣
dealt card: A♠
dealt card: A♥
dealt card: A♦
dealt card: A♣
--- 2 packs shuffled ---------------------------
dealt card: T♦
dealt card: 2♥
dealt card: 6♥
dealt card: 9♠
dealt card: J♠
dealt card: 4♣
dealt card: 6♠
dealt card: 3♥
dealt card: 3♣
dealt card: 8♥
dealt card: 3♠
dealt card: 5♣
dealt card: 5♦
dealt card: 7♠
dealt card: 5♠
dealt card: 4♥
dealt card: A♠
dealt card: 7♦
dealt card: 2♣
dealt card: A♠
dealt card: 7♦
dealt card: 8♥
dealt card: Q♠
dealt card: 4♠
dealt card: T♣
dealt card: 9♣
dealt card: 6♣
dealt card: 8♦
dealt card: 4♦
dealt card: T♥
dealt card: Q♣
dealt card: J♣
dealt card: Q♠
dealt card: T♣
dealt card: 5♠
dealt card: K♦
dealt card: 5♦
dealt card: 7♣
dealt card: K♥
dealt card: K♠
dealt card: J♦
dealt card: 3♠
dealt card: K♣
dealt card: A♦
dealt card: 2♥
dealt card: A♣
dealt card: 4♠
dealt card: 4♥
dealt card: 2♣
dealt card: 9♦
dealt card: 5♥
dealt card: 6♦
dealt card: 8♣
dealt card: 4♦
dealt card: 5♣
dealt card: 8♠
dealt card: 3♣
dealt card: 8♣
dealt card: A♥
dealt card: Q♣
dealt card: 7♥
dealt card: Q♥
dealt card: K♠
dealt card: 9♥
dealt card: Q♦
dealt card: A♥
dealt card: T♦
dealt card: 3♦
dealt card: K♥
dealt card: A♣
dealt card: J♥
dealt card: 7♣
dealt card: 6♠
dealt card: 2♦
dealt card: A♦
dealt card: 3♥
dealt card: T♠
dealt card: 9♣
dealt card: J♦
dealt card: 6♥
dealt card: 7♥
dealt card: 9♥
dealt card: 8♠
dealt card: 9♦
dealt card: 8♦
dealt card: Q♦
dealt card: T♠
dealt card: 3♦
dealt card: 2♦
dealt card: J♥
dealt card: J♣
dealt card: 7♠
dealt card: 2♠
dealt card: 6♣
dealt card: J♠
dealt card: 2♠
dealt card: K♣
dealt card: Q♥
dealt card: 6♦
dealt card: 5♥
dealt card: T♥
dealt card: 4♣
dealt card: 9♠
dealt card: K♦
--- 1 pack unshuffled --------------------------
dealt card: K♠
dealt card: K♥
dealt card: K♦
dealt card: K♣
dealt card: Q♠
dealt card: Q♥
dealt card: Q♦
dealt card: Q♣
dealt card: J♠
dealt card: J♥
dealt card: J♦
dealt card: J♣
dealt card: T♠
dealt card: T♥
dealt card: T♦
dealt card: T♣
dealt card: 9♠
dealt card: 9♥
dealt card: 9♦
dealt card: 9♣
dealt card: 8♠
dealt card: 8♥
dealt card: 8♦
dealt card: 8♣
dealt card: 7♠
dealt card: 7♥
dealt card: 7♦
dealt card: 7♣
dealt card: 6♠
dealt card: 6♥
dealt card: 6♦
dealt card: 6♣
dealt card: 5♠
dealt card: 5♥
dealt card: 5♦
dealt card: 5♣
dealt card: 4♠
dealt card: 4♥
dealt card: 4♦
dealt card: 4♣
dealt card: 3♠
dealt card: 3♥
dealt card: 3♦
dealt card: 3♣
dealt card: 2♠
dealt card: 2♥
dealt card: 2♦
dealt card: 2♣
dealt card: A♠
dealt card: A♥
dealt card: A♦
dealt card: A♣
--- 1 pack shuffled ----------------------------
dealt card: 3♦
dealt card: K♥
dealt card: Q♥
dealt card: 6♣
dealt card: J♠
dealt card: 4♥
dealt card: K♣
dealt card: Q♠
dealt card: 7♣
dealt card: 7♥
dealt card: 2♥
dealt card: K♠
dealt card: 5♦
dealt card: 5♣
dealt card: 4♠
dealt card: J♣
dealt card: Q♣
dealt card: Q♦
dealt card: 6♥
dealt card: 7♠
dealt card: A♣
dealt card: 3♣
dealt card: 2♠
dealt card: A♥
dealt card: 8♥
dealt card: T♥
dealt card: 9♠
dealt card: T♦
dealt card: J♦
dealt card: 9♦
dealt card: 3♥
dealt card: 2♦
dealt card: J♥
dealt card: 8♦
dealt card: 9♥
dealt card: T♠
dealt card: 2♣
dealt card: 6♠
dealt card: T♣
dealt card: A♦
dealt card: 7♦
dealt card: 9♣
dealt card: 3♠
dealt card: K♦
dealt card: 8♠
dealt card: 5♠
dealt card: 4♣
dealt card: 6♦
dealt card: 5♥
dealt card: 8♣
dealt card: A♠
dealt card: 4♦
==== Deck + Hand Tests =========================
How many players are joining this game?
9
--- 1 pack unshuffled --------------------------
hand #0: { K♠, J♥, 9♦, 7♣, 4♠, 2♥ }
hand #1: { K♥, J♦, 9♣, 6♠, 4♥, 2♦ }
hand #2: { K♦, J♣, 8♠, 6♥, 4♦, 2♣ }
hand #3: { K♣, T♠, 8♥, 6♦, 4♣, A♠ }
hand #4: { Q♠, T♥, 8♦, 6♣, 3♠, A♥ }
hand #5: { Q♥, T♦, 8♣, 5♠, 3♥, A♦ }
hand #6: { Q♦, T♣, 7♠, 5♥, 3♦, A♣ }
hand #7: { Q♣, 9♠, 7♥, 5♦, 3♣ }
hand #8: { J♠, 9♥, 7♦, 5♣, 2♠ }
--- 1 pack shuffled ----------------------------
hand #0: { T♣, 2♦, A♣, 8♦, K♣, 4♥ }
hand #1: { 5♠, A♥, 3♦, K♦, A♦, T♠ }
hand #2: { 2♠, 2♣, 7♠, Q♠, 5♥, J♣ }
hand #3: { J♦, 6♣, 7♥, 3♣, Q♥, J♥ }
hand #4: { T♦, 4♠, 4♣, 8♣, 5♦, 8♥ }
hand #5: { 4♦, 8♠, 9♥, 3♥, K♥, A♠ }
hand #6: { 3♠, 6♥, K♠, J♠, Q♦, 7♣ }
hand #7: { 6♠, Q♣, 9♣, 9♠, 2♥ }
hand #8: { 9♦, T♥, 5♣, 6♦, 7♦ }
\* ------------------------------------------------------------------------- */