angular.module('connector').directive('contact', function() {
        return {
            restrict: 'A',
            scope: {
                contact: '='
            },
            templateUrl: 'partials/contact_template.html'
        };
    });