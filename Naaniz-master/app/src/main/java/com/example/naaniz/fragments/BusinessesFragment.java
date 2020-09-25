package com.example.naaniz.fragments;

import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;

import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.Display_Info;
import com.example.naaniz.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;


public class BusinessesFragment extends Fragment {
   private LocationManager locationManager;
    private String str = "delhi";
    private String url;
    private  ListView listView;
    private CollegeAdapter arrayAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_businesses, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
       listView = (ListView)view.findViewById(R.id.list);
        Geocoder geocoder = new Geocoder(getActivity());
        List<Address> addressList=null;
        try {
            addressList = geocoder.getFromLocationName(str,1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        int i;
        listView.setOnItemClickListener((parent, view1, position, id) -> {
            Grocery grocery  = (Grocery) parent.getItemAtPosition(position);
            String place_id=grocery.getPlace_id();
            Double lat = Double.parseDouble(grocery.getLatd());
            Double lont = Double.parseDouble(grocery.getLongtd());
            String name = grocery.getName();
            Intent intent = new Intent(getContext(), Display_Info.class);
            intent.putExtra("name",name);
            intent.putExtra("place_id",place_id);
            intent.putExtra("lat",lat);
            intent.putExtra("lont",lont);
            startActivity(intent);
        });
        Address address = addressList.get(0);
        Log.i("123442543536546464", address.getLatitude()+" "+ address.getLongitude());
        StringBuilder stringBuilder = new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
        stringBuilder.append("location="+address.getLatitude()+","+address.getLongitude());
        stringBuilder.append("&radius=10000&type=restaurant&key=AIzaSyDnJUkEn7wanczQf6SViAhFYCvjbKRSAh4");
        url = stringBuilder.toString();
        GroceryAsyncTask groceryAsyncTask = new GroceryAsyncTask();
        groceryAsyncTask.execute();
    }
   class GroceryAsyncTask extends AsyncTask<Void,Void, ArrayList<Grocery>>
    {
        @Override
        protected ArrayList<Grocery> doInBackground(Void... voids) {
            Log.i("check", url.toString());
            URL url3=null;
            try {
                url3 = new URL(url);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            String response=null;
            try {
                response = getResponse(url3);
            }
            catch (Exception e)
            {

            }
            ArrayList<Grocery> gr =null;
            gr= extractJson(response);
            return gr;
        }
        public String getResponse(URL url3) throws IOException
        {
            HttpURLConnection httpURLConnection=null;
            InputStream inputStream=null;
            String json=null;
            try {
                httpURLConnection = (HttpURLConnection)url3.openConnection();
                httpURLConnection.connect();
                if(httpURLConnection.getResponseCode()==200)
                {
                    inputStream=httpURLConnection.getInputStream();
                    Log.i("12313213213", inputStream.toString()+" 123");
                    json=getJson(inputStream);
                }
            }
            catch (Exception e)
            {
                Log.i("check", "getResponse: ");
            }
            finally
            {
                if(httpURLConnection!=null)
                {
                    httpURLConnection.disconnect();
                }
                if(inputStream!=null)
                {
                    inputStream.close();
                }
            }
            return json;
        }
        public String getJson(InputStream inputStream)
        {
            StringBuilder stringBuilder = new StringBuilder();
            try
            {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String line=bufferedReader.readLine();
                while (line!=null)
                {
                    Log.i("c14313545254545", line);
                    stringBuilder.append(line);
                    line=bufferedReader.readLine();
                }
            }
            catch (IOException e)
            {
                Log.i("check", "getJson: ");
            }
            return stringBuilder.toString();
        }
        public ArrayList<Grocery> extractJson(String json)
        {
            ArrayList<Grocery> gr=new ArrayList<Grocery>();
            try {
                JSONObject jsonObject = new JSONObject(json);
                JSONArray js = jsonObject.getJSONArray("results");
                int i;
                for(i=0;i<js.length();i++)
                {
                    JSONObject js2 = js.getJSONObject(i);
                    String lat = js2.getJSONObject("geometry").getJSONObject("location").getString("lat");
                    String lng = js2.getJSONObject("geometry").getJSONObject("location").getString("lng");
                    String name = js2.getString("name");
                    String place_id=js2.getString("place_id");
                    gr.add(new Grocery(lat,lng,name,place_id));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return gr;
        }

        @Override
        protected void onPostExecute(ArrayList<Grocery> groceries) {
            super.onPostExecute(groceries);
            updateUI(groceries);
        }
    }
    public void updateUI(ArrayList<Grocery> g)
    {
        arrayAdapter  = new CollegeAdapter(getContext(),g);
        listView.setAdapter(arrayAdapter);
    }
}
