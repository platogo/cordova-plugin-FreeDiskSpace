var apsalarPlugin = {
  getFreeDiskSpace: function(successCallback, errorCallback) {
    cordova.exec(
      successCallback,
      errorCallback,
      'FreeDiskSpacePlugin',
      'get', []
    );
  }
};

module.exports = apsalarPlugin;
