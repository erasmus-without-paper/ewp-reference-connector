angular.module('person').controller('PersonController', function ($scope, PersonService) {
    $scope.getAllPersons = function(){
        PersonService.getAll(
            function(result) {
                $scope.persons = result;
        });
    };
    
    $scope.showAddForm = function(){
        $scope.showAddPersonForm = true;
        PersonService.getGenderNames(
            function(result) {
                $scope.genders = result;
                if ($scope.genders && $scope.genders.length > 0) {
                    $scope.newPerson.gender = $scope.genders[0];
                }
        });
    };
    
    $scope.addPerson = function(){
        if($scope.newPerson.countryCode){
            $scope.newPerson.countryCode = $scope.newPerson.countryCode.toUpperCase();
        }
        PersonService.addNew($scope.newPerson,
            function(result) {
                $scope.cancelAddPerson();
                $scope.getAllPersons();
        });
    };
    $scope.cancelAddPerson = function(){
        $scope.newPerson = {};
        $scope.showAddPersonForm = false;
    };
    
    $scope.getAllPersons();
    $scope.cancelAddPerson();
});