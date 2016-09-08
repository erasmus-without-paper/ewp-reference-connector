angular
    .module('connector', ['echo', 'ngResource', 'ngRoute'])
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
    })
    .service('IiaService', function ($http) {
        return {
            getAll: function (callback) {
                $http.get('rest/iia/get',
                        { method: 'GET',
                          params: {hei_id: 'hei-id', iia_id: ['iia-id-1', 'iia-id-2']}
                        }).success(callback);
            }
        };
    })
    .controller('IiaController', function ($scope, IiaService) {
        IiaService.getAll(
            function(result) {
                $scope.users = result;
            });
    });
