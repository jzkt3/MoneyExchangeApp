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
public class DrawerAdapter extends RecyclerView.Adapter<DrawerAdapter.MyViewHolder> {

    private LayoutInflater inflater;
    List<Information> DrawerData = Collections.emptyList();
    private Context context;

    private ClickListener clickListener;


    public DrawerAdapter(Context context, List<Information> drawerData){
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.DrawerData = drawerData;
    }


    public void delete(int position){
         DrawerData.remove(position);
        notifyItemRemoved(position);

    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.custom_row,parent,false);
        MyViewHolder holder = new MyViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder,int position) {

        Information current = DrawerData.get(position);
        holder.title.setText(current.title);
        holder.icon.setImageResource(current.itemId);


    }

    public void setClickListener(ClickListener clickListener){
        this.clickListener = clickListener;

    }


    @Override
    public int getItemCount() {
        return DrawerData.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView title;
        ImageView icon;

        public MyViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            title = (TextView) itemView.findViewById(R.id.listText);
            icon = (ImageView) itemView.findViewById(R.id.listIcon);

        }

        @Override
        public void onClick(View v) {

            //Place holder activity

            //if (getPosition() == 1){
            //    context.startActivity(new Intent(context,InfoActivity.class));
            //
            //}


            if (clickListener != null)
            {
                clickListener.itemClicked(v,getPosition());
            }

        }
    }

    public interface ClickListener{
        public void itemClicked(View view,int position);
    }
}
