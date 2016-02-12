package com.example.gjin.myhotel;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

/**
 * MainActivity class
 */
public class MainActivity extends Activity {


    private String[] result= new String[]{};
    private String city= new String();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final GetHotel getHotel= new GetHotel();//create a GetHotel object
        Button submitButton= (Button) findViewById(R.id.button);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editText = (EditText) findViewById(R.id.editText);
                city = editText.getText().toString();//get the input from the text input
                editText.setText("");//set the editText to empty every time
                //create a new thread
                Thread t = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        result = getHotel.searchHotel(city);//search the city from the server
                    }
                });
                t.start();
                try {
                    t.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                TextView text= (TextView) findViewById(R.id.textView2);
                if(result!=null){
                    text.setText("This is the hotel info of :"+city);
                    ListView listView= (ListView) findViewById(R.id.listView);
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, result);
                    listView.setAdapter(adapter);//set the result to the listView
                } else {
                    text.setText("Sorry, we do not have the hotel info of "+city);
                }


            }
        });

    }
}
