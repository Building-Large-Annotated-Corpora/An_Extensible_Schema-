import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;



public class Line_Slicer 
{

	 public static void main(String[] args) throws Exception 
    {
		 String name_list_file = "../../input/named_entities_mltp.txt";
		 
		 System.out.println("Starting...");
		 
		 stripDuplicatesFromFile( name_list_file );
		 
		 System.out.println("Finished");
    }
	
	 public static void stripDuplicatesFromFile(String filename) throws IOException 
	 {
	    BufferedReader reader = new BufferedReader(new FileReader(filename));
	    Set<String> lines = new HashSet<String>(1000000); // maybe should be bigger
	    String line;
	    while ((line = reader.readLine()) != null) 
	    {
	        lines.add(line);
	    }
	    reader.close();
	    
	    FileWriter fileWriter = new FileWriter( "../../input/named_entities_unique.txt" , true);
	    
	    BufferedWriter writer = new BufferedWriter(fileWriter);
	    for (String unique : lines) 
	    {
	        writer.write(unique);
	        writer.newLine();
	    }
	    writer.close();
	}
}

