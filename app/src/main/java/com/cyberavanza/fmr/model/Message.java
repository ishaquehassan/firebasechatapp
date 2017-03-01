package com.cyberavanza.fmr.model;

import android.view.View;

/**
 * Created by Ishaq Hassan on 12/3/2016.
 */

public class Message {
    String id;
    String name;
    String time;
    String uid;

    TextMessage textMessage;
    ImageMessage imageMessage;
    VoiceMessage voiceMessage;
    DocumentMessage documentMessage;

    MessageType messageType;
    View.OnClickListener clickListener;
    boolean fromMe = false;

    public Message() {
    }

    public Message(String id, String name, String time, TextMessage textMessage, ImageMessage imageMessage, VoiceMessage voiceMessage, DocumentMessage documentMessage, MessageType messageType, View.OnClickListener clickListener, boolean fromMe,String uid) {
        this.id = id;
        this.name = name;
        this.time = time;
        this.textMessage = textMessage;
        this.imageMessage = imageMessage;
        this.voiceMessage = voiceMessage;
        this.documentMessage = documentMessage;
        this.messageType = messageType;
        this.clickListener = clickListener;
        this.fromMe = fromMe;
        this.uid = uid;
    }

    public Message(String id, String name, String time, TextMessage textMessage, boolean fromMe, View.OnClickListener clickListener,String uid) {
        this.id = id;
        this.name = name;
        this.time = time;
        this.textMessage = textMessage;
        this.fromMe = fromMe;
        this.clickListener = clickListener;
        this.messageType = MessageType.TEXT;
        this.uid = uid;
    }

    public Message(String id, String name, String time, TextMessage textMessage, boolean fromMe,String uid) {
        this.id = id;
        this.name = name;
        this.time = time;
        this.textMessage = textMessage;
        this.fromMe = fromMe;
        this.messageType = MessageType.TEXT;
        this.uid = uid;
    }

    public Message(String id, String name, String time, ImageMessage imageMessage, boolean fromMe, View.OnClickListener clickListener,String uid) {
        this.id = id;
        this.name = name;
        this.time = time;
        this.imageMessage = imageMessage;
        this.clickListener = clickListener;
        this.fromMe = fromMe;
        this.messageType = MessageType.IMAGE;
        this.uid = uid;
    }

    public Message(String id, String name, String time, ImageMessage imageMessage, boolean fromMe,String uid) {
        this.id = id;
        this.name = name;
        this.time = time;
        this.imageMessage = imageMessage;
        this.fromMe = fromMe;
        this.messageType = MessageType.IMAGE;
        this.uid = uid;
    }

    public Message(String id, String name, String time, VoiceMessage voiceMessage, boolean fromMe, View.OnClickListener clickListener,String uid) {
        this.id = id;
        this.name = name;
        this.time = time;
        this.voiceMessage = voiceMessage;
        this.clickListener = clickListener;
        this.fromMe = fromMe;
        this.messageType = MessageType.VOICE;
        this.uid = uid;
    }

    public Message(String id, String name, String time, VoiceMessage voiceMessage, boolean fromMe,String uid) {
        this.id = id;
        this.name = name;
        this.time = time;
        this.voiceMessage = voiceMessage;
        this.fromMe = fromMe;
        this.messageType = MessageType.VOICE;
        this.uid = uid;
    }

    public Message(String id, String name, String time, DocumentMessage documentMessage, boolean fromMe, View.OnClickListener clickListener,String uid) {
        this.id = id;
        this.name = name;
        this.time = time;
        this.documentMessage = documentMessage;
        this.clickListener = clickListener;
        this.fromMe = fromMe;
        this.messageType = MessageType.DOCUMENT;
        this.uid = uid;
    }

    public Message(String id, String name, String time, DocumentMessage documentMessage, boolean fromMe,String uid) {
        this.id = id;
        this.name = name;
        this.time = time;
        this.documentMessage = documentMessage;
        this.fromMe = fromMe;
        this.messageType = MessageType.DOCUMENT;
        this.uid = uid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public TextMessage getTextMessage() {
        return textMessage;
    }

    public void setTextMessage(TextMessage textMessage) {
        this.textMessage = textMessage;
    }

    public ImageMessage getImageMessage() {
        return imageMessage;
    }

    public void setImageMessage(ImageMessage imageMessage) {
        this.imageMessage = imageMessage;
    }

    public VoiceMessage getVoiceMessage() {
        return voiceMessage;
    }

    public void setVoiceMessage(VoiceMessage voiceMessage) {
        this.voiceMessage = voiceMessage;
    }

    public DocumentMessage getDocumentMessage() {
        return documentMessage;
    }

    public void setDocumentMessage(DocumentMessage documentMessage) {
        this.documentMessage = documentMessage;
    }

    public MessageType getMessageType() {
        return messageType;
    }

    public void setMessageType(MessageType messageType) {
        this.messageType = messageType;
    }

    public View.OnClickListener getClickListener() {
        return clickListener;
    }

    public void setClickListener(View.OnClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public boolean isFromMe() {
        return fromMe;
    }

    public void setFromMe(boolean fromMe) {
        this.fromMe = fromMe;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public static class TextMessage{
        String messageText;

        public TextMessage() {
        }

        public TextMessage(String messageText) {
            this.messageText = messageText;
        }

        public String getMessageText() {
            return messageText;
        }

        public void setMessageText(String messageText) {
            this.messageText = messageText;
        }
    }

    public static class ImageMessage{
        String messageImageUrl;

        public ImageMessage() {
        }

        public ImageMessage(String messageImageUrl) {
            this.messageImageUrl = messageImageUrl;
        }

        public String getMessageImageUrl() {
            return messageImageUrl;
        }

        public void setMessageImageUrl(String messageImageUrl) {
            this.messageImageUrl = messageImageUrl;
        }
    }

    public static class VoiceMessage{
        String messageVoiceUrl;

        public VoiceMessage() {
        }

        public VoiceMessage(String messageVoiceUrl) {
            this.messageVoiceUrl = messageVoiceUrl;
        }

        public String getMessageVoiceUrl() {
            return messageVoiceUrl;
        }

        public void setMessageVoiceUrl(String messageVoiceUrl) {
            this.messageVoiceUrl = messageVoiceUrl;
        }
    }

    public static class DocumentMessage{
        String messageDocumentName;
        String messageDocumentUrl;

        public DocumentMessage() {
        }

        public DocumentMessage(String messageDocumentName, String messageDocumentUrl) {
            this.messageDocumentName = messageDocumentName;
            this.messageDocumentUrl = messageDocumentUrl;
        }

        public String getMessageDocumentName() {
            return messageDocumentName;
        }

        public void setMessageDocumentName(String messageDocumentName) {
            this.messageDocumentName = messageDocumentName;
        }

        public String getMessageDocumentUrl() {
            return messageDocumentUrl;
        }

        public void setMessageDocumentUrl(String messageDocumentUrl) {
            this.messageDocumentUrl = messageDocumentUrl;
        }
    }



}
