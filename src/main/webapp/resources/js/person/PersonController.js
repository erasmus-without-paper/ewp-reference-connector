angular.module('person').controller('PersonController', function ($scope, PersonService) {
    $scope.getAllPersons = function(){
        PersonService.getAll(
            function(result) {
                $scope.persons = result;
        });
    };
    
    $scope.addPerson = function(){
        PersonService.addNew($scope.newPerson,
            function(result) {
                $scope.newPerson = {};
                $scope.showAddPersonForm = false;
                $scope.getAllPersons();
        });
    };
    $scope.cancelAddPerson = function(){
        $scope.newPerson = {};
        $scope.showAddPersonForm = false;
    };
    
    $scope.getAllPersons();
});