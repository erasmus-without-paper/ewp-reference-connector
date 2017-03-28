angular.module('iia').controller('IiaClientController', function ($scope, IiaService, ClientCacheService) {
    IiaService.getIiaHeis(function(result) {
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
        $scope.heiIiaIds = ['<Missing IIA Id>'];
        
        $scope.currentHei = '';
        angular.forEach($scope.heis, function(hei) {
            if (hei.id === $scope.request.heiId) {
                $scope.currentHei = hei;
            }
        });
        
        var iiaIds = ClientCacheService.get('Iia', $scope.request.heiId);
        angular.forEach(iiaIds, function(iiaId) {
            $scope.heiIiaIds.push(iiaId);
        });
    };
    
    $scope.sendIiaRequest = function() {
        if ($scope.clientView === 'index') {
            IiaService.getIiaIndex($scope.request, function(result) {
                $scope.iiaIndexResult = result;
                $scope.result = result;
                if (result && result.result && result.result.iiaId) {
                    ClientCacheService.add('Iia', $scope.request.heiId, result.result.iiaId);
                }
            });
        } else {
            if ($scope.cachedIds) {
                $scope.request.params = {'iia_id': $scope.cachedIiaIds};
            } else {
                var iiaIds = $scope.manuallyIiaIds.split(',');
                $scope.request.params = {'iia_id': iiaIds};
            }
            IiaService.getIia($scope.request, function(result) {
                $scope.iiaResult = result;
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
