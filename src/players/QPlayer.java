package players;


import game.Card;
import game.GameState;
import game.HandRanks;
import game.Player;

import java.util.List;


public class QPlayer extends Player {
    public QPlayer(String name) {
        super(name);
    }

    @Override
    protected void takePlayerTurn() {
        if (shouldFold()) {
            fold();
        } else if (shouldCall()) {
            call();
        } else if (shouldRaise()) {
            raise(getGameState().getTableMinBet());
        } else if (shouldCheck()) {
            check();
        } else if (shouldAllIn()) {
            allIn();
        }
    }


    @Override
    protected boolean shouldFold(HandRanks handRank) {
        // Check the strength of the player's hand
        if (handRank.getValue() < HandRanks.HIGH_CARD.getValue()) {
            return true; // Fold if the hand is weak
        }

        // Check if the call amount is too high
        int callAmount = getGameState().getTableBet() - getBet();
        if (callAmount > getBank() / 4) {
            return true; // Fold if the call amount is too high relative to the player's bankroll
        }

        return false; // Don't fold by default
    }


    @Override
    protected boolean shouldCheck() {
        // Check the current bet amount
        int currentBet = getGameState().getTableBet();

        // Check if the player has already bet or raised in this round
        boolean hasBetOrRaised = getBet() > 0 || isBet();

        // If no active bet and the player hasn't bet or raised check
        if (currentBet == 0 && !hasBetOrRaised) {
            return true;
        }

        return false; // Don't check by default
    }


    @Override
    protected boolean shouldCall() {
        // Get the player's hand rank
        HandRanks handRank = evaluatePlayerHand();

        // Check if the player's hand rank is high enough to consider calling
        if (handRank.getValue() >= HandRanks.PAIR.getValue()) {
            // Add additional condition here if needed
            return true;
        }

        return false; // Don't call by default
    }


    @Override
    protected boolean shouldRaise() {
        GameState gameState = getGameState();
        List<Card> handCards = getHandCards();
        if (gameState.getNumRoundStage() == 0) {
            HandRanks handRank = evaluatePlayerHandRank(handCards);
            return handRank.compareTo(HandRanks.PAIR) >= 0; // Check if hand rank is PAIR or higher
        }
        return false;
    }

    protected HandRanks evaluatePlayerHandRank(List<Card> handCards) {
        // Call the appropriate method from HandRanks to evaluate hand
        return HandRanks.HIGH_CARD;
    }


    @Override
    protected boolean shouldAllIn() {
        // Get the player's hand rank
        HandRanks handRank = evaluatePlayerHand();

        // Check if the player's hand rank is the strongest (Royal Flush)
        if (handRank == HandRanks.ROYAL_FLUSH) {
            return true; // Go all-in if the player has the strongest hand
        }

        return false; // Don't go all-in by default
    }


    @Override
    protected boolean shouldFold() {
        return false;
    }

}

