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
        Object[] sortedFruitArray = Arrays
                .stream(fruitArray)
                .sorted(Comparator.comparingInt(Fruit::getWeight)).toArray();
        Fruit fruit = (Fruit) sortedFruitArray[fruitArray.length / 2];
        String fruitName = fruit.getName();
        String oracleAnswer = oracle.isIt(fruit);

        if (oracleAnswer.equals("correct!")) {
            return fruitName;
        } else if (oracleAnswer.equals("heavier")) {
            return play(Arrays.copyOfRange(sortedFruitArray, fruitArray.length / 2 + 1, fruitArray.length, Fruit[].class));
        }
        return play(Arrays.copyOfRange(sortedFruitArray, 0, fruitArray.length / 2, Fruit[].class));
    }
}
