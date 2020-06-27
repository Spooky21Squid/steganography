package steganography;

public class ASCII {
	
	/**
	 * Unit Testing Function
	 * @param args
	 */
	public static void main(String[] args) {

	}
	
	/**
	 * encode msg to an array of bits using ASCII encoding. This method assumes each character is a valid ASCII character.
	 * @param msg The message to be encoded, using English characters, numbers and punctuation
	 * @return An array of booleans representing the msg
	 */
	public static boolean[] encode(String msg) {
		
		if (msg == null) {
			return null;
		}
		
		if (msg.length() == 0) {
			return new boolean[0];
		}
		
		boolean[] msgArray = new boolean[msg.length() * 7];
		int nextIndex = 0;
		char c;
		boolean[] binaryVal;
		
		// For each character in the String message,
		for (int i = 0; i < msg.length(); i++) {
			c = msg.charAt(i);
			binaryVal = extractBinary(c); // Get the binary value of that character as a boolean array,
			
			// Add that binary value onto the msgArray
			for (int j = 0; j < binaryVal.length; j++) {
				msgArray[nextIndex] = binaryVal[j];
				nextIndex++;
			}
		}
		
		return msgArray;		
	}
	
	/**
	 * decode bits to a String using ASCII encoding. If the length of the boolean array != 7n, the remaining bits at the end of the
	 * array will be discarded.
	 * @param bits A boolean array representing a message
	 * @return A string message
	 */
	public static String decode(boolean[] bits) {
		
		if (bits == null) {
			return null;
		}
		
		if (bits.length == 0) {
			return "";
		}
		
		String msg = "";
		int asciiVal = 0;
		
		// Step over the bits array in groups of 7
		for (int i = 0; i < bits.length; i+=7) {
			
			// If there are not enough remaining bits, discard them and break
			if (bits.length < i + 7) {
				break;
			}
			
			// For each bit in the group of 7 bits,
			for (int j = 0; j < 7; j++) {
				
				// If the bit is true, add its binary counterpart to the ascii value
				if (bits[i + j]) {
					switch (j) {
					case 0:
						asciiVal += 64;
						break;
					case 1:
						asciiVal += 32;
						break;
					case 2:
						asciiVal += 16;
						break;
					case 3:
						asciiVal += 8;
						break;
					case 4:
						asciiVal += 4;
						break;
					case 5:
						asciiVal += 2;
						break;
					case 6:
						asciiVal += 1;
						break;
					default:
						break;
					}
				}
			}
			
			// If the character is the ASCII Null character (0), return the messsage
			if (asciiVal == 0) {
				return msg;
			}
			
			// Add the character to the end of the message, and reset the ascii val
			msg += (char) asciiVal;
			asciiVal = 0;
		}
		return msg;
	}
	
	/**
	 * Convert a single character into a boolean array, representing the binary-ascii representation of the character.
	 * @param c The character to be converted
	 * @return A boolean array representing the binary-ascii value of the character, least-significant-bit on the right
	 */
	public static boolean[] extractBinary(char c) {
		boolean[] binaryArr = new boolean[7];
		int asciiVal = (int) c;
		int result = asciiVal;
		int digit;
		int index = 0;
		
		while (result != 0) {
			digit = result % 2; // Gets the least significant digit
			result /= 2; // Shifts the number one to the right (discards the lsd)
			binaryArr[6 - index] = digit == 1; // Convert digit to boolean by comparing it to 1
			index++;
		}
		
		// Pad out the binary number with 0s
		while (index <= 6) {
			binaryArr[6 - index] = false;
			index++;
		}
		
		return binaryArr;
	}
}
