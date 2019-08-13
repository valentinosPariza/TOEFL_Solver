package TestFunctions;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class TestFunctions {

	public static void main(String[] args) {

		String[] filenames = new String[3];
		filenames[0] = "brown-train-sentences.txt";
		filenames[1] = "pg2600.txt";
		filenames[2] = "pg7178.txt";
		long time=0;
		long endtime=0;
		
		System.out.println("Start time in seconds: "+(time=System.currentTimeMillis())/1000+" In milliseconds: "+time);
		
		ArrayList<String[]> list=get_sentence_lists_from_files(filenames);
		
		endtime=System.currentTimeMillis();
		
		System.out.println("End time in seconds: "+(endtime/1000)+"  in milliseconds: "+endtime);
		System.out.println("The time needed to invoke the method and the method return the ArrayList is:\nIn milliseconds: "+(endtime-time)+"\nIn seconds: "+((double)(endtime-time)/1000));
	
		printToFiles(list, "File.txt");
	}
	
	
	
	
	
	public static void printToFiles(ArrayList<String[]> list,String filename)
	{
		PrintWriter outputStream=null;
		try {
			 outputStream=new PrintWriter(new FileOutputStream(filename));
			
			for(String[] sentence:list)
			{
				
				for(int i=0;i<sentence.length;i++)
				{
					if(i==sentence.length-1)
					outputStream.print(sentence[i]);
					else 	outputStream.print(sentence[i]+",");
						
				}
				outputStream.println();
				outputStream.println();
				outputStream.println();
			}
			
			outputStream.close();
			 
		}catch(FileNotFoundException fe)
		{
			System.out.println("File could not be opened");
			
		}catch(IOException e)
		{
			System.out.println("Problems writing in the file");
		}
		finally {
			outputStream.close();
		}
		
	}
}
