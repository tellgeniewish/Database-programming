package debug;

public class Counter {
	static private int total = 0;
	private int result = 0;

	public int getResult() {
		return result;
	}

	public static int getTotal() {
		return total;
	}
	
	public void count() {
		int pt = 0;
		for (int i = 1; i <= 100; i++) {
			result += i;
			System.out.println("i = " + i);
		}		
		pt = getTotal();
		total = pt + result;
	}
}
