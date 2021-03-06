module.exports = {
    setupDevice: function (name, successCallback, errorCallback) {
        cordova.exec(successCallback, errorCallback, "Mpos", "setupDevice", [name]);
    },
    
    getDeviceId: function(name, successCallback, errorCallback) {
        cordova.exec(successCallback, errorCallback, "Mpos", "getDeviceId", [name]);
    },
	
	queryOrder: function(name, successCallback, errorCallback) {
        cordova.exec(successCallback, errorCallback, "Mpos", "queryOrder", [name]);
    },
	
	placeOrder: function(name, successCallback, errorCallback) {
        cordova.exec(successCallback, errorCallback, "Mpos", "placeOrder", [name]);
    },
	
	txnInfoAndSign: function(name, successCallback, errorCallback) {
        cordova.exec(successCallback, errorCallback, "Mpos", "txnInfoAndSign", [name]);
    }
};
