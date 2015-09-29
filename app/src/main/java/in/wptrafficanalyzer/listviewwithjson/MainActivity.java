package in.wptrafficanalyzer.listviewwithjson;

import java.util.HashMap;
import java.util.List;

import org.json.JSONObject;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class MainActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);        
        
        String strJson = 
        		"{ " +        
        			" \"countries\":[ " +
        				
        				"{" +
        					"\"countryname\": \"India\","+
        					"\"flag\": "+ R.drawable.india + ","+
        					"\"language\": \"Hindi\","+
        					"\"capital\": \"New Delhi\"," +
        					"\"currency\": {" +
        									"\"code\": \"INR\", " + 
        									"\"currencyname\": \"Rupee\" " +
        								"}" + 
        				"}, " +
        								
        				"{" +
	    					"\"countryname\": \"Pakistan\","+
        					"\"flag\": "+ R.drawable.pakistan + ","+
	    					"\"language\": \"Urdu\","+
	    					"\"capital\": \"Islamabad\"," +	    					
	    					"\"currency\": {" +
	    									"\"code\": \"PKR\", " + 
	    									"\"currencyname\": \"Pakistani Rupee\" " +
	    								"}" + 
	    				"}," +
	    								
						"{" +
							"\"countryname\": \"Sri Lanka\","+
        					"\"flag\": "+ R.drawable.srilanka + ","+
							"\"language\": \"Sinhala\","+
							"\"capital\": \"Sri Jayawardenapura Kotte\"," +							
							"\"currency\": {" +
											"\"code\": \"SKR\", " + 
											"\"currencyname\": \"Sri Lankan Rupee\" " +
										"}" + 
						"}" +	
        			
        			"]" + 
        		"} ";							
    						
        
        /** The parsing of the xml data is done in a non-ui thread */
        ListViewLoaderTask listViewLoaderTask = new ListViewLoaderTask();
        
        /** Start parsing xml data */
        listViewLoaderTask.execute(strJson);
        
        
        
        
    }
    
    
    private class ListViewLoaderTask extends AsyncTask<String, Void, SimpleAdapter>{

    	JSONObject jObject;
    	/** Doing the parsing of xml data in a non-ui thread */
		@Override
		protected SimpleAdapter doInBackground(String... strJson) {
			try{
	        	jObject = new JSONObject(strJson[0]);
	        	CountryJSONParser countryJsonParser = new CountryJSONParser();
	        	countryJsonParser.parse(jObject);
	        	Log.d("JSON Message"," " + jObject.getJSONArray("countries").length());
	        }catch(Exception e){
	        	Log.d("JSON Exception1",e.toString());
	        }
			
			CountryJSONParser countryJsonParser = new CountryJSONParser();
			
	        List<HashMap<String, String>> countries = null;
	        
	        try{
	        	/** Getting the parsed data as a List construct */
	        	countries = countryJsonParser.parse(jObject);
	        }catch(Exception e){
	        	Log.d("Exception",e.toString());
	        }	       

	        /** Keys used in Hashmap */
	        String[] from = { "country","flag","details"};

	        /** Ids of views in listview_layout */
	        int[] to = { R.id.tv_country,R.id.iv_flag,R.id.tv_country_details};

	        /** Instantiating an adapter to store each items
	        *  R.layout.listview_layout defines the layout of each item
	        */
	        SimpleAdapter adapter = new SimpleAdapter(getBaseContext(), countries, R.layout.lv_layout, from, to);  
	        
			return adapter;
		}
		
        /** Invoked by the Android system on "doInBackground" is executed completely */
        /** This will be executed in ui thread */
		@Override
		protected void onPostExecute(SimpleAdapter adapter) {
	        
			/** Getting a reference to listview of main.xml layout file */
	        ListView listView = ( ListView ) findViewById(R.id.lv_countries);
	        
	        /** Setting the adapter containing the country list to listview */
	        listView.setAdapter(adapter);
		}		
    }
    
    
    

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
}
