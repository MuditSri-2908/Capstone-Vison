package com.example.android.capstone.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.example.android.capstone.R;
import com.example.android.capstone.model.Hit;
import com.example.android.capstone.model.Pic;
import com.example.android.capstone.ui.PicDetail;
import com.example.android.capstone.ui.util.WallpViewHolder;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.L;

/**
 * Created by DELL on 1/11/2017.
 */

public class CollectionAdapter extends RecyclerView.Adapter<WallpViewHolder> {

    private List<Hit> hit;
    private Context context;
    public int width;
    public int height;

    public CollectionAdapter(Context context){

        this.context=context;
        this.hit=new ArrayList<>();

    }

    @Override
    public WallpViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        // View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.wallp_item, null);

        View itemView = LayoutInflater.from(context).inflate(R.layout.wallp_item, null);
        final WallpViewHolder rcv=new WallpViewHolder(itemView);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = rcv.getAdapterPosition();
                Intent intent = new Intent(context, PicDetail.class);
                intent.putExtra(PicDetail.EXTRA_PIC, hit.get(position));
                intent.putExtra(PicDetail.origin,"collection");
                context.startActivity(intent);
            }
        });



        return rcv;
    }

    @Override
    public void onBindViewHolder(WallpViewHolder holder, int position) {

        Hit photo = this.hit.get(position);
        RelativeLayout.LayoutParams rlp = (RelativeLayout.LayoutParams) holder.discWallp.getLayoutParams();
        float height =photo.getPreviewHeight();
        float width = photo.getPreviewWidth();
        float ratio =  height/width ;
        rlp.height = (int) (rlp.width * ratio);
        holder.discWallp.setLayoutParams(rlp);

        holder.discWallp.setRatio(ratio);

        File file = new File(Environment.getExternalStoragePublicDirectory("/Canvas Vision"), photo.getId()+".jpg");


        Picasso.with(context)
                .load(file)
                .placeholder(R.drawable.plh)
                .into(holder.discWallp);
    }

    @Override
    public int getItemCount()
    {
        return (hit == null) ? 0 : hit.size();
    }

    public void setHitList(List<Hit> hits)
    {
        // this.hit.clear();
        if(hits!=null)
            this.hit.addAll(hits);
        // The adapter needs to know that the data has changed. If we don't call this, app will crash.
        notifyDataSetChanged();
    }


}
