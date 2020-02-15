package com.mobileproje;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.mobileproje.Activity.SecondActivity;

import java.util.ArrayList;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.MyViewHolder> {

    int imagess[] = {R.drawable.img1, R.drawable.img2, R.drawable.img3};
    Context context;
    ArrayList<Concert> data;
    SecondActivity ma;
    public MyRecyclerViewAdapter(Context context, ArrayList<Concert> data) {
        this.context = context;
        this.data =data;
        ma = (SecondActivity)context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.recyclerview_layout, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        //BIND DATA
        final Concert con = data.get(position);
        holder.tv1.setText(con.getName());
        holder.tv2.setText(con.getPlace());
        holder.tv3.setText(con.getPrice()+""+" TL");
        holder.image.setImageResource(imagess[position]);


    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        ConstraintLayout constlayoutt;
        ImageView image;
        TextView tv1;
        TextView tv2;
        TextView tv3;



        MyViewHolder(View viewItem){
            super(viewItem);
            constlayoutt = viewItem.findViewById(R.id.constlayoutt);
            tv1 = viewItem.findViewById(R.id.name);
            tv2 = viewItem.findViewById(R.id.place);
            tv3=viewItem.findViewById(R.id.price);
            image=viewItem.findViewById(R.id.imgsanatci);
            viewItem.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    final Concert concert = data.get(getLayoutPosition());
                    ma.displayDialog(getLayoutPosition(), concert );
                    return false;
                }
            });
        }

    }
}
