/**
 This class represents a computer player that keeps rolling until its
 turn total reaches a set number, and then holds.
 @author Ashutosh Agarwal
*/
public class ThresholdBot extends ComputerPlayer {

   /**
    The turn total at which this bot stops rolling.
   */
   private int holdAt;

   /**
    Constructs a ThresholdBot.
    @param nameIn the bot's name.
    @param holdAtIn the turn total at which the bot holds
                    (a value below 1 is replaced by 1).
   */
   public ThresholdBot(String nameIn, int holdAtIn) {
      super(nameIn);
      if (holdAtIn >= 1) {
         holdAt = holdAtIn;
      }
      else {
         holdAt = 1;
      }
   }

   /**
    Retrieves the turn total at which this bot holds.
    @return the hold threshold.
   */
   public int getHoldAt() {
      return holdAt;
   }

   /**
    Decides whether to roll again. The bot holds once its turn total
    reaches the threshold, or as soon as holding would win the game.
    @param turnTotal the points collected so far this turn.
    @param opponentScore the opponent's banked score (not used here).
    @return true to roll again, false to hold.
   */
   public boolean wantsToRoll(int turnTotal, int opponentScore) {
      boolean rollAgain = true;
      if (turnTotal >= holdAt) {
         rollAgain = false;
      }
      else if (getScore() + turnTotal >= getWinningScore()) {
         //Holding now wins the game, so there is no reason to risk it.
         rollAgain = false;
      }
      return rollAgain;
   }

   /**
    Describes this bot's strategy in words.
    @return a short description of the strategy.
   */
   public String describeStrategy() {
      return "holds at a turn total of " + holdAt;
   }

   /**
    Describes this bot.
    @return a String containing the player details and the threshold.
   */
   public String toString() {
      return "ThresholdBot[" + super.toString()
           + ", holdAt=" + holdAt + "]";
   }

   /**
    Checks whether this bot matches another object.
    @param otherObject the object to compare with.
    @return true if the two are equal.
   */
   public boolean equals(Object otherObject) {
      boolean result = false;
      if (super.equals(otherObject)) {
         //super.equals has checked that both objects belong to the
         //same class, so we can cast:
         ThresholdBot other = (ThresholdBot)otherObject;
         result = (holdAt == other.getHoldAt());
      }
      return result;
   }
}
