package kreedx.org.provider.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.List;

import kreedx.org.provider.R;
import kreedx.org.provider.activity.EditActivity;
import kreedx.org.provider.model.Info;

/**
 * @author kreedx
 * @since 2017年12月1日 10:58:03
 * Created by Administrator on 2017/12/1.
 */

public class InfoAdapter extends RecyclerView.Adapter<InfoAdapter.ViewHolder> {


    private List<Info> infoList;
    private Context context;

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    private OnClickListener onClickListener;

    public InfoAdapter(List<Info> infoList, Context context) {
        this.infoList = infoList;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_info,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Info info = infoList.get(position);
        holder.info_title.setText(info.getTitle());
        if(info.getPic().endsWith(".jpeg")||info.getPic().endsWith(".jpg")||info.getPic().endsWith(".png")){
            if(info.getPic().startsWith("/")){
                Picasso.with(context).load(new File(info.getPic())).into(holder.info_view);
            }else{
                Picasso.with(context).load(info.getPic()).into(holder.info_view);
            }
        }else {
            Picasso.with(context).load(R.mipmap.ic_launcher_round).into(holder.info_view);
        }
    }

    @Override
    public int getItemCount() {
        return infoList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnLongClickListener{
        TextView info_title;
        ImageView info_view;

        public ViewHolder(View itemView) {
            super(itemView);
            this.info_title = itemView.findViewById(R.id.info_title);
            this.info_view = itemView.findViewById(R.id.iinfo_image);
            info_view.setOnClickListener(this);
            info_view.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Snackbar.make(view,"touch"+getAdapterPosition(),Snackbar.LENGTH_SHORT).show();
            onClickListener.OnClick(infoList.get(getAdapterPosition()));

        }

        @Override
        public boolean onLongClick(View view) {
            onClickListener.OnLongClick(infoList.get(getAdapterPosition()));
            return true;
        }
    }
    public interface OnClickListener {
        void OnClick(Info info);
        void OnLongClick(Info info);
    }
}
