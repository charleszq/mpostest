angular.module('starter.controllers', [])

.controller('AppCtrl', function($scope, $ionicModal, $timeout) {
  
  // With the new view caching in Ionic, Controllers are only called
  // when they are recreated or on app start, instead of every page change.
  // To listen for when this page is active (for example, to refresh data),
  // listen for the $ionicView.enter event:
  //$scope.$on('$ionicView.enter', function(e) {
  //});
  
  // Form data for the login modal
  $scope.loginData = {};

  // Create the login modal that we will use later
  $ionicModal.fromTemplateUrl('templates/login.html', {
    scope: $scope
  }).then(function(modal) {
    $scope.modal = modal;
  });

  // Triggered in the login modal to close it
  $scope.closeLogin = function() {
    $scope.modal.hide();
  };

  // Open the login modal
  $scope.login = function() {
    $scope.modal.show();
  };

  // Perform the login action when the user submits the login form
  $scope.doLogin = function() {
    console.log('Doing login', $scope.loginData);
	console.log( coolMethod( '', function(msg) {
		console.log(msg);
	}, function() {}))
    // Simulate a login delay. Remove this and replace with your login
    // code if using a login system
    $timeout(function() {
      $scope.closeLogin();
    }, 1000);
  };
})

.controller('PlaylistsCtrl', function($scope, $http, ApiEndpoint) {
  $scope.playlists = [
  ];
  console.log( ApiEndpoint.url );
  
  $http({
	  url: './cars.json',
	  dataType: 'json',
  }).success(function( data, status, headers, config) {
	  var cars = data.d.results;
	  cars.forEach(function(car) {
		  $scope.playlists.push( { title: car.Model, id: car.Id, price: car.Price} );
	  });
  });
})

.controller('PlaylistCtrl', function($scope, $stateParams, $http, ApiEndpoint) {
	
	var carId = $stateParams.playlistId;
	$http({
		url: './cars.json',
		dataType: 'json'
	}).success(function( data, status, headers, config ) {
		$scope.car = data.d.results[0];
	} );
	
	$scope.testPlugin = function(event) {
		console.log('Trying to integrate the custom plugin.');
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
