import { Platform } from 'react-native';
import RNKInAppUpdate from './nativeModule';

class InAppUpdate {
  rnkInAppUpdate = null;

  constructor(opt) {
    if (Platform.OS === 'ios') {
      console.warn(`@kyivstarteam/react-native-in-app-update: Module does not support ios platform`);

      this.rnkInAppUpdate = {};
    } else {
      RNKInAppUpdate.init(opt.updateType, opt.stalenessDays);

      this.rnkInAppUpdate = RNKInAppUpdate;
    }
  }

  async isUpdateAvailable() {
    return this.rnkInAppUpdate.isUpdateAvailable();
  }

  async isUpdatePaused() {
    return this.rnkInAppUpdate.isUpdatePaused();
  }

  async startUpdate() {
    return this.rnkInAppUpdate.startUpdate();
  }

  async getUpdateStatus() {
    return this.rnkInAppUpdate.getUpdateStatus();
  }
};

export default InAppUpdate;
