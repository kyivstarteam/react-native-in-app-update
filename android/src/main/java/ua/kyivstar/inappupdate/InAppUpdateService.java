package ua.kyivstar.inappupdate;

import com.google.android.play.core.appupdate.AppUpdateInfo;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.install.model.AppUpdateType;
import com.google.android.play.core.install.model.UpdateAvailability;
import com.google.android.play.core.tasks.OnFailureListener;
import com.google.android.play.core.tasks.OnSuccessListener;
import com.google.android.play.core.tasks.Task;

import android.app.Activity;
import android.content.Context;
import android.content.IntentSender;
import android.util.Log;

final public class InAppUpdateService {

    public interface CallbackInterface {
        <T> void invoke(T result, String error);
    }

    private AppUpdateManager appUpdateManager;
    private Integer stalenessDaysForUpdate;
    private Integer updateType;
    private Activity activity;
    private Context appContext;

    public InAppUpdateService(Activity rootActivity, Context context,
                              String updateTypeKey, Integer stalenessDays) {

        appUpdateManager = AppUpdateManagerFactory.create(context);
        stalenessDaysForUpdate = stalenessDays != null
                ? stalenessDays
                : Constants.DEFAULT_STALENESS_DAYS_FOR_UPDATE;
        updateType = updateTypeKey != null
                ? Constants.MAP_UPDATE_TYPES.get(updateTypeKey)
                : AppUpdateType.FLEXIBLE;
        activity = rootActivity;
        appContext = context;
    }

    public void isUpdateAvailable(final CallbackInterface callback) {
        Task<AppUpdateInfo> appUpdateInfoTask = appUpdateManager.getAppUpdateInfo();

        appUpdateInfoTask.addOnSuccessListener(new OnSuccessListener<AppUpdateInfo>() {
                                                   @Override
                                                   public void onSuccess(AppUpdateInfo result) {
                                                       if (result.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                                                               && result.clientVersionStalenessDays() != null
                                                               && result.clientVersionStalenessDays() >= stalenessDaysForUpdate
                                                               && result.isUpdateTypeAllowed(updateType)) {
                                                           Log.d(Constants.DEBUG_TAG, "Update available");
                                                           callback.invoke(true, null);
                                                       } else {
                                                           Log.d(Constants.DEBUG_TAG, "Update not available");
                                                           callback.invoke(false, null);
                                                       }
                                                   }
                                               }
        );
        appUpdateInfoTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(Exception e) {
                Log.d(Constants.DEBUG_TAG, "Error: " + e.toString());
                callback.invoke(null, e.toString());
            }
        });
    }

    public void startUpdate(final CallbackInterface callback) {
        final Task<AppUpdateInfo> appUpdateInfoTask = appUpdateManager.getAppUpdateInfo();

        appUpdateInfoTask.addOnSuccessListener(new OnSuccessListener<AppUpdateInfo>() {
                                                   @Override
                                                   public void onSuccess(AppUpdateInfo appUpdateInfo) {
                                                       try {
                                                           appUpdateManager.startUpdateFlowForResult(
                                                                   appUpdateInfo,
                                                                   updateType,
                                                                   activity,
                                                                   Constants.REQUEST_UPDATE_CODE);
                                                           callback.invoke(true, null);
                                                       } catch (IntentSender.SendIntentException e) {
                                                           Log.d(Constants.DEBUG_TAG, "Error: " + e.toString());
                                                           callback.invoke(null, e.getLocalizedMessage());
                                                       }
                                                   }
                                               }
        );

        appUpdateInfoTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(Exception e) {
                Log.d(Constants.DEBUG_TAG, "Error: " + e.toString());
                callback.invoke(null, e.toString());
            }
        });
    }

    public void isUpdateStalled(final CallbackInterface callback) {
        final Task<AppUpdateInfo> appUpdateInfoTask = appUpdateManager.getAppUpdateInfo();

        appUpdateInfoTask.addOnSuccessListener(new OnSuccessListener<AppUpdateInfo>() {
            @Override
            public void onSuccess(AppUpdateInfo appUpdateInfo) {
                if (appUpdateInfo.updateAvailability()
                        == UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS
                ) {
                    callback.invoke(true, null);
                } else {
                    callback.invoke(false, null);
                }
            }
        });

        appUpdateInfoTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(Exception e) {
                Log.d(Constants.DEBUG_TAG, "Error: " + e.toString());
                callback.invoke(null, e.toString());
            }
        });
    }
}

