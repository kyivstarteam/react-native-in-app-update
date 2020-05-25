<p align="center">
  <h3 align="center">React Native In App Update</h3>

  <p align="center">
    Keeping your app up-to-date on your users’ devices.
  </p>
</p>

## Table of Contents

* [Getting Started](#getting-started)
  * [Prerequisites](#prerequisites)
  * [Installation](#installation)
* [Usage](#usage)
    * [Flexible Update](#flexible-update)
    * [Immediate Update](#immediate-update)
  
## Getting Started 

### Prerequisites
This feature works only on Android devices.

Before going any further make sure that you:
1. have published android application in Google Play Console.
2. have turned on internal app sharing in Google Play Console.
3. have your React Native project.

### Installation

1.  Add package in to the project
    ```sh
    yarn add https://github.com/kyivstarteam/react-native-in-app-update.git
     ```

## Usage

### Flexible update
    
   A user experience that provides background download and installation with graceful state monitoring. 
   This UX is appropriate when it’s acceptable for the user to use the app while downloading the update.
   
   ```js
    import { View, Text, Alert } from 'react-native';
    import React, { useEffect } from 'react';
    
    import InAppUpdate from '@kyivstarteam/react-native-in-app-update';

    const inAppUpdate = new InAppUpdate({ updateType: InAppUpdate.appUpdateType.FLEXIBLE });
  
    const onDownloadedUpdate = () => {
      Alert.alert(
        'An update has just been downloaded.',
        'Want to restart the app?',
        [
          { text: 'No', style: 'cancel' },
          { text: 'Yes', onPress: inAppUpdate.completeUpdate },
        ],
      );
    };
    
    const checkAvailableUpdate = async () => {
      const isUpdateAvailable = await inAppUpdate.isUpdateAvailable();
    
      if (isUpdateAvailable) {
        inAppUpdate.onFinishDownloadUpdate(onDownloadedUpdate);
        await inAppUpdate.startUpdate();
      }
    };
    
    const App = () => {
      useEffect(() => {
        checkAvailableUpdate();
      }, []);

      return (
        <View>
          <Text>App</Text>
        </View>
      );
    };
   ``` 

### Immediate update

A full screen user experience that requires the user to update and restart the app in order to continue
using the app. This UX is best for cases where an update is critical for continued use of the app. 
After a user accepts an immediate update, Google Play handles the update installation and app restart.
   
   ```js
    import { View, Text } from 'react-native';
    import React, { useEffect } from 'react';
    
    import InAppUpdate from '@kyivstarteam/react-native-in-app-update';
    
    const inAppUpdate = new InAppUpdate({ updateType: InAppUpdate.appUpdateType.IMMEDIATE });
    
    const checkAvailableUpdateAndInstall = async () => {
      const isUpdateAvailable = await inAppUpdate.isUpdateAvailable();
    
      if (isUpdateAvailable) {
        await inAppUpdate.startUpdate();
      }
    };
    
    const App = () => {
      useEffect(() => {
        checkAvailableUpdateAndInstall();
      }, []);
    
      return (
        <View>
          <Text>App</Text>
        </View>
      );
    };
   ``` 

## Reference
