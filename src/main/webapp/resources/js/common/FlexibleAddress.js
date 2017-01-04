angular.module('connector').directive('flexibleAddress', function() {
        return {
            restrict: 'A',
            scope: {
                address: '=address'
            },
            templateUrl: 'partials/flexible-address.html'
        };
    });