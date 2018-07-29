package io.github.rangaofei.timeline;

import android.databinding.DataBindingUtil;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import io.github.rangaofei.libannotations.TimeLineDividerAdapter;
import io.github.rangaofei.sakatimeline.TimeLineView;
import io.github.rangaofei.sakatimeline.adapter.AbstractTimeLineAdapter;
import io.github.rangaofei.sakatimeline.adapter.ItemClickListener;
import io.github.rangaofei.sakatimeline.divider.TimeLineType;
import io.github.rangaofei.timeline.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;


    private AbstractTimeLineAdapter secondAdapter;

    private List<StepViewModel> secondStepViewModels = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.ll_container, TaobaoFragment.newInstance("1", "2"))
                        .commit();
            }
        });
        binding.btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.ll_container, MukeFragment.newInstance("1", "2"))
                        .commit();
            }
        });
        initRecyclerView();

    }


    private void initRecyclerView() {


        secondStepViewModels.add(new StepViewModel("许愿日", true, "04/25"));
        secondStepViewModels.add(new StepViewModel("签约日", false, "04/25"));
        secondStepViewModels.add(new StepViewModel("签约日", false, "04/25"));
        secondStepViewModels.add(new StepViewModel("签约日", false, "04/25"));
        secondStepViewModels.add(new StepViewModel("签约日", false, "04/25"));
        secondStepViewModels.add(new StepViewModel("签约日", false, "04/25"));
        secondStepViewModels.add(new StepViewModel("签约日", false, "04/25"));
        secondStepViewModels.add(new StepViewModel("签约日", false, "04/25"));
        secondStepViewModels.add(new StepViewModel("签约日", false, "04/25"));
        secondStepViewModels.add(new StepViewModel("签约日", false, "04/25"));


    }
}
