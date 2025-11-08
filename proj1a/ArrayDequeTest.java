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
        for (int i = 0; i < 100; i++) {
            arr.addFirst(i);
        }


        for (int i = 0; i < 100; i++) {
            System.out.print(arr.get(i) + " ");
        }

        System.out.println();
        for (int i = 0; i < 20; i++) {
            System.out.print(arr.removeFirst() + " ");
            System.out.print(arr.removeLast() + " ");
        }

        System.out.println(arr.size());
        arr.printDeque();
    }
}
