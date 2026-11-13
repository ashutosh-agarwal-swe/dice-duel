/**
 This class represents one game of Dice Duel between two players and
 enforces the rules.

 Rules: On your turn you roll the die as many times as you like.
 Each roll of 2 to 6 is added to your turn total. Rolling a 1 wipes
 out the turn total and ends your turn. Choosing to hold banks the
 turn total and ends your turn. The first player to bank 100 wins.
 @author Ashutosh Agarwal
*/
public class DiceDuelGame {

   /**
    The roll that wipes out the turn total.
   */
   final int BUST_VALUE = 1;

   /**
    The die used in this game.
   */
   private Die die;

   /**
    The first player.
   */
   private Player playerOne;

   /**
    The second player.
   */
   private Player playerTwo;

   /**
    True when it is player one's turn, false when it is player two's.
   */
   private boolean playerOneTurn;

   /**
    The points collected so far this turn (not yet banked).
   */
   private int turnTotal;

   /**
    True once a player has won.
   */
   private boolean gameOver;

   /**
    Constructs a game between two players. Player one rolls first.
    @param playerOneIn the first player.
    @param playerTwoIn the second player.
   */
   public DiceDuelGame(Player playerOneIn, Player playerTwoIn) {
      die = new Die();
      playerOne = playerOneIn;
      playerTwo = playerTwoIn;
      this.startNewGame(true);
   }

   /**
    Resets both scores and starts a new game.
    @param playerOneStarts true if player one should roll first.
   */
   public void startNewGame(boolean playerOneStarts) {
      playerOne.resetScore();
      playerTwo.resetScore();
      turnTotal = 0;
      gameOver = false;
      playerOneTurn = playerOneStarts;
   }

   /**
    Rolls the die for the player whose turn it is. A roll of 1 wipes
    out the turn total and passes the turn to the other player. Any
    other roll is added to the turn total.
    @return the value rolled (or 0 if the game is already over).
   */
   public int roll() {
      int rolled = 0;
      if (!gameOver) {
         rolled = die.roll();
         if (rolled == BUST_VALUE) {
            turnTotal = 0;
            playerOneTurn = !playerOneTurn;
         }
         else {
            turnTotal += rolled;
         }
      }
      return rolled;
   }

   /**
    Banks the turn total for the player whose turn it is. If that
    player has now won, the game ends. Otherwise the turn passes to
    the other player.
   */
   public void hold() {
      if (!gameOver) {
         Player current = this.getCurrentPlayer();
         current.addToScore(turnTotal);
         turnTotal = 0;
         if (current.hasWon()) {
            gameOver = true;
            current.addWin();
         }
         else {
            playerOneTurn = !playerOneTurn;
         }
      }
   }

   /**
    Plays one complete turn for a computer player. The bot is asked
    before every roll whether it wants to continue.
    @param bot the computer player whose turn it is.
    @return a description of what happened during the turn.
   */
   public String playComputerTurn(ComputerPlayer bot) {
      String summary = bot.getName() + " rolls:";
      int opponentScore = this.getWaitingPlayer().getScore();
      boolean bust = false;

      while (!bust && bot.wantsToRoll(turnTotal, opponentScore)) {
         int rolled = this.roll();
         summary = summary + " " + rolled;
         if (rolled == BUST_VALUE) {
            bust = true;
         }
      }

      if (bust) {
         summary = summary + "\n" + bot.getName()
                 + " rolled a " + BUST_VALUE + " and banks nothing.";
      }
      else {
         int banked = turnTotal;
         this.hold();
         summary = summary + "\n" + bot.getName() + " holds and banks "
                 + banked + " (score: " + bot.getScore() + ").";
      }
      return summary;
   }

   /**
    Retrieves the player whose turn it is.
    @return the current player.
   */
   public Player getCurrentPlayer() {
      Player current;
      if (playerOneTurn) {
         current = playerOne;
      }
      else {
         current = playerTwo;
      }
      return current;
   }

   /**
    Retrieves the player who is waiting for their turn.
    @return the player whose turn it is not.
   */
   public Player getWaitingPlayer() {
      Player waiting;
      if (playerOneTurn) {
         waiting = playerTwo;
      }
      else {
         waiting = playerOne;
      }
      return waiting;
   }

   /**
    Checks whose turn it is.
    @return true if it is player one's turn.
   */
   public boolean isPlayerOneTurn() {
      return playerOneTurn;
   }

   /**
    Checks whether the game has finished.
    @return true if a player has won.
   */
   public boolean isGameOver() {
      return gameOver;
   }

   /**
    Retrieves the points collected so far this turn.
    @return the turn total.
   */
   public int getTurnTotal() {
      return turnTotal;
   }

   /**
    Retrieves the roll that wipes out the turn total.
    @return the bust value.
   */
   public int getBustValue() {
      return BUST_VALUE;
   }

   /**
    Describes the current state of the game as a small scoreboard.
    @return a String showing both scores and the turn total.
   */
   public String toString() {
      return "\n" + playerOne.getName() + ":\t" + playerOne.getScore()
           + "\n" + playerTwo.getName() + ":\t" + playerTwo.getScore()
           + "\nTurn total:\t" + turnTotal;
   }
}
