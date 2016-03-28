package com.izp.manzifinal.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.izp.manzifinal.R;

/**
 * Created by izp on 2016/2/22.
 */
public class ViewHolderStats extends RecyclerView.ViewHolder {
    public View view;//default权限只能保证在一个包里的程序可以访问，现在我们建立了几个分开的包，所以要用public
    public RelativeLayout contentItemStats;
    public ImageView circleImageStats;
    public TextView count,showCount,progressItemStats,showProgressStats;

    public ViewHolderStats(View itemView) {
        super(itemView);
        view = itemView;
        /*当这个recyclerView的item被点击之后的事件*/
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        contentItemStats = (RelativeLayout) view.findViewById(R.id.contentItemStats);
        circleImageStats = (ImageView) view.findViewById(R.id.circleImageStats);
        count = (TextView) view.findViewById(R.id.count);
        showCount = (TextView) view.findViewById(R.id.showCount);
        progressItemStats = (TextView) view.findViewById(R.id.progressItemStats);
        showProgressStats = (TextView) view.findViewById(R.id.showProgressStats);
    }
}
