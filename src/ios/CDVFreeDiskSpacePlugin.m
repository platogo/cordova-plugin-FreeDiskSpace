#import "CDVFreeDiskSpacePlugin.h"

@implementation CDVFreeDiskSpacePlugin

- (void)get: (CDVInvokedUrlCommand*)command
{
    uint64_t totalFreeSpace = 0;
    NSError *error = nil;
    NSArray *paths = NSSearchPathForDirectoriesInDomains(NSDocumentDirectory, NSUserDomainMask, YES);
    NSDictionary *dictionary = [[NSFileManager defaultManager] attributesOfFileSystemForPath:[paths lastObject] error: &error];

    if (dictionary) {
        NSNumber *freeFileSystemSizeInBytes = [dictionary objectForKey:NSFileSystemFreeSize];
        totalFreeSpace = [freeFileSystemSizeInBytes unsignedLongLongValue];
    }

    CDVPluginResult* result = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsString:[[NSNumber numberWithFloat:totalFreeSpace] stringValue]];

    [self.commandDelegate sendPluginResult:result callbackId:command.callbackId];
}

@end
