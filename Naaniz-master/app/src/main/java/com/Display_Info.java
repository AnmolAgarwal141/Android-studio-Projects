package com;

import androidx.appcompat.app.AppCompatActivity;

import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.widget.TextView;

import com.example.naaniz.R;

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
import java.util.List;
import java.util.Locale;

public class Display_Info extends AppCompatActivity {
    private String place_id;
    private String curr,name;
    private String url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display__info);

        name = getIntent().getStringExtra("name");
        Double lat = getIntent().getDoubleExtra("lat",1.0);
        Double longt = getIntent().getDoubleExtra("lont",1.0);
        place_id = getIntent().getStringExtra("place_id");

        Geocoder geocoder = new Geocoder(Display_Info.this, Locale.getDefault());
        List<Address> addresses = null;
        try {
            addresses = geocoder.getFromLocation(lat, longt, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        curr = addresses.get(0).getAddressLine(0);

        StringBuilder stringBuilder = new StringBuilder("https://maps.googleapis.com/maps/api/place/details/json?");
        stringBuilder.append("place_id="+place_id+"&fields=name,rating,formatted_phone_number");
        stringBuilder.append("&key=AIzaSyDnJUkEn7wanczQf6SViAhFYCvjbKRSAh4");
        url = stringBuilder.toString();
        GroceryAsyncTask groceryAsyncTask = new GroceryAsyncTask();
        groceryAsyncTask.execute();

    }
    class GroceryAsyncTask extends AsyncTask<Void,Void, String>
    {
        @Override
        protected String doInBackground(Void... voids) {
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
            String gr =null;
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
        public String extractJson(String json)
        {
            String phone=null;
            try {
                JSONObject jsonObject = new JSONObject(json);
                JSONObject js = jsonObject.getJSONObject("result");
                phone = js.getString("formatted_phone_number");

            } catch (JSONException e) {
                e.printStackTrace();
            }
            return phone;
        }

        @Override
        protected void onPostExecute(String groceries) {
            super.onPostExecute(groceries);
            updateUI(groceries);
        }
    }
    public void updateUI(String g)
    {
        TextView text1 = (TextView)findViewById(R.id.text1);
        TextView text2 = (TextView)findViewById(R.id.text2);
        TextView text3 = (TextView)findViewById(R.id.text3);
        text1.setText(name);
        text2.setText(curr);
        text3.setText(g);
    }
}
