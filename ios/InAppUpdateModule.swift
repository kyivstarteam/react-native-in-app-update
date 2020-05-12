import Foundation

@objc(RNKInAppUpdate)
class InAppUpdateModule: NSObject  {
    @objc(sampleMethod:num:callback:)
    func sampleMethod(str: String, num: Int, callback: RCTResponseSenderBlock) {
      callback(["Received numberArgument: \(num) stringArgument: \(str)"])
    }
}
