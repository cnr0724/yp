package edu.skku.swp3.yp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Community extends AppCompatActivity {
    List<String> data;
    ArrayAdapter<String> adapter;
    ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_community);

        data = new ArrayList<String>();


        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_single_choice,data);
        list =(ListView)findViewById(R.id.list);
        list.setAdapter(adapter);
        list.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String item = data.get(position);

                Toast.makeText(Community.this,item,Toast.LENGTH_LONG).show();
            }
        });


        Button add = (Button)findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                EditText newItem = (EditText)findViewById(R.id.newitem);
                String item = newItem.getText().toString();
                if(item!=null || item.trim().length()>0){
                    data.add(item.trim());
                    adapter.notifyDataSetChanged();
                    newItem.setText("");
                }


            }
        });

        Button delete = (Button)findViewById(R.id.delete);
        delete.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                int pos = list.getCheckedItemPosition();
                if(pos>=0 && pos<data.size()){
                    data.remove(pos);
                    list.clearChoices();
                    adapter.notifyDataSetChanged();

                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
