package com.example.lab6;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.lab6.databinding.ActivityChatRoomBinding;
import com.example.lab6.databinding.ReceiveMessageBinding;
import com.example.lab6.databinding.SentMessageBinding;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ChatRoom extends AppCompatActivity {

    ActivityChatRoomBinding binding;
    ArrayList<ChatMessage> messages;
    ChatRoomViewModel chatModel;

    private RecyclerView.Adapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        chatModel = new ViewModelProvider(this).get(ChatRoomViewModel.class);
        messages = chatModel.messages.getValue();

        if (messages == null) {
            chatModel.messages.postValue(messages = new ArrayList<>());
        }

        binding = ActivityChatRoomBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.recycleView.setLayoutManager(new LinearLayoutManager(this));

        binding.sendButton.setOnClickListener(click -> {
                    String typedMessage = binding.textInput.getText().toString();
                    onSendButtonClick(typedMessage);

                });

        binding.receiveButton.setOnClickListener(receiveclick -> {
            // Simulate receiving a message
            String typedMessage = binding.textInput.getText().toString();
            onReceiveButtonClick(typedMessage);
        });


        myAdapter = new RecyclerView.Adapter<MyRowHolder>() {
            @NonNull
            @Override
            public MyRowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                if (viewType == 0) {
                    SentMessageBinding binding = SentMessageBinding.inflate(getLayoutInflater());
                    return new MyRowHolder(binding.getRoot());
                } else {
                    ReceiveMessageBinding binding = ReceiveMessageBinding. inflate(getLayoutInflater());
                    return new MyRowHolder (binding.getRoot());
                }
            }

            @Override
            public void onBindViewHolder(@NonNull MyRowHolder holder, int position) {
                ChatMessage chatMessage = messages.get(position);

                holder.messageText.setText(chatMessage.getMessage());
                holder.timeText.setText(chatMessage.getTimeSent());
            }

            //returns the number of rows in the list
            @Override
            public int getItemCount() {
                return messages.size();
            }
            // returns an int which is the parameter which gets passed in to the onCreateViewHolder
            @Override
            public int getItemViewType(int position) {
                ChatMessage chatMessage = messages.get(position);
                return chatMessage.isSent() ? 0 : 1;
            }

        };

        binding.recycleView.setAdapter(myAdapter);
    }

    private void onSendButtonClick(String typedMessage) {
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE, dd-MMM-yyyy hh-mm-ss a");
        String currentDateandTime = sdf.format(new Date());

        ChatMessage sentMessage = new ChatMessage(typedMessage, currentDateandTime, true);

        messages.add(sentMessage);
        myAdapter.notifyItemInserted(messages.size() - 1);

        // Clear the previous text
        binding.textInput.setText("");
    }

    private void onReceiveButtonClick(String typedMessage) {
        // Simulate receiving a message
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE, dd-MMM-yyyy hh-mm-ss a");
        String currentDateandTime = sdf.format(new Date());

        ChatMessage receivedMessage = new ChatMessage(typedMessage, currentDateandTime, false);

        messages.add(receivedMessage);
        myAdapter.notifyItemInserted(messages.size() - 1);
        Log.d("ChatRoom", "Received message added: " + typedMessage);
    }


    static class MyRowHolder extends RecyclerView.ViewHolder {
        TextView messageText;
        TextView timeText;

        public MyRowHolder(@NonNull View itemView) {
            super(itemView);
            messageText = itemView.findViewById(R.id.messageText);
            timeText = itemView.findViewById(R.id.timeText);
        }
    }
}
