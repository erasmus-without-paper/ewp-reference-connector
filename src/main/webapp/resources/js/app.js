angular
    .module('connector', ['menu', 'home', 'echo', 'iia', 'institution', 'ngResource', 'ngRoute', 'person', 'contact', 'mobilityParticipant', 'academicterm', 'los', 'loi', 'mobility'])
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
            }).when('/contact', {
                templateUrl: 'partials/contact.html',
                controller: 'ContactController'
            }).when('/mobility-participant', {
                templateUrl: 'partials/mobility_participant.html',
                controller: 'MobilityParticipantController'
            }).when('/person', {
                templateUrl: 'partials/person.html',
                controller: 'PersonController'
            }).when('/mobility', {
                templateUrl: 'partials/mobility.html',
                controller: 'MobilityController'
            }).when('/institution-client', {
                templateUrl: 'partials/institution_client.html',
                controller: 'InstitutionClientController'
            }).when('/ounit-client', {
                templateUrl: 'partials/ounit_client.html',
                controller: 'OrganizationUnitClientController'
            }).otherwise({
                redirectTo: '/home'
            });
    });
