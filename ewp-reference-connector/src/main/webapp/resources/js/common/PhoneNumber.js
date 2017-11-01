angular.module('connector').directive('phoneNumber', function() {
        return {
            restrict: 'A',
            scope: {
                phoneNumber: '=',
                numberTitle: '='
            },
            templateUrl: 'templates/phone_number.html'
        };
    });
