angular.module('connector').directive('clientResult', function() {
        return {
            restrict: 'A',
            scope: {
                clientResult: '='
            },
            templateUrl: 'templates/client_result.html'
        };
    });
