package api;

public class Params {
	private int numCol;
	private int startCol;
	private int endCol;
	
	public Params(int numCol, int startCol, int endCol) {
		this.setNumCol(numCol);
		this.setStartCol(startCol);
		this.setEndCol(endCol);
	}

	/**
	 * @return the numCol
	 */
	public int getNumCol() {
		return numCol;
	}

	/**
	 * @param numCol the numCol to set
	 */
	public void setNumCol(int numCol) {
		this.numCol = numCol;
	}

	/**
	 * @return the startCol
	 */
	public int getStartCol() {
		return startCol;
	}

	/**
	 * @param startCol the startCol to set
	 */
	public void setStartCol(int startCol) {
		this.startCol = startCol;
	}

	/**
	 * @return the endCol
	 */
	public int getEndCol() {
		return endCol;
	}

	/**
	 * @param endCol the endCol to set
	 */
	public void setEndCol(int endCol) {
		this.endCol = endCol;
	}
}
