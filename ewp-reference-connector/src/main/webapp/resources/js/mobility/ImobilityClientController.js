angular.module('mobility').controller('ImobilityClientController', function ($scope, MobilityService, ClientCacheService) {
    MobilityService.getImobilityHeis(function(result) {
        $scope.heis = result;
        if ($scope.heis) {
            $scope.indexUrls = [];
            $scope.getUrls = [];
            angular.forEach($scope.heis, function(hei) {
                if ($scope.indexUrls.indexOf(hei.urls['index-url']) === -1) {
                    $scope.indexUrls.push(hei.urls['index-url']);
                }
                if ($scope.getUrls.indexOf(hei.urls['get-url']) === -1) {
                    $scope.getUrls.push(hei.urls['get-url']);
                }
            });
            
            $scope.urls = $scope.indexUrls;
        }
    });
    $scope.urlChanged = function() {
        $scope.urlHeis = [{name:'<Missing Institution>', id:'missing'}];
        angular.forEach($scope.heis, function(hei) {
            if (hei.urls[$scope.urlKey] === $scope.request.url) {
                $scope.urlHeis.push(hei);
            }
        });
    };
    
    $scope.institutionChanged = function() {
        $scope.heiImobilityIds = ['<Missing imobility Id>'];
        
        $scope.currentHei = '';
        angular.forEach($scope.heis, function(hei) {
            if (hei.id === $scope.request.heiId) {
                $scope.currentHei = hei;
            }
        });
        
        var imobilityIds = ClientCacheService.get('Imobility', $scope.request.heiId);
        angular.forEach(imobilityIds, function(imobilityId) {
            $scope.heiImobilityIds.push(imobilityId);
        });
    };
    
    $scope.sendImobilityRequest = function() {
        if ($scope.clientView === 'index') {
            // TODO: 
            alert("Index is not possible");
            return;
        } else {
            $scope.request.params = {'receiving_hei_id': [$scope.request.heiId]};
            if ($scope.cachedIds) {
                $scope.request.params['omobility_id'] = $scope.cachedOmobilityIds;
            } else {
                var omobilityIds = $scope.manuallyOmobilityIds.split(',');
                $scope.request.params['omobility_id'] = omobilityIds;
            }
            MobilityService.getImobility($scope.request, function(result) {
                $scope.imobilityResult = result;
                $scope.result = result;
            });
        }
    };
    
    $scope.setClientView = function(viewName) {
        $scope.clientView = viewName;
        if (viewName === 'index') {
            $scope.urls = $scope.indexUrls;
            $scope.urlKey = 'index-url';
        } else {
            $scope.urls = $scope.getUrls;
            $scope.urlKey = 'get-url';
            $scope.institutionChanged();
        }
        if ($scope.currentHei) {
            $scope.request.url = $scope.currentHei.urls[$scope.urlKey];
        }
    };
    
    $scope.clientView = 'index';
    $scope.urlKey = 'index-url';
    $scope.request = {method: "GET"};
});
