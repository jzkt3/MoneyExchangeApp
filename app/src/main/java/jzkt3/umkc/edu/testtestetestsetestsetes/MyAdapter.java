package jzkt3.umkc.edu.testtestetestsetestsetes;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

/**
 * Created by jzz on 2/20/2015.
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private LayoutInflater inflater;
    List<Information> DrawerData = Collections.emptyList();


    public MyAdapter(Context context, List<Information> drawerData){
        inflater = LayoutInflater.from(context);
        this.DrawerData = drawerData;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.custom_row,parent,false);
        MyViewHolder holder = new MyViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        Information current = DrawerData.get(position);
        holder.title.setText(current.title);
        holder.icon.setImageResource(current.itemId);

    }

    @Override
    public int getItemCount() {
        return DrawerData.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView title;
        ImageView icon;

        public MyViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.listText);
            icon = (ImageView) itemView.findViewById(R.id.listIcon);
        }
    }
}
