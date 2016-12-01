angular.module('iiapartner').controller('IiaPartnerController', function ($scope, IiaPartnerService) {
    $scope.getAllIiaPartners = function(){
        IiaPartnerService.getAll(
            function(result) {
                $scope.iiaPartners = result;
        });
    };

    $scope.getAllIiaPartners();
});
