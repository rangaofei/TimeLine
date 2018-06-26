package io.github.rangaofei.sakatimeline.divider;

public interface TimeLineType {

    //stepview的枚举类
    enum StepViewType implements TimeLineType {
        LEFT_STEP_PROGRESS,//左侧显示当前的步骤，右侧显示内容
        RIGHT_STEP_PROGRESS,
        TOP_STEP_PROGRESS,
        BOTTOM_STEP_PROGRESS,
    }

    enum TimeLineViewType implements TimeLineType{
        ONLY_RIGHT,//key和value在左侧侧显示，右侧显示时间轴
        ONLY_LEFT,//key和value在右侧显示，左侧显示时间轴
        LEFT_TO_RIGHT,//先左侧显示key，右侧显示value，中间显示时间轴，然后右侧显示key，左侧显示value，交替
        RIGHT_TO_LEFT,//先右侧显示key，左侧显示value，中间显示时间轴，然后左侧显示key，右侧显示value，交替
        LEFT_KEY,//左侧显示key，右侧显示value，中间显示时间轴
        LEFT_VALUE,//左侧显示value，右侧显示key，中间显示时间轴
    }

}
