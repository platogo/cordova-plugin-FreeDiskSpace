package org.apache.cordova.core;

import android.os.Environment;
import android.os.StatFs;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.PluginResult;
import org.json.JSONArray;
import org.json.JSONException;

public class FreeDiskSpacePlugin extends CordovaPlugin {
    public static final String ACTION_GET = "get";

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        if (action.equals(ACTION_GET)) {
            this.get(callbackContext);
            return true;
        }

        return false;
      }

    private void get(final CallbackContext callbackContext) {
        cordova.getThreadPool().execute(new Runnable() {
            public void run() {
                String status = Environment.getExternalStorageState();
                long freeSpace = 0;

                // If SD card exists
                if (status.equals(Environment.MEDIA_MOUNTED)) {
                    freeSpace = freeSpaceCalculation(Environment.getExternalStorageDirectory().getPath());
                }
                else if (checkInternal) {
                    freeSpace = freeSpaceCalculation("/");
                }

                callbackContext.sendPluginResult(new PluginResult(PluginResult.Status.OK, freeSpace));
            }
        });
    }

    private static long freeSpaceCalculation(String path) {
        StatFs stat = new StatFs(path);
        long blockSize = stat.getBlockSize();
        long availableBlocks = stat.getAvailableBlocks();
        return availableBlocks * blockSize / 1024;
    }
}
