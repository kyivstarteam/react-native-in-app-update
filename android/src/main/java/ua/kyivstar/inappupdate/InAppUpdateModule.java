package ua.kyivstar.inappupdate;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;

import com.facebook.react.bridge.ActivityEventListener;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import com.google.android.play.core.install.InstallState;
import com.google.android.play.core.install.InstallStateUpdatedListener;
import com.google.android.play.core.install.model.InstallStatus;

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
    public void onActivityResult(Activity activity, int requestCode, int resultCode, Intent data) {
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
    public void onNewIntent(Intent intent) {

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
    public void isUpdatedDownloaded(final Promise promise) {
        inAppUpdateService.isUpdatedDownloaded(new InAppUpdateService.CallbackInterface() {
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
    public void completeUpdate() {
        inAppUpdateService.completeUpdate();
    }

    @ReactMethod
    public void getUpdateStatus(Promise promise) {
        promise.resolve(updateStatus);
    }

    private void sendEvent(ReactContext reactContext,
                           String eventName,
                           @Nullable WritableMap params) {
        reactContext
                .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                .emit(eventName, params);
    }

    @ReactMethod
    public void subscribeForDownloadedState() {
        inAppUpdateService.appUpdateManager.registerListener(finishUpdateListener);
    }

    @ReactMethod
    public void unsubscribeForDownloadedState() {
        inAppUpdateService.appUpdateManager.unregisterListener(finishUpdateListener);
    }

    private InstallStateUpdatedListener finishUpdateListener = new
            InstallStateUpdatedListener() {
                @Override
                public void onStateUpdate(InstallState state) {
                    if (state.installStatus() == InstallStatus.DOWNLOADED){
                        WritableMap params = Arguments.createMap();
                        params.putString("status", Constants.FINISH_UPDATE_DOWNLOADING_STATUS);

                        sendEvent(reactContext, Constants.FINISH_UPDATE_DOWNLOADING_EVENT_NAME, params);
                    }
                }
            };
}
