angular.module('institution').controller('InstitutionClientController', function ($scope, InstitutionService, ClientCacheService) {
    InstitutionService.getHeis(function(result) {
        $scope.heis = result;
        if ($scope.heis) {
            $scope.urls = [];
            angular.forEach($scope.heis, function(hei) {
                if ($scope.urls.indexOf(hei.urls['url']) === -1) {
                    $scope.urls.push(hei.urls['url']);
                }
            });
        }
    });
    
    $scope.urlChanged = function() {
        $scope.urlHeis = [{name:'<Missing Institution>', id:'missing'}];
        angular.forEach($scope.heis, function(hei) {
            if (hei.urls['url'] === $scope.institutionRequest.url) {
                $scope.urlHeis.push(hei);
            }
        });
    };
    
    $scope.sendInstitutionRequest = function() {
        InstitutionService.getInstitutions($scope.institutionRequest, function(result) {
            $scope.institutionResult = result;
            if (result && result.result && result.result.hei) {
                angular.forEach(result.result.hei, function(hei) {
                    if (hei.ounitId) {
                        ClientCacheService.add('Institution', hei.heiId, hei.ounitId);
                    }
                });
            }
        });
    };
    
    $scope.institutionRequest = {method: "GET"};
});
