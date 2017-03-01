package com.cyberavanza.fmr.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.cyberavanza.fmr.R;
import com.cyberavanza.fmr.model.ChatListItem;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Ishaq Hassan on 12/2/2016.
 */

public class ChatsListAdapter extends RecyclerView.Adapter<ChatsListAdapter.ViewHolder>{
    ArrayList<ChatListItem> listItems = new ArrayList<>();
    Context context;
    private OnChatItemClickListener onChatItemClickListener;



    class ViewHolder extends RecyclerView.ViewHolder{

        View itemView;
        ImageView profileImageView;
        TextView nameTextView;
        TextView messageTextView;
        TextView timeTextView;

        ViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            this.profileImageView = (ImageView) itemView.findViewById(R.id.chatListItemProfileImageView);
            this.nameTextView = (TextView) itemView.findViewById(R.id.chatListItemNameTextView);
            this.messageTextView = (TextView) itemView.findViewById(R.id.chatListItemMessageTextView);
            this.timeTextView = (TextView) itemView.findViewById(R.id.chatListItemTimeTextView);
        }
    }

    public ChatsListAdapter(ArrayList<ChatListItem> items, Context context,OnChatItemClickListener onChatItemClickListener){
        listItems = items;
        this.context = context;
        this.onChatItemClickListener = onChatItemClickListener;
    }

    public ChatsListAdapter(ChatListItem[] items, Context context){
        listItems.addAll(Arrays.asList(items));
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        ChatListItem listItem = listItems.get(position);
        holder.itemView.setOnClickListener(v -> {
            onChatItemClickListener.onChatItemClick(position);
        });
        if(listItem.getImage() != null){
            Glide.with(context).load(listItem.getImage()).asBitmap().centerCrop().skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.ALL).into(new BitmapImageViewTarget(holder.profileImageView) {
                @Override
                protected void setResource(Bitmap resource) {
                    RoundedBitmapDrawable circularBitmapDrawable =
                            RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                    circularBitmapDrawable.setCircular(true);
                    holder.profileImageView.setImageDrawable(circularBitmapDrawable);
                }
            });
        }else{
            holder.profileImageView.setVisibility(View.GONE);
        }

        if(listItem.getName() != null){
            holder.nameTextView.setText(listItem.getName());
        }else {
            holder.nameTextView.setVisibility(View.GONE);
        }

        if(listItem.getMessage() != null){
            holder.messageTextView.setText(listItem.getMessage());
        }else{
            holder.messageTextView.setVisibility(View.GONE);
        }

        if(listItem.getTime() != null){
            holder.timeTextView.setText(listItem.getTime());
        }else{
            holder.timeTextView.setVisibility(View.GONE);
        }

        if(listItem.getClickListener() != null){
            holder.itemView.setOnClickListener(listItem.getClickListener());
        }
    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public interface OnChatItemClickListener {
        void onChatItemClick(int position);
    }
}