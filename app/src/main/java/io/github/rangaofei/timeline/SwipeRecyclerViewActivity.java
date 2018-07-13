package io.github.rangaofei.timeline;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import java.util.ArrayList;
import java.util.List;

import io.github.rangaofei.sakatimeline.divider.SakaItemTouchHelper;
import io.github.rangaofei.sakatimeline.divider.SlideTouchHelperCallBack;

public class SwipeRecyclerViewActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<String> data = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipe_recycler_view);
        recyclerView = findViewById(R.id.swipe_rv);
        for (int i = 0; i < 20; i++) {
            data.add(String.valueOf(i));
        }
        recyclerView.setAdapter(new SwipeAdapter(data));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        SakaItemTouchHelper itemTouchHelper = new SakaItemTouchHelper(new SlideTouchHelperCallBack());
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }
}
