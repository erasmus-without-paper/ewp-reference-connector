angular.module('connector').directive('clientResult', function() {
        return {
            restrict: 'A',
            scope: {
                clientResult: '=',
                formattedTemplate: '=formattedTemplate'
            },
            templateUrl: 'partials/client_result.html'
        };
    });
