package edu.skku.swp3.yp;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

public class showStore extends AppCompatActivity {

    TextView mTextView,mMenu, mRating,mAddress;
    ImageView mImageView;
    File mFile;
    Scanner sc;
    String storeName, rate,menun,addr,imagea,searcha;
    Intent intent;
    Button b, ratingB;
    Handler handler=new Handler();
    ArrayList<String> list=new ArrayList<>();
    RatingBar mRatingBar;
    private static String a="a.csv";
    String[] s=null;
    int storeNum, rater;
    float ratee;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showstore);

        intent=getIntent();
        storeName=intent.getStringExtra("storeName");
        mTextView=findViewById(R.id.storeName);
        mImageView=findViewById(R.id.imageView);
        mMenu=findViewById(R.id.menu);
        mRating=findViewById(R.id.textRate);
        mRatingBar=findViewById(R.id.ratingBar);
        ratingB=findViewById(R.id.ratingButton);
        mAddress=findViewById(R.id.address);

        mFile=new File(getApplicationContext().getCacheDir(),a);
        b=findViewById(R.id.moreInfo);
        rate="평균 평점 : ";

        try {
            sc = new Scanner(mFile);
            while (sc.hasNext()) {
                list.add(sc.nextLine());
            }
            sc.close();
            for(int i=0;i<list.size();i++){
                s=list.get(i).split(",");
                if(s[1].equals(storeName)){
                    storeNum=Integer.valueOf(s[0]);
                    menun=s[3];
                    addr=s[4];
                    imagea=s[5];
                    searcha=s[6];
                    ratee=Float.valueOf(s[7]);
                    if(s[8].equals("0")){
                        rater=0;
                    }else{
                        rater=Integer.valueOf(s[8]);
                    }
                    break;
                }
            }//여기서 얻은 storeNum으로 평점 얻어올 예정

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    URL url = new URL(s[5]);
                    //차후에 이 부분은 아예 가게 csv에 이미지 주소를 저장하는 방식으로 바꿔야 할 듯.
                    InputStream is = url.openStream();
                    final Bitmap bm = BitmapFactory.decodeStream(is);
                    handler.post(new Runnable() {

                        @Override
                        public void run() {
                            mImageView.setImageBitmap(bm);
                        }
                    });
                    mImageView.setImageBitmap(bm); //비트맵 객체로 보여주기
                } catch(Exception e){
                    e.printStackTrace();
                }
            }
        });

        t.start();

        ratingB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ratee=(ratee*rater+mRatingBar.getRating())/(rater+1);
                s[7]=String.valueOf(ratee);
                s[8]=String.valueOf(rater+1);
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
            }
        });

        mMenu.setText(menun);
        mAddress.setText(addr);
        mRating.setText(String.valueOf(ratee));
        mTextView.setText(storeName);

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri=Uri.parse(s[6]);
                intent=new Intent(Intent.ACTION_VIEW,uri);
                startActivity(intent);
            }
        });

    }
}
