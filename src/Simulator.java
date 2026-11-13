import java.text.NumberFormat;

/**
 This class plays many games of Dice Duel between two computer players and
 keeps track of the results, so that strategies can be compared.
 @author Ashutosh Agarwal
*/
public class Simulator {

   /**
    The first computer player.
   */
   private ComputerPlayer botOne;

   /**
    The second computer player.
   */
   private ComputerPlayer botTwo;

   /**
    The number of games played so far.
   */
   private int gamesPlayed;

   /**
    The number of games won by the first computer player.
   */
   private int botOneWins;

   /**
    The number of games won by the second computer player.
   */
   private int botTwoWins;

   /**
    The number of games won by whichever player rolled first.
   */
   private int starterWins;

   /**
    Constructs a Simulator for the two given computer players.
    @param botOneIn the first computer player.
    @param botTwoIn the second computer player.
   */
   public Simulator(ComputerPlayer botOneIn, ComputerPlayer botTwoIn) {
      botOne = botOneIn;
      botTwo = botTwoIn;
      gamesPlayed = 0;
      botOneWins = 0;
      botTwoWins = 0;
      starterWins = 0;
   }

   /**
    Plays the given number of games and adds the results to the
    totals. The two players take turns rolling first so that neither
    one gets an unfair advantage.
    @param numGames the number of games to play.
   */
   public void run(int numGames) {
      DiceDuelGame game = new DiceDuelGame(botOne, botTwo);
      boolean botOneStarts = true;
      int count = 0;

      while (count < numGames) {
         game.startNewGame(botOneStarts);

         //Play one full game:
         while (!game.isGameOver()) {
            if (game.isPlayerOneTurn()) {
               game.playComputerTurn(botOne);
            }
            else {
               game.playComputerTurn(botTwo);
            }
         }

         //Record the result:
         if (botOne.hasWon()) {
            botOneWins++;
            if (botOneStarts) {
               starterWins++;
            }
         }
         else {
            botTwoWins++;
            if (!botOneStarts) {
               starterWins++;
            }
         }

         gamesPlayed++;
         botOneStarts = !botOneStarts;
         count++;
      }
   }

   /**
    Retrieves the number of games played so far.
    @return the number of games played.
   */
   public int getGamesPlayed() {
      return gamesPlayed;
   }

   /**
    Retrieves the number of games won by the first computer player.
    @return the first player's wins.
   */
   public int getBotOneWins() {
      return botOneWins;
   }

   /**
    Retrieves the number of games won by the second computer player.
    @return the second player's wins.
   */
   public int getBotTwoWins() {
      return botTwoWins;
   }

   /**
    Calculates the percentage of games won by the first computer player.
    @return the percentage (0.0 if no games have been played).
   */
   public double getBotOneWinPercentage() {
      double percentage = 0.0;
      if (gamesPlayed > 0) {
         //Cast to double so that this is not integer division:
         percentage = (double)botOneWins / gamesPlayed * 100;
      }
      return percentage;
   }

   /**
    Calculates the percentage of games won by the second computer player.
    @return the percentage (0.0 if no games have been played).
   */
   public double getBotTwoWinPercentage() {
      double percentage = 0.0;
      if (gamesPlayed > 0) {
         percentage = (double)botTwoWins / gamesPlayed * 100;
      }
      return percentage;
   }

   /**
    Calculates the percentage of games won by the player who rolled first.
    @return the percentage (0.0 if no games have been played).
   */
   public double getStarterWinPercentage() {
      double percentage = 0.0;
      if (gamesPlayed > 0) {
         percentage = (double)starterWins / gamesPlayed * 100;
      }
      return percentage;
   }

   /**
    Builds a report of the results so far.
    @return a multi-line String with the win percentages.
   */
   public String getReport() {
      NumberFormat formatter = NumberFormat.getNumberInstance();
      formatter.setMaximumFractionDigits(1);
      formatter.setMinimumFractionDigits(1);

      return "Games played: " + gamesPlayed
           + "\n" + botOne.getName() + " won "
           + formatter.format(this.getBotOneWinPercentage()) + "%"
           + "\n" + botTwo.getName() + " won "
           + formatter.format(this.getBotTwoWinPercentage()) + "%"
           + "\nThe player who rolled first won "
           + formatter.format(this.getStarterWinPercentage()) + "%";
   }
}
