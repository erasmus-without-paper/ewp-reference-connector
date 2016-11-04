angular
    .module('connector', ['echo', 'iia', 'institution', 'ngResource', 'ngRoute'])
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
            }).when('/echo', {
                templateUrl: 'partials/echo.html',
                controller: 'EchoController'
            }).when('/manifest', {
                templateUrl: 'partials/todo.html'
            }).when('/course', {
                templateUrl: 'partials/todo.html'
            }).when('/mobility', {
                templateUrl: 'partials/todo.html'
            }).when('/coordinators', {
                templateUrl: 'partials/todo.html'
            }).when('/person', {
                templateUrl: 'partials/todo.html'
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
