package com.gmail.tienthinh12102.appbantruyentranh.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
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
import com.gmail.tienthinh12102.appbantruyentranh.adapter.ComicAdapter;
import com.gmail.tienthinh12102.appbantruyentranh.adapter.LaptopAdapter;
import com.gmail.tienthinh12102.appbantruyentranh.model.Sanpham;
import com.gmail.tienthinh12102.appbantruyentranh.ultil.CheckConnection;
import com.gmail.tienthinh12102.appbantruyentranh.ultil.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ComicActivity extends AppCompatActivity {
    Toolbar toolbarcomic;
    ListView lvcomic;
    ComicAdapter comicAdapter;
    ArrayList<Sanpham> mangcomic;
    int idcomic = 0;
    int page = 1;
    View footerview;
    boolean isLoading = false;
    boolean limitadata = false;
    mHandler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comic);
        Anhxa();
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


    private void ActionToolBar() {
        setSupportActionBar(toolbarcomic);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarcomic.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    private void GetIdloaisp() {
        idcomic = getIntent().getIntExtra("idloaisanpham",-1);
        Log.d("giatriloaisanpham",idcomic+"");
    }
    public class mHandler extends Handler {
        @Override
        public void handleMessage( Message msg) {
            switch (msg.what){
                case 0:
                    lvcomic.addFooterView(footerview);
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
    private void LoadMoreData() {
        lvcomic.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(),ChiTietSanPham.class);
                intent.putExtra("thongtinsanpham",mangcomic.get(i));
                startActivity(intent);
            }
        });

        lvcomic.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {

            }

            @Override
            public void onScroll(AbsListView absListView, int FirstItem, int VisibleItem, int TotalItem) {
                if (FirstItem + VisibleItem == TotalItem && TotalItem !=0 && isLoading==false && limitadata == false){
                    isLoading = true;
                    ComicActivity.ThreadData threadData = new ComicActivity.ThreadData();
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
                String Tenmanga = "";
                int Giamanga = 0;
                String Hinhanhmanga = "";
                String Mota = "";
                int Idmanga = 0;
                if(response !=null && response.length()!= 2 ){
                    lvcomic.removeFooterView(footerview);
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i = 0 ; i<jsonArray.length();i++){
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            Tenmanga = jsonObject.getString("tensp");
                            Giamanga = jsonObject.getInt("giasp");
                            Hinhanhmanga = jsonObject.getString("hinhanhsp");
                            Mota = jsonObject.getString("motasp");
                            Idmanga = jsonObject.getInt("idsanpham");
                            mangcomic.add(new Sanpham(id,Tenmanga,Giamanga,Hinhanhmanga,Mota,Idmanga));
                            comicAdapter.notifyDataSetChanged();
                        }

                    }catch (JSONException e){
                        e.printStackTrace();
                    }


                }else{
                    limitadata = true;
                    lvcomic.removeFooterView(footerview);
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
                param.put("idsanpham",String.valueOf(idcomic));
                return param;
            }
        };
        requestQueue.add(stringRequest);
    }
    private void Anhxa() {
        toolbarcomic = (Toolbar) findViewById(R.id.toolbarcomic);
        lvcomic = (ListView) findViewById(R.id.listviewcomic);
        mangcomic = new ArrayList<>();
        mangcomic.add(0,new Sanpham(0,"Justice vs Sucide",12,"https://vietcomic.net/files/files/QhUXerN.jpg", "Justice League hay c??n ???????c bi???t ?????n v???i c??i t??n Li??n minh c??ng l??, l?? t???p h???p nh???ng si??u anh h??ng mang trong m??nh s???c m???nh phi th?????ng ????? gi???i c???u th??? gi???i kh???i nh???ng th??? l???c ??en t???i trong v?? tr??? DC",1));
        mangcomic.add(1,new Sanpham(1,"V?? tr??? Li??n Minh Huy???n Tho???i",3,"https://universe-comics.leagueoflegends.com/comics/vn_vn/lux/issue-1/desktop/lux-01-00-full.jpg?v=bf425abe4bf089d0560c58dc1ccc5c51", "Li??n Minh Huy???n Tho???i hi???n l?? m???t trong nh???ng t???a game online hay nh???t 2016 v?? ???????c y??u th??ch",1));
        mangcomic.add(2,new Sanpham(2,"Drowned Earth",12,"https://vietcomic.net/files/files/1(6).jpg", "Drowned Earth l?? m???t trong nh???ng c??ng ty l???n nh???t v?? ph??? bi???n nh???t ho???t ?????ng trong th??? tr?????ng s??ch truy???n tranh c???a M??? v?? ph????ng ti???n truy???n th??ng c?? li??n quan.",1));
        mangcomic.add(3,new Sanpham(3,"Batgirl And The Birds Of Prey",24,"https://vietcomic.net//files/files/Batgirl%20and%20the%20Birds%20of%20Prey%20(2016-)%20002-000.jpg", "DC Comics, v?? ?????i th??? c???nh tranh ch??nh l??u n??m l?? Marvel Comics (s??? h???u b???i t???p ??o??n Walt Disney) c??ng nhau chi???m h??n 80% th??? tr?????ng truy???n tranh M??? v??o n??m 2008.",1));
        mangcomic.add(4,new Sanpham(4,"Dark Nights: Metal",25,"https://images-na.ssl-images-amazon.com/images/I/61xtgNiGNKL._SX323_BO1,204,203,200_.jpg", "Sau khi s??? ki???n Dark Nights: Metal k???t th??c, Batman Who Laughs ???? tr??? th??nh 1 trong nh???ng nh??n v???t ph???n di???n v?? c??ng ti???m n??ng, v???i vai tr?? quan tr???ng ???nh h?????ng t???i t????ng lai c???a v?? tr??? DC sau n??y. ?????c bi???t l?? trong giai ??o???n s??? ki???n Justice/Doom War ??ang di???n ra, s??? tr??? l???i c???a h???n ??ang ng??y c??ng ???????c mong ch??? h??n bao gi??? h???t.\n" +
                "\n" +
                "Nh?? c??c b???n ???? bi???t, sau khi ???????c ban s???c m???nh b???i Perpetua v?? h???p nh???t v???i Martian Manhunter, Lex Luthor ???? tr??? th??nh \"con qu??i v???t t???i th?????ng\" v???i t??n g???i Apex Lex. H???n s??? h???u quy???n n??ng kh???ng l??? ????? t??ng c?????ng s???c m???nh cho gi???i si??u t???i ph???m trong v?? tr??? DC, gi??p ch??ng c?? ???????c l???i th??? trong cu???c chi???n ch???ng l???i nh???ng si??u anh h??ng.Originally located in New York City at 432 Fourth Avenue, DC has been successively headquartered at 480 and later 575 Lexington Avenue; 909 Third Avenue; 75 Rockefeller Plaza; 666 Fifth",2));
        mangcomic.add(5,new Sanpham(5,"X-Men by Chris Claremont",16,"https://http2.mlstatic.com/batman-robin-eternal-6-televisa-D_NQ_NP_695297-MLM27529336948_062018-F.jpg", "Leger and Reuths the supernatural-crimefighter adventure DC has been successively ",2));
        mangcomic.add(6,new Sanpham(6,"X-Men (1991-2001) #1",22,"https://images-na.ssl-images-amazon.com/images/S/cmx-images-prod/Item/5001/ICO000467._SX360_QL80_TTD_.jpg", "The first American comic book with solely original material rather than comic strip reprints, it was a tabloid-sized",2));
        mangcomic.add(7,new Sanpham(7,"Batman The Rebirth Deluxe",1,"https://vignette.wikia.nocookie.net/batman/images/7/7d/Batman_and_Robin_Eternal_Vol.1_21.jpg/revision/latest?cb=20160227175654&path-prefix=es", "New York City at 432 Fourth Avenue, DC has been successively headquartered at 480 and later 575 Lexington Avenue; 909 Third Avenue; 75 Rockefeller Plaza; 666 Fifth",2));

        comicAdapter = new ComicAdapter(getApplicationContext(),mangcomic);
        lvcomic.setAdapter(comicAdapter);
    }
}
