import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import com.google.gson.Gson; 

import java.lang.reflect.Type;

import javax.naming.Context;

import org.apache.commons.lang3.tuple.Pair;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

public class JsonMapFile 
{ 
   
    public static void print_QL( Map<String, HashSet<String>> data_struc ) throws IOException 
    {
    	//fit to print
    	Gson print_gson = new GsonBuilder().setPrettyPrinting().create();
    	String print_json = print_gson.toJson( data_struc );
    	System.out.println(print_json);
    	
    	//fit to write
    	Gson write_gson = new GsonBuilder().create();
    	
    	try (Writer file_writer = new FileWriter("../../output/interim.json")) 
    	//try (Writer file_writer = new FileWriter("/Users/matthew/Workbench/Data/NYTimes/Entity_Lists/Unique_Entry_Lists/Q_Grabber/JSON_Output/persons_q.json"))
    	{
    		String write_json = write_gson.toJson( data_struc );
    		write_gson.toJson( write_json, file_writer );
    	}
    }
    
    public static void print_RL( Map<Pair<String, String>, Pair<Pair<String, String>, Pair<Pair<String, String>, Pair<String, String>>>> data_struc ) throws IOException 
    {
    	//fit to print
    	Gson print_gson = new GsonBuilder().setPrettyPrinting().create();
    	String print_json = print_gson.toJson( data_struc );
    	System.out.println(print_json);
    	
    	//fit to write
    	Gson write_gson = new GsonBuilder().create();
    	
    	try (Writer file_writer = new FileWriter("../../output/final_output.json")) 
    	//try (Writer file_writer = new FileWriter("/Users/matthew/Workbench/Data/NYTimes/Entity_Lists/Unique_Entry_Lists/Q_Grabber/JSON_Output/persons_q.json"))
    	{
    		String write_json = write_gson.toJson( data_struc );
    		write_gson.toJson( write_json, file_writer );
    	}
    }
}


	


	class MyEntity
	{
	    private String name;
	    Set<String> value; // use names that you want in the result JSON
	
	    //constructors
	    public MyEntity() 
	    {
	    	
	    }
	    public MyEntity(String name) 
	    {
	        this.name = name;
	    }
	
	    //getters
	    public String getName() 
	    {
	        return this.name;
	    }
	    public Set<String>  getValue() 
	    {
	        return this.value;
	    }
	
	    //setters
	    public void setName(String name) 
	    {
	        this.name = name;
	    }
	    public void setValue(Set<String> value) 
	    {
	        this.value = value;
	    }
	}



