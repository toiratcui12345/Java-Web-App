package tuananh.runnerz.run;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryRunRepositoryTest {
    InMemoryRunRepository repository;

    @BeforeEach
    void setUp() {
        repository = new InMemoryRunRepository();
        repository.create(new Run(1,
                "Monday Morning Run",
                LocalDateTime.now(),
                LocalDateTime.now().plus(30, ChronoUnit.MINUTES),
                3,
                Location.INDOOR, null));

        repository.create(new Run(2,
                "Wednesday Evening Run",
                LocalDateTime.now(),
                LocalDateTime.now().plus(60, ChronoUnit.MINUTES),
                6,
                Location.INDOOR, null));

    }

    @Test
    void shouldFindAllRuns() {
        List<Run> runs = repository.findAll();
        assertEquals(2, runs.size(), "Should have returned 2 runs");
    }

    @Test
    void shouldFindRunWithValidId() {
        var run = repository.findById(1).get();
        assertEquals("Monday Morning Run", run.title());
        assertEquals(3, run.miles());
    }

    @Test
    void shouldNotFindRunWithInvalidId() {
        RunNotFoundException notFoundException = assertThrows(
                RunNotFoundException.class,
                () -> repository.findById(3).get()
        );

        assertEquals("Could not find run", notFoundException.getMessage());
    }

    @Test
    void shouldCreateNewRun() {
        repository.create(new Run(3,
                "Friday Afternoon Run",
                LocalDateTime.now(),
                LocalDateTime.now().plus(45, ChronoUnit.MINUTES),
                5,
                Location.OUTDOOR, null));
        List<Run> runs = repository.findAll();
        assertEquals(3, runs.size(), "Should have returned 3 runs");
    }

    @Test
    void shouldUpdateRun() {
        repository.update(new Run(1,
                "Monday Morning Run - Updated",
                LocalDateTime.now(),
                LocalDateTime.now().plus(35, ChronoUnit.MINUTES),
                4,
                Location.OUTDOOR, null), 1);
        var run = repository.findById(1).get();
        assertEquals("Monday Morning Run - Updated", run.title());
        assertEquals(4, run.miles());
        assertEquals(Location.OUTDOOR, run.location());
    }

    @Test
    void shouldDeleteRun() {
        repository.delete(1);
        List<Run> runs = repository.findAll();
        assertEquals(1, runs.size());
    }

}