package android.example.com;

/**
 * Created by Administrator on 2015-08-28.
 */

import android.widget.ArrayAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import static android.widget.Toast.makeText;

public class OrderArrayAdapter extends ArrayAdapter<String> {
    private final Context context;
    private final String[] values;

    public OrderArrayAdapter(Context context, String[] values) {
        super(context, R.layout.activity_list_order, values);
        this.context = context;
        this.values = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.activity_list_order, parent, false);

        ImageView imageView = (ImageView) rowView.findViewById(R.id.logo);

        // Parse Coffee Menu from values
        String[] orderdetail= values[position].split(",");

        TextView menuView = (TextView) rowView.findViewById(R.id.coffeemenu);
        menuView.setText(orderdetail[0]);

        TextView orderView = (TextView) rowView.findViewById(R.id.order_text);
        String str= "Price: "+ Integer.parseInt(orderdetail[1])+ " ";
        str+= "Whipped: "+ Integer.parseInt(orderdetail[2])+ " ";
        str+= "Choco: "+ Integer.parseInt(orderdetail[3])+ " ";
        str+= "Qty: "+ Integer.parseInt(orderdetail[4])+ " ";
        str+= "Sum: "+ Integer.parseInt(orderdetail[5]);
        orderView.setText(str);

        // Change icon based on name
        String s = values[position];

        imageView.setImageResource(R.drawable.justjava);

        return rowView;
    }
}

