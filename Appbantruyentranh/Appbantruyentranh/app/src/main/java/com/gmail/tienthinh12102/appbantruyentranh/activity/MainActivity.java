package com.gmail.tienthinh12102.appbantruyentranh.activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ViewFlipper;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.gmail.tienthinh12102.appbantruyentranh.R;
import com.gmail.tienthinh12102.appbantruyentranh.adapter.LoaispAdapter;
import com.gmail.tienthinh12102.appbantruyentranh.adapter.SanphamAdapter;
import com.gmail.tienthinh12102.appbantruyentranh.model.Loaisp;
import com.gmail.tienthinh12102.appbantruyentranh.model.Sanpham;
import com.gmail.tienthinh12102.appbantruyentranh.ultil.CheckConnection;
import com.gmail.tienthinh12102.appbantruyentranh.ultil.Server;
import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Request;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;
    ViewFlipper viewFlipper;
    RecyclerView recyclerViewmanhinhchinh;
    NavigationView navigationView;
    ListView listViewmanhinhchinh;
    DrawerLayout drawerLayout;
    ArrayList<Loaisp> mangloaisp;
    LoaispAdapter loaispAdapter;
    int id = 0;
    String tenloaisp = "";
    int hinhanhloaisp = 0;
    ArrayList<Sanpham> mangsanpham;
    SanphamAdapter sanphamAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Anhxa(); //anh xa cac thuoc tinh vao`
        if(CheckConnection.haveNetworkConnection(getApplicationContext())){
            ActionBar ();
            ActionViewFlipper();
           // GetDuLieuLoaisp();
            //GetDuLieuSPMoinhat();
            CatchOnItemListView();
        }else{
            CheckConnection.ShowToast_Short(getApplicationContext(),"Bạn hãy kiểm tra lại kết nối");
        }

    }

    private void CatchOnItemListView() {
        listViewmanhinhchinh.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i){
                    case 0:
                        if (CheckConnection.haveNetworkConnection(getApplicationContext())){
                            Intent intent = new Intent(MainActivity.this,MainActivity.class);
                            startActivity(intent);
                        }else{
                            CheckConnection.ShowToast_Short(getApplicationContext(),"Bạn hãy kiểm tra lại kết nối");

                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 1:
                        if (CheckConnection.haveNetworkConnection(getApplicationContext())){
                            Intent intent = new Intent(MainActivity.this,MangaActivity.class);
                            intent.putExtra("idloaisanpham",mangloaisp.get(i).getId());
                            startActivity(intent);
                        }else{
                            CheckConnection.ShowToast_Short(getApplicationContext(),"Bạn hãy kiểm tra lại kết nối");

                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 2:
                        if (CheckConnection.haveNetworkConnection(getApplicationContext())){
                            Intent intent = new Intent(MainActivity.this,LightNovelActivity.class);
                            intent.putExtra("idloaisanpham",mangloaisp.get(i).getId());
                            startActivity(intent);
                        }else{
                            CheckConnection.ShowToast_Short(getApplicationContext(),"Bạn hãy kiểm tra lại kết nối");

                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 3:
                        if (CheckConnection.haveNetworkConnection(getApplicationContext())){
                            Intent intent = new Intent(MainActivity.this,ComicActivity.class);
                            startActivity(intent);
                        }else{
                            CheckConnection.ShowToast_Short(getApplicationContext(),"Bạn hãy kiểm tra lại kết nối");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 4:
                        if (CheckConnection.haveNetworkConnection(getApplicationContext())){
                            Intent intent = new Intent(MainActivity.this,ThongTinActivity.class);
                            startActivity(intent);
                        }else{
                            CheckConnection.ShowToast_Short(getApplicationContext(),"Bạn hãy kiểm tra lại kết nối");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                }
            }
        });
    } //Du lieu menu

//    private void GetDuLieuSPMoinhat() {
//        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
//        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Server.DuongdanSPMoinhat, new Response.Listener<JSONArray>() {
//            @Override
//            public void onResponse(JSONArray response) {
//                if (response != null) {
//                    int ID = 0;
//                    String Tensanpham = "";
//                    Integer Giasanpham = 0;
//                    String Hinhanhsanpham = "";
//                    String Motasanpham = "";
//                    int IDsanpham = 0;
//                    for (int i = 0; i < response.length(); i++) {
//                        try {
//                            JSONObject jsonObject = response.getJSONObject(i);
//                            ID = jsonObject.getInt("id");
//                            Tensanpham = jsonObject.getString("tensp");
//                            Giasanpham = jsonObject.getInt("giasp");
//                            Hinhanhsanpham = jsonObject.getString("hinhanhsp");
//                            Motasanpham = jsonObject.getString("motasp");
//                            IDsanpham = jsonObject.getInt("idsanpham");
//                            mangsanpham.add(new Sanpham(ID,Tensanpham,Giasanpham,Hinhanhsanpham,Motasanpham,IDsanpham));
//                            sanphamAdapter.notifyDataSetChanged();
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//
//                }
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//
//            }
//        });
//        requestQueue.add(jsonArrayRequest);
//    }

//    private void GetDuLieuLoaisp() {
//        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
//        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Server.DuongdanLoaisp, new Response.Listener<JSONArray>() {
//
//            @Override
//            public void onResponse(JSONArray response) {
//                if (response != null){
//                    for (int i = 0 ; i <response.length() ;i++){
//                        try {
//                            JSONObject jsonObject = response.getJSONObject(i);
//                            id = jsonObject.getInt("id");
//                            tenloaisp = jsonObject.getString("tenloaisp");
//                            hinhanhloaisp = jsonObject.getInt("hinhanhloaisp");
//                            mangloaisp.add(new Loaisp(id,tenloaisp,hinhanhloaisp));
//                            loaispAdapter.notifyDataSetChanged();
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
////                    mangloaisp.add(3,new Loaisp(0,"Liên hệ","https://cdn4.iconfinder.com/data/icons/social-media-2097/94/phone-512.png"));
////                    mangloaisp.add(4,new Loaisp(0,"Thông tin","https://img.icons8.com/bubbles/2x/admin-settings-male.png"));
//                }
//            }
//        },new Response.ErrorListener(){
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                CheckConnection.ShowToast_Short(getApplicationContext(),error.toString());
//            }
//        });
//        requestQueue.add(jsonArrayRequest);
//    }

    private void  ActionViewFlipper(){
        ArrayList<String> mangquangcao = new ArrayList<>();
        mangquangcao.add("https://genknews.genkcdn.vn/2019/2/21/photo-1-1550721717519259702175.jpg");
        mangquangcao.add("https://img5.goodfon.com/wallpaper/nbig/a/ed/demon-slayer-kimetsu-no-yaiba-klinok-rassekaiushchii-demon-1.jpg");
        mangquangcao.add("https://i1.wp.com/ramenswag.com/wp-content/uploads/2019/10/sword-art-online-alicization-war-of-underworld-wallpaper-4k.jpg?resize=1060%2C596&ssl=1");
        mangquangcao.add("https://wallpapercave.com/wp/rWDMSj3.jpg");
        mangquangcao.add("https://c4.wallpaperflare.com/wallpaper/672/578/501/manga-naruto-shippuuden-uzumaki-naruto-uchiha-sasuke-wallpaper-preview.jpg");
        for (int i=0;i< mangquangcao.size();i++){
            ImageView imageView = new ImageView(getApplicationContext());
            Picasso.with(getApplicationContext()).load(mangquangcao.get(i)).into(imageView);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            viewFlipper.addView(imageView);
        }
        viewFlipper.setFlipInterval(5000);
        viewFlipper.setAutoStart(true);
        Animation animation_slide_in = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_in_right);
        Animation animation_slide_out = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_out_right);
        viewFlipper.setInAnimation(animation_slide_in);
        viewFlipper.setOutAnimation(animation_slide_out);
    }
    private void ActionBar(){
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(android.R.drawable.ic_menu_sort_by_size);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }
    private void Anhxa(){
        toolbar = (Toolbar) findViewById(R.id.toolbarmanhinhchinh);
        viewFlipper = (ViewFlipper) findViewById(R.id.viewlipper);
        recyclerViewmanhinhchinh = findViewById(R.id.recyclerview);
        navigationView = findViewById(R.id.navigationview);
        listViewmanhinhchinh = findViewById(R.id.listviewmanhinhchinh);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerlayout);
        mangloaisp = new ArrayList<>();
        mangloaisp.add(0,new Loaisp(0,"Trang chinh",R.drawable.home));
        mangloaisp.add(1,new Loaisp(1,"Manga",R.drawable.manga));
        mangloaisp.add(2,new Loaisp(2,"Light Novel",R.drawable.lightnovel));
        mangloaisp.add(3,new Loaisp(3,"Comic",R.drawable.comic));
        mangloaisp.add(4,new Loaisp(4,"Thông tin",R.drawable.thongtin));
        
        loaispAdapter = new LoaispAdapter(mangloaisp,getApplicationContext());
        listViewmanhinhchinh.setAdapter(loaispAdapter);
        mangsanpham = new ArrayList<>();
        mangsanpham.add(0,new Sanpham(0,"One Piece",916,"https://genknews.genkcdn.vn/2018/12/11/anh-1-15445215232132139250648.jpg", "One Piece là bộ manga dành cho lứa tuổi thiếu niên của tác giả Eiichiro Oda, được đăng định kì trên tạp chí Weekly Shōnen Jump, ra mắt lần đầu trên ấn bản số 34 vào ngày 19 tháng 7 năm 1997. One Piece cũng được chuyển thể sang một vài loại hình truyền thông khác. Một OVA được hãng Production I.G sản xuất vào năm 1998. Tiếp đó, phiên bản anime dài tập do hãng Toei Animation thực hiện, bắt đầu lên sóng truyền hình Nhật Bản vào năm 1999. Toei cũng sản xuất 11 phim hoạt hình, một OVA, và 5 chương trình truyền hình đặc biệt liên quan. Một vài công ty đã phát triển nhiều sản phẩm khác dựa vào truyện như thẻ game, video game. Bộ manga phiên bản tiếng Anh được cấp phép cho hãng Viz Media phát hành ở thị trường Bắc Mỹ, hãng Gollancz Manga ở Vương quốc Anh, Madman Entertainment ở Australia và New Zealand. Ở Bắc Mỹ, bộ anime được cấp phép bản địa hóa và phân phối bằng tiếng Anh bởi hãng Funimation Entertainment.\n" +
                "\n" +
                "Trong năm 2008, One Piece trở thành bộ manga có số lượng phát hành kỷ lục. Năm 2010, Shueisha thông báo họ đã bán hơn 260 triệu tập One Piece, tập 61 lập kỷ lục số lượng in sách tại Nhật Bản với 3,8 triệu bản in (kỷ lục cũ thuộc về tập 60 với 3.4 triệu bản in). Theo hãng Oricon của Nhật Bản, tập 60 là sản phẩm sách đầu tiên bán được hơn 2 triệu bản trong tuần đầu phát hành.[1] One Piece hiện được đánh giá là bộ manga bán chạy nhất với số lượng người đọc rất cao, hơn 280 triệu tập bán ra tại Nhật Bản trong năm 2012. Nó nhận được nhiều sự phê bình hay khen ngợi, trước hết từ phong cách vẽ, đặc điểm nhân vật, sự hài hước và cốt truyện. Trong bảng xếp hạng 100 tập truyện tranh bán chạy nhất đầu năm 2015, căn cứ vào số lượng các tập sách được bán ra của các nhà xuất bản, 2 tập 76 và 77 của One Piece lần lượt được xếp vào vị trí quán quân và á quân bảng xếp hạng, bỏ xa thành tích của các tập truyện nổi tiếng khác đang xếp ở các vị trí tiếp theo.",1));
        mangsanpham.add(1,new Sanpham(1,"Naruto Shippuden",600,"https://www.reference-gaming.com/assets/media/product/29917/naruto-shippuden-tapis-de-souris-shippuden-group-pc-599b0cdb82871.jpg?format=product-cover-large&k=1503333595", "Naruto trong một ấn bản Akamaru Jump vào tháng 8 năm 1997.[1] Sự khác biệt ở chỗ Naruto là chủ thể của Hồ Ly Chín Đuôi được chính cha của mình phong ấn vào người, và câu chuyện được đặt trong bối cảnh hiện đại hơn.[2] Phiên bản ban đầu của Naruto này đã có khả năng biến thành một phụ nữ quyến rũ – nhưng khi cậu ta làm vậy, một cái đuôi cáo xuất hiện. Kishimoto sau đó mới sáng tác lại câu chuyện thành hiện trạng, và được phát hành lần đầu bởi Shueisha vào năm 1999 trong ấn bản thứ 43 của tạp chí Tuần san thiếu niên Jump tại Nhật. Chương cuối của Naruto được phát hành vào ngày 10 tháng 11 năm 2014 [3]. Bộ manga đã được phát hành dưới dạng tankōbon (sách) bao gồm 72 tập. Đến tập 36, bộ manga đã bán được hơn 71 triệu bản tại Nhật.[4] Tập truyện được cấp giấy phép cho việc phát hành bản dịch sang tiếng Anh bởi Viz Media. Được đăng nhiều kỳ trên tạp chí Shonen Jump, Naruto đã trở thành loạt manga bán chạy nhất của công ty.[5] Cho đến 2 tháng 4 năm 2008, 28 tập đầu tiên của bộ truyện đã có mặt trong tiếng Anh.Naruto shippuuden là một tác phẩm của Kishimoto Masashi. Nó cũng nói về Naruto, nhưng là 2 năm sau, sau khi cậu bé cùng sư phụ Jiraiya của mình đi tập ",1));
        mangsanpham.add(2,new Sanpham(2,"Kimetsu on Yaiba",12,"https://i1.wp.com/otakugo.net/wp-content/uploads/2019/09/6-anime-tuong-tu-kimetsu-no-yaiba.jpg?fit=640%2C422&ssl=1", "Kimetsu no Yaiba – Tanjirou là con cả của gia đình vừa mất cha. Một ngày nọ, Tanjirou đến thăm thị trấn khác để bán than, khi đêm về cậu ở nghỉ tại nhà người...",1));
        mangsanpham.add(3,new Sanpham(3,"Conan",555,"https://images4.alphacoders.com/231/thumb-1920-231266.jpg", "Thám tử lừng danh Conan hay Detective Conan, là một bộ truyện tranh trinh thám Nhật Bản của tác giả Aoyama Gōshō.Mở đầu câu truyện, cậu học ...",1));
        mangsanpham.add(4,new Sanpham(4,"Sword Art Online",13,"https://i.pinimg.com/originals/ea/1b/cf/ea1bcf65e6fe0f8edbc776c10fbd91bd.jpg", "SAO",2));
        mangsanpham.add(5,new Sanpham(5,"Mushoku Tensei",25,"https://www.otakutale.com/wp-content/uploads/2019/10/Mushoku-Tensei-TV-Anime-Slated-for-2020-Visual-Promotional-Video-Staff-Revealed.jpg", "Mushoku Tensei",2));
        mangsanpham.add(6,new Sanpham(6,"Konosuba",1,"https://t.a4vn.com/2018/11/2/anime-konosuba-gods-blessing-on-this-wonderful-world-cong-bo-tua-8c7.jpg", "Konosuba",2));
        mangsanpham.add(7,new Sanpham(7,"Dragon Ball Super",756,"https://www.imagenesanime.net/800x600/imagen-de-dragon-ball-z.jpg", "Balls",2));
        mangsanpham.add(8,new Sanpham(8,"Justice vs Sucide",145,"https://vietcomic.net/files/files/QhUXerN.jpg", "Justice League hay còn được biết đến với cái tên Liên minh công lý, là tập hợp những siêu anh hùng mang trong mình sức mạnh phi thường để giải cứu thế giới khỏi những thế lực đen tối trong vũ trụ DC",1));
        mangsanpham.add(9,new Sanpham(9,"Vũ trụ Liên Minh",1,"https://universe-comics.leagueoflegends.com/comics/vn_vn/lux/issue-1/desktop/lux-01-00-full.jpg?v=bf425abe4bf089d0560c58dc1ccc5c51", "Liên Minh Huyền Thoại hiện là một trong những tựa game online hay nhất 2016 và được yêu thích",1));
        mangsanpham.add(10,new Sanpham(10,"Drowned Earth",12,"https://vietcomic.net/files/files/1(6).jpg", "Drowned Earth là một trong những công ty lớn nhất và phổ biến nhất hoạt động trong thị trường sách truyện tranh của Mỹ và phương tiện truyền thông có liên quan.",1));
        mangsanpham.add(11,new Sanpham(11,"Birds Of Prey",10,"https://vietcomic.net//files/files/Batgirl%20and%20the%20Birds%20of%20Prey%20(2016-)%20002-000.jpg", "DC Comics, và đối thủ cạnh tranh chính lâu năm là Marvel Comics (sở hữu bởi tập đoàn Walt Disney) cùng nhau chiếm hơn 80% thị trường truyện tranh Mỹ vào năm 2008.",1));
        mangsanpham.add(12,new Sanpham(12,"Dark Nights: Metal",11,"https://images-na.ssl-images-amazon.com/images/I/61xtgNiGNKL._SX323_BO1,204,203,200_.jpg", "Originally located in New York City at 432 Fourth Avenue, DC has been successively headquartered at 480 and later 575 Lexington Avenue; 909 Third Avenue; 75 Rockefeller Plaza; 666 Fifth",2));
        mangsanpham.add(13,new Sanpham(13,"X-Men ",255,"https://http2.mlstatic.com/batman-robin-eternal-6-televisa-D_NQ_NP_695297-MLM27529336948_062018-F.jpg", "Leger and Reuths the supernatural-crimefighter adventure DC has been successively ",2));
        mangsanpham.add(14,new Sanpham(14,"X-Men (1991-2001)",120,"https://images-na.ssl-images-amazon.com/images/S/cmx-images-prod/Item/5001/ICO000467._SX360_QL80_TTD_.jpg", "The first American comic book with solely original material rather than comic strip reprints, it was a tabloid-sized X-Men là loạt phim siêu anh hùng của Mỹ dựa trên nhóm siêu anh hùng cùng tên trong các ấn phẩm truyện tranh do Stan Lee và Jack Kirby sáng tác và được Marvel Comics phát hành. Hãng 20th Century Fox giành được quyền chuyển thể nhân vật lên màn ảnh vào năm 1994. Sau một vài bản nháp, Bryan Singer được chọn làm đạo diễn cho hai phần đầu tiên là X-Men (2000) và X-Men: United (2003), và Brett Ratner trở thành đạo diễn của phần phim thứ ba, X-Men: The Last Stand (2006). X-Men: Thế hệ thứ nhất (2011) do Matthew Vaughn đạo diễn, và X-Men: Ngày cũ của tương lai (2014), lần lượt là hai phần phim thứ tư và thứ năm của loạt phim. Một vài phần phim ngoại truyện được ra mắt xen kẽ với các phim chính, trong đó có ba phim về Wolverine gồm Người Sói (2009), Người Sói Wolverine (2013) và Logan: Người Sói (2017). Deadpool (2016) và Deadpool 2 (2018) là hai bộ phim được thực hiện dựa trên Deadpool, một nhân vật được giới thiệu trong Người Sói.\n" +
                "\n" +
                "X-Men, X-Men: United, X-Men: Thế hệ thứ nhất, X-Men: Ngày cũ của tương lai, Người Sói Wolverine, Deadpool Deadpool 2 và Logan: Người Sói đều nhận được ý kiến phê bình tích cực, với Logan: Người Sói là phim được đánh giá cao nhất. Dị nhân và X2 được chú ý nhờ tính hiện thực và màu sắc có phần u ám, và ý nghĩa phim nói về phân biệt đối xử và sự không khoan nhượng, còn Deadpool nổi bật nhờ sự chuyển thể chính xác từ bản gốc và nội dung bị hạn chế. X-Men: The Last Stand và Người Sói nhận được ý kiến phê bình ít khả quan hơn.",2));
        mangsanpham.add(15,new Sanpham(15,"Batman Rebirth",12,"https://vignette.wikia.nocookie.net/batman/images/7/7d/Batman_and_Robin_Eternal_Vol.1_21.jpg/revision/latest?cb=20160227175654&path-prefix=es", "New York City at 432 Fourth Avenue, DC has been successively headquartered at 480 and later 575 Lexington Avenue; 909 Third Avenue; 75 Rockefeller Plaza; 666 Fifth",2));

        sanphamAdapter = new SanphamAdapter(getApplicationContext(),mangsanpham);
        recyclerViewmanhinhchinh.setHasFixedSize(true);
        recyclerViewmanhinhchinh.setLayoutManager(new GridLayoutManager(getApplicationContext(),2));
        recyclerViewmanhinhchinh.setAdapter(sanphamAdapter);
    }
}
