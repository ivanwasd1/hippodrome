import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class HippodromeTest {

    @Test
    public void constructorShouldThrowExceptionWhenNameIsNullAndItsMessage() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                new Hippodrome(null));
        assertEquals("Horses cannot be null.", exception.getMessage());
    }
    @Test
    public void constructorShouldThrowExceptionWhenNameIsEmptyAndItsMessage() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                new Hippodrome(new ArrayList<>()));
        assertEquals("Horses cannot be empty.", exception.getMessage());
    }
    @Test
    public void getHorsesCorrect() {
        List<Horse> expectedHorses = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            expectedHorses.add(new Horse("name" + i, i, i));
        }
        Hippodrome hippodrome = new Hippodrome(expectedHorses);
        assertEquals(expectedHorses, hippodrome.getHorses());
    }
    @Test
    public void getMoveCorrect() {
        List<Horse> expectedHorses = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            Horse horse = Mockito.mock(Horse.class);
            expectedHorses.add(horse);
        }
        Hippodrome hippodrome = new Hippodrome(expectedHorses);
        hippodrome.move();
        for (int i = 0; i < 50; i++) {
            Mockito.verify(expectedHorses.get(i)).move();
        }
    }
    @Test
    public void getWinnerCorrect() {

        double[] doubles = {4, 65, 2, 4, 5, 2, 5, 65.34, 0, 23, 5, 64};
        List<Horse> expectedHorses = new ArrayList<>();
        for (int i = 0; i < doubles.length; i++) {
            expectedHorses.add(new Horse("name" + i, i, doubles[i]));
        }
        Hippodrome hippodrome = new Hippodrome(expectedHorses);

        assertEquals(65.34, hippodrome.getWinner().getDistance());
    }
}
