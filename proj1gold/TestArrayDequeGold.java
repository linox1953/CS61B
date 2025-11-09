import static org.junit.Assert.*;
import org.junit.Test;

public class TestArrayDequeGold {

    @Test
    public void testStudentSolution() {

        StudentArrayDeque<Integer> stuArr = new StudentArrayDeque<>();
        ArrayDequeSolution<Integer> solArr = new ArrayDequeSolution<>();

        String message = "";

        for (int i = 0; i < 10; i += 1) {
            double numberBetweenZeroAndOne = StdRandom.uniform();

            if (numberBetweenZeroAndOne < 0.5) {
                message += "\naddLast(" + i + ")";
                stuArr.addLast(i);
                solArr.addLast(i);
            } else {
                message += "\naddFirst(" + i + ")";
                stuArr.addFirst(i);
                solArr.addFirst(i);
            }
        }

        for (int i = 0; i < 10; i += 1) {
            double numberBetweenZeroAndOne = StdRandom.uniform();

            if (numberBetweenZeroAndOne < 0.5) {
                Integer stuFirst = stuArr.removeFirst();
                message += "\nremoveFirst()";
                assertEquals(message, solArr.removeFirst(), stuFirst);
            } else {
                Integer stuLast = stuArr.removeLast();
                message += "\nremoveLast()";
                assertEquals(message, solArr.removeLast(), stuLast);
            }
        }
    }
}
