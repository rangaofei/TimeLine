package io.github.rangaofei.timeline;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import io.github.rangaofei.sakatimeline.TimeLineView;
import io.github.rangaofei.sakatimeline.adapter.AbstractTimeLineAdapter;
import io.github.rangaofei.sakatimeline.config.TimeLineConfig;
import io.github.rangaofei.sakatimeline.divider.TimeLineType;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MukeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MukeFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private TimeLineView timeLineView;
    private List<MukeBean> list = new ArrayList<>();
    private AbstractTimeLineAdapter adapter;

    public MukeFragment() {

    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MukeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MukeFragment newInstance(String param1, String param2) {
        MukeFragment fragment = new MukeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_muke, container, false);
        timeLineView = view.findViewById(R.id.muke_tlv);
        initView();
        return view;
    }

    private void initView() {
        list.add(new MukeBean("5月1日", "我开始饿"));
        list.add(new MukeBean("5月2日", "我吃饭了"));
        list.add(new MukeBean("5月3日", "我吃饱了"));
        list.add(new MukeBean("5月4日", "我"));
        list.add(new MukeBean("5月5日", "我长胖了"));
        list.add(new MukeBean("5月6日", "我要减肥了"));
        list.add(new MukeBean("5月7日", "我只吃蔬菜"));
        list.add(new MukeBean("5月8日", "我好想吃肉"));
        list.add(new MukeBean("5月9日", "我吃肉了"));
        adapter = new MukeBeanAdapter(list);

        timeLineView.setTimeLineConfig(adapter, TimeLineType.StepViewType.LEFT_STEP_PROGRESS, 0);
    }

}
