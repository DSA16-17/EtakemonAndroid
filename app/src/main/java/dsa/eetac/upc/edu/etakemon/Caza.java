package dsa.eetac.upc.edu.etakemon;

import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Debug;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.GridLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.loopj.android.http.TextHttpResponseHandler;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import cz.msebera.android.httpclient.Header;
import pl.droidsonroids.gif.GifImageView;
import pl.droidsonroids.gif.GifTextView;


public class Caza extends AppCompatActivity{

    List<String> patterns = new ArrayList<>();
    String tag = "Events";
    int maxcorrect = 0;
    int correct = 0;
    Random rnd = new Random();
    Chronometer mChronometer;
    CountDownTimer downTimer;
    Handler mHandler = new Handler();
    MediaPlayer mp;
    MediaPlayer ls;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);//llamada al metodo original
        setContentView(R.layout.activity_caza);
        getSupportActionBar().hide();Log.d(tag, "event onCreate()");
        patterns.add("BigO");
        patterns.add("BigX");
        patterns.add("DoubleO");
        patterns.add("DoubleS");
        patterns.add("DoubleX");
        patterns.add("DStrips");
        patterns.add("Eight");
        patterns.add("FatX");
        patterns.add("HStrips");
        patterns.add("HStrips2");
        patterns.add("InvertedBigX");
        patterns.add("InvertedDStrips");
        patterns.add("InvertedLambda");
        patterns.add("Lambda");
        patterns.add("VStrips");
        patterns.add("VStrips2");
        Bundle extra=getIntent().getExtras();
        final String etakemon=extra.getString("name");
        final String name=extra.getString("username");
        final String position=extra.getString("position");
        String problemas=etakemon;
        int mus=getResources().getIdentifier(problemas.toLowerCase(), "raw", getPackageName());
        mp=MediaPlayer.create(this,mus);
        ls=MediaPlayer.create(this,R.raw.losser);
        GridLayout gridLayout = (GridLayout) findViewById(R.id.gridLayout1);
        final ArrayList<View> layoutButtons = gridLayout.getTouchables();
        mp.start();
        mChronometer= (Chronometer)findViewById(R.id.chronometer2);
        downTimer =new CountDownTimer(32000, 1000) {
            int x=5000;
            boolean set= false;
            public void onTick(long millisUntilFinished) {

                if ((millisUntilFinished <32000)&&(millisUntilFinished > 30000)) {
                    mChronometer.setText("Preparate!");

                }else {
                    mChronometer.setText("" + millisUntilFinished / 1000);

                    if (!set) {
                        set=true;
                        setGrid();


                    }
                    else if(x==0){
                        x=5000;
                        set=false;
                    }
                    x = x - 1000;
                }

            }
            public void setGrid(){

                GridLayout gridLayout = (GridLayout) findViewById(R.id.gridLayout1);
                final ArrayList<View> layoutButtons = gridLayout.getTouchables();
                int max = 0;
                int random = rnd.nextInt(16);
                int i = 0;
                for (View v : layoutButtons) {

                    try {
                        if ((v instanceof Button) && ( Factory.getInstance().getPattern(patterns.get(random))).setPattern().get(i)) {
                            max++;
                            v.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                        } else {
                            v.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                        }
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InstantiationException e) {
                        e.printStackTrace();
                    }
            /*
            if ((v instanceof Button) && (rnd.nextBoolean()) && (max <= 14)) {
                max++;
                v.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
            } else {
                v.setBackgroundColor(getResources().getColor(R.color.colorAccent));
            }*/
                    i++;
                }
                maxcorrect = maxcorrect+max;
            }

            public void onFinish() {
                if(correct==maxcorrect) {
                    mChronometer.setText("Capturado!");
                }
                else{
                    mChronometer.setText("Escapado!");
                    mp.stop();
                    ls.start();
                }

                mHandler.postDelayed(new Runnable() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    public void run() {
                        for (View v : layoutButtons) {
                            if(v instanceof Button){
                                v.setEnabled(false);
                                v.setVisibility(View.INVISIBLE);
                            }
                        }
                        if(correct==maxcorrect) {
                            RelativeLayout layout =(RelativeLayout)findViewById(R.id.activity_caza);
                            String title=etakemon.toLowerCase();
                            int imag = getResources().getIdentifier(title, "drawable", getPackageName());
                            layout.setBackgroundResource(imag);
                            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                            String date = sdf.format(new Date());
                            cz.msebera.android.httpclient.entity.StringEntity entity = null;
                            try {
                                entity = new cz.msebera.android.httpclient.entity.StringEntity(date);
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            }

                            Inici.RestClient.post(getApplicationContext(), "/dsa/android/hunt/"+etakemon+"/"+name+"/"+position, entity, "application/json",
                                    new TextHttpResponseHandler(){
                                        @Override
                                        public void onSuccess(int statusCode, Header[] headers, String responseString) {
                                            if (responseString.equals("200")) {
                                                Toast.makeText(getApplicationContext(), "Añadido a la Etakedex", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                        @Override
                                        public void onFailure(int statusCOde, Header[] headers, String s, Throwable i){

                                        }

                                    });
                        }else{
                            GifTextView losser = (GifTextView)findViewById(R.id.losser);
                            losser.setVisibility(View.VISIBLE);
                        }
                    }
                }, 1500);

            }
        }.start();

    }
    public void onClick(View v) {

        ColorDrawable color = (ColorDrawable) v.getBackground();
        if (color.getColor() == getResources().getColor(R.color.colorPrimaryDark)) {
            correct++;
            v.setBackgroundColor(getResources().getColor(R.color.vomito));
        } else if (color.getColor() == getResources().getColor(R.color.colorAccent)) {
            v.setBackgroundColor(getResources().getColor(R.color.color1));
            downTimer.cancel();
            downTimer.onFinish();
        }

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
        mp.stop();
        ls.stop();
    }

}
