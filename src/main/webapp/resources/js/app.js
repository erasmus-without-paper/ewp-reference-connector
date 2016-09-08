angular
    .module('connector', ['echo', 'iia', 'ngResource', 'ngRoute'])
    .config(function ($routeProvider) {
        $routeProvider.when('/iia', {
                templateUrl: 'partials/iia.html',
                controller: 'IiaController'
            }).when('/home', {
                templateUrl: 'partials/home.html',
                controller: 'HomeController'
            }).when('/echo', {
                templateUrl: 'partials/echo.html',
                controller: 'EchoController'
            }).otherwise({
                redirectTo: '/home'
            });
    })
    .controller('HomeController', function ($scope) {
    });
