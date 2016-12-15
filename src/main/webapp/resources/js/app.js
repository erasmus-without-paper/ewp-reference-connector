angular
    .module('connector', ['menu', 'home', 'echo', 'iia', 'institution', 'ngResource', 'ngRoute', 'person', 'coordinator', 'academicterm', 'los', 'loi'])
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
            }).when('/academicterm', {
                templateUrl: 'partials/academicterm.html',
                controller: 'AcademicTermController'
            }).when('/los', {
                templateUrl: 'partials/los.html',
                controller: 'LosController'
            }).when('/loi', {
                templateUrl: 'partials/loi.html',
                controller: 'LoiController'
            }).when('/mobility', {
                templateUrl: 'partials/todo.html'
            }).when('/coordinator', {
                templateUrl: 'partials/coordinator.html',
                controller: 'CoordinatorController'
            }).when('/person', {
                templateUrl: 'partials/person.html',
                controller: 'PersonController'
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
    .filter('languageItem', function() {
        return function(languageList, lang) {
            var languageObject;
            angular.forEach(languageList, function(item) {
                if (item.lang === lang) {
                    languageObject = item;
                }
            });

            return languageObject ? languageObject : languageList[0];
        };
    })
    .filter('capitalize', function() {
        return function(text) {
            if(text){
                var firstLetter = text[0];
                var otherLetters = text.substring(1);
                return firstLetter.toUpperCase() + otherLetters.toLowerCase();
            }
        };
    });

