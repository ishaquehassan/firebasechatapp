package com.cyberavanza.fmr.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cyberavanza.fmr.R;
import com.cyberavanza.fmr.model.Message;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Ishaq Hassan on 12/2/2016.
 */

public class MessagesAdapter extends RecyclerView.Adapter<MessagesAdapter.ViewHolder>{
    ArrayList<Message> listItems = new ArrayList<>();
    Context context;


    class ViewHolder extends RecyclerView.ViewHolder{

        View itemView;
        ImageView senderArrow;
        ImageView receiverArrow;
        View senderSpacer;
        View receiverSpacer;
        TextView nameTextView;
        TextView timeTextView;

        LinearLayout messageTextContainer;
        TextView messageTextView;

        LinearLayout messageImageContainer;
        ImageView messageImageView;

        LinearLayout messageVoiceContainer;
        ImageView messageVoicePlayBtn;
        TextView messageVoiceTimePlayed;
        SeekBar messageVoiceSeekbar;
        TextView messageVoiceTimeTotal;

        LinearLayout messageDocumentContainer;
        TextView messageDocumentName;
        TextView messageDocumentSize;
        ImageView messageDocumentDownload;

        LinearLayout messageBgView;

        ViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            this.senderArrow = (ImageView) itemView.findViewById(R.id.senderArrow);
            this.receiverArrow = (ImageView) itemView.findViewById(R.id.receiverArrow);
            this.senderSpacer = itemView.findViewById(R.id.senderSpacer);
            this.receiverSpacer = itemView.findViewById(R.id.receiverSpacer);
            this.nameTextView = (TextView) itemView.findViewById(R.id.messageSenderName);
            this.timeTextView = (TextView) itemView.findViewById(R.id.messageTime);

            this.messageTextContainer = (LinearLayout) itemView.findViewById(R.id.messageTextContainer);
            this.messageTextView = (TextView) itemView.findViewById(R.id.messageText);

            this.messageImageContainer = (LinearLayout) itemView.findViewById(R.id.messageImageContainer);
            this.messageImageView = (ImageView) itemView.findViewById(R.id.messageImage);

            this.messageVoiceContainer = (LinearLayout) itemView.findViewById(R.id.messageVoiceContainer);
            this.messageVoicePlayBtn = (ImageView) itemView.findViewById(R.id.messageVoicePlayBtn);
            this.messageVoiceTimePlayed = (TextView) itemView.findViewById(R.id.messageVoiceTimePlayed);
            this.messageVoiceSeekbar = (SeekBar) itemView.findViewById(R.id.messageVoiceSeekbar);
            this.messageVoiceTimeTotal = (TextView) itemView.findViewById(R.id.messageVoiceTimeTotal);

            this.messageDocumentContainer = (LinearLayout) itemView.findViewById(R.id.messageDocumentContainer);
            this.messageDocumentName = (TextView) itemView.findViewById(R.id.messageDocumentName);
            this.messageDocumentSize = (TextView) itemView.findViewById(R.id.messageDocumentSize);
            this.messageDocumentDownload = (ImageView) itemView.findViewById(R.id.messageDocumentDownload);

            this.messageBgView = (LinearLayout) itemView.findViewById(R.id.messageBgView);
        }
    }


    public MessagesAdapter(ArrayList<Message> items, Context context){
        listItems = items;
        this.context = context;
    }

    public MessagesAdapter(Message[] items, Context context){
        listItems.addAll(Arrays.asList(items));
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.message_item, parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Message listItem = listItems.get(position);

        if(listItem.isFromMe()){
            holder.senderArrow.setVisibility(View.VISIBLE);
            holder.receiverArrow.setVisibility(View.GONE);
            holder.senderSpacer.setVisibility(View.VISIBLE);
            holder.receiverSpacer.setVisibility(View.GONE);
            ((LinearLayout)holder.itemView).setGravity(Gravity.LEFT);
            holder.messageBgView.setBackgroundResource(R.drawable.text_message_to_bg);
        }else{
            holder.senderArrow.setVisibility(View.GONE);
            holder.receiverArrow.setVisibility(View.VISIBLE);
            holder.senderSpacer.setVisibility(View.GONE);
            holder.receiverSpacer.setVisibility(View.VISIBLE);
            ((LinearLayout)holder.itemView).setGravity(Gravity.RIGHT);
            holder.messageBgView.setBackgroundResource(R.drawable.text_message_from_bg);
        }

        if(listItem.getName() != null){
            holder.nameTextView.setText(listItem.getName());
        }else {
            holder.nameTextView.setVisibility(View.GONE);
        }

        if(listItem.getTime() != null){
            holder.timeTextView.setText(listItem.getTime());
        }else {
            holder.timeTextView.setVisibility(View.GONE);
        }

        switch (listItem.getMessageType()){
            case TEXT:
                holder.messageTextContainer.setVisibility(View.VISIBLE);
                holder.messageImageContainer.setVisibility(View.GONE);
                holder.messageVoiceContainer.setVisibility(View.GONE);
                holder.messageDocumentContainer.setVisibility(View.GONE);
                Message.TextMessage textMessage = listItem.getTextMessage();
                if(textMessage != null && textMessage.getMessageText() != null){
                    holder.messageTextView.setText(textMessage.getMessageText());
                }else {
                    holder.messageTextView.setVisibility(View.GONE);
                }
                break;
            case IMAGE:
                holder.messageImageContainer.setVisibility(View.VISIBLE);
                holder.messageTextContainer.setVisibility(View.GONE);
                holder.messageVoiceContainer.setVisibility(View.GONE);
                holder.messageDocumentContainer.setVisibility(View.GONE);
                Message.ImageMessage imageMessage = listItem.getImageMessage();
                if(imageMessage != null && imageMessage.getMessageImageUrl() != null){
                    Glide.with(context).load(imageMessage.getMessageImageUrl()).into(holder.messageImageView);
                }else {
                    holder.messageImageView.setVisibility(View.GONE);
                }
                break;
            case VOICE:
                holder.messageVoiceContainer.setVisibility(View.VISIBLE);
                holder.messageTextContainer.setVisibility(View.GONE);
                holder.messageImageContainer.setVisibility(View.GONE);
                holder.messageDocumentContainer.setVisibility(View.GONE);
                Message.VoiceMessage voiceMessage = listItem.getVoiceMessage();
                if(voiceMessage != null && voiceMessage.getMessageVoiceUrl() != null){
                    //holder.messageTextView.setText(listItem.getMessageText());
                }else {
                    //holder.messageTextView.setVisibility(View.GONE);
                }
                break;
            case DOCUMENT:
                holder.messageDocumentContainer.setVisibility(View.VISIBLE);
                holder.messageTextContainer.setVisibility(View.GONE);
                holder.messageImageContainer.setVisibility(View.GONE);
                holder.messageVoiceContainer.setVisibility(View.GONE);
                Message.DocumentMessage documentMessage = listItem.getDocumentMessage();
                if(documentMessage != null && documentMessage.getMessageDocumentName() != null){
                    holder.messageDocumentName.setText(documentMessage.getMessageDocumentName());
                }else {
                    holder.messageDocumentName.setVisibility(View.GONE);
                }
                break;
        }

        if(listItem.getClickListener() != null){
            holder.itemView.setOnClickListener(listItem.getClickListener());
        }

    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }
}