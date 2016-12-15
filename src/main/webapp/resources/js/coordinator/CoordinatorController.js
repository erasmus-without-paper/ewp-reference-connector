angular.module('coordinator').controller('CoordinatorController', function ($scope, CoordinatorService, PersonService, InstitutionService) {

    $scope.getAllCoordinators = function() {
        CoordinatorService.getAll(
            function(result) {
            $scope.coordinators = result;
        });
    };
    
    $scope.viewAddCoordinatorForm = function() {
        InstitutionService.getLocal(
            function(result) {
            $scope.institutions = result;
        });

        PersonService.getAll(
            function(result) {
            $scope.persons = result;
        });
        
        $scope.roles = ['Admission', 'Course', 'Housing', 'Insurance', 'Visas'];
        $scope.organizations = [];
        $scope.showAddCoordinatorForm = true;
    };

    $scope.addOrganizationUnitsToList = function(obj) {
        angular.forEach(obj.organizationUnits, function(item) {
            $scope.organizations.push(item);
            $scope.addOrganizationUnitsToList(item);
        });
    };
    
    $scope.institutionChanged = function() {
        var currentInst;
        angular.forEach($scope.institutions, function(item) {
            if (item.institutionId === $scope.newCoordinator.institutionId) {
                currentInst = item;
            }
        });
        
        $scope.organizations = [];
        $scope.addOrganizationUnitsToList(currentInst);
    };
    
    $scope.addCoordinator  = function(){
        var currentPerson;
        angular.forEach($scope.persons, function(item) {
            if (item.personId === $scope.newCoordinator.personId) {
                currentPerson = item;
            }
        });
        $scope.newCoordinator.person = currentPerson;
        CoordinatorService.addNew($scope.newCoordinator,
            function(result) {
                $scope.newCoordinator = {};
                $scope.showAddCoordinatorForm = false;
                $scope.getAllCoordinators();
        });
    };
    
    $scope.cancelAddCoordinator = function(){
        $scope.newCoordinator = {};
        $scope.showAddCoordinatorForm = false;
    };
    
    $scope.getAllCoordinators();
});