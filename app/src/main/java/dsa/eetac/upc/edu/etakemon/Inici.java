package dsa.eetac.upc.edu.etakemon;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Debug;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.TextHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;

import cz.msebera.android.httpclient.Header;


public class Inici extends AppCompatActivity {
    String tag = "Events";
    HashMap<String,String> hs=new HashMap<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);//llamada al metodo original
        setContentView(R.layout.activity_inici);
        Log.d(tag, "event onCreate()");
}

    @Override
    public void onStart() {
        super.onStart();
        Log.d(tag, "event onStart()");

    }

    @Override
    public void onResume() {
        super.onResume();  // Always call the superclass method first
        Log.d(tag, "event onResume()");
    }

    @Override
    public void onPause() {
        super.onPause();  // Always call the superclass method first
        Log.d(tag, "event onPause()");
    }

    @Override
    public void onStop() {
        super.onStop();  // Always call the superclass method first
        Log.d(tag, "event onStop()");

    }

    @Override
    protected void onRestart() {
        super.onRestart();  // Always call the superclass method first
        Log.d(tag, "event onRestart()");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();  // Always call the superclass
        // Stop method tracing that the activity started during onCreate()
        Log.d(tag, "event onDestroy()");
        Debug.stopMethodTracing();
    }
    public static class RestClient {
        private static final String BASE_URL = "http://10.192.229.94:8080";
        private static AsyncHttpClient client = new AsyncHttpClient();


        public static void get(String url, AsyncHttpResponseHandler responseHandler) {
            client.get(getAbsoluteUrl(url), responseHandler);

        }

        public static void post(Context context, String url, cz.msebera.android.httpclient.entity.StringEntity entity, String c, AsyncHttpResponseHandler responseHandler) {
            client.post(context, getAbsoluteUrl(url), entity, c, responseHandler);
        }
        private static String getAbsoluteUrl(String relativeUrl) {
            return BASE_URL + relativeUrl;
        }

    }

    public void inici(View v) throws JSONException, UnsupportedEncodingException {
        final TextView user=(TextView) findViewById(R.id.user);
        final TextView passw=(TextView) findViewById(R.id.password);
        JSONObject jsonParams = new JSONObject();
        jsonParams.put("name", user.getText().toString());
        jsonParams.put("password",passw.getText().toString());
        cz.msebera.android.httpclient.entity.StringEntity entity = new cz.msebera.android.httpclient.entity.StringEntity(jsonParams.toString());
        RestClient.post(getApplicationContext(), "/dsa/android/login", entity, "application/json",
                new TextHttpResponseHandler(){
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, String responseString) {
                        if (responseString.equals("200")) {
                            Intent a = new Intent(getApplicationContext(), Menu.class);
                            Bundle extras = new Bundle();
                            extras.putString("name", user.getText().toString());

                            a.putExtras(extras);
                            startActivity(a);
                        }
                        if (responseString.equals("500")) {
                            Toast.makeText(getApplicationContext(), "El usuario ya existe", Toast.LENGTH_SHORT).show();
                        }

                    }

            @Override
            public void onFailure(int statusCOde, Header[] headers, String s, Throwable i){

            }

        });
    }
    public void registro (View v) throws JSONException, UnsupportedEncodingException {
        final TextView user =(TextView) findViewById(R.id.user);
        final TextView pass =(TextView) findViewById(R.id.password);


        JSONObject jsonParams = new JSONObject();
        jsonParams.put("name", user.getText().toString());
        jsonParams.put("password", pass.getText().toString());
        cz.msebera.android.httpclient.entity.StringEntity entity = new cz.msebera.android.httpclient.entity.StringEntity(jsonParams.toString());
        RestClient.post(getApplicationContext(), "/dsa/android/register", entity, "application/json",
                new TextHttpResponseHandler() {

                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

                    }

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, String responseString) {
                        if (responseString.equals("200")) {
                            Intent a = new Intent(getApplicationContext(), Menu.class);
                            Bundle extras = new Bundle();
                            extras.putString("name", user.getText().toString());

                            a.putExtras(extras);
                            startActivity(a);
                        }
                        if (responseString.equals("500")) {
                            Toast.makeText(getApplicationContext(), "El usuario ya existe", Toast.LENGTH_SHORT).show();
                        }

                    }
                });

    }


}
