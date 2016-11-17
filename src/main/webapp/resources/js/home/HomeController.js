angular.module('home').controller('HomeController', function ($scope, HomeService) {
    HomeService.name(
        function(result) {
            $scope.myName = result;
        });
});