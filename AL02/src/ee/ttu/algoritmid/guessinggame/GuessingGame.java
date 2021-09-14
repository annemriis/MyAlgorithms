package ee.ttu.algoritmid.guessinggame;

import java.util.Arrays;
import java.util.Comparator;

public class GuessingGame {

    Oracle oracle;

    public GuessingGame(Oracle oracle) {
        this.oracle = oracle;
    }

    /**
     * @param fruitArray - All the possible fruits.
     * @return the name of the fruit.
     */
    public String play(Fruit[] fruitArray) {
        Fruit fruit = (Fruit) Arrays
                .stream(fruitArray)
                .sorted(Comparator.comparingInt(Fruit::getWeight)).toArray()[fruitArray.length / 2];
        String fruitName = fruit.getName();
        String oracleAnswer = oracle.isIt(fruit);
        if (oracleAnswer.equals("correct!")) {
            return fruitName;
        } else if (oracleAnswer.equals("heavier")) {
            return play(Arrays.copyOfRange(fruitArray, fruitArray.length / 2, fruitArray.length - 1));
        }
        return play(Arrays.copyOfRange(fruitArray, 0, fruitArray.length / 2 - 1));
    }
}
