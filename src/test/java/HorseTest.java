import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

public class HorseTest {

    @Test
    public void constructorShouldThrowExceptionWhenNameIsNullAndItsMessage() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                new Horse(null, 0, 0));
        assertEquals("Name cannot be null.", exception.getMessage());
    }
    @ParameterizedTest
    @ValueSource(strings = {"  ", "       ", "", "\u0020"})
    public void constructorShouldThrowExceptionWhenNameIsBlank(String name) {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                new Horse(name, 0, 0));
        assertEquals("Name cannot be blank.", exception.getMessage());
    }
    @Test
    public void constructorShouldThrowExceptionWhenSpeedIsNeg() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                new Horse("sdf", -100, 0));
        assertEquals(exception.getMessage(), "Speed cannot be negative.");
    }
    @Test
    public void constructorShouldThrowExceptionWhenDistIsNeg() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                new Horse("sdf", 0, -100));
        assertEquals(exception.getMessage(), "Distance cannot be negative.");
    }

    @Test
    void getNameCorrect() {
        String name = "Wind";
        Horse horse = new Horse(name, 0, 0);
        assertEquals(name, horse.getName());
    }
    @Test
    void getSpeedCorrect() {
        double speed = 10.2345;
        Horse horse = new Horse("name", speed, 0);
        assertEquals(speed, horse.getSpeed());
    }
    @Test
    void getDistanceCorrect() {
        double distance = 10.2345;
        Horse horse = new Horse("name", 0, distance);
        assertEquals(distance, horse.getDistance());
    }

    @Test
    public void moveCallsRandomWithCorrectDistance() {
        try (MockedStatic<Horse> horseMockedStatic = Mockito.mockStatic(Horse.class)) {
            Horse horse = new Horse("name", 0, 0);
            horse.move();

            horseMockedStatic.verify(() -> Horse.getRandomDouble(0.2, 0.9));
        }
    }

    @ParameterizedTest
    @CsvSource({
            "10, 100, 0.5, 105",
            "20, 50, 0.2, 54",
            "30, 10, 0.9, 37"
    })
    public void moveCallsRandomWithCorrectSolutions(double speed, double dist, double resultOfGetRandomDouble, double expectedMoveResult) {

        try (MockedStatic<Horse> horseMockedStatic = Mockito.mockStatic(Horse.class)) {
            horseMockedStatic.when(() -> Horse.getRandomDouble(0.2, 0.9)).thenReturn(resultOfGetRandomDouble);

            Horse horse = new Horse("name", speed, dist);
            horse.move();
            assertEquals(expectedMoveResult, horse.getDistance(), 0.001);
        }
    }
}
