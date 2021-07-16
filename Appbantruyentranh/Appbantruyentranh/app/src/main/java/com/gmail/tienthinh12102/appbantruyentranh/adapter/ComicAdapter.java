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

public class ComicAdapter extends BaseAdapter {
    Context context;

    public ComicAdapter(Context context, ArrayList<Sanpham> arrayComic) {
        this.context = context;
        this.arrayComic = arrayComic;
    }

    ArrayList<Sanpham> arrayComic;


    @Override
    public int getCount() {
        return arrayComic.size();
    }

    @Override
    public Object getItem(int i) {
        return arrayComic.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public class ViewHolder{
        public TextView txttencomic,txtgiacomic,txtmotacomic;
        public ImageView imgcomic;

    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ComicAdapter.ViewHolder viewHolder = null;
        if (view == null){
            viewHolder = new ComicAdapter.ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.dong_comic,null);
            viewHolder.txttencomic = (TextView) view.findViewById(R.id.textviewtencomic);
            viewHolder.txtgiacomic = (TextView) view.findViewById(R.id.textviewgiacomic);
            viewHolder.txtmotacomic = (TextView) view.findViewById(R.id.textviewmotacomic);
            viewHolder.imgcomic = (ImageView) view.findViewById(R.id.imageviewcomic);
            view.setTag(viewHolder);
        }else{
            viewHolder = (ComicAdapter.ViewHolder) view.getTag();
        }
        Sanpham sanpham =(Sanpham) getItem(i);
        viewHolder.txttencomic.setText(sanpham.getTensanpham());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHolder.txtgiacomic.setText("Tập : " +(sanpham.getGiasanpham())+ "/999");
        viewHolder.txtmotacomic.setMaxLines(2);
        viewHolder.txtmotacomic.setEllipsize(TextUtils.TruncateAt.END);
        viewHolder.txtmotacomic.setText(sanpham.getMotasanpham());
        Picasso.with(context).load(sanpham.getHinhanhsanpham())
                .placeholder(R.drawable.load)
                .error(R.drawable.error)
                .into(viewHolder.imgcomic);
        return view;
    }
}
