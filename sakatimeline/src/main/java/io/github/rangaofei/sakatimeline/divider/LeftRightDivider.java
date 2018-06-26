package io.github.rangaofei.sakatimeline.divider;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import io.github.rangaofei.sakatimeline.TimeLineConfig;
import io.github.rangaofei.sakatimeline.adapter.AbstractTimeLineAdapter;

public class LeftRightDivider extends BaseDivider {
    public LeftRightDivider(Context context, TimeLineConfig timeLineConfig) {
        super(context, timeLineConfig);
    }

    @Override
    public void onChildDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {

    }

    @Override
    public void onChildDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {

    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int realLeftPadding = 0;
        int realRightPadding = 0;
        if (parent.getChildViewHolder(view) instanceof AbstractTimeLineAdapter.KeyViewHolder) {
            realRightPadding = (int) padding;
        } else if (parent.getChildViewHolder(view) instanceof AbstractTimeLineAdapter.ValueViewHolder) {
            realLeftPadding = (int) padding;
        }
        outRect.set(realLeftPadding, 10, realRightPadding, 10);
    }
}
