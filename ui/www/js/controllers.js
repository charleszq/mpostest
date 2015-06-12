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
		}
	});
