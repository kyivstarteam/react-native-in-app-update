import { Platform } from 'react-native';
import RNKInAppUpdate, { RNKInAppUpdateEvents } from './nativeModule';

class InAppUpdate {
  static updateDownloadingFinishEventName = 'RNKInAppUpdate_FINISH_UPDATE_DOWNLOADING_EVENT_NAME';
  static appUpdateType = {
    FLEXIBLE: 'flexible',
    IMMEDIATE: 'immediate',
  };
  rnkInAppUpdate = null;
  onFinisDownloadUpdateListiner = null;

  constructor({ updateType, stalenessDays = 0 }) {
    if (this.isSupportedPlatform) {
      RNKInAppUpdate.init(updateType, stalenessDays);

      this.rnkInAppUpdate = RNKInAppUpdate;
    } else {
      this.rnkInAppUpdate = {};
    }
  }

  get isSupportedPlatform() {
    return Platform.OS === 'android';
  }

  async isUpdateAvailable() {
    if (this.isSupportedPlatform) {
      return this.rnkInAppUpdate.isUpdateAvailable();
    }
  }

  async isUpdatePaused() {
    if (this.isSupportedPlatform) {
      return this.rnkInAppUpdate.isUpdatePaused();
    }
  }

  async startUpdate() {
    if (this.isSupportedPlatform) {
      return this.rnkInAppUpdate.startUpdate();
    }
  }

  completeUpdate() {
    if (this.isSupportedPlatform) {
      this.rnkInAppUpdate.completeUpdate();
    }
  }

  async getUpdateStatus() {
    if (this.isSupportedPlatform) {
      return this.rnkInAppUpdate.getUpdateStatus();
    }
  }

  async isUpdatedDownloaded() {
    if (this.isSupportedPlatform) {
      return this.rnkInAppUpdate.isUpdatedDownloaded();
    }
  }

  onFinishDownloadUpdate(callback = () => { }) {
    this.rnkInAppUpdate.subscribeForDownloadedState();
    this.onFinisDownloadUpdateListiner = RNKInAppUpdateEvents.addListener(InAppUpdate.updateDownloadingFinishEventName, callback);

    return () => {
      this.rnkInAppUpdate.unsubscribeForDownloadedState();
      RNKInAppUpdateEvents.removeAllListeners(InAppUpdate.updateDownloadingFinishEventName);
      this.onFinisDownloadUpdateListiner = null;
    };
  }
};

export { UpdateType };
export default InAppUpdate;
