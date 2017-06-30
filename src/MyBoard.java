
public interface MyBoard {
	public int hamming();
	public int manhattan();
	public boolean equals(Board y);
	public Iterable<Board> neighbors();
	public String toString();
}
