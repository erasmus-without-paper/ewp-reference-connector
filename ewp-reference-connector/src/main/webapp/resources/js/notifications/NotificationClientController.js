angular.module('notifications').controller('NotificationClientController', function ($scope, NotificationService) {
    $scope.changeTypes = ['IIA', 'OMOBILITY', 'IMOBILITY', 'TORS'];
    $scope.typeChanged = function() {
        var urlFkn = function(result) {
            $scope.heis = result;
            if ($scope.heis) {
                $scope.urls = [];
                angular.forEach($scope.heis, function(hei) {
                    if (hei.urls[$scope.urlKey] && $scope.urls.indexOf(hei.urls[$scope.urlKey]) === -1) {
                        $scope.urls.push(hei.urls[$scope.urlKey]);
                    }
                });

            }
        };
        
        switch ($scope.selectedType) {
            case 'IIA':
                NotificationService.getIiaCnrHeis(urlFkn);
                $scope.heiIdParamName = "notifier_hei_id";
                $scope.changedItemIdParamName = "iia_id";
                break;
            case 'OMOBILITY':
                NotificationService.getOmobilityCnrHeis(urlFkn);
                $scope.heiIdParamName = "sending_hei_id";
                $scope.changedItemIdParamName = "omobility_id";
                break;
            case 'IMOBILITY':
                NotificationService.getImobilityCnrHeis(urlFkn);
                $scope.heiIdParamName = "receiving_hei_id";
                $scope.changedItemIdParamName = "omobility_id";
                break;
            case 'TORS':
                NotificationService.getTorsCnrHeis(urlFkn);
                $scope.heiIdParamName = "receiving_hei_id";
                $scope.changedItemIdParamName = "omobility_id";
                break;
            default:
                break;
        }
        
    };
    $scope.urlChanged = function() {
        $scope.urlHeis = [{name:'<Missing Institution>', id:'missing'}];
        angular.forEach($scope.heis, function(hei) {
            if (hei.urls[$scope.urlKey] === $scope.request.url) {
                $scope.urlHeis.push(hei);
            }
        });
    };
    
    $scope.institutionChanged = function() {
        $scope.currentHei = '';
        angular.forEach($scope.heis, function(hei) {
            if (hei.id === $scope.request.heiId) {
                $scope.currentHei = hei;
            }
        });
    };
    
    $scope.sendNotificationRequest = function() {
        $scope.request.params = {};
        $scope.request.params[$scope.heiIdParamName] = [$scope.request.heiId];
        $scope.request.params[$scope.changedItemIdParamName] = $scope.notifyChangedItemIds.split(',');
        NotificationService.sendNotification($scope.request, function(result) {
            $scope.notificationResult = result;
            $scope.result = result;
        });
    };
    
    $scope.urlKey = 'url';
    $scope.request = {method: "POST"};
});
