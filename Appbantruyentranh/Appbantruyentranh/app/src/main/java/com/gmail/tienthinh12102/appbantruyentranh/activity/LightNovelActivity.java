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
            CheckConnection.ShowToast_Short(getApplicationContext(),"Bạn hãy kiểm tra lại kết nối");
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
                    CheckConnection.ShowToast_Short(getApplicationContext(),"Đã hết dữ liệu");
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
        manglaptop.add(2,new Sanpham(2,"Goblin Slayer",12,"https://images-eu.ssl-images-amazon.com/images/I/51IBEGkF35L.jpg", "World’s Strongest, the hit multimedia franchise that’s soon to be an anime! , Before ..",1));
        manglaptop.add(3,new Sanpham(3,"Reprise of the Spear",14,"https://media.archonia.com/images/samples/27/12/322712_s0.jpg", "Summoned to another world to serve as the Spear Hero, Motoyasu is a pitiful young man who eventually finds himself only able to love filolials. But ..",1));
        manglaptop.add(4,new Sanpham(4,"Konosuba: God's",2,"https://kbimages1-a.akamaihd.net/03c54a90-6ffb-4e66-b3b2-80ec8a95fd96/1200/1200/False/konosuba-god-s-blessing-on-this-wonderful-world-vol-5-light-novel.jpg", "Läs ”Konosuba: God's Blessing on This Wonderful World!, Vol. 5 (light novel) Crimson Magic Clan, Let's & Go!!” av Natsume Akatsuki på Rakuten Kobo. ...",2));
        manglaptop.add(5,new Sanpham(5,"Mushoku Tensei",3,"https://sakatamary.files.wordpress.com/2017/06/neobk-2004469.jpg?w=825", "Rakuten Kobo. He does not let anyone roll the dice., An urgent council meeting ...",2));
        manglaptop.add(6,new Sanpham(6,"Boian Sam",8,"https://i.pinimg.com/originals/5b/fe/8a/5bfe8ab9ceb68621e7cefc563d5c47af.jpg", "Trong những năm gần đây light novel trở nên rất thông dụng ở Nhật Bản, và trở thành một lựa chọn thông dụng cho việc chuyển thể ",2));
        manglaptop.add(7,new Sanpham(7,"Skeleton Knight",1,"https://i.pinimg.com/originals/59/c4/cc/59c4cc3f77a54971297b1ea52bda7ab8.jpg", "Kannatuki is the illustrator behind the light novel series Goblin Slayer. Kumo Kagyu is the author behind the light novel series Goblin Slayer.Light novel  raito noberu là một dòng tiểu thuyết có nguồn gốc Nhật Bản.cần thẩm tra Raito noberu là một họa chế Anh ngữ (wasei-eigo), tức là một từ mượn Nhật Bản có gốc từ tiếng Anh light novel. Light novel thường được gọi tắt là ranobe  ranobe hay rainobe rainobe. Mỗi light novel thường dài không quá 4-5 vạn từ (loại light novel ngắn hơn có độ dài tương đương với tiểu thuyết ngắn theo chuẩn của Hoa Kỳ), thường được xuất bản dưới dạng văn khố bản (bunkobon), và thường được minh họa.[3] Nội dung truyện thường được đăng nhiều kỳ trên các tập san văn thơ trước khi xuất bản dưới dạng tập tiểu thuyết hoàn chỉnh.Các công ty xuất bản thường xuyên săn lùng các tài năng mới với các cuộc thi thường niên - nhiều người đoạt giải trong các cuộc thi được nhận tiền thưởng và được các công ty đó xuất bản các tác phẩm của mình. Giải thưởng tiểu thuyết Dengeki là giải thưởng lớn nhất, với hơn 2 nghìn thí sinh tranh giải này hàng năm. Tất cả được dán nhãn \"light novel\" và được xuất bản trong các tập sách giấy bìa mềm giá rẻ. Vào năm 2007, theo một trang mạng do chính quyền Nhật Bản tài trợ thì ước tính giá trị lợi nhuận của thị trường light novel là 20 tỉ yên Nhật (166.7 triệu đôla Mỹ) và khoảng 30 triệu ấn bản được phát hành hàng năm.[2] Kadokawa Group Holdings - tổ chức sở hữu các thương hiệu như Kadokawa Sneaker Books và Dengeki Books - chiếm lĩnh 70-80 phần trăm thị phần light novel.\n" +
                "\n" +
                "Độc giả được nhắm vào là các học sinh trung học cơ sở hay trung học phổ thông.",2));

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
