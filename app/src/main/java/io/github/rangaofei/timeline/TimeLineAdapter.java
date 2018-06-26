package io.github.rangaofei.timeline;

import android.widget.TextView;

import java.util.List;

import io.github.rangaofei.sakatimeline.adapter.AbstractTimeLineAdapter;
import io.github.rangaofei.sakatimeline.adapter.BaseViewHolder;

public class TimeLineAdapter extends AbstractTimeLineAdapter<BaseModel> {
    public TimeLineAdapter(List<BaseModel> list) {
        super(list);
    }

    @Override
    public int getKeyLayoutId() {
        return R.layout.item_key;
    }

    @Override
    public int getValueLayoutId() {
        return R.layout.item_value;
    }

    @Override
    public void bindKeyItem(BaseViewHolder holder, BaseModel baseModel) {
        ((TextView) (((KeyViewHolder) holder).itemView.findViewById(R.id.key))).setText(baseModel.getKey());
    }

    @Override
    public void bindValueItem(BaseViewHolder holder, BaseModel baseModel) {
        ((TextView) (((ValueViewHolder) holder).itemView.findViewById(R.id.value))).setText(baseModel.getValue());
    }


}
