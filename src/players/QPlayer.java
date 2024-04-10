package players;


import game.HandRanks;
import game.Player;


public class QPlayer extends Player {


    public QPlayer(String name) {
        super(name);
    }


    @Override
    protected void takePlayerTurn() {
        HandRanks handRank = evaluatePlayerHand();
        System.out.println("handrank" + handRank);
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
    protected boolean shouldFold() {
        // Check the strength of the player's hand
        HandRanks handRank = evaluatePlayerHand();
        if (handRank.getValue() < HandRanks.HIGH_CARD.getValue()) {
            return true; // Fold if the hand is weak
        }


        // Check if the community cards do not improve the player's hand
//        if (!handRank.isMadeHand() && handRank.getValue() < HandRanks.TWO_PAIR.getValue()) {
//            return true; // Fold if the hand is not improved and weak
//        }


        // Check the betting situation
        if (getBet() > 0) {
            int callAmount = getGameState().getTableBet() - getBet();
            if (callAmount > getBank() / 4) {
                return true; // Fold if the call amount is too high relative to the player's bankroll
            }
        }


        // Add more sophisticated logic here based on opponent behavior, position, etc.


        return false; // Don't fold by default
    }






    @Override
    protected boolean shouldCheck() {
        // Implement check logic
        // Example: Return true if no active bet and hand is decent
        return false;
    }


    @Override
    protected boolean shouldCall() {
        // Implement call logic
        // Example: Return true if current bet is affordable
        return false;
    }


    @Override
    protected boolean shouldRaise() {
        // Implement raise logic
        // Example: Return true if hand is strong enough to raise
        return false;
    }


    @Override
    protected boolean shouldAllIn() {
        // Implement all-in logic
        // Example: Return true if hand is extremely strong
        return false;
    }
}

