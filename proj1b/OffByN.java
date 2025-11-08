public class OffByN implements CharacterComparator {
    private int DIFF;
    public OffByN(int n) {
        DIFF = n;
    }

    @Override
    public boolean equalChars(char x, char y) {
        return Math.abs(x - y) == DIFF;
    }
}
