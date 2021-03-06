package jzkt3.umkc.edu.testtestetestsetestsetes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by jzz on 3/8/2015.
 */
public class RateAdapter extends ArrayAdapter<Rate> {
    public RateAdapter(Context context, int resource) {
        super(context, resource);
    }

    public RateAdapter(Context context, int resource, List<Rate> items){
        super(context,resource,items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        View v = convertView;

        if (v == null){
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.rates_layout,null);
        }

        Rate p = getItem(position);

        if (p != null){
            TextView t1 = (TextView) v.findViewById(R.id.nameName);
            TextView t2 = (TextView) v.findViewById(R.id.rateRate);
            TextView t3 = (TextView) v.findViewById(R.id.fnFN);

            if (t1 !=null){
                t1.setText(p.getFullName());
            }
            if (t2 !=null){
                t2.setText(Double.toString(p.getExchangeRate()));
            }
            if (t3 !=null){
                t3.setText(p.getName());
            }
        }
        return v;
    }
}
