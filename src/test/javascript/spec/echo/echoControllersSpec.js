'use strict';

describe('Echo Tests', function () {
 
    beforeEach(module('echo'));

    describe('EchoController', function () {
        var $controller, $scope, $httpBackend;

        beforeEach(inject(function (_$httpBackend_, _$controller_, $rootScope) {
            $httpBackend = _$httpBackend_;
            $controller = _$controller_;
            
            $httpBackend.expectGET('rest/echo?echo=test1&echo=test2').respond('echo result');
            
            $scope = $rootScope.$new();
            var ctrl = $controller('EchoController', {$scope: $scope});
        }));

        it('should add echo text to list', function () {
            $scope.echoItem = 'test';
            $scope.addEchoToList();
            expect($scope.echoList).not.toBeUndefined();
            expect($scope.echoList[0]).not.toBeUndefined();
            expect($scope.echoList[0]).toBe('test');
        });

        it('should send echo request', function () {
            $scope.echoList = ['test1', 'test2'];
            $scope.sendEcho();
            $httpBackend.flush();
            
            expect($scope.echoResult).not.toBeUndefined();
            expect($scope.echoResult).toBe('echo result');
        });
    });

});
