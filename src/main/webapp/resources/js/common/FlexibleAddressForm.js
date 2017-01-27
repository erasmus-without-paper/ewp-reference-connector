angular.module('connector').directive('flexibleAddressForm', function() {
        return {
            restrict: 'A',
            scope: {
                address: '=flexibleAddressForm'
            },
            templateUrl: 'partials/flexible_address_form.html'
        };
    });