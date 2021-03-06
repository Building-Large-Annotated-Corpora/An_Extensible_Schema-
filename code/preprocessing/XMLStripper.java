
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;


public class XMLStripper 
{
	
	static String DIRECTORY_OF_NYTIMES_DATA = "../../input/test_input";
			
    public static void main(String[] args) throws Exception 
    {
    	
    	String dirStart = DIRECTORY_OF_NYTIMES_DATA;

    	Path root = Paths.get(dirStart);
    	
    	Files.walkFileTree(root.toAbsolutePath().normalize(), new SimpleFileVisitor<Path>() 
    	{
    	    @Override
    	    public FileVisitResult visitFile(Path file, java.nio.file.attribute.BasicFileAttributes attrs) throws IOException 
    	    {
    	    	
    	    	
    	        try(InputStream inputStream = Files.newInputStream(file);
    	        		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream)))
    	        {
    	            
    	            // CHANGE OUTPUT TO NEW FILE
    	            String print_file = file.getFileName().toString();
    	            Path folder = file.getParent(); 
    	            
    	            
    	            if( ! print_file.toUpperCase().contains("stp".toUpperCase() ) )
      	          	{
	    	            String fileNameWithOutExt = print_file.replaceFirst("[.][^.]+$", "");
	    	            
	    	            System.out.println(" :: " +  folder + "/" + fileNameWithOutExt + "_stp.txt" );
	    	            
	
	    	        	
	    	        	// SOUP PART
	    	            StringBuilder sb = new StringBuilder();
	    	            String line = bufferedReader.readLine();
	
	    	            while (line != null) 
	    	            {
	    	            	
	    	                sb.append(line);
	    	                sb.append(System.lineSeparator());
	    	                line = bufferedReader.readLine();
	    	            }
	    	            String everything = sb.toString();
	
	
	    	            Document doc = Jsoup.parse(everything);
	    	            String link = doc.select("block.full_text").text();
	    	            //System.out.println(link);
	    	            
	    	          
	    	        	PrintWriter writer = new PrintWriter( folder + "/" + fileNameWithOutExt + "_stp.txt" , "UTF-8" );
	    	        	writer.println(link);
	    	        	writer.close();
	    	        	
	    	        	Files.delete(file);
    	        
      	          	}

    	            
    	        } 
    	        catch (IOException e) 
    	        {
    	        	e.printStackTrace();
    	        }
    	        
    	        return FileVisitResult.CONTINUE;
    	    }
    	}); 
    	
    	
    }
}

  

