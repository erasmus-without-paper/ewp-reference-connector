angular.module('connector').directive('phoneNumberForm', function() {
        return {
            restrict: 'A',
            scope: {
                phoneNumber: '=phoneNumberForm'
            },
            templateUrl: 'templates/phone_number_form.html'
        };
    });
