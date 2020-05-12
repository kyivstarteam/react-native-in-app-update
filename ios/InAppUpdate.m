#import "InAppUpdate.h"
#import <React/RCTBridgeModule.h>

@interface RCT_EXTERN_MODULE(RNKInAppUpdate, NSObject)

RCT_EXTERN_METHOD(sampleMethod:(NSString *)str
                  num:(NSInteger *)num
                  callback:(RCTResponseSenderBlock)callback
                  )

+ (BOOL)requiresMainQueueSetup
{
    return YES;
}

- (dispatch_queue_t)methodQueue
{
    return dispatch_get_main_queue();
}

@end
