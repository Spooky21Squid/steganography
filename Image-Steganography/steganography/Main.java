package steganography;

/**
 * Testing the functions of this project
 *
 */
public class Main {

	public static void main(String[] args) {
		
		int[] numbers = new int[10];
		int row = -1;
		
		for (int i = 0; i < 100; i++) {
			System.out.print("col: " + (i % 6) + ", ");
			if (i % 6 == 0) {
				row += 1;
			}
			System.out.println("row: " + row);
		}
		
	}

}
