import java.util.Random;
public class ArrayDequeTest {
    public static void main() {
        ArrayDeque<Integer> arr = new ArrayDeque<>();

//        arr.addFirst(4);
//        arr.addFirst(3);
//        arr.addLast(3);
//        arr.addFirst(5);
//        arr.addLast(112);
//        arr.addFirst(1);
//        arr.addFirst(4);

        Random rand = new Random();
        for (int i = 0; i < 5000; i++) {
            arr.addLast(i);
            arr.addFirst(i);
        }

        int size = arr.size();
        System.out.println(size);

        for (int i = 0; i < 2000; i++) {
            arr.removeFirst();
            arr.removeLast();
        }
        arr.printDeque();
    }
}
