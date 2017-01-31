angular.module('institution').controller('InstitutionClientController', function ($scope, InstitutionService, ClientCacheService) {
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
        $scope.urlHeis = [{name:'<Missing Institution>', id:'missing'}];
        angular.forEach($scope.heis, function(hei) {
            if (hei.url === $scope.institutionRequest.url) {
                $scope.urlHeis.push(hei);
            }
        });
    };
    
    $scope.sendInstitutionRequest = function() {
        InstitutionService.getInstitutions($scope.institutionRequest, function(result) {
            $scope.institutionResult = result;
            if (result && result.heis) {
                angular.forEach(result.heis, function(hei) {
                    if (hei.ounitId) {
                        ClientCacheService.add('Institution', hei.heiId, hei.ounitId);
                    }
                });
            }
        });
    };
    
    $scope.institutionRequest = {method: "GET"};
});
