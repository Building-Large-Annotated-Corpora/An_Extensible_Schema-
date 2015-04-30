import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class Name_Grabber 
//public class Name_Grabber
{
	static String DIRECTORY_OF_NYTIMES_DATA = "../../input/";
	
    public static void main(String[] args) throws Exception 
    {
    	
    	
    	String dirStart = DIRECTORY_OF_NYTIMES_DATA;


    	Path root = Paths.get( dirStart + "bounded_sentences/" );
    	
    	Files.walkFileTree(root.toAbsolutePath().normalize(), new SimpleFileVisitor<Path>() 
    	{
    	    @Override
    	    public FileVisitResult visitFile(Path file, java.nio.file.attribute.BasicFileAttributes attrs) throws IOException 
    	    {
    	    	
    	    	
	        try(InputStream inputStream = Files.newInputStream(file);
	        		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream)))
	        {
	                 	
    	        	
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
    	            
    	            
    	            Elements contents = doc.select("sentence");

    	            
    		        for (Element content : contents) 
    		        {
    		        	
    		        	Elements pers = content.select("PERSON");
    		        	Elements locs = content.select("LOCATION");
    		        	Elements orgs = content.select("ORGANIZATION");
    		        	
    		        	for (Element per : pers)
    		        	{
    		        		String linkText_per = per.text();

                            FileWriter fileWriter = new FileWriter( "../../input/named_entities_mltp.txt" , true);

    			            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
    			            bufferedWriter.write( linkText_per + "\n" );
    			            bufferedWriter.close();
    		
    		        	}
    		        	
    		        	for (Element loc : locs)
    		        	{
    		        		String linkText_loc = loc.text();

                            FileWriter fileWriter = new FileWriter( "../../input/named_entities_mltp.txt" , true);

    			            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
    			            bufferedWriter.write( linkText_loc + "\n" );
    			            bufferedWriter.close();
    		        	}
    		        	
    		        	for (Element org : orgs)
    		        	{
    		        		String linkText_org = org.text();

                            FileWriter fileWriter = new FileWriter( "../../input/named_entities_mltp.txt" , true);

    			            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
    			            bufferedWriter.write( linkText_org + "\n" );
    			            bufferedWriter.close();

    		        	}

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

  

