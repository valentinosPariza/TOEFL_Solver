package functions_1_2;

import java.util.ArrayList;
import java.util.regex.Pattern;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class ListCreationSentences {

	
	
	
	
	
	public static ArrayList<String[]> get_sentence_lists_from_files(String[] filenames)
	{
		
		StringBuilder stringObject=new StringBuilder();
		
		try {
			
			for(int i=0;i<filenames.length;i++)
			{
			BufferedReader inputStream=new BufferedReader(new FileReader(filenames[i]));
			
			String line=null;
			
			while((line=inputStream.readLine())!=null)
			{
				stringObject.append(line);
			}
			
			inputStream.close();
			
			}
			
		}catch(FileNotFoundException fileException)
		{
			System.err.println("File could not be opened.");
			System.exit(1);
		}
		catch(IOException eIO)
		{
			System.err.println("Problems with reading from the files");
			System.exit(0);
		}
		
		return getSentenceLists(stringObject.toString());
		
	}
	
	
	
	
	
	
	
	public static boolean isSemanticCharacter(char c)
	{
		return c==','||c==';'||c=='\''||c=='\"'||c=='`'||c==':'||c=='-';
	}
	
	
	public static boolean isBreakSentenceCharacter(char c)
	{
		return c=='.'||c=='?'||c=='!';
	}
	
	
	public static boolean isWhiteSpace(char c)
	{
		return c==' '||c=='\n'||c=='\t'||c=='\r'||c=='\f';
	}
	
	public static ArrayList<String[]> getSentenceLists(String text)
	{	
		
		
		String newtext=text;
		
		ArrayList<String[]> list=new ArrayList<String[]>(100);
		
		StringBuilder builder=new StringBuilder();
		
		
			char c=newtext.charAt(0);			// check the first character of the String to append it if it is
												// a requested character
				
		if(!isWhiteSpace(c)&&!isSemanticCharacter(c)&&!isBreakSentenceCharacter(c))   
			builder.append(Character.toLowerCase(c));
		
		
		for(int i=1;i<newtext.length();i++)		// beginning from i=1 because i=0 was checked
		{
			 c=newtext.charAt(i);				// the current character which is examined
			
			if(isBreakSentenceCharacter(c))
			{									
					char previousC=newtext.charAt(i-1);			// previous character
					
															// if a character which breaks the word is  
															// appeared consecutive times is ignored
					
															//  builder.length()>0 --> check for cases as " .;,:? "
													// when the length of the StringBuilder object is zero because 
													// between '.'  and '?' no important character was found
					
				   if(!isBreakSentenceCharacter(previousC)&& builder.length()>0)				
				    {
				      list.add(StringManipulation.split(builder.toString()));			 
				      builder=new StringBuilder();							  // create a new StringBuilder Object	    
				      														  // to create the new Sentence	
				   }				
				   
			}
		
			else if(!isSemanticCharacter(c)) 									// append the character if it 
			{																	// is not a semantic character
																				// as indicated in the if statement
				builder.append(Character.toLowerCase(c));
				
			}	
			else if(c=='\'')
			 {
				builder.append(" ");  // append space for indicating the break of the word which has symbol "\'" 
			 }	
			
			
		}
		
			if(builder.length()>0)			//In case if the last sentence wasn't added in the list.This happens
			{															       // If the last sentence does not
				list.add(StringManipulation.split(builder.toString()));		   // has '.' or '?' or '!' at the end.
			}
		
			return list;         
		
	}
	
}
