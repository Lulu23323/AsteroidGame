package si.model;

public enum AlienType {
	A(30, 30, 25), B(18, 18,50), C(8, 8 , 100), D(14,6,200);
	private int width;
	private int height;
	private int score;

	private AlienType(int w, int h, int s) {
		width = w;
		height = h;
		score = s;
	}

	public int getWidth() {
		return width;
	}

	public int getScore() {
		return score;
	}

	public int getHeight() {return height; }
}
