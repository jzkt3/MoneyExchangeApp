package jzkt3.umkc.edu.testtestetestsetestsetes;

import android.content.ClipData;
import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by jzz on 3/8/2015.
 */

public class HistoryAdapter extends ArrayAdapter<History> {
    public HistoryAdapter(Context context, int resource) {
        super(context, resource);
    }

    public HistoryAdapter(Context context, int resource, List<History> items){
        super(context,resource,items);
    }

    @Override
    public View getView(final int position, final View convertView, ViewGroup parent){
        View v = convertView;

        if (v == null){
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.history_layout,null);
        }

        History p = getItem(position);

        if (p != null){
            TextView t1 = (TextView) v.findViewById(R.id.dateDate);
            TextView t2 = (TextView) v.findViewById(R.id.timeTime);
            TextView t3 = (TextView) v.findViewById(R.id.nN);
            TextView t4 = (TextView) v.findViewById(R.id.rR);
            TextView t5 = (TextView) v.findViewById(R.id.dD);
            final TextView t6 = (TextView) v.findViewById(R.id.resultResult);


            ImageButton b1 = (ImageButton) v.findViewById(R.id.imageButton);
            b1.setTag(position);

            if (t1 !=null){
                t1.setText("Date: "+p.getDate());
            }
            if (t2 !=null){
                t2.setText("Time: "+p.getTime());
            }
            if (t3 !=null){
                t3.setText("Currency: "+p.getName());
            }
            if (t4 !=null){
                t4.setText("Rate: "+p.getRate());
            }
            if (t5 !=null){
                t5.setText("Dollars: "+p.getDollars());
            }
            if (t6 !=null){
                t6.setText(p.getResult());
            }
            if (b1 !=null){
                b1.setImageResource(R.drawable.delete_button);
            }


            b1.setOnClickListener(new ImageButton.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DBAdapter helper;
                    helper = new DBAdapter(getContext());
                    Integer index = (Integer) v.getTag();
                    String result = t6.getText().toString();
                    HistoryActivity.data.remove(index.intValue());

                    try {
                        helper.delete(result);
                    }catch(Exception x){
                        System.out.println("Item does not exist in database");
                    }
                    
                    notifyDataSetChanged();
                }
            });
        }
        return v;
    }
}
