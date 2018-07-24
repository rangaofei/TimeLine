package io.github.rangaofei.sakatimeline.indexdecoration;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class IndexDecoration extends RecyclerView.ItemDecoration {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private int indexPadding;


    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        View view = parent.getLayoutManager().getChildAt(0);
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
    }


}
