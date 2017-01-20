angular.module('institution').controller('InstitutionClientController', function ($scope, InstitutionService) {
    InstitutionService.getHeis(function(result) {
        $scope.heis = result;
        if ($scope.heis) {
            $scope.urls = [];
            angular.forEach($scope.heis, function(hei) {
                if ($scope.urls.indexOf(hei.url) === -1) {
                    $scope.urls.push(hei.url);
                }
            });
        }
    });
    
    $scope.urlChanged = function() {
        $scope.urlHeis = [];
        angular.forEach($scope.heis, function(hei) {
            if (hei.url === $scope.institutionRequest.url) {
                $scope.urlHeis.push(hei);
            }
        });
        $scope.urlHeis.push({name:'<Missing Institution>', id:'missing'});
    };
    
    $scope.sendInstitutionRequest = function() {
        InstitutionService.getInstitutions($scope.institutionRequest, function(result) {
            $scope.institutionResult = result;
        });
    };
    
    $scope.institutionRequest = {method: "GET"};
});
