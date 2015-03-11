package org.apache.cordova.core;

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
                long freeDiskSpace = DirectoryManager.getFreeDiskSpace(false);
                callbackContext.sendPluginResult(new PluginResult(PluginResult.Status.OK, freeDiskSpace));
            }
        });
    }
}
