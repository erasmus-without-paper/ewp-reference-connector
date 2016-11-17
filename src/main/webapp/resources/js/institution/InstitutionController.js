angular.module('institution').controller('InstitutionController', function ($scope, InstitutionService) {
    $scope.getAllPersons = function() {
        InstitutionService.getLocal(
            function(result) {
                $scope.institutions = result;
        });
    };
        
    $scope.browseInstOrgObjectList = [];
    $scope.setCurrentInstOrgObject = function(obj) {
        $scope.currentInstOrgObject = obj;
        $scope.browseInstOrgObjectList.push(obj);
    };
    
    $scope.backCurrentInstOrgObject = function() {
        $scope.browseInstOrgObjectList.pop();
        if($scope.browseInstOrgObjectList.length > 0) {
            $scope.currentInstOrgObject = $scope.browseInstOrgObjectList[$scope.browseInstOrgObjectList.length-1];
        } else {
            $scope.currentInstOrgObject = '';
        }
    };
    $scope.viewAddForm = function() {
        if ($scope.currentInstOrgObject) {
            $scope.showAddInstitiutionForm = false;
            $scope.showAddOrganizationUnitForm = true;
        } else {
            $scope.showAddInstitiutionForm = true;
            $scope.showAddOrganizationUnitForm = false;
        }
    };
    $scope.addInstitution = function() {
        $scope.newInstitution.name = [{text:$scope.newInstitution.nameStr,'lang':'en'}];
        $scope.newInstitution.description = [{text:$scope.newInstitution.descriptionStr,'lang':'en'}];
        $scope.saveInstitution($scope.newInstitution);
        
        $scope.showAddInstitiutionForm = false;
        $scope.newInstitution = {};
    };
    
    $scope.addOrganizationUnit = function() {
        $scope.newOrganizationUnit.name = [{text:$scope.newOrganizationUnit.nameStr,'lang':'en'}];
        $scope.newOrganizationUnit.description = [{text:$scope.newOrganizationUnit.descriptionStr,'lang':'en'}];
        if (!$scope.currentInstOrgObject.organizationUnits) {
            $scope.currentInstOrgObject.organizationUnits = [];
        }
        $scope.currentInstOrgObject.organizationUnits.push($scope.newOrganizationUnit);
        $scope.saveInstitution($scope.browseInstOrgObjectList[0]);
        
        $scope.showAddOrganizationUnitForm = false;
        $scope.newOrganizationUnit = {};
    };
    
    $scope.saveInstitution = function(institution) {
        InstitutionService.save(institution, function(result) {
            $scope.getAllPersons();
        });
    };
    
    $scope.cancelAddForm = function() {
        $scope.showAddInstitiutionForm = false;
        $scope.showAddOrganizationUnitForm = false;
        $scope.newInstitution = {};
        $scope.newOrganizationUnit = {};
    };
    
    $scope.getAllPersons();
});
