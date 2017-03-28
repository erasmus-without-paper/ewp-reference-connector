angular.module('connector').directive('contactDetails', function() {
        return {
            restrict: 'A',
            scope: {
                details: '=contactDetails'
            },
            templateUrl: 'templates/contact_details.html'
        };
    });
