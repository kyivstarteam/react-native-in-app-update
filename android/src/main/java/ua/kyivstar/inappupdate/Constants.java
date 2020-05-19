package ua.kyivstar.inappupdate;

import com.google.android.play.core.install.model.AppUpdateType;

import java.util.HashMap;
import java.util.Map;

public final class Constants {
    public static final String RN_MODULE_NAME = "RNKInAppUpdate";
    public static final String DEBUG_TAG = RN_MODULE_NAME;
    public static final Integer REQUEST_UPDATE_CODE = 1;
    public static final Integer DEFAULT_STALENESS_DAYS_FOR_UPDATE = 0;
    public static final String SUCCESS_UPDATE_STATUS = "success";
    public static final String FAILED_UPDATE_STATUS = "failed";
    public static final String INACTIVE_UPDATE_STATUS = "inactive";
    public static final Map<String, Integer> MAP_UPDATE_TYPES = new HashMap<String, Integer>() {{
        put("flexible", AppUpdateType.FLEXIBLE);
        put("immediate", AppUpdateType.IMMEDIATE);
    }};
}
