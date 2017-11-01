angular.module('connector').directive('flexibleAddressForm', function() {
        return {
            restrict: 'A',
            scope: {
                address: '=flexibleAddressForm'
            },
            templateUrl: 'templates/flexible_address_form.html',
            controller: function($scope){
                $scope.addAdressLine = function() {
                    if (!$scope.address) {
                        $scope.address = {};
                    }
                    if (!$scope.address.addressLine) {
                        $scope.address.addressLine = [];
                    }
                    if ($scope.address.addressLine.length < 4) {
                        $scope.address.addressLine.push('');
                    }
                };
                $scope.addDeliveryPointCode = function() {
                    if (!$scope.address) {
                        $scope.address = {};
                    }
                    if (!$scope.address.deliveryPointCode) {
                        $scope.address.deliveryPointCode = [];
                    }
                    $scope.address.deliveryPointCode.push('');
                };
                $scope.addRecipientName = function() {
                    if (!$scope.address) {
                        $scope.address = {};
                    }
                    if (!$scope.address.recipientName) {
                        $scope.address.recipientName = [];
                    }
                    $scope.address.recipientName.push('');
                };
            }
        };
    });