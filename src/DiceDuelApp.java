import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.BorderPane;
import javafx.geometry.Pos;
import javafx.event.ActionEvent;

/**
 A graphical version of Dice Duel: one human against one computer player,
 plus a panel for simulating games between two computer players.
 @author Ashutosh Agarwal
*/
public class DiceDuelApp extends Application {

   private Player human;
   private ComputerPlayer computer;
   private DiceDuelGame game;
   private boolean humanStarts;

   private Button cautiousButton;
   private Button classicButton;
   private Button endgameButton;

   private Button rollButton;
   private Button holdButton;
   private Button newGameButton;

   private Button sim100Button;
   private Button sim1000Button;
   private Button sim10000Button;

   private Text opponentText;
   private Text humanScoreText;
   private Text computerScoreText;
   private Text turnTotalText;
   private Text recordText;
   private Text messageText;
   private Text simResultText;

   /**
    Builds the window and starts the first game.
    @param primaryStage the main window.
   */
   public void start (Stage primaryStage) {
      primaryStage.setTitle ("Dice Duel");

      //Top: buttons for choosing an opponent.
      Label opponentLabel = new Label ("Choose an opponent:");
      cautiousButton = new Button ("Cautious Carl");
      cautiousButton.setOnAction (this::processOpponentChoice);
      classicButton = new Button ("Classic Clara");
      classicButton.setOnAction (this::processOpponentChoice);
      endgameButton = new Button ("Endgame Eddie");
      endgameButton.setOnAction (this::processOpponentChoice);

      FlowPane topPane = new FlowPane
         (opponentLabel, cautiousButton, classicButton, endgameButton);
      topPane.setAlignment (Pos.CENTER);
      topPane.setHgap (10);
      topPane.setPrefHeight (60);
      topPane.setStyle ("-fx-background-color: lightblue");

      //Center: the scoreboard and the game controls.
      opponentText = new Text ();
      humanScoreText = new Text ();
      computerScoreText = new Text ();
      turnTotalText = new Text ();
      recordText = new Text ();
      messageText = new Text ();

      rollButton = new Button ("Roll");
      rollButton.setOnAction (this::processRoll);
      holdButton = new Button ("Hold");
      holdButton.setOnAction (this::processHold);
      newGameButton = new Button ("New Game");
      newGameButton.setOnAction (this::processNewGame);

      GridPane centerPane = new GridPane ();
      centerPane.add (opponentText, 0, 0, 3, 1);   //spans 3 columns
      centerPane.add (humanScoreText, 0, 1);
      centerPane.add (computerScoreText, 1, 1);
      centerPane.add (turnTotalText, 2, 1);
      centerPane.add (rollButton, 0, 2);
      centerPane.add (holdButton, 1, 2);
      centerPane.add (newGameButton, 2, 2);
      centerPane.add (messageText, 0, 3, 3, 1);
      centerPane.add (recordText, 0, 4, 3, 1);
      centerPane.setAlignment (Pos.CENTER);
      centerPane.setHgap (30);
      centerPane.setVgap (15);

      //Bottom: the strategy simulator.
      Label simLabel = new Label ("Simulate Classic vs. Endgame:");
      sim100Button = new Button ("100 games");
      sim100Button.setOnAction (this::processSimulation);
      sim1000Button = new Button ("1,000 games");
      sim1000Button.setOnAction (this::processSimulation);
      sim10000Button = new Button ("10,000 games");
      sim10000Button.setOnAction (this::processSimulation);
      simResultText = new Text ("No simulation has been run yet.");

      GridPane bottomPane = new GridPane ();
      bottomPane.add (simLabel, 0, 0);
      bottomPane.add (sim100Button, 1, 0);
      bottomPane.add (sim1000Button, 2, 0);
      bottomPane.add (sim10000Button, 3, 0);
      bottomPane.add (simResultText, 0, 1, 4, 1);  //spans 4 columns
      bottomPane.setAlignment (Pos.CENTER);
      bottomPane.setHgap (10);
      bottomPane.setVgap (10);
      bottomPane.setPrefHeight (150);
      bottomPane.setStyle ("-fx-background-color: lightgray");

      //Putting it all together:
      BorderPane mainPane = new BorderPane ();
      mainPane.setTop (topPane);
      mainPane.setCenter (centerPane);
      mainPane.setBottom (bottomPane);

      //Start with a default opponent:
      this.startMatch (new ThresholdBot ("Classic Clara", 20));

      Scene scene = new Scene (mainPane, 620, 520);

      primaryStage.setScene (scene);
      primaryStage.show ();
   }

   /**
    Starts a fresh match (both win counts at zero) against the
    given opponent.
    @param opponent the computer player to play against.
   */
   public void startMatch (ComputerPlayer opponent) {
      human = new Player ("You");
      computer = opponent;
      game = new DiceDuelGame (human, computer);
      humanStarts = true;
      this.beginGame ();
   }

   /**
    Starts a new game against the current opponent. The human and
    the computer take turns rolling first.
   */
   public void beginGame () {
      game.startNewGame (humanStarts);

      if (humanStarts) {
         messageText.setText ("New game. You roll first.");
      }
      else {
         messageText.setText ("New game. " + computer.getName()
                              + " rolls first.\n"
                              + this.takeComputerTurn ());
      }

      //The other player rolls first next time:
      humanStarts = !humanStarts;
      this.updateDisplay ();
   }

   /**
    Plays the computer's turn.
    @return a description of the turn, ending with what happens next.
   */
   public String takeComputerTurn () {
      String summary = game.playComputerTurn (computer);
      if (game.isGameOver ()) {
         summary = summary + "\n" + computer.getName()
                 + " wins. Press New Game to play again.";
      }
      else {
         summary = summary + "\nYour turn.";
      }
      return summary;
   }

   /**
    Refreshes every piece of text on the scoreboard.
   */
   public void updateDisplay () {
      //Grey out Roll and Hold once the game is over:
      rollButton.setDisable (game.isGameOver ());
      holdButton.setDisable (game.isGameOver ());

      opponentText.setText ("Opponent: " + computer.getName() + " ("
                            + computer.describeStrategy() + ")");
      humanScoreText.setText ("Your score: " + human.getScore());
      computerScoreText.setText (computer.getName() + ": "
                                 + computer.getScore());
      turnTotalText.setText ("Turn total: " + game.getTurnTotal());
      recordText.setText ("Games won - You: " + human.getGamesWon()
                          + "   " + computer.getName() + ": "
                          + computer.getGamesWon());
   }

   /**
    Responds to one of the three opponent buttons.
    @param event the button press.
   */
   public void processOpponentChoice (ActionEvent event) {
      if (event.getSource() == cautiousButton) {
         this.startMatch (new ThresholdBot ("Cautious Carl", 15));
      }
      else if (event.getSource() == classicButton) {
         this.startMatch (new ThresholdBot ("Classic Clara", 20));
      }
      else {
         this.startMatch (new EndgameBot ("Endgame Eddie", 20, 25));
      }
   }

   /**
    Responds to the New Game button.
    @param event the button press.
   */
   public void processNewGame (ActionEvent event) {
      this.beginGame ();
   }

   /**
    Responds to the Roll button.
    @param event the button press.
   */
   public void processRoll (ActionEvent event) {
      if (game.isGameOver ()) {
         messageText.setText
            ("This game is over. Press New Game to play again.");
      }
      else {
         int rolled = game.roll ();
         if (rolled == game.getBustValue ()) {
            messageText.setText ("You rolled a " + rolled
                                 + " and bank nothing.\n"
                                 + this.takeComputerTurn ());
         }
         else {
            messageText.setText ("You rolled a " + rolled
                                 + ". Roll again or hold?");
         }
      }
      this.updateDisplay ();
   }

   /**
    Responds to the Hold button.
    @param event the button press.
   */
   public void processHold (ActionEvent event) {
      if (game.isGameOver ()) {
         messageText.setText
            ("This game is over. Press New Game to play again.");
      }
      else if (game.getTurnTotal () == 0) {
         messageText.setText ("Roll at least once before holding.");
      }
      else {
         int banked = game.getTurnTotal ();
         game.hold ();
         if (game.isGameOver ()) {
            messageText.setText ("You banked " + banked
                                 + " and win! Press New Game to play again.");
         }
         else {
            messageText.setText ("You banked " + banked + ".\n"
                                 + this.takeComputerTurn ());
         }
      }
      this.updateDisplay ();
   }

   /**
    Responds to one of the three simulation buttons.
    @param event the button press.
   */
   public void processSimulation (ActionEvent event) {
      int numGames;
      if (event.getSource() == sim100Button) {
         numGames = 100;
      }
      else if (event.getSource() == sim1000Button) {
         numGames = 1000;
      }
      else {
         numGames = 10000;
      }

      Simulator simulator = new Simulator
         (new ThresholdBot ("Classic (hold at 20)", 20),
          new EndgameBot ("Endgame", 20, 25));
      simulator.run (numGames);
      simResultText.setText (simulator.getReport ());
   }

   /**
    Starts the application. The launch method is inherited from
    Application: it sets up JavaFX, creates a DiceDuelApp object
    and calls its start method.
    @param args command-line arguments (not used).
   */
   public static void main (String[] args) {
      launch (args);
   }

} //end class
