package edu.skku.swp3.yp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.io.PrintWriter;

public class newStore extends AppCompatActivity {
    EditText edN, edM, edA,edIA,edSA;
    Button b;
    Spinner edC;
    String s;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newstore);
        edN=findViewById(R.id.editName);
        edM=findViewById(R.id.editMenu);
        edA=findViewById(R.id.editAddress);
        edIA=findViewById(R.id.editImageAddress);
        edSA=findViewById(R.id.editSearchAddress);
        b=findViewById(R.id.button);
        edC=findViewById(R.id.spinner);
        ArrayAdapter adapter=ArrayAdapter.createFromResource(getApplicationContext(),R.array.category,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(R.layout.spinner_item);
        edC.setAdapter(adapter);
        edC.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                s=parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edN.getText().toString().equals("")||s.equals("")||
                        edM.getText().toString().equals("")||edA.getText().toString().equals("")||
                        edIA.getText().toString().equals("")||edSA.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(),"모두 작성해주세요",Toast.LENGTH_LONG).show();
                }else{
                    try{
                        File mFile=new File(getApplicationContext().getCacheDir(),"a.csv");
                        FileOutputStream fos=new FileOutputStream(mFile,true);
                        PrintWriter pw=new PrintWriter(fos);
                        FileReader fr=new FileReader(mFile);
                        LineNumberReader lnr=new LineNumberReader(fr);
                        int lineNum=0;
                        while(lnr.readLine()!=null){
                            lineNum++;
                        }
                        lnr.close();
                        fr.close();

                        String whole=Integer.toString(lineNum)+","+edN.getText()+","+s+","+
                                edM.getText()+","+edA.getText()+","+edIA.getText()+","+
                                edSA.getText()+",0,0\n";
                        pw.append(whole);

                        finish();
                    }catch(IOException e){
                        e.printStackTrace();
                    }
                }
            }
        });

    }
}
