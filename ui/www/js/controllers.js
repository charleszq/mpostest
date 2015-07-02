angular.module('starter.controllers', ['ionic'])
	.controller('MainCtrl', function($scope, $ionicModal, $timeout, $ionicLoading) {
		
		$scope.showLoading = function() {
			$ionicLoading.show({
				template: 'Please wait...',
				duration: 10000
			});
		},
		
		$scope.hideLoading = function() {
			$ionicLoading.hide();
		},
		
		$scope.setupDevice = function() {
			var clientdata = {
				billsMID: '898000156911002',
				billsTID:  '00019130'
			};
			
			$scope.showLoading();
			Mpos.setupDevice(clientdata,function(msg) {
				alert(msg);
				$scope.hideLoading();
			},  function(err) {
				alert(err);
				$scope.hideLoading();
			});

		},
		
		$scope.getDeviceId = function() {
			var clientdata = {
				billsMID: '898000156911002',
				billsTID:  '00019130'
			};
			$scope.showLoading();
			Mpos.getDeviceId(clientdata,function(msg) {
				alert(msg);
				$scope.hideLoading();
			}, function(err) {
				alert(err);
				$scope.hideLoading();
			});
		},
		
		$scope.queryOrderInfo = function() {
			var clientdata = {
				billsMID: '898350259988888',
				billsTID:  '59708888',
                orderId: '20150627191256000002',
                merOrderId: '20150627191256000002'
			};
			$scope.showLoading();
			Mpos.queryOrder(clientdata,function(msg) {
				alert(msg);
				$scope.hideLoading();
			}, function(err) {
				alert(err);
				$scope.hideLoading();
			});
		},
		
		$scope.getCurrentPosition = function() {
			var option = { maximumAge: 3000, timeout: 50000, enableHighAccuracy: false };
			$scope.showLoading();
			navigator.geolocation.getCurrentPosition(
				function(position) { //success
					console.log(position);
					alert( "Longtitude: " + position.coords.longitude + ", Latitude: " + position.coords.latitude );
					$scope.hideLoading();
				}, function(err) { //error
					alert(err);
					$scope.hideLoading();
				},
				option
			);
		},
		
		$scope.placeOrder = function() {
			var clientdata = {
				billsMID: '898000156911002',
				billsTID:  '00019130',
				orderId: '622014040126637643',
				salesSlipType: '1',
				payType: 'boxpay'
			};
			$scope.showLoading();
			Mpos.placeOrder(clientdata,function(msg) {
				alert(msg);
				$scope.hideLoading();
			}, function(err) {
				alert(err);
				$scope.hideLoading();
			});
		},
		
		$scope.txnInfoAndSign = function() {
			var clientdata = {
				billsMID: '898000156911002',
				billsTID:  '00019130',
				orderId: '622014040126637643',
				salesSlipType: '1'
			};
			$scope.showLoading();
			Mpos.txnInfoAndSign(clientdata,function(msg) {
				alert(msg);
				$scope.hideLoading();
			}, function(err) {
				alert(err);
				$scope.hideLoading();
			});
		}

	});
