angular.module('starter.controllers', [])
	.controller('MainCtrl', function($scope, $ionicModal, $timeout) {
		$scope.setupDevice = function() {
			var clientdata = {
				billsMID: '898000156911002',
				billsTID:  '00019130'
			};
			Mpos.setupDevice(clientdata,function(msg) {
				alert(msg);
			}, function() {
				alert('error');
			});

		},
		
		$scope.getDeviceId = function() {
			var clientdata = {
				billsMID: '898000156911002',
				billsTID:  '00019130'
			};
			Mpos.getDeviceId(clientdata,function(msg) {
				alert(msg);
			}, function() {
				alert('error');
			});
		},
		
		$scope.queryOrderInfo = function() {
			var clientdata = {
				billsMID: '898000156911002',
				billsTID:  '00019130',
				orderId: '622014040126637643'
			};
			Mpos.queryOrder(clientdata,function(msg) {
				alert(msg);
			}, function() {
				alert('error');
			});
		},
		
		$scope.getCurrentPosition = function() {
			var option = { maximumAge: 3000, timeout: 5000, enableHighAccuracy: false };
			navigator.geolocation.getCurrentPosition(
				function(position) { //success
					alert( JSON.stringify(position) );
				}, function(err) { //error
					alert('Error: ' + err.toString());
				},
				option
			);
		}

	});
