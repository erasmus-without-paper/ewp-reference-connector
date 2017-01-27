angular.module('connector').directive('contactDetails', function() {
        return {
            restrict: 'A',
            scope: {
                details: '=contactDetails'
            },
            templateUrl: 'partials/contact_details.html'
        };
    });
