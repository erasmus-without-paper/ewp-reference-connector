angular.module('los').controller('CoursesClientController', function ($scope, LosService, ClientCacheService) {
    LosService.getCourseHeis(function(result) {
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
            if (hei.url === $scope.coursesRequest.url) {
                $scope.urlHeis.push(hei);
            }
        });
    };
    
    $scope.institutionChanged = function() {
        $scope.heiLosIds = ['<Missing Los Id>'];
        var losIds = ClientCacheService.get('CourseReplication', $scope.coursesRequest.heiId);
        angular.forEach(losIds, function(ounitId) {
            $scope.heiLosIds.push(ounitId);
        });
    };
    
    $scope.sendCoursesRequest = function() {
        if ($scope.cachedIds) {
            $scope.coursesRequest.losIds = $scope.cachedLosIds;
        } else {
            var losIds = $scope.manuallyLosIds.split(',');
            $scope.coursesRequest.losIds = losIds;
        }
        LosService.getCourse($scope.coursesRequest, function(result) {
            $scope.losResult = result;
        });
    };
    
    $scope.coursesRequest = {method: "GET"};
});
