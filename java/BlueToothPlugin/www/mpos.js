module.exports = {
    setupDevice: function (name, successCallback, errorCallback) {
        cordova.exec(successCallback, errorCallback, "Mpos", "setupDevice", [name]);
    }
};
