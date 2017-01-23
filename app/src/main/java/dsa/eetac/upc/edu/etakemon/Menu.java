package dsa.eetac.upc.edu.etakemon;

import android.content.Intent;
import android.os.Bundle;
import android.os.Debug;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

/**
 * Created by Alberto on 05/12/2016.
 */

public class Menu extends AppCompatActivity {
    String tag = "Events";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);//llamada al metodo original

        setContentView(R.layout.activity_menu);

        Log.d(tag, "event onCreate()2");

    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(tag, "event onStart()2");

    }

    @Override
    public void onResume() {
        super.onResume();  // Always call the superclass method first
        Log.d(tag, "event onResume()2");
    }

    @Override
    public void onPause() {
        super.onPause();  // Always call the superclass method first
        Log.d(tag, "event onPause()2");
    }

    @Override
    public void onStop() {
        super.onStop();  // Always call the superclass method first
        Log.d(tag, "event onStop()2");

    }

    @Override
    protected void onRestart() {
        super.onRestart();  // Always call the superclass method first
        Log.d(tag, "event onRestart()2");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();  // Always call the superclass
        // Stop method tracing that the activity started during onCreate()
        Log.d(tag, "event onDestroy()2");
        Debug.stopMethodTracing();
    }
    public void capturar(View v){
        Bundle extra=getIntent().getExtras();
        String usuari=extra.getString("name");
        Bundle extra1=new Bundle();
        extra1.putString("name",usuari);
        Intent Capturar=new Intent(getApplicationContext(), dsa.eetac.upc.edu.etakemon.Capturar.class);
        Capturar.putExtras(extra1);
        startActivity(Capturar);
    }
    public void etakedex(View v){
        Bundle extra=getIntent().getExtras();
        String usuari=extra.getString("name");
        Bundle extra1=new Bundle();
        extra1.putString("name",usuari);
        Intent etakedex=new Intent(getApplicationContext(),Etakedex.class);
        etakedex.putExtras(extra1);
        startActivity(etakedex);
    }

}
