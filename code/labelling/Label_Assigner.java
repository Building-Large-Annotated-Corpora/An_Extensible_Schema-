import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.google.gson.Gson;




public class Label_Assigner 
{
	static Map<Pair<String, String>, Pair<Pair<String, String>, Pair<Pair<String, String>, Pair<String, String>>>> relationship_labels 
	= new HashMap<Pair<String, String>, Pair<Pair<String, String>, Pair<Pair<String, String>, Pair<String, String>>>> ();

	 @SuppressWarnings({ "unchecked" })
	public static void main(String[] args) throws Exception 
    {
	        String jsonTxt_Q = null;
	        
		    // GET JSON DATA
	        File Q_Value_file = new File("../../output/interim.json");
	        
	        //raed in the Q values
	        if (Q_Value_file.exists())
	        {
	            InputStream is = new FileInputStream("../../output/interim.json");
	            jsonTxt_Q = IOUtils.toString(is);
	        }
	        //reformat
	        jsonTxt_Q = ( jsonTxt_Q.substring(1, jsonTxt_Q.length()-1) ).replace("\\","");
	        
	        Gson json_Q = new Gson();
	        Map<String, ArrayList<String>> massive_Q_storage_map = new HashMap<String, ArrayList<String>>();
	        massive_Q_storage_map = (Map<String, ArrayList<String>>) json_Q.fromJson(jsonTxt_Q, massive_Q_storage_map.getClass());
	        System.out.println(massive_Q_storage_map);
	        
	        

    
	        
	        BufferedReader reader = new BufferedReader(new FileReader(new File("../../properties-en.json")));
	        Map<String, Map<String, Object>> property_p_values_map = 
	        new Gson().fromJson(reader, HashMap.class);
	        
	        


	        // Read in all the sentences, that are in files, in this folder
	        final File folder = new File("../../input/bounded_sentences/");
	        
	        String filename_for_sentences = null;
	        
            for (final File fileEntry : folder.listFiles()) 
            {
            	Document doc = null;
            	
            	BufferedReader br = new BufferedReader(new FileReader(fileEntry));
    	    	try 
    	    	{
    	    		//Store the filename
    	    		filename_for_sentences = fileEntry.getName();
	                
	                StringBuilder sb = new StringBuilder();
		            String line = br.readLine();
	
		            while (line != null) 
		            { 	
		                sb.append(line);
		                sb.append(System.lineSeparator());
		                line = br.readLine();
		            }
		            String everything = sb.toString();
		            //System.out.println(everything);
		            
		            doc = Jsoup.parse(everything);
    	    	}
    	    	finally 
    	    	{
    	            br.close();
    	        }
		            
	            Elements sentences = doc.getElementsByTag("sentence");
	            
	            String sentence_number = null;
	            
	            for (Element sentence : sentences) 
		        {
		        	//hold the q values we need to search over
	            	//List<String> Q_value_references_for_sentence_entities = new ArrayList<String>();
	            	//List<String> English_names_references_for_sentence_entities = new ArrayList<String>();
	            	
	            	Map<String,String> Q_value_references_for_sentence_entities__and_ENGLISH_NAME = new HashMap<String,String>();
		        	
	            	//store the sentence number
	            	sentence_number = sentence.select("sentence").text();
	            	sentence_number = sentence_number.substring(0, sentence_number.indexOf(" ")); 
	            	
	            	
	            	//get all the entities in this sentence
		        	Elements pers = sentence.select("PERSON");
		        	Elements locs = sentence.select("LOCATION");
		        	Elements orgs = sentence.select("ORGANIZATION");

		        	//collect all the elements to a list, all the elements of one sentence
		        	List<String> sentence_entity_list = new ArrayList<String>();
		        	
		        	for (Element per : pers) 
			        {
		        		sentence_entity_list.add(per.text().trim());
			        }
		        	for (Element loc : locs) 
			        {
		        		sentence_entity_list.add(loc.text().trim());
			        }
		        	for (Element org : orgs) 
			        {
		        		sentence_entity_list.add(org.text().trim());
			        }

			        	
		        	// for the list of Q values to keys
		        	for (Entry<String, ArrayList<String>> DB__key_to_value_item : massive_Q_storage_map.entrySet()) 
		        	{
		        		for (String entity : sentence_entity_list)
		        		{
			        		if (DB__key_to_value_item.getKey().toLowerCase().contains(entity.toLowerCase())) 
			        		{
			        		    //for all the entries( Qs) associated with that key
			        			//add them to Q_value_references_for_sentence_entities
			        			
			        			for (String Q_value : DB__key_to_value_item.getValue()) 
			        			{
			        				//Q_value_references_for_sentence_entities.add( Q_value );
			        				//English_names_references_for_sentence_entities.add(DB__key_to_value_item.getKey());
			        				
			        				Q_value_references_for_sentence_entities__and_ENGLISH_NAME.put( Q_value, DB__key_to_value_item.getKey());
			        			}
			        		}
		        		}
		        	}
				        		    
				        		    
				        		    
                    //czeher
					Map<String, Pair<Pair<String,String>,Pair<String,String>>> URL_and_entities = new HashMap<String, Pair<Pair<String,String>,Pair<String,String>>>();
					//Map<String,Pair<String,String>> 
					//String, Pair<Map.Entry<String,String>,Map.Entry<String,String>>
				
				
				    for (Entry<String,String> entity_1: Q_value_references_for_sentence_entities__and_ENGLISH_NAME.entrySet()) 
				    {
				      
				      for (Entry<String,String> entity_2: Q_value_references_for_sentence_entities__and_ENGLISH_NAME.entrySet())  
				      {

				    	    if( ! (
				    	    	   (entity_1.getValue().toLowerCase() == entity_2.getValue().toLowerCase())           || 
				    	    	    entity_1.getValue().toLowerCase().contains( entity_2.getValue().toLowerCase() )   || 
				    	    	    entity_2.getValue().toLowerCase().contains( entity_1.getValue().toLowerCase() )   
				    	    	  )
				    	      )     
					    	      
				    	    {
				    	    	
				    	   
					     		String URL_czech = "http://milenio.dcc.uchile.cl/sparql?default-graph-uri=&query=PREFIX+%3A+%3Chttp%3A%2F%2Fwww.wikidata.org%2Fentity%2F%3E%0D%0ASELECT+*+WHERE+%7B%0D%0A+++%3A" 
					     					       + entity_1.getKey() + "+%3FsimpleProperty+%3A" 
					     					       + entity_2.getKey() + "%0D%0A%7D%0D%0A&format=text%2Fhtml&timeout=0&debug=on";
					     		             		
					    		URL wikidata_page = new URL(URL_czech);
					    		HttpURLConnection wiki_connection = (HttpURLConnection)wikidata_page.openConnection();
					    		InputStream wikiInputStream = null;
					    		
								try 
								{
								    // try to connect and use the input stream
								    wiki_connection.connect();
								    wikiInputStream = wiki_connection.getInputStream();
								} 
								catch(IOException error) 
								{
								    // failed, try using the error stream
								    wikiInputStream = wiki_connection.getErrorStream();
								}
								// parse the input stream using Jsoup
								Document docx = Jsoup.parse(wikiInputStream, null, wikidata_page.getProtocol()+"://"+wikidata_page.getHost()+"/");
					
								
								Elements link_text = docx.select("table.sparql > tbody > tr:nth-child(2) > td > a");
								//link_text.text();
								for (Element url_link : link_text) 
							    {
									String output = url_link.text();
									output = output.substring(0, output.length()-1);
								
									
							       //System.out.println( "entity_1.getValue() " + entity_1.getValue() );
							       //System.out.println( "entity_1.getValue() " + entity_1.getKey() );
									Pair<String, String> entity_1_pair = Pair.of( entity_1.getKey(), entity_1.getValue() );
									Pair<String, String> entity_2_pair = Pair.of( entity_2.getKey(), entity_2.getValue());
							       
							       URL_and_entities.put(output, Pair.of( entity_1_pair, entity_2_pair ));
				
									
									
							    }
				    	    }
				        }		    
				    }
        
                    //now we're back at the sentence level
					//iter hash
				    for (Entry<String, Pair<Pair<String, String>, Pair<String, String>>> url : URL_and_entities.entrySet()) 
				    { 
				    	
				 		
				 		String P_value = url.getKey().substring(url.getKey().lastIndexOf('P'));
				 		
				 		System.out.println( "File name: " + filename_for_sentences );
				 		System.out.println( "sentence_number: " + sentence_number );
				 		
				 		String eng_value = (String) property_p_values_map.get("properties").get( P_value );
				 		

				       
				       
						String first__english_lang_Q = URL_and_entities.get( url.getKey() ).getLeft().getLeft();
						String second_english_lang_Q = URL_and_entities.get( url.getKey() ).getRight().getLeft();
						
						System.out.println(
								            "`(" + eng_value + ")'" + "`( " + first__english_lang_Q  + 
								                                   ", " + second_english_lang_Q  + 
								            ")'____filename_for_sentences" + filename_for_sentences +
								            "______sentence_number_______" +  sentence_number
								          );
						
				
						
						
						Pair<String, String> filename__sentence_num = Pair.of( filename_for_sentences , sentence_number );
						
						Pair<String, String> p_val__english = Pair.of( P_value, eng_value );
						
						Pair<Pair<String, String>, Pair<String, String>> q_val__english = Pair.of( Pair.of( URL_and_entities.get( url.getKey() ).getLeft().getLeft() ,  URL_and_entities.get( url.getKey() ).getLeft().getRight() ) , Pair.of( URL_and_entities.get( url.getKey() ).getRight().getLeft() , URL_and_entities.get( url.getKey() ).getRight().getRight() ) );
						
						Pair<Pair<String, String>, Pair<Pair<String, String>, Pair<String, String>>> mind_your_fs_and_qs = Pair.of( filename__sentence_num, q_val__english );
						
						
						
						relationship_labels.put( p_val__english, mind_your_fs_and_qs );
						
				    }
						
						 
						  
				
						
				    
				    	
				}

    	    	
            }




	        

            JsonMapFile.print_RL(relationship_labels);
	          
	}
	 

	 
	 
	

}       
	


	          

