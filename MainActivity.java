package com.example.foodtruck;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.method.BaseKeyListener;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonArray;

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
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;

import javax.net.ssl.HttpsURLConnection;


public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new BackgroundTask().execute();
        init();
    }

    String arr="";
    private void init(){
        Button btnMap = (Button) findViewById(R.id.mapview);
        btnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("Intennnnnnarrrrrrrrrrrrrrttttttttt"+arr);
                Intent intent = new Intent(MainActivity.this, MapActivity.class);

                intent.putExtra("json_data", arr);
                startActivity(intent);
            }
        });
    }

    String str1= new String();

    class BackgroundTask extends AsyncTask<Void, Void, String>{
        String json_url = "https://data.sfgov.org/resource/jjew-r69b.json";
        String json_string = new String();

        Context context;
        TrucksAdapter trucksAdapter;

        private BackgroundTask(){
            this.trucksAdapter = new TrucksAdapter(MainActivity.this, R.layout.line_activity, R.id.applicant);
            ListView listView = (ListView)findViewById(R.id.listview);
            listView.setAdapter(this.trucksAdapter);
            System.out.println("======== I m in onPostExecute ==============");
        }
        @Override
        protected String doInBackground(Void... voids){
            try {
                URL url = new URL(json_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder stringBuilder = new StringBuilder();
                while ((json_string = bufferedReader.readLine()) != null) {
                    stringBuilder.append(json_string + "\n");
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return stringBuilder.toString().trim();

            }catch(MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String result){
            TextView textView = (TextView)findViewById(R.id.headview);

            str1=result;
            Calendar calendar = Calendar.getInstance();
            String currentDay = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());
            String[] dat = currentDay.split(",");
            currentDay=dat[0];
//            System.out.println("Dateeeeeeeeeeeeeeeeeeeee"+currentDay);
            ArrayList<String> arrayList= new ArrayList<>();
            try {
                JSONArray jsonArray = new JSONArray(result);

                String app, add, item, time, start, end, day, lat, lng;
                for(int i=0; i<jsonArray.length(); i++){

                    JSONObject jsonObject= jsonArray.getJSONObject(i);
                    day = jsonObject.getString("dayofweekstr");
                    if(day.equals(currentDay)) {
//                        System.out.println("Hurayyyyyyyyyyyyyyyyyyyy");


                        app = jsonObject.getString("applicant");
                        add = jsonObject.getString("location");
                        item = jsonObject.getString("optionaltext");
                        start = jsonObject.getString("starttime");
                        end = jsonObject.getString("endtime");
                        lat = jsonObject.getString("latitude");
                        lng = jsonObject.getString("longitude");
                        time = start + " - " + end;
                        System.out.println("I m here ====================" + app + ", " + add + ", " + item);


                        arr+=app+"("+add+"("+item+"("+time+"("+lat+"("+lng+")";
                        arrayList.add(app+"("+add+"("+item+"("+time+"("+lat+"("+lng);
                    }


                }

                Collections.sort(arrayList);
                System.out.println("araylist"+arrayList);
                for(int i=0; i<arrayList.size(); i++){
                    String[] truckDetails = arrayList.get(i).split("\\(");
                    Trucks truck1 = new Trucks(truckDetails[0], truckDetails[1], truckDetails[2], truckDetails[3]);
                    trucksAdapter.add(truck1);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

    }

//    public void openMap(View view)
//    {
//        System.out.println(" ============== openMap are you in here ===============");
////        if(str1==null){
////            Toast.makeText(getApplicationContext(), "Check JSON", Toast.LENGTH_LONG).show();
////        }
////        else {
//            if(str1 instanceof String){
//                System.out.println(" ================== i am string ===============");
//            }
//            String str2 = "Hello";
////            System.out.println(" ======== json data= ==============" + str1);
//            Intent myIntent = new Intent(MainActivity.this, MapActivity.class);
////            myIntent.putExtra("json_data", str1);
//            startActivity(myIntent);
////        }
//    }
}
