package com.linuxgroup.homeschool.client.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.linuxgroup.homeschool.client.R;
import com.linuxgroup.homeschool.client.domain.Message;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by tan on 14-9-20.
 */
public class ChatListAdapter extends BaseAdapter {
    // 用户的 Account, 用它来辨别是接收的消息还是发送的消息
    private String ownerAccount;

    private Context context;
    private List<Message> messages;
    LayoutInflater inflater;

    public ChatListAdapter(Context context, String ownerAccount, List<Message> messages) {
        this.context = context;
        this.messages = messages;
        inflater = LayoutInflater.from(context);
        this.ownerAccount = ownerAccount;
    }

    @Override
    public int getCount() {
        return messages.size();
    }

    @Override
    public Message getItem(int i) {
        return messages.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        CacheView cacheView = null;

        Message message = getItem(i);

        if (message.getFromAccount().equals(ownerAccount)) { // 表示发送的消息

            if (view == null || view.getTag(R.id.tag_right) == null) {
                view = inflater.inflate(R.layout.chat_listview_item_right, null);

                cacheView = new CacheView(view);

                view.setTag(R.id.tag_right, cacheView);
            } else {
                cacheView = (CacheView) view.getTag(R.id.tag_right);
            }
        } else { // 表示接收的消息

            if (view == null || view.getTag(R.id.tag_left) == null) {
                view = inflater.inflate(R.layout.chat_listview_item_left, null);

                cacheView = new CacheView(view);

                view.setTag(R.id.tag_left, cacheView);
            } else {
                cacheView = (CacheView) view.getTag(R.id.tag_left);
            }
        }


        cacheView.tv_content.setText(message.getContent());
        cacheView.tv_send_time.setText(message.getTime()+"");


        return view;
    }

    class CacheView {
        @InjectView(R.id.avatar_chat)
        ImageView iv_avatar;

        @InjectView(R.id.content)
        TextView tv_content;

        @InjectView(R.id.time)
        TextView tv_send_time;

        public CacheView(View view) {
            ButterKnife.inject(this, view);
        }
    }
}
