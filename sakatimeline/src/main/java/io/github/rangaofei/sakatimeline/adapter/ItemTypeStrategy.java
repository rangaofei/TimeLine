package io.github.rangaofei.sakatimeline.adapter;

import io.github.rangaofei.sakatimeline.TimeLineType;

import static io.github.rangaofei.sakatimeline.adapter.AbstractTimeLineAdapter.TYPE_KEY;
import static io.github.rangaofei.sakatimeline.adapter.AbstractTimeLineAdapter.TYPE_KEY_ONLY;
import static io.github.rangaofei.sakatimeline.adapter.AbstractTimeLineAdapter.TYPE_VALUE;
import static io.github.rangaofei.sakatimeline.adapter.AbstractTimeLineAdapter.TYPE_VALUE_ONLY;

public class ItemTypeStrategy {


    public static int getItemType(int position, TimeLineType type) {

        switch (type) {
            case LEFT_TO_RIGHT:
                if (position % 4 == 0 | position % 4 == 3) {
                    return TYPE_KEY;
                }
                if (position % 4 == 1 || position % 4 == 2) {
                    return TYPE_VALUE;
                }
            case RIGHT_TO_LEFT:
                if (position % 4 == 0 | position % 4 == 3) {
                    return TYPE_VALUE;
                }
                if (position % 4 == 1 || position % 4 == 2) {
                    return TYPE_KEY;
                }
            case LEFT_KEY:
                if (position % 2 == 0) {
                    return TYPE_KEY;
                } else {
                    return TYPE_VALUE;
                }
            case LEFT_VALUE:
                if (position % 2 == 1) {
                    return TYPE_KEY;
                } else {
                    return TYPE_VALUE;
                }
            case ONLY_LEFT:
                if (position % 2 == 0) {
                    return TYPE_KEY;
                } else {
                    return TYPE_VALUE;
                }
            case ONLY_RIGHT:
                if (position % 2 == 0) {
                    return TYPE_KEY;
                } else {
                    return TYPE_VALUE;
                }
            default:
                if (position % 2 == 1) {
                    return TYPE_KEY;
                } else {
                    return TYPE_VALUE;
                }

        }
    }

    public static int getItemTypeOnlyKey(int position, TimeLineType type) {

        switch (type) {
            case LEFT_TO_RIGHT:
                if (position % 4 == 0 | position % 4 == 3) {
                    return TYPE_KEY;
                }
                if (position % 4 == 1 || position % 4 == 2) {
                    return TYPE_KEY_ONLY;
                }
            case RIGHT_TO_LEFT:
                if (position % 4 == 0 | position % 4 == 3) {
                    return TYPE_KEY_ONLY;
                }
                if (position % 4 == 1 || position % 4 == 2) {
                    return TYPE_KEY;
                }
            case LEFT_KEY:
                if (position % 2 == 0) {
                    return TYPE_KEY;
                } else {
                    return TYPE_KEY_ONLY;
                }
            case LEFT_VALUE:
                if (position % 2 == 1) {
                    return TYPE_KEY;
                } else {
                    return TYPE_KEY_ONLY;
                }
            case ONLY_LEFT:
                return TYPE_KEY;
            case ONLY_RIGHT:
                return TYPE_KEY;
            case LEFT_STEP_PROGRESS:
                return TYPE_KEY;
            default:
                if (position % 2 == 1) {
                    return TYPE_KEY;
                } else {
                    return TYPE_KEY_ONLY;
                }

        }
    }

    public static int getItemTypeOnlyValue(int position, TimeLineType type) {

        switch (type) {
            case LEFT_TO_RIGHT:
                if (position % 4 == 0 | position % 4 == 3) {
                    return TYPE_VALUE_ONLY;
                }
                if (position % 4 == 1 || position % 4 == 2) {
                    return TYPE_VALUE;
                }
            case RIGHT_TO_LEFT:
                if (position % 4 == 0 | position % 4 == 3) {
                    return TYPE_VALUE;
                }
                if (position % 4 == 1 || position % 4 == 2) {
                    return TYPE_VALUE_ONLY;
                }
            case LEFT_KEY:
                if (position % 2 == 0) {
                    return TYPE_VALUE_ONLY;
                } else {
                    return TYPE_VALUE;
                }
            case LEFT_VALUE:
                if (position % 2 == 1) {
                    return TYPE_VALUE_ONLY;
                } else {
                    return TYPE_VALUE;
                }
            case ONLY_LEFT:
                return TYPE_VALUE;
            case ONLY_RIGHT:
                return TYPE_VALUE;
            case LEFT_STEP_PROGRESS:
                return TYPE_VALUE;
            default:
                if (position % 2 == 1) {
                    return TYPE_VALUE_ONLY;
                } else {
                    return TYPE_VALUE;
                }

        }
    }
}
