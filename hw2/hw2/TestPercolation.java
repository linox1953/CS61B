package hw2;

import org.junit.Test;
import static org.junit.Assert.*;

public class TestPercolation {

    @Test
    public void test() {
        Percolation pl = new Percolation(5);
        pl.open(0, 0);
        pl.open(0, 1);
        pl.open(1, 3);

        assertFalse(pl.percolates());

        assertTrue(pl.isOpen(0, 1));
        assertFalse(pl.isOpen(0, 2));

        assertTrue(pl.isFull(0, 0));
        assertTrue(pl.isFull(0, 1));
        assertFalse(pl.isFull(1, 3));
        assertFalse(pl.isFull(2, 3));

        assertEquals(3, pl.numberOfOpenSites());

        pl.open(1, 1);
        pl.open(1, 2);
        pl.open(2, 3);
        pl.open(3, 3);
        pl.open(4, 3);

        assertTrue(pl.percolates());

        Percolation pl2 = new Percolation(5);

        pl2.open(1, 1);
        pl2.open(2, 1);
        pl2.open(3, 1);
        pl2.open(0, 0);
        pl2.open(0, 1);
        pl2.open(4, 1);

        assertTrue(pl2.percolates());

        Percolation pl3 = new Percolation(5);
        assertEquals(0, pl3.numberOfOpenSites());
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                pl3.open(i, j);
            }
        }
        assertEquals(25, pl3.numberOfOpenSites());
        assertTrue(pl3.percolates());

        PercolationStats ps = new PercolationStats(500, 1000, new PercolationFactory());
        System.out.println(ps.mean());
        System.out.println(ps.stddev());
        System.out.println(ps.confidenceHigh());
        System.out.println(ps.confidenceLow());
    }
}
