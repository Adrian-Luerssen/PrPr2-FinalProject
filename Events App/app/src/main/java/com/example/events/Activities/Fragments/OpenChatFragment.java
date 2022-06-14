package com.example.events.Activities.Fragments;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.events.DataStructures.Users.AuthUser;
import com.example.events.DataStructures.Users.Message;
import com.example.events.DataStructures.Users.User;
import com.example.events.Persistence.ServiceAPI;
import com.example.events.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OpenChatFragment extends Fragment {
    public static final long REFRESH_TIME = 1000;
    private final User recipient;
    private ArrayList<Message> messages;
    private RecyclerView messageRecycler;
    private boolean notClosed;

    private EditText messageInput;


    private ChatOnClickListener listener;
    private MessageAdapter messageAdapter;

    public interface ChatOnClickListener{
        void onBackClicked();
        void onProfileClicked(User user);
    }

    public OpenChatFragment(User recipient){
        this.recipient = recipient;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view2 = inflater.inflate(R.layout.open_chat_fragment, container, false);
        TextView username = view2.findViewById(R.id.username);
        ImageView back = view2.findViewById(R.id.back_button);
        messageRecycler = view2.findViewById(R.id.messageRecyclerView);
        messageRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        Button sendMessage = view2.findViewById(R.id.sendMessage);
        messageInput = view2.findViewById(R.id.messageInput);
        username.setText(recipient.getName()+" "+recipient.getLastName());
        messages = new ArrayList<>();
        notClosed = true;
        updateUI();
        refresh();
        username.setOnClickListener(view1 -> {
            listener.onProfileClicked(recipient);
            notClosed = false;
        });
        back.setOnClickListener(view1 -> {
            listener.onBackClicked();
            notClosed = false;
        });

        sendMessage.setOnClickListener(view -> {
            String message = messageInput.getText().toString();
            if (message.isEmpty()) {
                Toast.makeText(getContext(), "Message cannot be empty", Toast.LENGTH_SHORT).show();
            } else {
                Message newMessage = new Message(AuthUser.getAuthUser().getId(), recipient.getId(), message);
                ServiceAPI.getInstance().sendMessage(newMessage, AuthUser.getAuthUser().getToken().getToken()).enqueue(new Callback<Message>() {
                    @Override
                    public void onResponse(Call<Message> call, Response<Message> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(getContext(), "Message sent", Toast.LENGTH_SHORT).show();
                            messageInput.setText("");
                            messages.add(response.body());
                            updateUI();
                        } else {
                            Toast.makeText(getContext(), "Message not sent", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Message> call, Throwable t) {
                        Toast.makeText(getContext(), "Message not sent", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });


        return view2;
    }


    public void refresh (){
        getMessages();
        if (messageAdapter.messageList.size() != messages.size()){
            updateUI();
        }
        refreshUI();
    }


    private void refreshUI() {
        if (notClosed){
            final Handler handler = new Handler();
            final Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    refresh();
                }
            };
            handler.postDelayed(runnable, OpenChatFragment.REFRESH_TIME);
        }

    }

    public void updateUI(){
        getMessages();
        messageAdapter = new MessageAdapter(messages);
        messageRecycler.setAdapter(messageAdapter);
        System.out.println("Top:"+messageRecycler.getTop()+" Bottom:"+messageRecycler.getBottom()+" Baseline:"+messageRecycler.getBaseline());
        messageRecycler.scrollBy(0,Integer.MAX_VALUE);
        System.out.println(messageRecycler.getY());
    }

    private void getMessages() {
        ServiceAPI.getInstance().getMessages(recipient.getId(),AuthUser.getAuthUser().getToken().getToken()).enqueue(new Callback<List<Message>>() {
            @Override
            public void onResponse(Call<List<Message>> call, Response<List<Message>> response) {
                if (response.isSuccessful()) {
                    messages = (ArrayList<Message>) response.body();
                    Log.d("messages", messages.toString());
                }
            }

            @Override
            public void onFailure(Call<List<Message>> call, Throwable t) {

            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ChatOnClickListener) {
            listener = (ChatOnClickListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement ChatOnClickListener");
        }
    }


    private class MessageAdapter extends RecyclerView.Adapter<MessageHolder> {

        private List<Message> messageList = new ArrayList<>();


        private MessageAdapter(List<Message> messageList) {
            this.messageList = messageList;
        }

        @Override
        public MessageHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view;
            if (viewType == MessageHolder.VIEW_TYPE_SENT) {
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_sent_item, parent, false);
            } else {
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_recived_item, parent, false);
            }
            return new MessageHolder(view);

        }

        @Override
        public void onBindViewHolder(MessageHolder holder, int position) {
            Message message = messageList.get(position);
            holder.bind(message);
        }

        @Override
        public int getItemViewType(int position) {
            if (messageList.get(position).getSenderID() == AuthUser.getAuthUser().getId()) {
                return MessageHolder.VIEW_TYPE_SENT;
            } else {
                return MessageHolder.VIEW_TYPE_RECEIVED;
            }
        }

        @Override
        public int getItemCount() {
            return messageList.size();
        }
    }


    // View Holder
    private class MessageHolder extends RecyclerView.ViewHolder {

        public static final int VIEW_TYPE_SENT = 1;
        public static final int VIEW_TYPE_RECEIVED = 2;
        private Message message;
        private final TextView messageText;

        public MessageHolder(View view) {
            super(view);
            messageText = view.findViewById(R.id.message_text);
        }

        public void bind(Message message) {
            messageText.setText(message.getContent());
        }


    }
}