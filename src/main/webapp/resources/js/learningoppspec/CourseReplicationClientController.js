angular.module('los').controller('CourseReplicationClientController', function ($scope, LosService, ClientCacheService) {
    LosService.getCourseReplicationHeis(function(result) {
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
            if (hei.urls['url'] === $scope.courseReplicationRequest.url) {
                $scope.urlHeis.push(hei);
            }
        });
    };
    $scope.sendCourseReplicationRequest = function() {
        LosService.getCourseReplication($scope.courseReplicationRequest, function(result) {
            $scope.losResult = result;
            if (result && result.result && result.result.losId) {
                ClientCacheService.add('CourseReplication', $scope.courseReplicationRequest.heiId, result.result.losId);
            }
        });
    };
    
    $scope.courseReplicationRequest = {method: "GET"};
});
