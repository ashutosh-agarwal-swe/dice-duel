/**
 This class represents a single die that can be rolled.
 @author Ashutosh Agarwal
*/
public class Die {

   /**
    The number of sides on a standard die.
   */
   final int DEFAULT_SIDES = 6;

   /**
    The number of sides on this die.
   */
   private int sides;

   /**
    The value that is currently showing on this die.
   */
   private int faceValue;

   /**
    Constructs a standard six-sided die.
   */
   public Die() {
      sides = DEFAULT_SIDES;
      faceValue = 1;
   }

   /**
    Constructs a die with the given number of sides.
    @param sidesIn the number of sides (a value below 2 is replaced by 6).
   */
   public Die(int sidesIn) {
      if (sidesIn >= 2) {
         sides = sidesIn;
      }
      else {
         sides = DEFAULT_SIDES;
      }
      faceValue = 1;
   }

   /**
    Rolls this die.
    @return the value that was rolled (between 1 and the number of sides).
   */
   public int roll() {
      // Math.random() gives a value from 0.0 up to (but not including) 1.0.
      // Multiplying by sides and casting to int gives 0 to (sides - 1).
      // Adding 1 shifts that to 1 to sides.
      faceValue = 1 + (int)(Math.random() * sides);
      return faceValue;
   }

   /**
    Retrieves the value that is currently showing.
    @return the value from the most recent roll.
   */
   public int getFaceValue() {
      return faceValue;
   }

   /**
    Retrieves the number of sides on this die.
    @return the number of sides.
   */
   public int getSides() {
      return sides;
   }

   /**
    Describes this die.
    @return a String containing the number of sides and the current value.
   */
   public String toString() {
      return "Die[sides=" + sides + ", faceValue=" + faceValue + "]";
   }
}
