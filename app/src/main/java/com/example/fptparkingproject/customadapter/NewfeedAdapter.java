package com.example.fptparkingproject.customadapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fptparkingproject.R;
import com.example.fptparkingproject.constant.Constant;
import com.example.fptparkingproject.model.Newfeed;
import com.example.fptparkingproject.untils.Until;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class NewfeedAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private ArrayList<Newfeed> listNewfeed;
    Constant constant = new Constant();

    public NewfeedAdapter(Context context, ArrayList<Newfeed> listNewfeed) {
        this.context = context;
        this.listNewfeed = listNewfeed;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.newfeed_layout, parent, false);
        return new NewfeedViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int position) {
        ((NewfeedViewHolder) holder).txtSeeMore.setVisibility(View.INVISIBLE);
        final Newfeed newfeed = listNewfeed.get(position);
        ((NewfeedViewHolder) holder).txtTitle.setText(newfeed.getNewfeedTitle());
        ((NewfeedViewHolder) holder).txtDateTime.setText(new Until().nomalizeDateTime(newfeed.getNewfeedDateTime()));
        if (!newfeed.getNewfeedShortContent().isEmpty() && !newfeed.isExpanded()) {
            ((NewfeedViewHolder) holder).txtShortContent.setText(newfeed.getNewfeedShortContent());
            ((NewfeedViewHolder) holder).txtSeeMore.setVisibility(View.VISIBLE);
        } else {
            ((NewfeedViewHolder) holder).txtShortContent.setText(newfeed.getNewfeedLongContent());
        }
        if (newfeed.getNewfeedImage() != null && !newfeed.getNewfeedImage().isEmpty()) {
            Picasso.with(context).load(newfeed.getNewfeedImage()).into(((NewfeedViewHolder) holder).imgImage);
        }

        ((NewfeedViewHolder) holder).txtSeeMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((NewfeedViewHolder) holder).txtShortContent.setVisibility(View.INVISIBLE);
                boolean expanded = newfeed.isExpanded();
                // Change the state
                newfeed.setExpanded(!expanded);
                // Notify the adapter that item has changed
                notifyItemChanged(position);
                ((NewfeedViewHolder) holder).bind(newfeed);
                ((NewfeedViewHolder) holder).txtSeeMore.setVisibility(View.INVISIBLE);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listNewfeed.size();
    }
}
