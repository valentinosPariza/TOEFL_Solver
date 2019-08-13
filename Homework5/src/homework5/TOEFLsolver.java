package homework5;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
/**
*This class is responsible to represent a system which uses algorithms to answer in questions type of "which is the synonymous of..."
*.In this class the class {@link StringManipulation} is used for manipulating Strings, an algorithm as given is used in order
*to caclculate the synonymus similarity and many more which will be explained below.
*
*
*
*/
public class TOEFLsolver {

	
	/**
	 * This method returns a TOEFLsolver object so the user can use the object to
	 * run the functionalities of the programme.
	 * 
	 * @return TOEFLsolver Object to use the required functionalities of the
	 *         programme
	 * @author Eleni Ioakim
	 */
	public static TOEFLsolver getSolver() {
		return new TOEFLsolver();
	}

	/**
	 * This method takes an array of String which represents the file-names.It reads
	 * the data from the files using the class {@link java.io.BufferedReader} and
	 * creates a String using the {@link java.lang.StringBuilder} which then uses it
	 * in the method {@link #getSentenceLists(String)}in order to get an
	 * ArrayList<String[]> which inside has all the sentences broken in words in
	 * each String cell in the array.
	 * 
	 * @param filenames
	 *            a String[] array which inside has the String names of the files
	 * @return an ArrayList<String[]> which inside has arrays of String
	 * @author valentinos Pariza
	 */
	public ArrayList<String[]> get_sentence_lists_from_files(String[] filenames) {

		StringBuilder stringObject = new StringBuilder();
		TOEFLsolver solver = getSolver();

		try {

			for (int i = 0; i < filenames.length; i++) {
				BufferedReader inputStream = new BufferedReader(new FileReader(filenames[i]));

				String line = null;

				while ((line = inputStream.readLine()) != null) {
					stringObject.append(line);
				}

				inputStream.close();

			}

		} catch (FileNotFoundException fileException) {
			System.err.println("File could not be opened.");
			System.exit(1);
		} catch (IOException eIO) {
			System.err.println("Problems with reading from the files");
			System.exit(0);
		}

		return solver.getSentenceLists(stringObject.toString());

	}

	/**
	 * This method examines whether a character c belongs to o the set of {',' , ';'
	 * , '\'' , '\"' , '`' , ':' , '-'}
	 * 
	 * 
	 * @param c
	 *            character to be examined if belongs to the set of characters
	 * @return true if the character is one of these
	 *         c==','||c==';'||c=='\''||c=='\"'||c=='`'||c==':'||c=='-' else false
	 * @author valentinos Pariza
	 */
	private static boolean isSemanticCharacter(char c) {
		return c == ',' || c == ';' || c == '\'' || c == '\"' || c == '`' || c == ':' || c == '-';
	}

	/**
	 * This method examines whether a character c belongs to o the set of {'!' , '?'
	 * , '.' }
	 * 
	 * 
	 * @param c
	 *            character to be examined if belongs to the set of characters
	 * @return true if the character is one of these c=='.'||c=='?'||c=='!' else
	 *         false
	 * @author valentinos Pariza
	 */
	private static boolean isBreakSentenceCharacter(char c) {
		return c == '.' || c == '?' || c == '!';
	}

	/**
	 * This method examines whether a character c belongs to o the set of {' ' ,
	 * '\t' , '\n' , '\r' , '\f'}
	 * 
	 * 
	 * @param c
	 *            character to be examined if belongs to the set of characters
	 * @return true if the character is one of these c=='
	 *         '||c=='\n'||c=='\t'||c=='\r'||c=='\f' else false
	 * @author valentinos Pariza
	 */
	private static boolean isWhiteSpace(char c) {
		return c == ' ' || c == '\n' || c == '\t' || c == '\r' || c == '\f';
	}

	/**
	 * This method takes a String and breaks it into sentences and the sentences are
	 * broken further (splited) into pieces-words.Each cell of the ArrayList which
	 * will be created ,represents a sentence and in each cell a String array is put
	 * which inside has all the words of the indicated sentence.This method uses
	 * {@link StringManipulation#split(String)} to split the words in each sentence.
	 * 
	 * 
	 * @param text
	 *            a String which is going to be broken into sentences and the
	 *            sentences into words
	 * @return an ArrayList<String[]>,which is an Arraylist which indicates the
	 *         sentences andcontains arrays of String which represent the wordsS
	 * @author valentinos Pariza
	 */
	private ArrayList<String[]> getSentenceLists(String text) {

		String newtext = text;

		ArrayList<String[]> list = new ArrayList<String[]>(100);

		StringBuilder builder = new StringBuilder();

		char c = newtext.charAt(0); // check the first character of the String to append it if it is
									// a requested character

		if (!isWhiteSpace(c) && !isSemanticCharacter(c) && !isBreakSentenceCharacter(c))
			builder.append(Character.toLowerCase(c));

		for (int i = 1; i < newtext.length(); i++) // beginning from i=1 because i=0 was checked
		{
			c = newtext.charAt(i); // the current character which is examined

			if (isBreakSentenceCharacter(c)) {
				char previousC = newtext.charAt(i - 1); // previous character

				// if a character which breaks the word is
				// appeared consecutive times is ignored

				// builder.length()>0 --> check for cases as " .;,:? "
				// when the length of the StringBuilder object is zero because
				// between '.' and '?' no important character was found

				if (!isBreakSentenceCharacter(previousC) && builder.length() > 0) {
					list.add(StringManipulation.split(builder.toString()));
					builder = new StringBuilder(); // create a new StringBuilder Object
													// to create the new Sentence
				}

			}

			else if (!isSemanticCharacter(c)) // append the character if it
			{ // is not a semantic character
				// as indicated in the if statement
				builder.append(Character.toLowerCase(c));

			} else {
				builder.append(" "); // append space for indicating the break of the word which has symbol "\'"
			}

		}

		if (builder.length() > 0) // In case if the last sentence wasn't added in the list.This happens
		{ // If the last sentence does not
			list.add(StringManipulation.split(builder.toString())); // has '.' or '?' or '!' at the end.
		}

		return list;

	}

	/**
	 * Function 3 (build_semantic_descriptors) is responsible for irritating through
	 * the sentences that they were created from the Function 2 and creating the
	 * HashMap that has the semantic descriptors of the words.
	 * 
	 * @param sentences
	 *            The ArrayList that contains the sentences.
	 * @return HashMap<String, HashMap<String, Integer>> The HashMap that represents
	 *         the semantic descriptors for each word.
	 * @author Eleni Ioakim
	 */
	public HashMap<String, HashMap<String, Integer>> build_semantic_descriptors(ArrayList<String[]> sentences) {

		HashMap<String, HashMap<String, Integer>> d = new HashMap<String, HashMap<String, Integer>>();

		for (String[] s : sentences) {

			for (String k : s) {

				HashMap<String, Integer> inner = null;

				if (d.containsKey(k)) {
					inner = d.get(k);

				} else {
					inner = new HashMap<String, Integer>();
				}

				for (String v : s) {

					if (inner.containsKey(v)) {
						inner.put(v, inner.get(v) + 1);

					} else
						inner.put(v, 1);

				}

				d.put(k, inner);
			}

		}

		return d;
	}

	/**
	 * Function 4 (most_similar_word) has the responsibility to find the biggest
	 * cosine similarity between a word and an array of words.
	 * 
	 * @param word
	 *            The word that we are looking a synonym for.
	 * @param choices
	 *            The array of words that will find the cosine similarity between
	 *            them and the word.
	 * @param semantic_descriptors
	 *            The HashMap that has the semantic descriptor vectors of the words.
	 * @return String It will return the word from the words array that has the
	 *         biggest cosine similarity with the word given as a parameter.
	 * 
	 * @author Eleni Ioakim
	 */
	private String most_similar_word(String word, String[] choices,
			HashMap<String, HashMap<String, Integer>> semantic_descriptors) {

		double max = 0.0;
		int index = 0;

		double sim = 0.0;

		HashMap<String, Integer> wordVector = semantic_descriptors.get(word);

		if (wordVector == null)
			return "-1";

		HashMap<String, Integer> choiceVector;
		int length = choices.length;
		for (int i = 0; i < length; i++) {

			choiceVector = semantic_descriptors.get(choices[i]);

			if (choiceVector == null)
				return "-1";

			for (String key : wordVector.keySet()) {
				if (choiceVector.containsKey(key)) {
					int n = wordVector.get(key);
					sim += n * n;

				}
			}
			sim = sim / (norm(wordVector) * norm(choiceVector));
			if (sim > max) {
				max = sim;
				index = i;
			}

		}

		return choices[index];
	}

	/**
	 * norm is a helper method of the most_similar_word method. It calculates the
	 * sum of squares of the values, that belong to the HashMap of the words that we
	 * are trying to find the cosine similarity.
	 * 
	 * @param vec
	 *            The semantic descriptor vector of the word that we want the sum of
	 *            squares of it's values.
	 * @return double The value of the sum of squares.
	 * @author Eleni Ioakim
	 */
	private static double norm(HashMap<String, Integer> vec) {
		double sum_of_squares = 0.0;

		for (String key : vec.keySet()) {
			int n = vec.get(key);
			sum_of_squares += n * n;
		}
		return Math.sqrt(sum_of_squares);
	}

	/**
	 * This method is responsible to read data from the filename which contains the
	 * questions ,the answers as well as the choices of each question.It uses the
	 * method {@link most_similar_word(String,String[],HashMap<String,
	 * HashMap<String, Integer>>)} in order to convert from the choices the choice
	 * of the system.Also at the end of the choicing of the system.The program
	 * calculates the possibility of correctness of its choices.
	 *
	 * @param fileaname
	 *            the name of the file from which the answers must be reade
	 * 
	 * @param descriptors
	 *            HashMap<String,hashmap<String,Integer>> The HashMap that has the
	 *            semantic descriptor vectors of the words.
	 * @author Valentinos Pariza
	 * 
	 */
	public void run_similarity_test(String filename, HashMap<String, HashMap<String, Integer>> descriptors) {

		TOEFLsolver solver = getSolver();
		int correctAnswers = 0;
		int questions = 0;

		try {

			String outputfilename = "correctnessOf" + filename;

			BufferedReader inputStream = new BufferedReader(new FileReader(filename));
			PrintWriter outputStream = new PrintWriter(new FileOutputStream(outputfilename));

			String line = null;

			while ((line = inputStream.readLine()) != null) {

				String[] questionpieces = StringManipulation.split(line);

				if (questionpieces.length > 0) {
					++questions;
					String[] choices = new String[questionpieces.length - 2];

					for (int i = 0; i < choices.length; i++)
						choices[i] = questionpieces[i + 2]; // read the choices from the file which are placed plus 2
															// words from the beginning

					// Excecute the function most_similar_word(questionpieces[0],choices,semantic)
					// At questionpieces[1] is the correct answer
					// check for the correctness Increase the correctAnswers if it is correct
					String answer = solver.most_similar_word(questionpieces[0], choices, descriptors);
					if (answer.equals(questionpieces[1]))
						correctAnswers++;
					
					if(!answer.equals("-1"))
						outputStream.print("Answer (" + questions + ") by system is " + answer + " and the correct answer is "+questionpieces[1]);
					else
						outputStream.print("Answer (" + questions + ") by system is " + answer + ". The programme doesn't know some words.");
					outputStream.println();

				}

				
			}
			
			outputStream.println();
				outputStream.println("The propotion of correctness is : " + ((double) correctAnswers / questions));
				outputStream.println("The percentage of correctness is : "
						+ (((double) correctAnswers * 100) / questions) + "%" + "\n");
			
			outputStream.println();
			
			outputStream.println("\nOur programme learned "+descriptors.size()+" different words.");
			outputStream.close();
			inputStream.close();

		} catch (FileNotFoundException fileException) {
			System.out.println("The file could not be found or opened.");

		} catch (IOException e) {
			System.out.println("Problems reading on the file:");
		}

	}

	/**
	 * Valentinos Pariza ID:909759 Eleni Ioakim ID:1003771
	 */
	public static void main(String[] args) {

		String[] filenames = new String[3];
		filenames[0] = args[0];
		filenames[1] = args[1];
		filenames[2] = args[2];
		String questionsFileName = args[3];

		TOEFLsolver solver1 = getSolver();

		ArrayList<String[]> list = solver1.get_sentence_lists_from_files(filenames);

		HashMap<String, HashMap<String, Integer>> d = solver1.build_semantic_descriptors(list);

		solver1.run_similarity_test(questionsFileName, d);

	}

}
