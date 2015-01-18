package de.hsworms.fritz.ema.aufgabe03;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.ArrayList;
import de.hsworms.fritz.ema.R;

/**
 * Created by fritz on 18/11/14.
 */
public class CatListAdapter extends ArrayAdapter<CatListItem> {

    private Context context;
    private ArrayList<CatListItem> objects;


    public CatListAdapter(Context context, int resource, ArrayList<CatListItem> objects) {
        super(context, resource, objects);
        this.context = context;
        this.objects = objects;
    }

    @SuppressLint("InflateParams") @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if(v == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.category_list_item, parent, false);
        }

        TextView name = (TextView) v.findViewById(R.id.cat_list_item_title);
        TextView numOfItems = (TextView) v.findViewById(R.id.cat_list_item_number);
        CatListItem item = objects.get(position);

        name.setText(item.getName());
        numOfItems.setText(""+item.getNumOfItems());

        return v;
    }
}
