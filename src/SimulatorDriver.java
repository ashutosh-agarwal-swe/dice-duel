import java.util.Scanner;

/**
 A driver class that compares Dice Duel strategies by simulating many games.
 @author Ashutosh Agarwal
*/
public class SimulatorDriver {

   public static void main(String[] args) {
      Scanner sc = new Scanner(System.in);

      System.out.println("How many games per matchup?");
      System.out.println("\t1) 1,000");
      System.out.println("\t2) 10,000");
      System.out.println("\t3) 100,000");

      //The choice is read as text (not with nextInt) so that typing
      //letters by mistake cannot crash the program.
      int numGames = 0;
      while (numGames == 0) {
         System.out.print("Enter 1, 2 or 3: ");
         String choice = sc.next();
         switch (choice) {
            case "1": numGames = 1000;
                      break;
            case "2": numGames = 10000;
                      break;
            case "3": numGames = 100000;
                      break;
            default:  System.out.println("That is not one of the choices.");
         }
      }

      //Matchup 1: is holding at 20 better than holding at 15?
      Simulator matchOne = new Simulator(
         new ThresholdBot("Hold at 15", 15),
         new ThresholdBot("Hold at 20", 20));
      matchOne.run(numGames);
      System.out.println("\nMatchup 1");
      System.out.println(matchOne.getReport());

      //Matchup 2: is holding at 20 better than holding at 25?
      Simulator matchTwo = new Simulator(
         new ThresholdBot("Hold at 25", 25),
         new ThresholdBot("Hold at 20", 20));
      matchTwo.run(numGames);
      System.out.println("\nMatchup 2");
      System.out.println(matchTwo.getReport());

      //Matchup 3: does paying attention to the opponent help?
      Simulator matchThree = new Simulator(
         new ThresholdBot("Hold at 20", 20),
         new EndgameBot("Endgame", 20, 25));
      matchThree.run(numGames);
      System.out.println("\nMatchup 3");
      System.out.println(matchThree.getReport());

      //Matchup 4: two identical bots, to measure the advantage
      //of rolling first.
      Simulator matchFour = new Simulator(
         new ThresholdBot("Hold at 20 (A)", 20),
         new ThresholdBot("Hold at 20 (B)", 20));
      matchFour.run(numGames);
      System.out.println("\nMatchup 4");
      System.out.println(matchFour.getReport());
   }
}
