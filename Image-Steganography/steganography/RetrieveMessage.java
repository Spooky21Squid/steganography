package steganography;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class RetrieveMessage {
	
	/**
	 * retrieve an (optionally encrypted) message in an image
	 * @param args
	 */
	public static void main(String[] args) {
		
		if (args.length != 1) {
			System.out.println("Program Exited: Number of arguments was not 1.");
			System.exit(1);
		}
		
		if (!args[0].endsWith(".png")) {
			System.out.println("Program Exited: File supplied was not a .png.");
			System.exit(1);
		}
		
		BufferedImage image = null;
		
		try {
			image = ImageIO.read(new File(args[0]));
		} catch (IOException e) {
			System.out.println("Program Exited: Could not read image.");
			e.printStackTrace();
			System.exit(1);
		}
		
		int width = image.getWidth();
		int height = image.getHeight();
		
		boolean[] bits = new boolean[width * height]; // Stores all the bits extracted from the image
		int pixel = 0; // The pixel we are currently looking at
		int bitIndex; // The next available index for a bit inside the bits array
		
		for (int row = 0; row < height; row++) {
			for (int col = 0; col < width; col++) {
				
				// This loop iterates through ALL the pixels in the image
				// It could be made more efficient if it stops after it has found the ASCII null character
				
				try {
					pixel = image.getRGB(col, row) & 0xFFFFFF;
				} catch (ArrayIndexOutOfBoundsException e) {
					e.printStackTrace();
					System.out.println("Row: " + row);
					System.out.println("Col: " + col);
					System.exit(1);
				}
				
				bitIndex = (row * width) + col; // Converts the 2d position in the image's pixels array to a 1d position
				
				if (pixel % 2 == 0) {
					bits[bitIndex] = false;
				} else {
					bits[bitIndex] = true;
				}
				
			}
		}
		
		String msg = ASCII.decode(bits);
		System.out.println("Secret Message: ");
		System.out.println(msg);		
	}
}