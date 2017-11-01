angular.module('mobilityParticipant').controller('MobilityParticipantController', function ($scope, MobilityParticipantService, PersonService) {

    $scope.getAllMobilityParticipants = function() {
       MobilityParticipantService.getAll(
            function(result) {
            $scope.mobilityParticipants = result;
        });
    };
    
    $scope.viewAddMobilityParticipantForm = function() {
        PersonService.getAll(
            function(result) {
            $scope.persons = result;
        });
        
        $scope.showAddMobilityParticipantForm = true;
    };

    $scope.addMobilityParticipant = function(){
        var currentPerson;
        angular.forEach($scope.persons, function(item) {
            if (item.personId === $scope.newMobilityParticipant.personId) {
                currentPerson = item;
            }
        });
        $scope.newMobilityParticipant.person = currentPerson;
        MobilityParticipantService.addNew($scope.newMobilityParticipant,
            function(result) {
                $scope.newMobilityParticipant = {};
                $scope.showAddMobilityParticipantForm = false;
                $scope.getAllMobilityParticipants();
        });
    };
    
    $scope.cancelAddMobilityParticipant = function(){
        $scope.newMobilityParticipant = {};
        $scope.showAddMobilityParticipantForm = false;
    };
    
    $scope.getAllMobilityParticipants();
});