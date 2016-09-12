angular.module('iia').controller('IiaController', function ($scope, IiaService) {
    IiaService.getAll(
        function(result) {
            $scope.iias = result;
        });
});
