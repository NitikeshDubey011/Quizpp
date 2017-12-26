package com.nitikesh.abc.quizpp.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.nitikesh.abc.quizpp.Interface.ItemClickListener;
import com.nitikesh.abc.quizpp.R;

/**
 * Created by Nitikesh Dubey on 12/26/2017.
 */

public class CategoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView category_name;
    public ImageView category_image;
    private ItemClickListener itemClickListener;

    public CategoryViewHolder(View itemView) {
        super(itemView);
        category_image=(ImageView)itemView.findViewById(R.id.category_image);
        category_name=(TextView)itemView.findViewById(R.id.category_name);
        itemView.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        itemClickListener.onClick(v, getAdapterPosition(), false);
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }
}
