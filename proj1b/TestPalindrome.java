import org.junit.Test;
import static org.junit.Assert.*;

public class TestPalindrome {
    // You must use this palindrome, and not instantiate
    // new Palindromes, or the autograder might be upset.
    static Palindrome palindrome = new Palindrome();

    @Test
    public void testWordToDeque() {
        Deque d = palindrome.wordToDeque("persiflage");
        String actual = "";
        for (int i = 0; i < "persiflage".length(); i++) {
            actual += d.removeFirst();
        }
        assertEquals("persiflage", actual);
    }

    @Test
    public void testPalindrome() {
        assertFalse(palindrome.isPalindrome("pig"));
        assertFalse(palindrome.isPalindrome("cat"));
        assertTrue(palindrome.isPalindrome("mom"));
        assertTrue(palindrome.isPalindrome("noooooon"));
        assertTrue(palindrome.isPalindrome(""));
        assertTrue(palindrome.isPalindrome("d"));

        OffByOne offByOne = new OffByOne();
        assertTrue(palindrome.isPalindrome("flake", offByOne));
        assertFalse(palindrome.isPalindrome("noooooon", offByOne));

        OffByN offBy5 = new OffByN(5);
        assertTrue(palindrome.isPalindrome("af", offBy5));
        assertFalse(palindrome.isPalindrome("db", offBy5));

        OffByN offBy2 = new OffByN(2);
        assertTrue(palindrome.isPalindrome("ce", offBy2));
        assertFalse(palindrome.isPalindrome("do", offBy2));
    }
}
