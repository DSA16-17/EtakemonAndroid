package dsa.eetac.upc.edu.etakemon;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Debug;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import cz.msebera.android.httpclient.Header;


public class Etakedex extends ListActivity {

    String tag = "Events";
    HashMap<String,Etakemon> hs=new HashMap<String,Etakemon>();
    HashMap<String,Etakemon> hsCapturados=new HashMap<String,Etakemon>();
    static String user;
    Handler mHandler = new Handler();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extra = getIntent().getExtras();
        final String usuari = extra.getString("name");
        user = usuari;

                Inici.RestClient.get("/dsa/android/all/" + usuari, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONArray json) {
                        try {
                            final String[] arrayEtakemon = new String[json.length()];
                            for (int i = 0; i < json.length(); i++) {
                                int id = Integer.parseInt(json.getJSONObject(i).get("id").toString());
                                String name = json.getJSONObject(i).get("name").toString();
                                String description = json.getJSONObject(i).get("description").toString();
                                int health = Integer.parseInt(json.getJSONObject(i).get("health").toString());
                                String type = json.getJSONObject(i).get("type").toString();
                                Etakemon e = new Etakemon(name, description, type, id, health);
                                hs.put(name, e);
                            }
                            final List<Etakemon> list = new ArrayList<Etakemon>(hs.values());
                            Collections.sort(list, new Comparator<Etakemon>() {
                                @Override
                                public int compare(Etakemon o, Etakemon t1) {
                                    return new Integer(o.getId()).compareTo(new Integer(t1.getId()));
                                }
                            });

                            Inici.RestClient.get("/dsa/android/get/" + user, new JsonHttpResponseHandler() {
                                @Override
                                public void onSuccess(int statusCode, Header[] headers, JSONArray json) {

                                    try {
                                        for (int i = 0; i < json.length(); i++) {
                                            int id = Integer.parseInt(json.getJSONObject(i).get("id").toString());
                                            String name = json.getJSONObject(i).get("name").toString();
                                            String description = json.getJSONObject(i).get("description").toString();
                                            int health = Integer.parseInt(json.getJSONObject(i).get("health").toString());
                                            String type = json.getJSONObject(i).get("type").toString();
                                            Etakemon e = new Etakemon(name, description, type, id, health);
                                            hsCapturados.put(name, e);
                                        }

                                        for (int i = 0; i < list.size(); i++) {
                                            Etakemon etk = new Etakemon();
                                            etk.setName(list.get(i).getName());
                                            etk.setDescription(list.get(i).getDescription());
                                            etk.setId(list.get(i).getId());
                                            etk.setHealth(list.get(i).getHealth());
                                            etk.setType(list.get(i).getType());
                                            try {

                                                etk.setName(hsCapturados.get(list.get(i).getName()).getName());
                                            } catch (Exception e) {
                                                list.get(i).setName(list.get(i).getName()+" (no encontrado)");
                                            }

                                        }
                                                for (int i = 0; i < list.size(); i++) {
                                                arrayEtakemon[i] = list.get(i).getName();
                                                }

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                }

                                @Override
                                public void onFailure(int statusCOde, Header[] headers, String s, Throwable i) {

                                }

                            });

                         mHandler.postDelayed(new Runnable() {
                        @RequiresApi(api = Build.VERSION_CODES.N)
                        public void run() {

                            setListAdapter(new ElmeuArrayAdapter(getApplicationContext(), arrayEtakemon));

                        }
                         }, 100);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(int statusCOde, Header[] headers, String s, Throwable i) {

                    }

                });


    }

    public static String getName(){
        return user;
    }

    protected void onListItemClick(ListView l, View view, int position, long id) {
        String selectedValue =(String) getListAdapter().getItem(position);
        String selectedValue2 = "";
        try {
            selectedValue2 = selectedValue.substring(selectedValue.length() - 15, selectedValue.length());
        }
        catch (Exception e)
        {

        }
        try {

            if (selectedValue2.equals("(no encontrado)")) {
                Toast.makeText(getApplicationContext(), "Etakemon no encontrado", Toast.LENGTH_SHORT).show();
            } else {
                Intent Activity2 = new Intent(getApplicationContext(), Detalle.class);
                Bundle extras = new Bundle();
                extras.putString("name", selectedValue);
                extras.putString("description", hs.get(selectedValue).description);
                extras.putString("type", hs.get(selectedValue).type);
                extras.putString("health", String.valueOf(hs.get(selectedValue).health));
                extras.putString("id", String.valueOf(hs.get(selectedValue).id));
                Activity2.putExtras(extras);
                startActivity(Activity2);
            }
        }
        catch (Exception e){
            Toast.makeText(getApplicationContext(), "No tienes ningun etakemon", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onStart() {
        super.onStart();
        Log.d(tag, "event onStart()4");

    }

    @Override
    public void onResume() {
        super.onResume();  // Always call the superclass method first
        Log.d(tag, "event onResume()4");
    }

    @Override
    public void onPause() {
        super.onPause();  // Always call the superclass method first
        Log.d(tag, "event onPause()4");
    }

    @Override
    public void onStop() {
        super.onStop();  // Always call the superclass method first
        Log.d(tag, "event onStop()4");

    }

    @Override
    protected void onRestart() {
        super.onRestart();  // Always call the superclass method first
        Log.d(tag, "event onRestart()4");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();  // Always call the superclass
        // Stop method tracing that the activity started during onCreate()
        Log.d(tag, "event onDestroy()4");
        Debug.stopMethodTracing();
    }
}
