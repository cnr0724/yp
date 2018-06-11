package edu.skku.swp3.yp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class FirstAfterLogin extends AppCompatActivity {
    File mFile=null;
    Scanner scanner;
    ListView mListView;
    private static String a="a.csv";
    ArrayList<String> data=new ArrayList<>();
    ArrayAdapter<String> adapter;
    Intent intent,getIntent;
    ArrayList<String> list=new ArrayList<>();
    ArrayList<String> chosenList=new ArrayList<>();
    int r1,r2,r3,size;
    Random r=new Random();
    TextView t1, t2, t3, t4, t5, t6;
    String[] s;
    String name;
    int c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firstafterlogin);

//        getIntent=getIntent();
        mListView=findViewById(R.id.listView);
        mFile = new File(getApplicationContext().getCacheDir(),a);
        t1=findViewById(R.id.trtv1);
        t2=findViewById(R.id.trtv2);
        t3=findViewById(R.id.trtv3);
        t4=findViewById(R.id.trtv1m);
        t5=findViewById(R.id.trtv2m);
        t6=findViewById(R.id.trtv3m);
        list=new ArrayList<>();

        if(!mFile.exists()){
            try {
                mFile.createNewFile();
            }catch(IOException e){
                e.printStackTrace();
            }
        }//파일 있는지 없는지 확인해서 없을 경우 생성

        try{
            PrintWriter pw=new PrintWriter(mFile);
            pw.append("0,화덕초대파불고기,한식,메인 메뉴1,율전1,https://pbs.twimg.com/media/DKPEyOjVAAABLn_.jpg,http://www.hwadukcho.com/,0,0\n");
            pw.append("1,지지고,분식,메인 메뉴2,율전2,https://pbs.twimg.com/profile_images/2090074551/Untitled-1_copy_400x400.jpg,http://www.gggo.co.kr/,0,0\n");
            pw.append("2,미가 라멘,양식,메인 메뉴3,율전3,https://img.siksinhot.com/place/1515687127659241.jpg?w=540&h=436&c=Y," +
                    "https://store.naver.com/restaurants/detail?entry=plt&id=37620144&query=%EB%AF%B8%EA%B0%80%EB%9D%BC%EB%A9%98,0,0\n");
            pw.flush();
            pw.close();
        }catch(IOException e){
            e.printStackTrace();
        }

        try {
            scanner = new Scanner(mFile);
            while (scanner.hasNext()) {
                String s = scanner.nextLine() + "\n";
                list.add(s);
            }
            scanner.close();

            if (list != null) {

                c = recommendation(list);
                while (c == 4) {
                    c = recommendation(list);
                }
                switch (c) {
                    case 1:
                        size = chosenList.size() - 1;
                        r1 = r.nextInt(size);
                        r2 = r.nextInt(size);
                        r3 = r.nextInt(size);
                        if (r1 == r2) {
                            while (r2 == r1 | r2 == r3) {
                                r2 = r.nextInt(size);
                            }
                        } else if (r1 == r3) {
                            while (r3 == r1 | r3 == r2) {
                                r3 = r.nextInt(size);
                            }
                        } else if (r2 == r3) {
                            while (r3 == r1 | r3 == r2) {
                                r3 = r.nextInt(size);
                            }
                        }
                        s = chosenList.get(r1).split(",");
                        t1.setText(s[1]);
                        t4.setText(s[3]);
                        s = chosenList.get(r2).split(",");
                        t2.setText(s[1]);
                        t5.setText(s[3]);
                        s = chosenList.get(r3).split(",");
                        t3.setText(s[1]);
                        t6.setText(s[3]);
                    case 2:
                        s = chosenList.get(0).split(",");
                        t1.setText(s[1]);
                        t4.setText(s[3]);
                        s = chosenList.get(1).split(",");
                        t2.setText(s[1]);
                        t5.setText(s[3]);
                        t3.setText("앞에서 골라보세요");
                        t6.setText("앞에서 골라보세요");
                    case 3:
                        s = chosenList.get(0).split(",");
                        t1.setText(s[1]);
                        t4.setText(s[3]);
                        t2.setText("앞에서 골라보세요");
                        t5.setText("앞에서 골라보세요");
                        t3.setText("앞에서 골라보세요");
                        t6.setText("앞에서 골라보세요");
                }
            }
        } catch(IOException e){
            e.printStackTrace();
        }//추천 부분

        data.clear();
        data.add("1. 가게 목록 조회");
        data.add("2. 쿠폰 조회");
        data.add("3. 커뮤니티");
        data.add("4. 쿠폰 적립");
        adapter=new ArrayAdapter<>(getApplicationContext(),R.layout.view_item,data);
        mListView.setAdapter(adapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position==0){
                    intent=new Intent(getApplicationContext(),List.class);
                }else if(position==1){
   //                 intent=new Intent(getApplicationContext(),Coupon.class);
                }else if(position==2){
                    intent=new Intent(getApplicationContext(),Community.class);
                }else if(position==3){}
                startActivity(intent);
            }
        });
    }

    public int recommendation(ArrayList<String> list){
        String cate=null;
        final int r4=r.nextInt(6);
        switch (r4){
            case 0: cate="한식";
            case 1: cate="분식";
            case 2: cate="중식";
            case 3: cate="일식";
            case 4: cate="양식";
            case 5: cate="기타";
            case 6: cate="카페";
        }
        int j=0;
        this.chosenList=new ArrayList<>();
        for(int i=0;i<list.size();i++){
            s=list.get(i).split(",");
            if(s[2].equals(cate)){
                chosenList.add(list.get(i));
                j++;
            }
        }
        int ca=0;
        size=j-1;
        if(size>=3){
            r1=r.nextInt(size);
            r2=r.nextInt(size);
            r3=r.nextInt(size);
            if(r1==r2){
                while(r2==r1|r2==r3){
                    r2=r.nextInt(size);
                }
            } else if (r1 == r3) {
                while(r3==r1|r3==r2){
                    r3=r.nextInt(size);
                }
            }else if(r2==r3){
                while(r3==r1|r3==r2){
                    r3=r.nextInt(size);
                }
            }
            ca=1;
        }else if(size==2){
            r1=r.nextInt(size);
            r2=r.nextInt(size);
            if(r1==r2){
                while(r2==r1){
                    r2=r.nextInt(size);
                }
            }
            ca=2;
        }else if(size==1){
            r1=r.nextInt(size);
            ca=3;
        }else if(size==0){
            ca=4;
        }
        size=0;
        return ca;
    }
}
