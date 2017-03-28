angular.module('los').controller('CoursesClientController', function ($scope, LosService, ClientCacheService) {
    LosService.getCourseHeis(function(result) {
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
            if (hei.urls['url'] === $scope.coursesRequest.url) {
                $scope.urlHeis.push(hei);
            }
        });
    };
    
    $scope.institutionChanged = function() {
        $scope.heiLosIds = ['<Missing Los Id>'];
        var losIds = ClientCacheService.get('CourseReplication', $scope.coursesRequest.heiId);
        angular.forEach(losIds, function(losId) {
            $scope.heiLosIds.push(losId);
        });
    };
    
    $scope.sendCoursesRequest = function() {
        if ($scope.cachedIds) {
            $scope.coursesRequest.params = {'los_id': $scope.cachedLosIds};
        } else {
            var losIds = $scope.manuallyLosIds.split(',');
            $scope.coursesRequest.params = {'los_id': losIds};
        }
        LosService.getCourse($scope.coursesRequest, function(result) {
            $scope.losResult = result;
        });
    };
    
    $scope.coursesRequest = {method: "GET"};
});
