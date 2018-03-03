package arish.com.myapplication;

import android.app.Activity;
import android.content.Context;
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

    Button sendBtn;
    EditText editTxt;
    ArrayList<String> storeChat = new ArrayList<String> ();
    ChatAdapter messageAdapter;
    TextView message;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_window);
        sendBtn = (Button)findViewById(R.id.button4);
        editTxt = (EditText)findViewById(R.id.edit);

        ListView list = (ListView)findViewById(R.id.listview);
        messageAdapter =new ChatAdapter( this );
        list.setAdapter (messageAdapter);
        onClick();
    }

    public void onClick(){
        sendBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v){
                Log.v("EditText", editTxt.getText().toString());
                String chatString = editTxt.getText().toString();
                storeChat.add(chatString);
                messageAdapter.notifyDataSetChanged();
                editTxt.setText("");

            }
        });
    }

    private class ChatAdapter extends ArrayAdapter<String>
    {

        public ChatAdapter(Context ctx) {
            super(ctx, 0);
        }

        public int getCount(){
            return storeChat.size();
        }

        public String getItem(int position){
            return storeChat.get(position);
        }

        public View getView(int position, View convertView, ViewGroup parent){
            LayoutInflater inflater = ChatWindow.this.getLayoutInflater();
            View result = null ;
            if(position%2 == 0)
                result = inflater.inflate(R.layout.chat_row_incoming, null);
            else
                result = inflater.inflate(R.layout.chat_row_outgoing, null);
            TextView message = (TextView)result.findViewById(R.id.messageText);
            message.setText(   getItem(position)  );
            return result;

        }

    }

}
