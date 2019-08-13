package homework5;
public class StringManipulation {

	/**
	 * @param words
	 *            an array type of String[] which is used to get the data in it and
	 *            place them into a bigger array
	 * @return a String[] array with all the data from the array words,but bigger
	 *         capacity
	 * @author Valentinos Pariza
	 */
	public static String[] expand(String[] words) {

		String[] newArray = new String[words.length * 2];

		for (int i = 0; i < words.length; i++) {
			newArray[i] = words[i];
		}

		return newArray;

	}

	/**
	 * 
	 * @param array
	 *            a String[] array to be trimmed
	 * @param length
	 *            the current number of the Strings in the array
	 * @return a String[] array which is an array with length as indicated and all
	 *         the data from the array(from position 0-->length-1) which are given
	 *         into the array.
	 * @author Valentinos Pariza
	 */
	public static String[] trimArray(String[] array, int length) {

		String[] newArray = new String[length];

		for (int i = 0; i < length; i++) {
			newArray[i] = array[i];
		}

		return newArray;

	}

	/**
	 * 
	 * @param text
	 *            a String which is given to split it into pieces
	 * @return a String[] array which is an Array which inside has into consecutive
	 *         positions all the substrings (broken pieces by characters '
	 *         ','\n','\t' from the String which is given as parameter).
	 * @author Valentinos Pariza
	 */
	public static String[] split(String text) {
		if (text == null || text.length() <= 0)
			return null;

		String textCopy = text;

		int length = textCopy.length();

		String[] words = new String[length / 5 + 4]; // Average number of characters in every word ,plus 4 in case of a
														// word with characters less than 5

		int index = 0; // index of a non "white space"='\n'||' '||'\t'||'\r'||'\f' (Hypothetically for
						// this
						// exercise there are only these)

		char c = textCopy.charAt(0); // checking for the first character in order to be legal to examine the previous
										// character of every character of the word in the loop

		if (c == ' ' || c == '\n' || c == '\t' || c == '\r' || c == '\f') // if the first character is a "white space"
																			// then it is
			index = 1; // ignored and not included in the next substring of the
						// text

		int arrayIndex = 0; // the available index where a String can be placed in the array.Also the number
							// of Strings in the array

		for (int i = 1; i < length; i++) // Beginning from i=1 because the first character was already checked.
		{

			c = textCopy.charAt(i); // c is the current character which is being examined

			if (c == ' ' || c == '\n' || c == '\t' || c == '\r' || c == '\f') {
				char previousC = textCopy.charAt(i - 1); // no possibility to go out of range of the String
															// because it starts from i=1

				if (previousC != ' ' && previousC != '\n' && previousC != '\t' && previousC != '\r'
						&& previousC != '\f') {
					words[arrayIndex++] = textCopy.substring(index, i); // the String which does not have
																		// previous any whiteSpace and is located
																		// in the text from index to the current
																		// position minus 1 (i-1) is taken and placed
																		// in the array
					if (arrayIndex > words.length - 1)
						words = expand(words); // expand the array to the double size if the next position
												// in the array is greater than the last position of the
												// Array

				}
				index = i + 1; // if a "white space" was found then after checking, the index must be placed
								// after
								// the "white space" in order to represent the position of a non "white space"
								// character
			}

		}

		// After finishing the for-Loop,the variable c is the last character of the
		// text.If that character is not a
		// "white space" then the last String of the text was not placed inside the
		// array.If so it places it.

		if (c != ' ' && c != '\n' && c != '\t' && c != '\r' && c != '\f') {

			words[arrayIndex++] = textCopy.substring(index, length); // placing the last String in the array and
		} // increasing the index== the current length
			// of the array

		return trimArray(words, arrayIndex); // trim the array to the appropriate size
	}

}
