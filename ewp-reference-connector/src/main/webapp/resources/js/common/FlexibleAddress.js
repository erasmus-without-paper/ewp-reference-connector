angular.module('connector').directive('flexibleAddress', function() {
        return {
            restrict: 'A',
            scope: {
                address: '=flexibleAddress'
            },
            templateUrl: 'templates/flexible_address.html'
        };
    });