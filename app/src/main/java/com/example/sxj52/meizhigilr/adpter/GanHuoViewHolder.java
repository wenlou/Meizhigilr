package com.example.sxj52.meizhigilr.adpter;

import android.graphics.drawable.Drawable;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.sxj52.meizhigilr.R;
import com.example.sxj52.meizhigilr.model.GanHuo;
import com.example.sxj52.meizhigilr.util.TimeUtil;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;

/**
 * Created by sxj52 on 2016/7/25.
 */
public class GanHuoViewHolder extends BaseViewHolder<GanHuo.Result> {
    private TextView title;
    private TextView type;
    private TextView who;
    private TextView time;
    public GanHuoViewHolder(ViewGroup parent) {
        super(parent, R.layout.ganhuo_item);
        title=$(R.id.title);
        type=$(R.id.type);
        who= $(R.id.who);
        time=$(R.id.time);
    }

    @Override
    public void setData(GanHuo.Result data) {
        super.setData(data);
        title.setText(data.getDesc());
        type.setText(data.getType());
        who.setText(data.getWho());
        time.setText(TimeUtil.getFormatTime(data.getPublishedAt()));
        //动态改变类型边上的图片
        if(data.getType().equals("Android")){
            setDrawableLeft(R.drawable.ic_android_black_24dp);
        }else if (data.getType().equals("iOS")){
            setDrawableLeft(R.drawable.ic_whatshot_black_24dp);
        }else {
            setDrawableLeft(R.drawable.ic_play_circle_filled_black_24dp);
        }
    }

    private void setDrawableLeft(int imageid) {
        Drawable drawable=getContext().getResources().getDrawable(imageid);
        drawable.setBounds(0,0,drawable.getMinimumWidth(),drawable.getMinimumHeight());
        type.setCompoundDrawables(drawable,null,null,null);

    }
}
