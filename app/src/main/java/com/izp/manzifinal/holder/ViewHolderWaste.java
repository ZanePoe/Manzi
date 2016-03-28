package com.izp.manzifinal.holder;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.view.ViewStub;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import android.widget.TextView;

import com.izp.manzifinal.R;

/**
 * Created by izp on 2015/9/12.
 */
public class ViewHolderWaste extends RecyclerView.ViewHolder {
    public View view;//default权限只能保证在一个包里的程序可以访问，现在我们建立了几个分开的包，所以要用public
    public RelativeLayout relativeLayout;
    public ImageView circleImageWaste;
    public TextView titleItemWaste,createTimeItemWaste,weightItemWaste,showWeightItemWaste;
    public ViewStub wasteRemarkView;
    public SwitchCompat wasteSwitch;

    public ViewHolderWaste(View itemView) {
        super(itemView);
        view= itemView;
        /*当这个recyclerView的item被点击之后的事件,这个电视事件给了继承ViewHolder的*/
        relativeLayout = (RelativeLayout) itemView.findViewById(R.id.contentItemWaste);
        circleImageWaste = (ImageView) itemView.findViewById(R.id.circleImageWaste);
        titleItemWaste = (TextView) itemView.findViewById(R.id.titleItemWaste);
        createTimeItemWaste = (TextView) itemView.findViewById(R.id.createTimeItemWaste);
        weightItemWaste = (TextView) itemView.findViewById(R.id.weightItemWaste);
        showWeightItemWaste = (TextView) itemView.findViewById(R.id.showWeightItemWaste);
        wasteRemarkView = (ViewStub) itemView.findViewById(R.id.wasteRemarkView);
        wasteSwitch = (SwitchCompat) itemView.findViewById(R.id.wasteSwitch);
    }

}
