package arish.com.myapplication;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ChatWindow extends Activity {
    final ArrayList<String> chatArray = new ArrayList<>();
    private static final String ACTIVITY_NAME = "ChatWindow";
    ChatDatabaseHelper chatDatabaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_window);

        final ListView listViewChat = (ListView) findViewById(R.id.listview);
        final EditText editTextChat = (EditText) findViewById(R.id.edit);
        Button buttonSend = (Button) findViewById(R.id.button4);

        final ChatAdapter messageAdapter =new ChatAdapter( this );
        listViewChat.setAdapter (messageAdapter);

        chatDatabaseHelper = new ChatDatabaseHelper(this);
        Cursor cursor= chatDatabaseHelper.getChatMessages();
        while(cursor.moveToNext()){
            chatArray.add( cursor.getString( cursor.getColumnIndex(chatDatabaseHelper.KEY_Message) ) );
            Log.i(ACTIVITY_NAME, "SQL MESSAGE: " + cursor.getString( cursor.getColumnIndex(chatDatabaseHelper.KEY_Message) ) );
        }

        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String chatString = editTextChat.getText().toString();
                chatArray.add(chatString);
                chatDatabaseHelper.insertData(chatString);
                messageAdapter.notifyDataSetChanged();
                editTextChat.setText("");
            }
        });



    }
    public void onDestroy(){
        super.onDestroy();
        chatDatabaseHelper.close();
    }
    private class ChatAdapter extends ArrayAdapter<String> {
        public ChatAdapter(Context ctx) {
            super(ctx, 0);
        }
        public int getCount() {
            return chatArray.size();
        }


        public String getItem(int position) {
            return chatArray.get(position);
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = ChatWindow.this.getLayoutInflater();
            View result = null;
            if (position%2 == 0) {
                result = inflater.inflate(R.layout.chat_row_incoming, null);
            } else {
                result = inflater.inflate(R.layout.chat_row_outgoing, null);
            }


            TextView message = (TextView)result.findViewById(R.id.messageText);
            message.setText(   getItem(position)  );
            return result;

        }
    }
}