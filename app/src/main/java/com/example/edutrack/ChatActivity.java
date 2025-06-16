package com.example.edutrack;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.ImageButton;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ChatActivity extends AppCompatActivity {

    private EditText etMessage482;
    private RecyclerView recyclerMessages482;
    private MessageAdapter adapter482;
    private List<Message> messageList482;

    private DatabaseReference chatRef;
    private String userId482;
    private final String teacherId482 = "teacher_1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        findViewById(R.id.chatToolbar482);
        etMessage482 = findViewById(R.id.etMessage482);
        ImageButton btnSend482 = findViewById(R.id.btnSend482);
        recyclerMessages482 = findViewById(R.id.recyclerMessages482);

        userId482 = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
        chatRef = FirebaseDatabase.getInstance().getReference("chats").child(userId482).child(teacherId482);

        messageList482 = new ArrayList<>();
        adapter482 = new MessageAdapter(messageList482, userId482);

        recyclerMessages482.setLayoutManager(new LinearLayoutManager(this));
        recyclerMessages482.setAdapter(adapter482);

        loadMessages();

        btnSend482.setOnClickListener(v -> {
            String text = etMessage482.getText().toString().trim();
            if (!TextUtils.isEmpty(text)) {
                sendMessage(text);
                etMessage482.setText("");
            }
        });

    }

    private void sendMessage(String content) {
        String messageId = chatRef.push().getKey();
        Message message = new Message(messageId, userId482, teacherId482, content, System.currentTimeMillis());
        assert messageId != null;
        chatRef.child(messageId).setValue(message);
    }

    private void loadMessages() {
        chatRef.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                messageList482.clear();
                for (DataSnapshot msgSnap : snapshot.getChildren()) {
                    Message msg = msgSnap.getValue(Message.class);
                    messageList482.add(msg);
                }
                adapter482.notifyDataSetChanged();
                recyclerMessages482.scrollToPosition(messageList482.size() - 1);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) { }
        });
    }
}
