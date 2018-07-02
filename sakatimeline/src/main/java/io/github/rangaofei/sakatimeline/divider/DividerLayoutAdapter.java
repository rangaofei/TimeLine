package io.github.rangaofei.sakatimeline.divider;

import android.graphics.drawable.Drawable;

import java.util.List;

public abstract class DividerLayoutAdapter {
    private List<Drawable> list;

    public DividerLayoutAdapter(List<Drawable> list) {
        this.list = list;
    }

    public int getItemSize() {
        if (list == null) {
            throw new RuntimeException("list is null");
        }
        return list.size();
    }

    public Drawable getDrawable(int position) {
        if (this.list == null) {
            throw new RuntimeException("list is null");
        }
        if (position >= list.size()) {
            return list.get(position % list.size());
        }
        return list.get(position);
    }
}
