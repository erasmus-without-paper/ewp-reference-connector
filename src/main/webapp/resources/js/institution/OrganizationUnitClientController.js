angular.module('institution').controller('OrganizationUnitClientController', function ($scope, InstitutionService, ClientCacheService) {
    InstitutionService.getOrganizationUnitHeis(function(result) {
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
            if (hei.urls['url'] === $scope.organizationUnitRequest.url) {
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
            $scope.organizationUnitRequest.params = {'ounit_id': $scope.cachedOrgUnitIds};
        } else {
            var orgUnitIds = $scope.manuallyOrgUnitIds.split(',');
            $scope.organizationUnitRequest.params = {'ounit_id': orgUnitIds};
        }
        InstitutionService.getOrganizationUnits($scope.organizationUnitRequest, function(result) {
            $scope.organizationUnitResult = result;
        });
    };
    
    $scope.organizationUnitRequest = {method: "GET"};
});
