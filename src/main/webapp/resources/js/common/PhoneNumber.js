angular.module('connector').directive('phoneNumber', function() {
        return {
            restrict: 'A',
            scope: {
                phoneNumber: '=',
                numberTitle: '='
            },
            templateUrl: 'partials/phone_number.html'
        };
    });
