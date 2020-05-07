package com.example.earthquakeapp.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.earthquakeapp.Model.Earthquake;
import com.example.earthquakeapp.R;
import com.example.earthquakeapp.Util.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class QuakelistActivity extends AppCompatActivity {
    private ArrayList<String> arrayList;
    private ListView listView;
    private RequestQueue queue;
    private ArrayAdapter arrayAdapter;
    private List<Earthquake> quakelist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quakelist);
        quakelist=new ArrayList<>();
        listView=findViewById(R.id.listview);
        queue= Volley.newRequestQueue(this);
        arrayList=new ArrayList<>();
        getAllQuakes(Constants.url);
    }
    void getAllQuakes(String url){
        JsonObjectRequest jsonObjectRequest =new JsonObjectRequest(Request.Method.GET, url, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
               Earthquake earthquake = new Earthquake();
                try {
                    JSONArray features =response.getJSONArray("features");
                    for(int i = 0; i<Constants.LIMIT; i++){
                        JSONObject properties =features.getJSONObject(i).getJSONObject("properties");
                        JSONObject geometry =features.getJSONObject(i).getJSONObject("geometry");
                        JSONArray coordinates = geometry.getJSONArray("coordinates");
                        double lon =coordinates.getDouble(0);
                        double lat = coordinates.getDouble(1);
                        earthquake.setPlace(properties.getString("place"));
                        earthquake.setType(properties.getString("type"));
                        earthquake.setTime(properties.getLong("time"));
                        earthquake.setLat(lat);
                        earthquake.setLon(lon);
                        earthquake.setMagnitude(properties.getDouble("mag"));
                        earthquake.setDetaillink(properties.getString("detail"));
                        arrayList.add(earthquake.getPlace());
            }
                    arrayAdapter=new ArrayAdapter(QuakelistActivity.this,android.R.layout.simple_list_item_1,android.R.id.text1,arrayList);
                    listView.setAdapter(arrayAdapter);
                    arrayAdapter.notifyDataSetChanged();
        } catch (JSONException e) {
                    e.printStackTrace();
                }}}, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue.add(jsonObjectRequest);
    }
}
