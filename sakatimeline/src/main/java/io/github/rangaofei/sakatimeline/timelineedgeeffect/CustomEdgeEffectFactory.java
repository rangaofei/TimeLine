package io.github.rangaofei.sakatimeline.timelineedgeeffect;

import android.os.HandlerThread;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.widget.EdgeEffect;

public class CustomEdgeEffectFactory  extends RecyclerView.EdgeEffectFactory{
//    HandlerThread
    @NonNull
    @Override
    protected EdgeEffect createEdgeEffect(RecyclerView view, int direction) {
        return super.createEdgeEffect(view, direction);
    }
}
