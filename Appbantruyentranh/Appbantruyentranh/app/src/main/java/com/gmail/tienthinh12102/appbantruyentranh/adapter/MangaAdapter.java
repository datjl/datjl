package com.gmail.tienthinh12102.appbantruyentranh.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.gmail.tienthinh12102.appbantruyentranh.R;
import com.gmail.tienthinh12102.appbantruyentranh.model.Sanpham;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class MangaAdapter extends BaseAdapter {
    Context context;

    public MangaAdapter(Context context, ArrayList<Sanpham> arrayManga) {
        this.context = context;
        this.arrayManga = arrayManga;
    }

    ArrayList<Sanpham> arrayManga;


    @Override
    public int getCount() {
        return arrayManga.size();
    }

    @Override
    public Object getItem(int i) {
        return arrayManga.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }
    public class ViewHolder{
        public TextView txttenmanga,txtgiamanga,txtmotamanga;
        public ImageView imgmanga;

    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if (view == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.dong_manga,null);
            viewHolder.txttenmanga = (TextView) view.findViewById(R.id.textviewmanga);
            viewHolder.txtgiamanga = (TextView) view.findViewById(R.id.textviewgiamanga);
            viewHolder.txtmotamanga = (TextView) view.findViewById(R.id.textviewmotamanga);
            viewHolder.imgmanga = (ImageView) view.findViewById(R.id.imageviewmanga);
            view.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) view.getTag();
        }
        Sanpham sanpham =(Sanpham) getItem(i);
        viewHolder.txttenmanga.setText(sanpham.getTensanpham());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHolder.txtgiamanga.setText("Tập : " +(sanpham.getGiasanpham())+ "/999");
        viewHolder.txtmotamanga.setMaxLines(2);
        viewHolder.txtmotamanga.setEllipsize(TextUtils.TruncateAt.END);
        viewHolder.txtmotamanga.setText(sanpham.getMotasanpham());
        Picasso.with(context).load(sanpham.getHinhanhsanpham())
                .placeholder(R.drawable.load)
                .error(R.drawable.error)
                .into(viewHolder.imgmanga);
        return view;
    }
}
