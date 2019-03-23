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

        // init & print test cards
        Card card1 = new Card();
        System.out.printf("card1: %s (test: default card)%n", card1);

        Card card2 = new Card('2', Suit.hearts);
        System.out.printf("card2: %s (test: valid card)%n", card2);

        Card card3 = new Card('X', Suit.diamonds); // X -- invalid
        System.out.printf("card3: %s (test: invalid card)%n", card3);

        card2.set('X', Suit.clubs); // X -- invalid
        System.out.printf("card2: %s (test: valid -> invalid card)%n", card2);

        card3.set('J', Suit.diamonds);
        System.out.printf("card3: %s (test: invalid -> valid card)%n", card3);

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
            return "��"; // invalid card
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
                return '♣';
            case diamonds:
                return '♦';
            case hearts:
                return '♥';
            case spades:
                return '♠';
            default:
                return '�'; // should never happen

        }

    }
}

class Hand {
    //TODO: J Asato
}

class Deck {
    //TODO: R Talmage
}