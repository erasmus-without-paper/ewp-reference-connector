angular.module('institution').controller('OrganizationUnitClientController', function ($scope, InstitutionService, ClientCacheService) {
    InstitutionService.getOrganizationUnitHeis(function(result) {
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
            if (hei.url === $scope.organizationUnitRequest.url) {
                $scope.urlHeis.push(hei);
            }
        });
    };
    
    $scope.institutionChanged = function() {
        $scope.heiOrganizationUnits = ['<Missing Organization Unit Id>'];
        var organizationUnitIds = ClientCacheService.get('Institution', $scope.organizationUnitRequest.heiId);
        angular.forEach(organizationUnitIds, function(ounitId) {
            $scope.heiOrganizationUnits.push(ounitId);
        });
    };
    
    $scope.sendOrganizationUnitRequest = function() {
        if ($scope.cachedIds) {
            $scope.organizationUnitRequest.organizationUnitIds = $scope.cachedOrgUnitIds;
        } else {
            var orgUnitIds = $scope.manuallyOrgUnitIds.split(',');
            $scope.organizationUnitRequest.organizationUnitIds = orgUnitIds;
        }
        InstitutionService.getOrganizationUnits($scope.organizationUnitRequest, function(result) {
            $scope.organizationUnitResult = result;
        });
    };
    
    $scope.organizationUnitRequest = {method: "GET"};
});
