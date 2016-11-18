angular.module('home').controller('HomeController', function ($scope, HomeService) {
    HomeService.name(
        function(result) {
            $scope.universityName = result;
        });

    HomeService.hostname(
        function(result) {
            $scope.hostname = result;
        });
});