declare module '@kyivstarteam/react-native-in-app-update' {
  enum AppUpdateType {
    FLEXIBLE = 'flexible',
    IMMEDIATE = 'immediate',
  };
  type UpdateStatus = 'FAILED' | 'SUCCESS' | 'INACTIVE';

  interface RNKInAppUpdate {
    init: (type: typeof UpdateType, stalenessDays: number) => void,
    isUpdateAvailable: () => Promise<boolean>,
    startUpdate: () => Promise<boolean>,
    isUpdatePaused: () => Promise<boolean>,
    getUpdateStatus: () => Promise<UpdateStatus>,
    isUpdateDownloaded: () => Promise<boolean>,
    completeUpdate: () => void,
    subscribeForDownloadedState: () => void,
    unsubscribeForDownloadedState: () => void,
  }


  interface InitOptions {
    updateType: AppUpdateType,
    stalenessDays?: number,
  }

  class InAppUpdate {
    static appUpdateType = AppUpdateType;
    private static updateDownloadingFinishEventName: String;
    private rnkInAppUpdate: RNKInAppUpdate;

    constructor(opt: InitOptions);

    public isUpdateAvailable(): Promise<boolean>;
    public isUpdatePaused(): Promise<boolean>;
    public startUpdate(): Promise<boolean>;
    public getUpdateStatus(): Promise<UpdateStatus>;
    public isUpdateDownloaded(): Promise<boolean>;
    public completeUpdate(): void;
    public onFinishDownloadUpdate(callback: (state?: string) => void): () => void;

    private get isSupportedPlatform(): boolean;
  }
  export default InAppUpdate;
}
