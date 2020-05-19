import { NativeModules } from 'react-native';
const RNKInAppUpdate = NativeModules.RNKInAppUpdate;

if (Platform.OS === 'ios') {
  console.warn(`@kyivstarteam/react-native-in-app-update: Module does not support ios platform`);
}

if (!RNKInAppUpdate) {
  throw new Error(
    `@kyivstarteam/react-native-in-app-update: NativeModule.RNKInAppUpdate is null. To fix this issue try these steps:
    • For react-native <= 0.59: Run \`react-native link @kyivstarteam/react-native-in-app-update\` in the project root.
    • Rebuild and re-run the app.
    • If you are using CocoaPods on iOS, run \`pod install\` in the \`ios\` directory and then rebuild and re-run the app. You may also need to re-open Xcode to get the new pods.`,
  );
}

export default RNKInAppUpdate;
