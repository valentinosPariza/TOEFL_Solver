package functions_5;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

public class RunSimilarityFunction {

	
	
	
	public static void run_similarity_test(String filename,HashMap<String,HashMap<String,Integer>> descriptors)
	{
		
		int correctAnswers=0;
		int questions=0;
		
		
		try {
			
			String outputfilename="correctnessOf"+filename;
			
			BufferedReader inputStream=new BufferedReader(new FileReader(filename));
			PrintWriter outputStream=new PrintWriter(new FileOutputStream(outputfilename));
			
			String line=null;
			
			
			while((line=inputStream.readLine())!=null)
			{
				
			String[] questionpieces=functions_1_2.StringManipulation.split(line);
			
			if(questionpieces.length>0)
			{
					++questions;
					String[] choices=new String[questionpieces.length-2];
					
					for(int i=0;i<choices.length;i++)
					choices[i]=questionpieces[i+2];		// read the choices from the file which are placed plus 2
														// words from the beginning
					
					
					// Excecute the function most_similar_word(questionpieces[0],choices,semantic)
					// At questionpieces[1] is the correct answer
					// check for the correctness Increase the correctAnswers if it is correct
					String answer=most_similar_word(questionpieces[0],choices,descriptors);
					if(answer.equals(questionpieces[1]))
						correctAnswers++;
					
					
					outputStream.print("Answer ("+questions+") by system is "+answer);
					outputStream.println();
					
					
			}
			
			outputStream.println();
			outputStream.println("The propotion of correctness is : "+((double)correctAnswers/questions));
			outputStream.println("The percentage of correctness is : "+(((double)correctAnswers*100)/questions)+"%");
			}
			
			
			
			outputStream.close();
			inputStream.close();
			
		}catch(FileNotFoundException fileException)
		{
			System.out.println("The file could not be found or opened.");
			
		}
		catch(IOException e)
		{
			System.out.println("Problems reading on the file:");
		}
		
		
		
		
	}
	
	
}
