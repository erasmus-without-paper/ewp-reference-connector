angular.module('echo').controller('EchoController', function ($scope, EchoService) {
    $scope.echoRequest = {};
    $scope.echoRequest.method = 'GET';
    $scope.echoRequest.httpsec = true;
    $scope.echoRequest.params = {echo:['']};
    
    $scope.addEchoItem = function() {
        $scope.echoRequest.params.echo.push('');
    };
    
    $scope.sendEcho = function() {
        $scope.echoResult = '';
        EchoService.echo($scope.echoRequest,
            function(result) {
                $scope.echoResult = result;
            });
    };
    
    EchoService.echoHeis(
        function(result) {
            $scope.echoHeis = result;
        });
        
});