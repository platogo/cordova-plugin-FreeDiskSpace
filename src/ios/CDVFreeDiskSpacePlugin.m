#import "CDVFreeDiskSpacePlugin.h"

@implementation CDVFreeDiskSpacePlugin

- (void)get: (CDVInvokedUrlCommand*)command
{
    uint64_t totalFreeSpaceKiloBytes = 0;
    NSError *error = nil;
    NSArray *paths = NSSearchPathForDirectoriesInDomains(NSDocumentDirectory, NSUserDomainMask, YES);
    NSDictionary *dictionary = [[NSFileManager defaultManager] attributesOfFileSystemForPath:[paths lastObject] error: &error];

    if (dictionary) {
        NSNumber *freeFileSystemSizeInBytes = [dictionary objectForKey:NSFileSystemFreeSize];
        totalFreeSpaceKiloBytes = [freeFileSystemSizeInBytes unsignedLongLongValue] / 1024ll;
    }

    NSNumber* totalFreeSpaceNumber = [NSNumber numberWithUnsignedLongLong:totalFreeSpaceKiloBytes];
    CDVPluginResult* result = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsString:[totalFreeSpaceNumber stringValue]];

    [self.commandDelegate sendPluginResult:result callbackId:command.callbackId];
}

@end
