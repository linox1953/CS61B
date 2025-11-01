public class HelloNumbers {
    public static void main(String[] args) {
        int x = 0;
        int n = 1;
        while (n <= 10) {
            System.out.print(x + " ");
            x = x + n++;
        }
    }
}