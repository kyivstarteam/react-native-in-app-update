package ua.kyivstar.inappupdate;

import android.content.Intent;
import android.util.Log;

import com.facebook.react.bridge.ActivityEventListener;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

import static android.app.Activity.RESULT_OK;

public class InAppUpdateModule extends ReactContextBaseJavaModule implements ActivityEventListener {
    private final ReactApplicationContext reactContext;
    private InAppUpdateService inAppUpdateService;
    private String updateStatus;

    public InAppUpdateModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.updateStatus = Constants.INACTIVE_UPDATE_STATUS;
        this.reactContext = reactContext;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constants.REQUEST_UPDATE_CODE) {
            if (resultCode != RESULT_OK) {
                updateStatus = Constants.FAILED_UPDATE_STATUS;
            } else {
                updateStatus = Constants.SUCCESS_UPDATE_STATUS;
            }
            Log.d(Constants.DEBUG_TAG, "Update status: " + updateStatus);
        }
    }

    @Override
    public String getName() {
        return Constants.RN_MODULE_NAME;
    }

    @ReactMethod
    public void init(String updateType, Integer stalenessDays) {
        inAppUpdateService = new InAppUpdateService(
                getCurrentActivity(),
                reactContext,
                updateType,
                stalenessDays
        );
    }

    @ReactMethod
    public void isUpdateAvailable(final Promise promise) {
        inAppUpdateService.isUpdateAvailable(new InAppUpdateService.CallbackInterface() {
            @Override
            public <T> void invoke(T result, String error) {
                if (error != null) {
                    promise.reject(null, error);
                    return;
                }

                promise.resolve(result);
                return;
            }
        });
    }

    @ReactMethod
    public void startUpdate(final Promise promise) {
        inAppUpdateService.startUpdate(new InAppUpdateService.CallbackInterface() {
            @Override
            public <T> void invoke(T result, String error) {
                if (error != null) {
                    promise.reject(null, error);
                    return;
                }

                promise.resolve(result);
                return;
            }
        });
    }

    @ReactMethod
    public void isUpdatePaused(final Promise promise) {
        inAppUpdateService.isUpdateStalled(new InAppUpdateService.CallbackInterface() {
            @Override
            public <T> void invoke(T result, String error) {
                if (error != null) {
                    promise.reject(null, error);
                    return;
                }

                promise.resolve(result);
                return;
            }
        });
    }

    @ReactMethod
    public void getUpdateStatus(Promise promise) {
        promise.resolve(updateStatus);
    }
}
