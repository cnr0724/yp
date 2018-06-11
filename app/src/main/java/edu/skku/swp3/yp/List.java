package edu.skku.swp3.yp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class List extends AppCompatActivity {
    BottomNavigationView bottomNavigation;
    Intent intent;
    ListView mListView;
    File mFile;
    Scanner sc;
    ArrayList<String> list=new ArrayList<>();
    ArrayList<String> nameList=new ArrayList<>();
    ArrayAdapter<String> adapter;
    private static String a="a.csv";
    String[] s;
    Button b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        bottomNavigation=(BottomNavigationView)findViewById(R.id.navigationView);
        mListView=findViewById(R.id.listView);
        mFile=new File(getApplicationContext().getCacheDir(),a);
        b=findViewById(R.id.button);

        try {
            sc = new Scanner(mFile);

            while (sc.hasNext()) {
                list.add(sc.nextLine());
            }
            sc.close();
            for(int i=0;i<list.size();i++){
                s=list.get(i).split(",");
                nameList.add(i,s[1]);
            }
            adapter=new ArrayAdapter<>(getApplicationContext(),R.layout.view_item,nameList);
            mListView.setAdapter(adapter);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                intent=new Intent(getApplicationContext(),showStore.class);
                intent.putExtra("storeName",nameList.get(position));
                Toast.makeText(getApplicationContext(),nameList.get(position),Toast.LENGTH_LONG).show();
                startActivity(intent);
            }
        });

        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id){

                AlertDialog.Builder popUp=new AlertDialog.Builder(List.this);
                View dialogView=getLayoutInflater().inflate(R.layout.dialograte,null);
                final RatingBar rating=dialogView.findViewById(R.id.dialogRb);
                TextView title=dialogView.findViewById(R.id.dialogTitle);
                title.setText("빠른 평가 : "+nameList.get(position));
                final int storeNum=position;

                popUp.setView(dialogView).setPositiveButton("평가", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        s=list.get(storeNum).split(",");
                        float rate=Float.valueOf(s[7]);
                        int rater=Integer.valueOf(s[8]);
                        rate=(rate*rater+rating.getRating())/(rater+1);
                        s[8]=String.valueOf(rater+1);
                        s[7]=String.valueOf(rate);
                        String s1=s[0]+","+s[1]+","+s[2]+","+s[3]+","+s[4]+","+s[5]+","+s[6]+","+s[7]+","+s[8];
                        list.set(storeNum,s1);
                        try{
                            PrintWriter pw=new PrintWriter(mFile);
                            for(int i=0;i<list.size();i++){
                                pw.append(list.get(i)+"\n");
                            }
                            pw.flush();
                            pw.close();
                        }catch(IOException e){
                            e.printStackTrace();
                        }
                        dialog.dismiss();
                    }
                }).setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                }).show();
                return true;
            }
        });


        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.navigationFirst:
                        Toast.makeText(getApplicationContext(),R.string.toast,Toast.LENGTH_LONG).show();
                    case R.id.navigationSecond:
//                        intent=new Intent(getApplicationContext(), Coupon.class);
                        startActivity(intent);
                        return true;
                    case R.id.navigationThird:
//                        intent=new Intent(getApplicationContext(), Community.class);
                        startActivity(intent);
                        return true;
                }
                return false;
            }
        });

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent=new Intent(getApplicationContext(),newStore.class);
                startActivity(intent);
            }
        });
    }
}
