angular.module('connector').directive('contact', function() {
        return {
            restrict: 'A',
            scope: {
                contact: '='
            },
            templateUrl: 'templates/contact.html'
        };
    });