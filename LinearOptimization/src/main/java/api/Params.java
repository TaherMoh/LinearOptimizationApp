package api;

public class Params {
	private int numCol;
	private int startCol;
	private int endCol;
	private String weights;
	private String uploadFileName;
	
	public Params(int numCol, int startCol, int endCol, String weights, String uploadFileName) {
		this.setNumCol(numCol);
		this.setStartCol(startCol);
		this.setEndCol(endCol);
		this.setWeights(weights);
		this.setUploadFileName(uploadFileName);
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

	/**
	 * @return the weights
	 */
	public String getWeights() {
		return weights;
	}

	/**
	 * @param weights the weights to set
	 */
	public void setWeights(String weights) {
		this.weights = weights;
	}

	/**
	 * @return the uploadFileName
	 */
	public String getUploadFileName() {
		return uploadFileName;
	}

	/**
	 * @param uploadFileName the uploadFileName to set
	 */
	public void setUploadFileName(String uploadFileName) {
		this.uploadFileName = uploadFileName;
	}
}
