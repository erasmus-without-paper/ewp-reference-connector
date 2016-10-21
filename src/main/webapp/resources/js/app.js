angular
    .module('connector', ['echo', 'iia', 'institution', 'department', 'ngResource', 'ngRoute'])
    .config(function ($routeProvider) {
        $routeProvider.when('/iia', {
                templateUrl: 'partials/iia.html',
                controller: 'IiaController'
            }).when('/newIia', {
                templateUrl: 'partials/new_iia_form.html',
                controller: 'IiaController'
            }).when('/home', {
                templateUrl: 'partials/home.html',
                controller: 'HomeController'
            }).when('/institution', {
                templateUrl: 'partials/institution.html',
                controller: 'InstitutionController'
            }).when('/department', {
                templateUrl: 'partials/department.html',
                controller: 'DepartmentController'
            }).when('/echo', {
                templateUrl: 'partials/echo.html',
                controller: 'EchoController'
            }).otherwise({
                redirectTo: '/home'
            });
    })
    .filter('toDate', function() {
        return function(date) {
            if (date) {
                var dt = new Date(date.replace(/\+.*/,''));
                return dt ? dt : date;
            }
            return date;
        };
    })
    .controller('HomeController', function ($scope) {
    });
