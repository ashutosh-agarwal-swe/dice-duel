import java.util.Scanner;

/**
 A text-based version of Dice Duel: one human against one computer player.
 @author Ashutosh Agarwal
*/
public class DiceDuelConsole {

   public static void main(String[] args) {
      Scanner sc = new Scanner(System.in);

      System.out.println("Welcome to Dice Duel!");
      System.out.println("Roll as often as you like. Rolls of 2 to 6 "
                       + "add to your turn total.");
      System.out.println("A 1 wipes out the turn total. Hold to bank it. "
                       + "First to 100 wins.\n");

      System.out.println("What is your name?");
      String name = sc.nextLine();
      Player human = new Player(name);

      System.out.println("\nChoose your opponent:");
      System.out.println("\t1) Cautious Carl");
      System.out.println("\t2) Classic Clara");
      System.out.println("\t3) Endgame Eddie");

      //The choice is read as text (not with nextInt) so that typing
      //letters by mistake cannot crash the program.
      ComputerPlayer computer = new ThresholdBot("Classic Clara", 20);
      boolean validChoice = false;
      while (!validChoice) {
         System.out.print("Enter 1, 2 or 3: ");
         String choice = sc.next();
         switch (choice) {
            case "1": computer = new ThresholdBot("Cautious Carl", 15);
                      validChoice = true;
                      break;
            case "2": computer = new ThresholdBot("Classic Clara", 20);
                      validChoice = true;
                      break;
            case "3": computer = new EndgameBot("Endgame Eddie", 20, 25);
                      validChoice = true;
                      break;
            default:  System.out.println("That is not one of the choices.");
         }
      }
      System.out.println("\nYou are playing " + computer.getName()
                       + ", who " + computer.describeStrategy() + ".");

      DiceDuelGame game = new DiceDuelGame(human, computer);
      boolean humanStarts = true;
      String playAgain = "y";

      while (playAgain.equals("y") || playAgain.equals("Y")) {
         game.startNewGame(humanStarts);

         while (!game.isGameOver()) {
            System.out.println(game);

            if (game.isPlayerOneTurn()) {
               //The human's turn: keep asking until the turn ends.
               boolean turnOver = false;
               while (!turnOver) {
                  System.out.print("Turn total is " + game.getTurnTotal()
                                 + ". Roll or hold? (r/h): ");
                  String action = sc.next();

                  if (action.equals("r") || action.equals("R")) {
                     int rolled = game.roll();
                     System.out.println("You rolled a " + rolled + ".");
                     if (rolled == game.getBustValue()) {
                        System.out.println("You bank nothing this turn.");
                        turnOver = true;
                     }
                  }
                  else if (action.equals("h") || action.equals("H")) {
                     if (game.getTurnTotal() > 0) {
                        game.hold();
                        turnOver = true;
                     }
                     else {
                        System.out.println("Roll at least once first.");
                     }
                  }
                  else {
                     System.out.println("Please type r or h.");
                  }
               }
            }
            else {
               //The computer's turn:
               System.out.println(game.playComputerTurn(computer));
            }
         }

         System.out.println(game);
         if (human.hasWon()) {
            System.out.println("\nYou win!");
         }
         else {
            System.out.println("\n" + computer.getName() + " wins.");
         }
         System.out.println("Games won - " + human.getName() + ": "
                          + human.getGamesWon() + ", "
                          + computer.getName() + ": "
                          + computer.getGamesWon());

         //The other player rolls first next time:
         humanStarts = !humanStarts;

         System.out.print("\nPlay again? (y/n): ");
         playAgain = sc.next();
      }

      System.out.println("Thanks for playing!");
   }
}
