angular.module('connector').directive('clientResult', function() {
        return {
            restrict: 'A',
            scope: {
                clientResult: '=',
                formattedTemplate: '=formattedTemplate'
            },
            templateUrl: 'templates/client_result.html'
        };
    });
