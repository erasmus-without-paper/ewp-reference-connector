angular.module('connector').directive('flexibleAddress', function() {
        return {
            restrict: 'A',
            scope: {
                address: '=flexibleAddress'
            },
            templateUrl: 'partials/flexible_address.html'
        };
    });