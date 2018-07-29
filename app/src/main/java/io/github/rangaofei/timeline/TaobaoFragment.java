package io.github.rangaofei.timeline;


import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import io.github.rangaofei.sakatimeline.TimeLineView;
import io.github.rangaofei.sakatimeline.adapter.AbstractTimeLineAdapter;
import io.github.rangaofei.sakatimeline.divider.TimeLineType;


public class TaobaoFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private AbstractTimeLineAdapter firstAdapter;

    private TimeLineView timeLineView;
    private List<Drawable> list;
    private List<StepViewModel> firstStepViewModels = new ArrayList<>();

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public TaobaoFragment() {
        // Required empty public constructor
    }

    public static TaobaoFragment newInstance(String param1, String param2) {
        TaobaoFragment fragment = new TaobaoFragment();
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
        View view = inflater.inflate(R.layout.fragment_blank, container, false);
        timeLineView = view.findViewById(R.id.taobao_tlv);
        initView();
        return view;
    }

    private void initView() {

        firstStepViewModels.add(new StepViewModel("卖家退款", false, "2016年5月1日"));
        firstStepViewModels.add(new StepViewModel("银行处理", false, "2016年6月1日"));
        firstStepViewModels.add(new StepViewModel("退款成功", false, "2016年6月1日"));
        firstAdapter = new StepViewModelAdapter(firstStepViewModels);
        list = new ArrayList<>();
        list.add(getResources().getDrawable(R.drawable.withpadding));
        list.add(getResources().getDrawable(R.drawable.withpadding));
        list.add(getResources().getDrawable(R.drawable.ic_offline_pin_black_24dp));
        timeLineView.setTimeLineConfig(firstAdapter, TimeLineType.StepViewType.TOP_STEP_PROGRESS, 3f, list);
    }

}
