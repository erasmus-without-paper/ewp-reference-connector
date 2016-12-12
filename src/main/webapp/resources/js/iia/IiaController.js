angular.module('iia').controller('IiaController', function ($scope, IiaService) {
    IiaService.getAll(
        function(result) {
            $scope.iiaList = result;
        });
    
    $scope.setSelectedIia = function(iia) {
        $scope.selectedIia = iia;
    };

    $scope.setSelectedCoopCond = function(coop) {
        $scope.selectedCoopCond = coop;
    };
    
    $scope.backIia = function() {
            $scope.selectedIia = '';
    };

    $scope.backCoopCond = function() {
            $scope.selectedCoopCond = '';
    };
});
