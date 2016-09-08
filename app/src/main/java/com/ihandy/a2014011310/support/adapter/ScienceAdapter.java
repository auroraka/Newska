
package com.ihandy.a2014011310.support.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.ihandy.a2014011310.R;
import com.ihandy.a2014011310.database.cache.ICache;
import com.ihandy.a2014011310.database.table.ScienceTable;
import com.ihandy.a2014011310.model.science.ArticleBean;
import com.ihandy.a2014011310.support.HttpUtil;
import com.ihandy.a2014011310.support.Settings;
import com.ihandy.a2014011310.ui.science.ScienceDetailsActivity;
import com.ihandy.a2014011310.ui.support.WebViewUrlActivity;
import com.ihandy.a2014011310.support.adapter.ScienceAdapter.ViewHolder;



public class ScienceAdapter extends BaseListAdapter<ArticleBean,ViewHolder>{


    public ScienceAdapter(Context context, ICache<ArticleBean> cache) {
        super(context, cache);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_science, parent, false);
        final ViewHolder vh = new ViewHolder(itemView);
        return vh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final ArticleBean articleBean = getItem(position);
        holder.title.setText(articleBean.getTitle());

        //holder.image.setImageURI(null);
        if(Settings.noPicMode && HttpUtil.isWIFI == false){
            holder.image.setImageURI(null);
        }else {
            holder.image.setImageURI(Uri.parse(articleBean.getImage_info().getUrl()));
        }

        //holder.comment.setText(" "+articleBean.getReplies_count());
        holder.comment.setText(" 233");
        holder.parentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ScienceDetailsActivity.class);
                Bundle bundle = new Bundle();

                if(isCollection){
                    articleBean.setIs_collected(1);
                }
                bundle.putSerializable(mContext.getString(R.string.id_science),articleBean);
                intent.putExtras(bundle);
                mContext.startActivity(intent);
            }
        });
    }


    class ViewHolder extends RecyclerView.ViewHolder{
        private View parentView;
        private TextView title;
        private TextView comment;
        private SimpleDraweeView image;
        public ViewHolder(View itemView) {
            super(itemView);
            parentView = itemView;
            title = (TextView) parentView.findViewById(R.id.title);
            image = (SimpleDraweeView) parentView.findViewById(R.id.image);
            comment = (TextView) parentView.findViewById(R.id.comment);
        }
    }
}
