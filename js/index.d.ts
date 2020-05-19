declare module '@kyivstarteam/react-native-in-app-update' {
  type UpdateType = 'flexible' | 'immediate';
  type UpdateStatus = 'failed' | 'success' | 'inactive';

  interface RNKInAppUpdate {
    init: (type: UpdateType, stalenessDays: number) => void,
    isUpdateAvailable: () => Promise<boolean>,
    startUpdate: () => Promise<boolean>,
    isUpdatePaused: () => Promise<boolean>,
    getUpdateStatus: () => Promise<UpdateStatus>,
  }

  interface InitOptions {
    updateType: UpdateType,
    stalenessDays: number,
  }

  class InAppUpdate {
    private rnkInAppUpdate: RNKInAppUpdate;

    constructor(opt: InitOptions);

    public isUpdateAvailable(): Promise<boolean>;
    public isUpdatePaused(): Promise<boolean>;
    public startUpdate(): Promise<boolean>;
    public getUpdateStatus(): Promise<UpdateStatus>;
  }

  export default InAppUpdate;
}
