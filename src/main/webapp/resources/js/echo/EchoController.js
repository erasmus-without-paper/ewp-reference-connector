angular.module('echo').controller('EchoController', function ($scope, EchoService) {
    $scope.echoList = [];
    $scope.addEchoToList = function() {
        if ($scope.echoItem) {
            $scope.echoList.push($scope.echoItem);
            $scope.echoItem = '';
        }
    };
    $scope.sendEcho = function() {
        EchoService.echo($scope.echoList,
            function(result) {
                $scope.echoResult = result;
            });
    };
});