angular.module('connector').directive('contact', function() {
        return {
            restrict: 'A',
            scope: {
                contact: '=contact'
            },
            templateUrl: 'partials/contact_template.html'
        };
    });