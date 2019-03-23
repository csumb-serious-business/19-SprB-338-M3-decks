/* ------------------------------------------------------------------------- *\
Authors:    C Ahangama, J Asato, M Robertson, R Talmage
Class:      CST338
Assignment: M3 Decks of Cards
Date:       3/26/2019
\* ------------------------------------------------------------------------- */

enum Suit {clubs, diamonds, hearts, spades}

public class Assig3 {
    public static void main(String[] args) {
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

    public void set(char value, Suit suit) {
        this.suit = suit;
        value = Character.toUpperCase(value);
        if (VALID_VALUE_CHARS.indexOf(value) != -1) {
            this.value = value;
        }

        if (this.suit == null || this.value == '\u0000') {
            this.errorFlag = true;
        }
    }

    /**
     * @return suit of the Card
     */
    public Suit getSuit() {
        return suit;
    }

    /**
     * @return errorFlag of the Card
     */
    public boolean getErrorFlag() {
        return errorFlag;
    }

    /**
     * @return value of the Card
     */
    public char getValue() {
        return value;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Card) {
            Card other = (Card) obj;
            // errorFlag (invalid) cards can be checked also (not sure if that is OK)
            // they are technically evaluable, but not useful for the app
            return other.getSuit() == this.getSuit() &&
                    other.getValue() == this.getValue() &&
                    other.getErrorFlag() == this.getErrorFlag();
        }
        return false;
    }

    @Override
    public String toString() {
        if (this.getErrorFlag()) {
            return "❎❎"; // invalid card
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
                return '❎'; // should never happen

        }

    }
}

class Hand {
    //TODO: J Asato
}

class Deck {
    //TODO: R Talmage
}