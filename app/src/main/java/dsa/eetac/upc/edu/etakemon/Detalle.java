package dsa.eetac.upc.edu.etakemon;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

public class Detalle extends AppCompatActivity {
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_detalle);
            Intent intent=getIntent();
            Bundle extras=intent.getExtras();
            String name= extras.getString("name");
            String description= extras.getString("description");
            String type= extras.getString("type");
            String health= extras.getString("health");
            String id= extras.getString("id");
            ImageView imageView = (ImageView)findViewById(R.id.imageView2);
            TextView names=(TextView) findViewById(R.id.name1);
            TextView descriptions =(TextView) findViewById(R.id.description1);
            TextView type1=(TextView) findViewById(R.id.type1);
            TextView health1=(TextView) findViewById(R.id.health1);
            TextView id1=(TextView) findViewById(R.id.id1);
            if (name.equals("Espatula Khan")){name="espatulakhan";}
            int imag = getResources().getIdentifier(name.toLowerCase(), "drawable", getPackageName());
            Drawable drawable = getResources().getDrawable(imag);
            imageView.setImageDrawable(drawable);
            names.setText(name);
            descriptions.setText(description);
            type1.setText(type);
            health1.setText(health);
            id1.setText(id);

        }
}
