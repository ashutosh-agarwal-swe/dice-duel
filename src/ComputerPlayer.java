/**
 This class represents a computer-controlled player in a game of Dice Duel.
 It is abstract because every computer player needs a strategy, and
 each subclass supplies its own.
 @author Ashutosh Agarwal
*/
public abstract class ComputerPlayer extends Player {

   /**
    Constructs a ComputerPlayer with the given name.
    @param nameIn the computer player's name.
   */
   public ComputerPlayer(String nameIn) {
      super(nameIn);
   }

   /**
    Decides whether this computer player wants to roll again.
    @param turnTotal the points collected so far this turn (not yet banked).
    @param opponentScore the opponent's banked score.
    @return true to roll again, false to hold.
   */
   public abstract boolean wantsToRoll(int turnTotal, int opponentScore);

   /**
    Describes this computer player's strategy in words.
    @return a short description of the strategy.
   */
   public abstract String describeStrategy();
}
