package com.gmail.tienthinh12102.appbantruyentranh.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import androidx.appcompat.widget.Toolbar;


import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.gmail.tienthinh12102.appbantruyentranh.R;
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

public class MangaActivity extends AppCompatActivity {
    Toolbar toolbarmanga;
    ListView lvmanga;
    MangaAdapter mangaAdapter;
    ArrayList<Sanpham> mangmanga;
    int idmanga = 0;
    int page = 1;
    View footerview;
    boolean isLoading = false;
    boolean limitadata = false;
    mHandler mHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manga);
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
        lvmanga.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(),ChiTietSanPham.class);
                intent.putExtra("thongtinsanpham",mangmanga.get(i));
                startActivity(intent);
            }
        });

        lvmanga.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {

            }

            @Override
            public void onScroll(AbsListView absListView, int FirstItem, int VisibleItem, int TotalItem) {
                if (FirstItem + VisibleItem == TotalItem && TotalItem !=0 && isLoading==false && limitadata == false){
                    isLoading = true;
                    ThreadData threadData = new ThreadData();
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
                    lvmanga.removeFooterView(footerview);
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i = 0 ; i<jsonArray.length();i++){
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            Tenmanga = jsonObject.getString("tensp");
                            Giamanga = jsonObject.getInt("giasp");
                            Hinhanhmanga = jsonObject.getString("hinhanhsp");
                            Mota = jsonObject.getString("motasp");
                            Idmanga = jsonObject.getInt("idsanpham");
                            mangmanga.add(new Sanpham(id,Tenmanga,Giamanga,Hinhanhmanga,Mota,Idmanga));
                            mangaAdapter.notifyDataSetChanged();
                        }

                    }catch (JSONException e){
                        e.printStackTrace();
                    }


                }else{
                    limitadata = true;
                    lvmanga.removeFooterView(footerview);
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
                param.put("idsanpham",String.valueOf(idmanga));
                return param;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void ActionToolBar() {
        setSupportActionBar(toolbarmanga);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarmanga.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void GetIdloaisp() {
        idmanga = getIntent().getIntExtra("idloaisanpham",-1);
        Log.d("giatriloaisanpham",idmanga+"");
    }

    private void Anhxa() {
        toolbarmanga = (Toolbar) findViewById(R.id.toolbarmanga);
        lvmanga = (ListView) findViewById(R.id.listviewmanga);
        mangmanga = new ArrayList<>();
        mangmanga.add(0,new Sanpham(0,"One Piece",916,"https://genknews.genkcdn.vn/2018/12/11/anh-1-15445215232132139250648.jpg", "One Piece là bộ manga dành cho lứa tuổi thiếu niên của tác giả Eiichiro Oda, được đăng định kì trên tạp chí Weekly Shōnen Jump, ra mắt lần đầu trên ấn bản số 34 vào ngày 19 tháng 7 năm 1997. One Piece cũng được chuyển thể sang một vài loại hình truyền thông khác. Một OVA được hãng Production I.G sản xuất vào năm 1998. Tiếp đó, phiên bản anime dài tập do hãng Toei Animation thực hiện, bắt đầu lên sóng truyền hình Nhật Bản vào năm 1999. Toei cũng sản xuất 11 phim hoạt hình, một OVA, và 5 chương trình truyền hình đặc biệt liên quan. Một vài công ty đã phát triển nhiều sản phẩm khác dựa vào truyện như thẻ game, video game. Bộ manga phiên bản tiếng Anh được cấp phép cho hãng Viz Media phát hành ở thị trường Bắc Mỹ, hãng Gollancz Manga ở Vương quốc Anh, Madman Entertainment ở Australia và New Zealand. Ở Bắc Mỹ, bộ anime được cấp phép bản địa hóa và phân phối bằng tiếng Anh bởi hãng Funimation Entertainment.\n" +
                "\n" +
                "Trong năm 2008, One Piece trở thành bộ manga có số lượng phát hành kỷ lục. Năm 2010, Shueisha thông báo họ đã bán hơn 260 triệu tập One Piece, tập 61 lập kỷ lục số lượng in sách tại Nhật Bản với 3,8 triệu bản in (kỷ lục cũ thuộc về tập 60 với 3.4 triệu bản in). Theo hãng Oricon của Nhật Bản, tập 60 là sản phẩm sách đầu tiên bán được hơn 2 triệu bản trong tuần đầu phát hành.[1] One Piece hiện được đánh giá là bộ manga bán chạy nhất với số lượng người đọc rất cao, hơn 280 triệu tập bán ra tại Nhật Bản trong năm 2012. Nó nhận được nhiều sự phê bình hay khen ngợi, trước hết từ phong cách vẽ, đặc điểm nhân vật, sự hài hước và cốt truyện. Trong bảng xếp hạng 100 tập truyện tranh bán chạy nhất đầu năm 2015, căn cứ vào số lượng các tập sách được bán ra của các nhà xuất bản, 2 tập 76 và 77 của One Piece lần lượt được xếp vào vị trí quán quân và á quân bảng xếp hạng, bỏ xa thành tích của các tập truyện nổi tiếng khác đang xếp ở các vị trí tiếp theo.",1));
        mangmanga.add(1,new Sanpham(1,"Naruto Shippuden",600,"https://www.reference-gaming.com/assets/media/product/29917/naruto-shippuden-tapis-de-souris-shippuden-group-pc-599b0cdb82871.jpg?format=product-cover-large&k=1503333595", "Naruto trong một ấn bản Akamaru Jump vào tháng 8 năm 1997.[1] Sự khác biệt ở chỗ Naruto là chủ thể của Hồ Ly Chín Đuôi được chính cha của mình phong ấn vào người, và câu chuyện được đặt trong bối cảnh hiện đại hơn.[2] Phiên bản ban đầu của Naruto này đã có khả năng biến thành một phụ nữ quyến rũ – nhưng khi cậu ta làm vậy, một cái đuôi cáo xuất hiện. Kishimoto sau đó mới sáng tác lại câu chuyện thành hiện trạng, và được phát hành lần đầu bởi Shueisha vào năm 1999 trong ấn bản thứ 43 của tạp chí Tuần san thiếu niên Jump tại Nhật. Chương cuối của Naruto được phát hành vào ngày 10 tháng 11 năm 2014 [3]. Bộ manga đã được phát hành dưới dạng tankōbon (sách) bao gồm 72 tập. Đến tập 36, bộ manga đã bán được hơn 71 triệu bản tại Nhật.[4] Tập truyện được cấp giấy phép cho việc phát hành bản dịch sang tiếng Anh bởi Viz Media. Được đăng nhiều kỳ trên tạp chí Shonen Jump, Naruto đã trở thành loạt manga bán chạy nhất của công ty.[5] Cho đến 2 tháng 4 năm 2008, 28 tập đầu tiên của bộ truyện đã có mặt trong tiếng Anh.Naruto shippuuden là một tác phẩm của Kishimoto Masashi. Nó cũng nói về Naruto, nhưng là 2 năm sau, sau khi cậu bé cùng sư phụ Jiraiya của mình đi tập ",1));
        mangmanga.add(2,new Sanpham(2,"Kimetsu on Yaiba",12,"https://i1.wp.com/otakugo.net/wp-content/uploads/2019/09/6-anime-tuong-tu-kimetsu-no-yaiba.jpg?fit=640%2C422&ssl=1", "Kimetsu no Yaiba – Tanjirou là con cả của gia đình vừa mất cha. Một ngày nọ, Tanjirou đến thăm thị trấn khác để bán than, khi đêm về cậu ở nghỉ tại nhà người...",1));
        mangmanga.add(3,new Sanpham(3,"Conan",555,"https://images4.alphacoders.com/231/thumb-1920-231266.jpg", "Thám tử lừng danh Conan hay Detective Conan, là một bộ truyện tranh trinh thám Nhật Bản của tác giả Aoyama Gōshō.Mở đầu câu truyện, cậu học ...",1));
        mangmanga.add(4,new Sanpham(4,"Sword Art Online",12,"https://i.pinimg.com/originals/ea/1b/cf/ea1bcf65e6fe0f8edbc776c10fbd91bd.jpg", "Sword art online là một bộ light novel Nhật Bản được viết bởi Tử Linh và được minh họa bởi abec. Bộ truyện lấy bối cảnh tương lai gần và nhiều thế giới ...",2));
        mangmanga.add(5,new Sanpham(5,"Mushoku Tensei",1,"https://www.otakutale.com/wp-content/uploads/2019/10/Mushoku-Tensei-TV-Anime-Slated-for-2020-Visual-Promotional-Video-Staff-Revealed.jpg", "Mushoku Tensei: Isekai Ittara Honki Dasu (無職転生 〜異世界行ったら本気だす〜, nghĩa đen Chuyển sinh không nghề nghiệp: Tôi sẽ cố gắng hết sức nếu tôi ...",2));
        mangmanga.add(6,new Sanpham(6,"Konosuba",13,"https://t.a4vn.com/2018/11/2/anime-konosuba-gods-blessing-on-this-wonderful-world-cong-bo-tua-8c7.jpg", "Satou Kazuma là một cậu học sinh cao trung mê game, nghiện truyện, ghiền anime, và là một hikikomori (người sống tách biệt với xã ...",2));
        mangmanga.add(7,new Sanpham(7,"Dragon Ball Super",999,"https://www.imagenesanime.net/800x600/imagen-de-dragon-ball-z.jpg", "Dragon Ball Super: Bản năng vô cực - Ultra Instinct là kỹ thuật mà 1 người có thể đạt được khi phá vỡ giới hạn của mình.",2));

        mangaAdapter = new MangaAdapter(getApplicationContext(),mangmanga);
        lvmanga.setAdapter(mangaAdapter);
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        footerview = inflater.inflate(R.layout.progressbar,null);
        mHandler = new mHandler();
    }
    public class mHandler extends Handler{
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what){
                case 0:
                    lvmanga.addFooterView(footerview);
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
