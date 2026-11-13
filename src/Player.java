/**
 This class represents a player in a game of Dice Duel.
 A human player is a plain Player object. Computer players are
 represented by subclasses (see ComputerPlayer).
 @author Ashutosh Agarwal
*/
public class Player {

   /**
    The score a player must reach to win a game.
   */
   private final int WINNING_SCORE = 100;

   /**
    The player's name.
   */
   private String name;

   /**
    The points this player has banked in the current game.
   */
   private int score;

   /**
    The number of games this player has won.
   */
   private int gamesWon;

   /**
    Constructs a Player with the given name, a score of zero and no wins.
    @param nameIn the player's name.
   */
   public Player(String nameIn) {
      name = nameIn;
      score = 0;
      gamesWon = 0;
   }

   /**
    Retrieves the player's name.
    @return the name.
   */
   public String getName() {
      return name;
   }

   /**
    Retrieves the player's banked score in the current game.
    @return the score.
   */
   public int getScore() {
      return score;
   }

   /**
    Retrieves the number of games this player has won.
    @return the number of wins.
   */
   public int getGamesWon() {
      return gamesWon;
   }

   /**
    Retrieves the score needed to win a game.
    @return the winning score.
   */
   public int getWinningScore() {
      return WINNING_SCORE;
   }

   /**
    Banks points for this player.
    @param points the points to add to the score.
   */
   public void addToScore(int points) {
      score += points;
   }

   /**
    Sets the score back to zero (used at the start of a new game).
   */
   public void resetScore() {
      score = 0;
   }

   /**
    Records one more win for this player.
   */
   public void addWin() {
      gamesWon++;
   }

   /**
    Checks whether this player has reached the winning score.
    @return true if the banked score is high enough to win.
   */
   public boolean hasWon() {
      return score >= WINNING_SCORE;
   }

   /**
    Describes this player.
    @return a String containing the name, score and number of wins.
   */
   public String toString() {
      return "Player[name=" + name
           + ", score=" + score
           + ", gamesWon=" + gamesWon + "]";
   }

   /**
    Checks whether this player matches another object. Two players are
    considered equal if they are the same type of player and have the
    same name and the same score.
    @param otherObject the object to compare with.
    @return true if the two are equal.
   */
   public boolean equals(Object otherObject) {
      boolean result = false;
      if (otherObject != null) {
         //A quick test to see if the references are the same:
         if (this == otherObject) {
            result = true;
         }
         //Check to make sure that they are the same object type:
         else if (this.getClass() == otherObject.getClass()) {
            //Now we know that we can cast the parameter:
            Player other = (Player)otherObject;
            result = (name.equals(other.getName())
                      && score == other.getScore());
         }
      }
      return result;
   }
}
