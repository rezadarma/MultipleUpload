package com.reza.multipleupload.adapter;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.reza.multipleupload.R;
import com.reza.multipleupload.getterSetter.ListImage;

import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ViewHolder> {
    Context context;

    List<ListImage> listImages;

    public ImageAdapter(List<ListImage> getDataAdapter, Context context){

        super();

        this.listImages = getDataAdapter;

        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_image, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        final ListImage getDataAdapter1 =  listImages.get(position);
        holder.iv.setImageBitmap(BitmapFactory.decodeFile(getDataAdapter1.getPicturePath()));
        holder.iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
        holder.btnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listImages.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position,listImages.size());
            }
        });

    }

    @Override
    public int getItemCount() {

        return listImages.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        public ImageView iv;
        public Button btnRemove;
        public ViewHolder(View itemView) {

            super(itemView);
            iv = (ImageView) itemView.findViewById(R.id.Iv);
            btnRemove = (Button) itemView.findViewById(R.id.btnRemove);


        }
    }
}
