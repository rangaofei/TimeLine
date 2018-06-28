package io.github.rangaofei.sakatimeline.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import io.github.rangaofei.javatimeline.annotations.TimeLine;
import io.github.rangaofei.sakatimeline.R;
import io.github.rangaofei.sakatimeline.divider.TimeLineType;

import static io.github.rangaofei.sakatimeline.divider.TimeLineType.StepViewType.*;
import static io.github.rangaofei.sakatimeline.divider.TimeLineType.TimeLineViewType.*;

public abstract class AbstractTimeLineAdapter<T> extends RecyclerView.Adapter<BaseViewHolder> {

    public static final int TYPE_KEY = 1;
    public static final int TYPE_VALUE = 2;
    public static final int TYPE_KEY_ONLY = 3;
    public static final int TYPE_VALUE_ONLY = 4;

    private ItemClickListener itemClickListener;

    private List<T> datas;

    private TimeLineType timeLineType;
    private int holderCount = 0;

    public AbstractTimeLineAdapter(List<T> list) {
        this.datas = list;
    }

    public ItemClickListener getItemClickListener() {
        return itemClickListener;
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        switch (viewType) {
            case TYPE_KEY:
                View keyView = inflater.inflate(getKeyLayoutId(), parent, false);
                return new KeyViewHolder(keyView);
            case TYPE_VALUE:
                View valueView = inflater.inflate(getValueLayoutId(), parent, false);
                return new ValueViewHolder(valueView);
            default:
                View nullView = inflater.inflate(R.layout.null_view, parent, false);
                return new BaseViewHolder(nullView) {
                    @Override
                    public String toString() {
                        return super.toString();
                    }
                };
        }

    }

    @Override
    public void onBindViewHolder(@NonNull final BaseViewHolder holder, int position) {
        T data = null;
        if (timeLineType instanceof StepViewType) {
            switch ((StepViewType) timeLineType) {
                case TOP_STEP_PROGRESS:
                case BOTTOM_STEP_PROGRESS:
                case LEFT_STEP_PROGRESS:
                case RIGHT_STEP_PROGRESS:
                    data = datas.get(position);
                    break;
                default:
                    throw new RuntimeException("nu");
            }
        }
        if (timeLineType instanceof TimeLineViewType) {
            switch ((TimeLineViewType) timeLineType) {
                case ONLY_LEFT:
                case ONLY_RIGHT:
                    data = datas.get(position / holderCount);
                    break;
                case LEFT_TO_RIGHT:
                case RIGHT_TO_LEFT:
                    data = datas.get(position / 2);
                    break;
                case LEFT_KEY:
                case LEFT_VALUE:
                    data = datas.get(position / 2);
                    break;
            }
        }


        if (data == null) {
            return;
        }

        if (holder instanceof KeyViewHolder) {
            bindKeyItem(holder, data);
            ((KeyViewHolder) holder).itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (itemClickListener != null) {
                        itemClickListener.onKeyViewClick(holder.getAdapterPosition());
                    }
                }
            });
        } else if (holder instanceof ValueViewHolder) {
            bindValueItem(holder, data);
            ((ValueViewHolder) holder).itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (itemClickListener != null) {
                        itemClickListener.onValueViewClick(holder.getAdapterPosition());
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if (timeLineType instanceof StepViewType) {
            switch ((StepViewType) timeLineType) {
                case TOP_STEP_PROGRESS:
                case BOTTOM_STEP_PROGRESS:
                case LEFT_STEP_PROGRESS:
                case RIGHT_STEP_PROGRESS:
                    return datas.size();
                default:
                    throw new RuntimeException("nu");
            }
        }

        if (timeLineType instanceof TimeLineViewType) {
            switch ((TimeLineViewType) timeLineType) {
                case ONLY_RIGHT:
                case ONLY_LEFT: {
                    if (getValueLayoutId() == -1 && getKeyLayoutId() == -1) {
                        return 0;
                    }
                    if (getValueLayoutId() == -1 || getKeyLayoutId() == -1) {
                        holderCount = 1;
                        return datas.size();
                    }
                    holderCount = 2;
                    return datas.size() * 2;
                }
                case RIGHT_TO_LEFT:
                case LEFT_TO_RIGHT:
                    return datas.size() * 2;
                case LEFT_KEY:
                case LEFT_VALUE:
                    return datas.size() * 2;
                default:
                    return datas.size() * 2;
            }
        } else {
            return datas.size();
        }

    }

    public TimeLineType getTimeLineType() {
        return timeLineType;
    }

    public void setTimeLineType(TimeLineType timeLineType) {
        this.timeLineType = timeLineType;

    }

    @Override
    public int getItemViewType(int position) {
        if (getKeyLayoutId() == -1 && getValueLayoutId() != -1) {
            return ItemTypeStrategy.getItemTypeOnlyValue(position, timeLineType);
        }
        if (getValueLayoutId() == -1 && getKeyLayoutId() != -1) {
            return ItemTypeStrategy.getItemTypeOnlyKey(position, timeLineType);
        }
        if (timeLineType instanceof StepViewType) {
            return ItemTypeStrategy.getItemTypeOnlyValue(position, timeLineType);
        }
        return ItemTypeStrategy.getItemType(position, timeLineType);
    }


    public static class KeyViewHolder extends BaseViewHolder {


        public KeyViewHolder(View itemView) {
            super(itemView);
        }
    }

    public static class ValueViewHolder extends BaseViewHolder {


        public ValueViewHolder(View itemView) {
            super(itemView);
        }
    }

    public abstract int getKeyLayoutId();

    public abstract int getValueLayoutId();

    public abstract void bindKeyItem(BaseViewHolder holder, T t);

    public abstract void bindValueItem(BaseViewHolder holder, T t);

}
