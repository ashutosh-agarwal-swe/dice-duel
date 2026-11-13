/**
 This class represents a computer player that normally behaves like a
 ThresholdBot, but takes more risk when its opponent is close to winning.
 @author Ashutosh Agarwal
*/
public class EndgameBot extends ThresholdBot {

   /**
    How close to the winning score the opponent must be before this
    bot starts taking extra risk.
   */
   private int dangerMargin;

   /**
    Constructs an EndgameBot.
    @param nameIn the bot's name.
    @param holdAtIn the turn total at which the bot normally holds.
    @param dangerMarginIn the bot keeps rolling when the opponent is
                          within this many points of winning.
   */
   public EndgameBot(String nameIn, int holdAtIn, int dangerMarginIn) {
      super(nameIn, holdAtIn);
      dangerMargin = dangerMarginIn;
   }

   /**
    Retrieves the danger margin.
    @return the danger margin.
   */
   public int getDangerMargin() {
      return dangerMargin;
   }

   /**
    Decides whether to roll again. The bot starts with the normal
    threshold decision. If the opponent is close to winning and this
    bot cannot win by holding right now, it keeps rolling instead,
    because holding would probably hand the game to the opponent.
    @param turnTotal the points collected so far this turn.
    @param opponentScore the opponent's banked score.
    @return true to roll again, false to hold.
   */
   public boolean wantsToRoll(int turnTotal, int opponentScore) {
      boolean rollAgain = super.wantsToRoll(turnTotal, opponentScore);

      boolean canWinNow =
         (getScore() + turnTotal >= getWinningScore());
      boolean opponentClose =
         (opponentScore >= getWinningScore() - dangerMargin);

      if (opponentClose && !canWinNow) {
         rollAgain = true;
      }
      return rollAgain;
   }

   /**
    Describes this bot's strategy in words.
    @return a short description of the strategy.
   */
   public String describeStrategy() {
      return super.describeStrategy()
           + ", but keeps rolling when the opponent is within "
           + dangerMargin + " points of winning";
   }

   /**
    Describes this bot.
    @return a String containing the bot details and the danger margin.
   */
   public String toString() {
      return "EndgameBot[" + super.toString()
           + ", dangerMargin=" + dangerMargin + "]";
   }

   /**
    Checks whether this bot matches another object.
    @param otherObject the object to compare with.
    @return true if the two are equal.
   */
   public boolean equals(Object otherObject) {
      boolean result = false;
      if (super.equals(otherObject)) {
         EndgameBot other = (EndgameBot)otherObject;
         result = (dangerMargin == other.getDangerMargin());
      }
      return result;
   }
}
