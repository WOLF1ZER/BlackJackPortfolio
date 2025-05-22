package gamelogic;

import java.util.ArrayList;
import java.util.Random;

public class GameLogic {
    public class Card {
        String value, type;

        Card(String value, String type) {
            this.value = value;
            this.type = type;
        }

        public String toString() {
            return value + "-" + type;
        }

        public int getValue() {
            if ("JQK".contains(value)) return 10;
            if (value.equals("A")) return 11;
            return Integer.parseInt(value);
        }

        public boolean isAce() {
            return value.equals("A");
        }

        public String getImagePath() {
            return "/cardsimages/" + toString() + ".png";
        }
    }

    public ArrayList<Card> deck;
    public ArrayList<Card> dealerHand;
    public ArrayList<Card> playerHand;
    public Card hiddenCard;

    public int dealerSum, dealerAceCount;
    public int playerSum, playerAceCount;

    private Random random = new Random();

    public GameLogic() {
        buildDeck();
        shuffleDeck();
        dealerHand = new ArrayList<>();
        playerHand = new ArrayList<>();
    }

    public void buildDeck() {
        deck = new ArrayList<>();
        String[] values = {"A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"};
        String[] types = {"C", "D", "H", "S"};

        for (String type : types) {
            for (String value : values) {
                deck.add(new Card(value, type));
            }
        }
    }

    public void shuffleDeck() {
        for (int i = 0; i < deck.size(); i++) {
            int j = random.nextInt(deck.size());
            Card temp = deck.get(i);
            deck.set(i, deck.get(j));
            deck.set(j, temp);
        }
    }

    public void startGame() {
        buildDeck();
        shuffleDeck();

        dealerHand.clear();
        playerHand.clear();

        dealerSum = playerSum = dealerAceCount = playerAceCount = 0;

        hiddenCard = deck.remove(deck.size() - 1);
        dealerSum += hiddenCard.getValue();
        dealerAceCount += hiddenCard.isAce() ? 1 : 0;

        Card card = deck.remove(deck.size() - 1);
        dealerHand.add(card);
        dealerSum += card.getValue();
        dealerAceCount += card.isAce() ? 1 : 0;

        for (int i = 0; i < 2; i++) {
            card = deck.remove(deck.size() - 1);
            playerHand.add(card);
            playerSum += card.getValue();
            playerAceCount += card.isAce() ? 1 : 0;
        }
    }

    public Card drawPlayerCard() {
        Card card = deck.remove(deck.size() - 1);
        playerHand.add(card);
        playerSum += card.getValue();
        playerAceCount += card.isAce() ? 1 : 0;
        reducePlayerAce();
        return card;
    }

    public Card drawDealerCard() {
        Card card = deck.remove(deck.size() - 1);
        dealerHand.add(card);
        dealerSum += card.getValue();
        dealerAceCount += card.isAce() ? 1 : 0;
        reduceDealerAce();
        return card;
    }

    public void reducePlayerAce() {
        while (playerSum > 21 && playerAceCount > 0) {
            playerSum -= 10;
            playerAceCount--;
        }
    }

    public void reduceDealerAce() {
        while (dealerSum > 21 && dealerAceCount > 0) {
            dealerSum -= 10;
            dealerAceCount--;
        }
    }
}
