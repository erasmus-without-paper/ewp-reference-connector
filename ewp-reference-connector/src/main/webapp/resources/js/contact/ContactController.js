angular.module('contact').controller('ContactController', function ($scope, ContactService, PersonService, InstitutionService) {

    $scope.getAllContacts = function() {
       ContactService.getAll(
            function(result) {
            $scope.contacts = result;
        });
    };
    
    $scope.viewAddContactForm = function() {
        InstitutionService.getLocal(
            function(result) {
            $scope.institutions = result;
        });

        PersonService.getAll(
            function(result) {
            $scope.persons = result;
        });
        
        ContactService.getContactRoles(function(result) {
            $scope.roles = result;
        });
        $scope.organizations = [];
        $scope.showAddContactForm = true;
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
            if (item.institutionId === $scope.newContact.institutionId) {
                currentInst = item;
            }
        });
        
        $scope.organizations = [];
        $scope.addOrganizationUnitsToList(currentInst);
    };
    
    $scope.addContact  = function(){
        var currentPerson;
        angular.forEach($scope.persons, function(item) {
            if (item.personId === $scope.newContact.personId) {
                currentPerson = item;
            }
        });
        $scope.newContact.person = currentPerson;
        ContactService.addNew($scope.newContact,
            function(result) {
                $scope.newContact = {};
                $scope.showAddContactForm = false;
                $scope.getAllContacts();
        });
    };
    
    $scope.cancelAddContact = function(){
        $scope.newContact = {};
        $scope.showAddContactForm = false;
    };
    
    $scope.getAllContacts();
});