package dsa.eetac.upc.edu.etakemon;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ElmeuArrayAdapter extends ArrayAdapter<String> {
    private final Context context;
    private final String[] values;


    public ElmeuArrayAdapter(Context context, String[] values) {
        super(context, R.layout.activity_etakedex, values);
        this.context = context;
        this.values = values;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.activity_etakedex, parent, false);
        TextView textView = (TextView) rowView.findViewById(R.id.label);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.logo);
        String s = values[position];
        try{
        if(!s.equals(null)) {
            textView.setText(values[position]);
        }
        else {

            textView.setText("Captura un etakemon primero");
        }
        }
        catch (Exception e){
            textView.setText("Captura un etakemon primero");
        }

return rowView;
    }
}

