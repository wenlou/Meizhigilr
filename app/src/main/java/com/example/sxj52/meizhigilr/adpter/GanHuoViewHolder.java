package com.example.sxj52.meizhigilr.adpter;

import android.graphics.drawable.Drawable;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.sxj52.meizhigilr.R;
import com.example.sxj52.meizhigilr.model.GanHuo;
import com.example.sxj52.meizhigilr.util.TimeUtil;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;

/**
 * Created by sxj52 on 2016/5/27.
 */
public class GanHuoViewHolder extends BaseViewHolder<GanHuo.Result> {
    private TextView title;
    private TextView type;
    private TextView who;
    private TextView time;

    public GanHuoViewHolder(ViewGroup parent) {
       super(parent, R.layout.ganhuo_item);
        title = $(R.id.title);
        type = $(R.id.type);
        who = $(R.id.who);
        time = $(R.id.time);
    }

    @Override
    public void setData(GanHuo.Result data) {
        super.setData(data);
        //标题
        title.setText(data.getDesc());
        //干货类型
        type.setText(data.getType());
        //根据干货类型动态替换干货图标
        if (data.getType().equals("Android")){
            setDrawableLeft(R.drawable.ic_android_black_24dp);
        }else if (data.getType().equals("iOS")){
            setDrawableLeft(R.drawable.ic_whatshot_black_24dp);
        }else {
            setDrawableLeft(R.drawable.ic_play_circle_filled_black_24dp);
        }
        //干货贡献者
        who.setText(data.getWho());
        //干货提交时间
        time.setText(TimeUtil.getFormatTime(data.getPublishedAt()));
    }

    private void setDrawableLeft(int imaegId) {
        Drawable drawable = getContext().getResources().getDrawable(imaegId);
        drawable.setBounds(0,0,drawable.getMinimumWidth(),drawable.getMinimumHeight());
        type.setCompoundDrawables(drawable,null,null,null);

    }
}
