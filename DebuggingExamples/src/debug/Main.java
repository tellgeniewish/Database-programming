package debug;

public class Main {
	
	public static void main(String[] args) {
		Counter counter1 = new Counter();
		counter1.count();
	    System.out.println("We have counted " + counter1.getResult() + " using Counter 1");

		Counter counter2 = new Counter();
	    counter2.count();
	    System.out.println("We have counted " + counter2.getResult() + " using Counter 2");

	    System.out.println("Total: " + Counter.getTotal());
	}
}
