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
        final Newfeed newfeed = listNewfeed.get(position);
        ((NewfeedViewHolder) holder).txtTitle.setText(newfeed.getNewfeedTitle());
        ((NewfeedViewHolder) holder).txtDateTime.setText(newfeed.getNewfeedDateTime());
        ((NewfeedViewHolder) holder).txtShortContent.setText(newfeed.getNewfeedShortContent());
        ((NewfeedViewHolder) holder).txtSeeMore.setText("See more");
        if (newfeed.getNewfeedImage() != null && !newfeed.getNewfeedImage().isEmpty()) {
            new Until().circleTransformAvatar(context, ((NewfeedViewHolder) holder).imgImage, newfeed.getNewfeedImage(), R.drawable.ic_image);
        }

        ((NewfeedViewHolder) holder).txtSeeMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean expanded = newfeed.isExpanded();
                // Change the state
                newfeed.setExpanded(!expanded);
                // Notify the adapter that item has changed
                notifyItemChanged(position);
                ((NewfeedViewHolder) holder).bind(newfeed);
                ((NewfeedViewHolder) holder).txtSeeMore.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listNewfeed.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (isPositionHeader(position))
            return constant.TYPE_HEADER;
        return constant.TYPE_ITEM;
    }
    private boolean isPositionHeader(int position) {
        return position == 0;
    }
}
