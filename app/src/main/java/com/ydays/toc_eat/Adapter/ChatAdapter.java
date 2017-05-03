package com.ydays.toc_eat.Adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.text.Layout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ydays.toc_eat.Class.ChatMessage;
import com.ydays.toc_eat.toc_eat.ChatActivity;
import com.ydays.toc_eat.toc_eat.R;

import java.util.List;

/**
 * Created by clemb on 02/05/2017.
 */

public class ChatAdapter extends BaseAdapter {

    private final List<ChatMessage> chatMessages;
    private Activity context;
    private static LayoutInflater inflater;


    public ChatAdapter(Activity context, List<ChatMessage> chatMessages) {
        this.context = context;
        this.chatMessages = chatMessages;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        if (chatMessages != null) {
            return chatMessages.size();
        } else {
            return 0;
        }
    }

    @Override
    public ChatMessage getItem(int position) {
        if (chatMessages != null) {
            return chatMessages.get(position);
        } else {
            return null;
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ChatMessage message = chatMessages.get(position);
        View vi = convertView;
        if (convertView == null)
            vi = inflater.inflate(R.layout.list_chat_message, null);

        TextView msg = (TextView) vi.findViewById(R.id.message_text);

        msg.setText(message.getMessage());
        msg.setTextColor(Color.WHITE);
        LinearLayout layout = (LinearLayout) vi
                .findViewById(R.id.bubble_layout);
        LinearLayout parent_layout = (LinearLayout) vi
                .findViewById(R.id.bubble_layout_parent);

        // if message is mine then align to right
        if (message.getIsme()) {
            layout.setBackgroundResource(R.mipmap.inmessage);
            parent_layout.setGravity(Gravity.RIGHT);


        }
        // If not mine then align to left
        else {
            layout.setBackgroundResource(R.mipmap.outmessage);
            parent_layout.setGravity(Gravity.LEFT);


        }
        msg.setTextColor(Color.BLACK);
        return vi;
    }


}