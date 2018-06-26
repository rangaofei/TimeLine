package io.github.rangaofei.timeline;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.BaseAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import io.github.rangaofei.javatimeline.annotations.TimeLine;
import io.github.rangaofei.sakatimeline.TimeLineConfig;
import io.github.rangaofei.sakatimeline.TimeLineType;
import io.github.rangaofei.sakatimeline.TimeLineView;
import io.github.rangaofei.sakatimeline.adapter.ItemClickListener;
import io.github.rangaofei.sakatimeline.divider.TimeLineDivider;
import io.github.rangaofei.sakatimeline.adapter.AbstractTimeLineAdapter;
import io.github.rangaofei.timeline.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private List<String> data;

    private AbstractTimeLineAdapter adapter;
    private List<BaseModel> baseModels = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        initRecyclerView();
    }

    private void initRecyclerView() {
        baseModels.add(new BaseModel("2017年10月1日星期五", "今天我做了一个伟大的决定，我要去吃一顿肯德基", R.drawable.ic_order));
        baseModels.add(new BaseModel("2017年11月2日", "我又做了一个伟大的决定\n我在家呆着", R.drawable.ic_launcher_background));
        baseModels.add(new BaseModel("2017年2月22日", "我彻底歇菜", R.drawable.ic_launcher_background));
        adapter = new BaseModelAdapter(baseModels);
        adapter.setItemClickListener(new ItemClickListener() {
            @Override
            public void onKeyViewClick(int position) {
                Toast.makeText(MainActivity.this, "key=" + position, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onValueViewClick(int position) {
                Toast.makeText(MainActivity.this, "value=" + position, Toast.LENGTH_SHORT).show();
            }
        });

        binding.tlv.setTimeLineConfig(adapter, TimeLineType.LEFT_STEP_PROGRESS);

    }
}
