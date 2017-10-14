'use strict';

describe('Echo Tests', function () {
 
    beforeEach(module('echo'));

    describe('EchoController', function () {
        var $controller, $scope, $httpBackend;

        beforeEach(inject(function (_$httpBackend_, _$controller_, $rootScope) {
            $httpBackend = _$httpBackend_;
            $controller = _$controller_;
            
            $httpBackend.expectGET('gui/echo').respond(['rest/echo']);
            $httpBackend.expectPOST('gui/echo', '{"method":"GET","httpsec":true,"params":{"echo":["test1","test2"]},"heiId":"id1"}').respond('echo result');
            
            $scope = $rootScope.$new();
            var ctrl = $controller('EchoController', {$scope: $scope});
        }));

        it('should add echo text to list', function () {
            $scope.echoItem = 'test';
            $scope.addEchoItem();
            expect($scope.echoRequest.params.echo).not.toBeUndefined();
            expect($scope.echoRequest.params.echo.length).toBe(2);
        });

        it('should send echo request', function () {
            $scope.echoRequest.heiId = 'id1';
            $scope.echoRequest.params = {echo:['test1', 'test2']};
            $scope.sendEcho();
            $httpBackend.flush();
            
            expect($scope.echoResult).not.toBeUndefined();
            expect($scope.echoResult).toBe('echo result');
        });
    });

});
