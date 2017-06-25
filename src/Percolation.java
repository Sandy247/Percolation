/**
 * Created by sandy on 6/19/17.
 */
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;
public class Percolation {
    private int n;//, id[], size[];
    private WeightedQuickUnionUF uf;
    private boolean[] open;
    public Percolation(int n)
    {
        if (n <= 0)
            throw new IllegalArgumentException("Invalid n value!!!");
        this.n = n;
        uf=new WeightedQuickUnionUF(n*n+2);
        // id = new int[n*n+2];
        // size = new int[n*n+2];
        open = new boolean[n*n+2];
        for (int i = 0; i < n*n+2; i++) {
            // id[i] = i;
            // size[i] = 1;
            open[i] = false;
        }
        for (int c = 1; c <= n; c++)
            uf.union(0, c);
        connectToBottom();
        open[0] = true;
        open[n*n+1] = true;
    }
    private int position(int row,int col)
    {

        if (row < 1 || col < 1 || row > n+1 || col > n)
            throw new IndexOutOfBoundsException("Invalid indices ("+row+","+col+")");
        return (row-1)*n+col;
    }
    private void connectToBottom()
    {
        for (int c = 1; c <= n; c++)
        if (uf.connected(position(n, c), 0))
            uf.union(position(n, c), n*n+1);
    }
    /* private int root(int p)
    {
        int i = p, j;
        while (i != id[i])
            i = id[i];
        while (id[p]!= i)
        {
            j = id[p];
            id[p] = i;
            p = j;
        }
        return i;
    }
    private void union(int p, int q)
    {
        int i = root(p);
        int j = root(q);
        if (!(i == j))
        {
            if (size[i] < size[j]) {
                id[i] = j;
                size[j] += size[i];
            }
            else {
                id[j] = i;
                size[i] += size[j];
            }
        }
    }
    private boolean connected(int p, int q)
    {
        return root(p) == root(q);
    } */
    public void open(int row, int col)
    {
        int pos = position(row, col);
        if (!isOpen(row, col)) {
            if (col > 1 && isOpen(row, col-1))
                uf.union(pos, pos - 1);
            if (col < n && isOpen(row, col+1))
                uf.union(pos, pos + 1);
            if (row > 1 && isOpen(row-1, col))
                uf.union(pos, pos - n);
            if (row < n && isOpen(row+1, col))
                uf.union(pos, pos + n);
            connectToBottom();
            open[pos] = true;
        }
    }
    public boolean isOpen(int row, int col)
    {
       return open[position(row, col)];
    }
    public boolean isFull(int row, int col)
    {
        return isOpen(row, col) && uf.connected(position(row, col), 0);
    }
    public int numberOfOpenSites()
    {
        int count = 0;
        for (int i = 1; i <= n*n; i++)
            if (open[i])
                count++;
        return count;
    }
    public boolean percolates()
    {
        if (n == 1)
            return isOpen(1, 1) && isFull(n+1, 1);
        return isFull(n+1,1);
    }
    public static void main(String[] args)
    {
        Percolation perc = new Percolation(Integer.parseInt(args[0]));
        int row, col;
        while (!perc.percolates())
        {
            row = StdIn.readInt();
            col = StdIn.readInt();
            perc.open(row, col);
            StdOut.println(perc.numberOfOpenSites()+","+perc.percolates());
        }
    }
}
