/**
 * 
 */



import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;
import java.util.TreeSet;


import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;




/**
 * @author abhishek kumar
 *
 */
public class JsonMain {

	/**
	 * 
	 */
	public JsonMain() {
		// TODO Auto-generated constructor stub
	}
	/**
	 * @param args
	 */	
	public static void main(String[] args) {		
		
		List<Entry> all_entries = new ArrayList<Entry>();
		ListIterator<Entry> litr = null;
		Path path1 = null;
		String path = null;
		try {
			if(args.length > 0) {
				path = args[0];	
			}
			else {
				System.out.println("Please provide the absolute path of the input json file, exiting ");
				System.exit(0);
			}
		}
		catch ( ArrayIndexOutOfBoundsException e) {
			e.printStackTrace();
		}				
		
		Object obj;
		try {
			path1 = Paths.get(path);
			System.out.format("Path of input is : %s%n", path1.getParent());
			obj = new JSONParser().parse(new FileReader(path));
			JSONObject jo = (JSONObject) obj;
			//JSONArray arr;
			JSONArray arr = (JSONArray) jo.get("leads");
			Iterator i = arr.iterator();	
			while (i.hasNext()) {	
				
			    JSONObject innerObj = (JSONObject) i.next();
			    Entry entry = new Entry((String)innerObj.get("_id"), (String)innerObj.get("email"), 
			    		(String)innerObj.get("firstName"), (String)innerObj.get("lastName"), (String)innerObj.get("address"), 
			    		(String)innerObj.get("entryDate"), Entry.getSerialNumber() );
			    all_entries.add(entry);			   			 
			}
			
		} catch (FileNotFoundException e1) {			
			e1.printStackTrace();	
			System.exit(0);
		} catch (IOException e1) {			
			e1.printStackTrace();
			System.exit(0);
		} catch (ParseException e1) {			
			e1.printStackTrace();
			System.exit(0);
		}
		
		String outputpath = path1.getParent() + "\\output.json";

		//Sorting to make sure the entries are in increasing order of timestamp in input
		//As the later entries are to be preserved
		
		Collections.sort(all_entries, Entry.EntryComparator);
		
		System.out.println("Input entries size is " + all_entries.size());
		System.out.println("");
		Set<Entry> set = new TreeSet<Entry>();
		set.clear();
		
		//Putting in the set in reverse order will make sure the latest entries will be preserved
		//in case of duplicates
		
		litr = all_entries.listIterator();
		for(int j = all_entries.size() - 1; j >= 0; j--) {			 		
			Entry entry = all_entries.get(j);			
			set.add(entry);			
		}
		
		final ArrayList<Entry> newList = new ArrayList<Entry>(set);		
	
		//Sorting to preserve the original timestamp order in final output
		Collections.sort(newList, Entry.EntryComparator);
		System.out.println("Number of entries after removing duplicates is " + newList.size());
		System.out.println("");
		litr = newList.listIterator();		
		
		JSONArray ja = new JSONArray();
		while(litr.hasNext()){
			Entry e = litr.next();		    
		    //System.out.println(e.get_id() + " " + e.getFirst_name() + " " +  e.getLast_name());
		    JSONObject jo = new JSONObject();
		    jo.put("_id", e.get_id());
		    jo.put("email", e.getEmail());      
		    jo.put("firstName", e.getFirst_name());
		    jo.put("lastName", e.getLast_name());
		    jo.put("address", e.getAddress());
		    jo.put("entryDate", e.getEntrydate());
		    ja.add(jo);
		}
		
	        JSONObject mainObj = new JSONObject();
	        mainObj.put("leads", ja);
	        
	        Gson gson = new GsonBuilder().setPrettyPrinting().create();	                
	        
	        try (FileWriter file = new FileWriter(outputpath)) {

	           // file.write(mainObj.toJSONString());
	        	file.write(gson.toJson(mainObj));
	            file.flush();

	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	        
	        System.out.print("output file created in same directory as input: Output.json ");
		
	}

}
