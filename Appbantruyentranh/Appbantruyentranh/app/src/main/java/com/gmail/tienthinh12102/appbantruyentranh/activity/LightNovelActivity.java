package com.gmail.tienthinh12102.appbantruyentranh.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.gmail.tienthinh12102.appbantruyentranh.R;
import com.gmail.tienthinh12102.appbantruyentranh.adapter.LaptopAdapter;
import com.gmail.tienthinh12102.appbantruyentranh.adapter.MangaAdapter;
import com.gmail.tienthinh12102.appbantruyentranh.model.Sanpham;
import com.gmail.tienthinh12102.appbantruyentranh.ultil.CheckConnection;
import com.gmail.tienthinh12102.appbantruyentranh.ultil.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class LightNovelActivity extends AppCompatActivity {
    Toolbar toolbarlaptop;
    ListView lvlaptop;
    LaptopAdapter laptopAdapter;
    LightNovelActivity lightNovelActivity;
    ArrayList<Sanpham> manglaptop;
    int idlaptop = 0;
    int page = 1;
    View footerview;
    boolean isLoading = false;
    boolean limitadata = false;
    mHandler mHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_light_novel);
        Anhxa(); // khoi tao va gan thuoc tinh
        if (CheckConnection.haveNetworkConnection(getApplicationContext())){
            GetIdloaisp();
            ActionToolBar();
            GetData(page);
            LoadMoreData();
        }
        else{
            CheckConnection.ShowToast_Short(getApplicationContext(),"B???n h??y ki???m tra l???i k???t n???i");
            finish();
        }

    }

    private void LoadMoreData() {
        lvlaptop.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(),ChiTietSanPham.class);
                intent.putExtra("thongtinsanpham",manglaptop.get(i));
                startActivity(intent);
            }
        });

        lvlaptop.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {

            }

            @Override
            public void onScroll(AbsListView absListView, int FirstItem, int VisibleItem, int TotalItem) {
                if (FirstItem + VisibleItem == TotalItem && TotalItem !=0 && isLoading==false && limitadata == false){
                    isLoading = true;
                    LightNovelActivity.ThreadData threadData = new LightNovelActivity.ThreadData();
                    threadData.start();
                }

            }
        });
    }

    private void GetData(int Page) {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        String duongdan = Server.Duongdanmanga+String.valueOf(Page);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, duongdan, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                int id = 0;
                String Tenlaptop = "";
                int Gialaptop = 0;
                String Hinhanhlaptop = "";
                String Motalaptop = "";
                int Idlaptop = 0;
                if(response !=null && response.length()!= 2 ){
                    lvlaptop.removeFooterView(footerview);
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i = 0 ; i<jsonArray.length();i++){
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            Tenlaptop = jsonObject.getString("tensp");
                            Gialaptop = jsonObject.getInt("giasp");
                            Hinhanhlaptop = jsonObject.getString("hinhanhsp");
                            Motalaptop = jsonObject.getString("motasp");
                            Idlaptop = jsonObject.getInt("idsanpham");
                            manglaptop.add(new Sanpham(id,Tenlaptop,Gialaptop,Hinhanhlaptop,Motalaptop,Idlaptop));
                            laptopAdapter.notifyDataSetChanged();
                        }

                    }catch (JSONException e){
                        e.printStackTrace();
                    }


                }else{
                    limitadata = true;
                    lvlaptop.removeFooterView(footerview);
                    CheckConnection.ShowToast_Short(getApplicationContext(),"???? h???t d??? li???u");
                }

            }
        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> param = new HashMap<String,String>();
                param.put("idsanpham",String.valueOf(idlaptop));
                return param;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void ActionToolBar() {
        setSupportActionBar(toolbarlaptop);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarlaptop.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void GetIdloaisp() {
        idlaptop = getIntent().getIntExtra("idloaisanpham",-1);
        Log.d("giatriloaisanpham",idlaptop+"");
    }

    private void Anhxa() {
        toolbarlaptop = (Toolbar) findViewById(R.id.toolbarlightnovel);
        lvlaptop = (ListView) findViewById(R.id.listviewlightnovel);
        manglaptop = new ArrayList<>();
        manglaptop.add(0,new Sanpham(0,"The Empty Box",566,"https://kbimages1-a.akamaihd.net/c81ed91e-10ba-44b3-a25c-4f70801a4716/353/569/90/False/the-empty-box-and-zeroth-maria-vol-4-light-novel.jpg", "Read The Empty Box and Zeroth Maria, Vol. 4 (light novel)by Eiji Mikage available from Rakuten Kobo. What are you truly wishing for?, Kazuki ...",1));
        manglaptop.add(1,new Sanpham(1,"Realist Kingdom",124,"https://images-na.ssl-images-amazon.com/images/I/5189ineNmdL._SX354_BO1,204,203,200_.jpg", "Noboru Kannatuki is the illustrator behind the light novel series Goblin Slayer. Kumo Kagyu is the author behind the light novel series Goblin Slayer.",1));
        manglaptop.add(2,new Sanpham(2,"Goblin Slayer",12,"https://images-eu.ssl-images-amazon.com/images/I/51IBEGkF35L.jpg", "World???s Strongest, the hit multimedia franchise that???s soon to be an anime! , Before ..",1));
        manglaptop.add(3,new Sanpham(3,"Reprise of the Spear",14,"https://media.archonia.com/images/samples/27/12/322712_s0.jpg", "Summoned to another world to serve as the Spear Hero, Motoyasu is a pitiful young man who eventually finds himself only able to love filolials. But ..",1));
        manglaptop.add(4,new Sanpham(4,"Konosuba: God's",2,"https://kbimages1-a.akamaihd.net/03c54a90-6ffb-4e66-b3b2-80ec8a95fd96/1200/1200/False/konosuba-god-s-blessing-on-this-wonderful-world-vol-5-light-novel.jpg", "L??s ???Konosuba: God's Blessing on This Wonderful World!, Vol. 5 (light novel) Crimson Magic Clan, Let's & Go!!??? av Natsume Akatsuki p?? Rakuten Kobo. ...",2));
        manglaptop.add(5,new Sanpham(5,"Mushoku Tensei",3,"https://sakatamary.files.wordpress.com/2017/06/neobk-2004469.jpg?w=825", "Rakuten Kobo. He does not let anyone roll the dice., An urgent council meeting ...",2));
        manglaptop.add(6,new Sanpham(6,"Boian Sam",8,"https://i.pinimg.com/originals/5b/fe/8a/5bfe8ab9ceb68621e7cefc563d5c47af.jpg", "Trong nh???ng n??m g???n ????y light novel tr??? n??n r???t th??ng d???ng ??? Nh???t B???n, v?? tr??? th??nh m???t l???a ch???n th??ng d???ng cho vi???c chuy???n th??? ",2));
        manglaptop.add(7,new Sanpham(7,"Skeleton Knight",1,"https://i.pinimg.com/originals/59/c4/cc/59c4cc3f77a54971297b1ea52bda7ab8.jpg", "Kannatuki is the illustrator behind the light novel series Goblin Slayer. Kumo Kagyu is the author behind the light novel series Goblin Slayer.Light novel  raito noberu l?? m???t d??ng ti???u thuy???t c?? ngu???n g???c Nh???t B???n.c???n th???m tra Raito noberu l?? m???t h???a ch??? Anh ng??? (wasei-eigo), t???c l?? m???t t??? m?????n Nh???t B???n c?? g???c t??? ti???ng Anh light novel. Light novel th?????ng ???????c g???i t???t l?? ranobe  ranobe hay rainobe rainobe. M???i light novel th?????ng d??i kh??ng qu?? 4-5 v???n t??? (lo???i light novel ng???n h??n c?? ????? d??i t????ng ??????ng v???i ti???u thuy???t ng???n theo chu???n c???a Hoa K???), th?????ng ???????c xu???t b???n d?????i d???ng v??n kh??? b???n (bunkobon), v?? th?????ng ???????c minh h???a.[3] N???i dung truy???n th?????ng ???????c ????ng nhi???u k??? tr??n c??c t???p san v??n th?? tr?????c khi xu???t b???n d?????i d???ng t???p ti???u thuy???t ho??n ch???nh.C??c c??ng ty xu???t b???n th?????ng xuy??n s??n l??ng c??c t??i n??ng m???i v???i c??c cu???c thi th?????ng ni??n - nhi???u ng?????i ??o???t gi???i trong c??c cu???c thi ???????c nh???n ti???n th?????ng v?? ???????c c??c c??ng ty ???? xu???t b???n c??c t??c ph???m c???a m??nh. Gi???i th?????ng ti???u thuy???t Dengeki l?? gi???i th?????ng l???n nh???t, v???i h??n 2 ngh??n th?? sinh tranh gi???i n??y h??ng n??m. T???t c??? ???????c d??n nh??n \"light novel\" v?? ???????c xu???t b???n trong c??c t???p s??ch gi???y b??a m???m gi?? r???. V??o n??m 2007, theo m???t trang m???ng do ch??nh quy???n Nh???t B???n t??i tr??? th?? ?????c t??nh gi?? tr??? l???i nhu???n c???a th??? tr?????ng light novel l?? 20 t??? y??n Nh???t (166.7 tri???u ????la M???) v?? kho???ng 30 tri???u ???n b???n ???????c ph??t h??nh h??ng n??m.[2] Kadokawa Group Holdings - t??? ch???c s??? h???u c??c th????ng hi???u nh?? Kadokawa Sneaker Books v?? Dengeki Books - chi???m l??nh 70-80 ph???n tr??m th??? ph???n light novel.\n" +
                "\n" +
                "?????c gi??? ???????c nh???m v??o l?? c??c h???c sinh trung h???c c?? s??? hay trung h???c ph??? th??ng.",2));

        laptopAdapter = new LaptopAdapter(getApplicationContext(),manglaptop);
        lvlaptop.setAdapter(laptopAdapter);
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        footerview = inflater.inflate(R.layout.progressbar,null);
        mHandler = new mHandler();
    }
    public class mHandler extends Handler{
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what){
                case 0:
                    lvlaptop.addFooterView(footerview);
                    break;
                case 1:
                    GetData(++page);
                    isLoading = false;
                    break;
            }
            super.handleMessage(msg);
        }
    }
    public class ThreadData extends Thread{
        @Override
        public void run() {
            mHandler.sendEmptyMessage(0);
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Message message = mHandler.obtainMessage(1);
            mHandler.sendMessage(message);
            super.run();
        }
    }
}
