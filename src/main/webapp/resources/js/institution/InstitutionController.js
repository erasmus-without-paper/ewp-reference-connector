angular.module('institution').controller('InstitutionController', function ($scope, InstitutionService) {
    $scope.getAllInstitutions = function() {
        InstitutionService.getLocal(
            function(result) {
                $scope.institutions = result;
        });
    };
        
    $scope.browseInstOrgObjectList = [];
    $scope.setCurrentInstOrgObject = function(obj) {
        $scope.currentInstOrgObject = obj;
        $scope.browseInstOrgObjectList.push(obj);
        $scope.cancelAddForm();
    };
    
    $scope.backCurrentInstOrgObject = function() {
        $scope.browseInstOrgObjectList.pop();
        if($scope.browseInstOrgObjectList.length > 0) {
            $scope.currentInstOrgObject = $scope.browseInstOrgObjectList[$scope.browseInstOrgObjectList.length-1];
        } else {
            $scope.currentInstOrgObject = '';
        }
        $scope.cancelAddForm();
    };
    $scope.viewAddForm = function() {
        if ($scope.currentInstOrgObject) {
            $scope.showAddInstitutionForm = false;
            $scope.showAddOrganizationUnitForm = true;
        } else {
            $scope.showAddInstitutionForm = true;
            $scope.showAddOrganizationUnitForm = false;
        }
    };
    
    $scope.addOtherIdForInstitution = function() {
        if (!$scope.newInstitution.otherId) {
            $scope.newInstitution.otherId = [];
        }
        $scope.newInstitution.otherId.push({idType: '',  idValue: ''});
    };

    $scope.addOtherIdForOrgUnit = function() {
        if (!$scope.newOrganizationUnit.otherId) {
            $scope.newOrganizationUnit.otherId = [];
        }
        $scope.newOrganizationUnit.otherId.push({idType: '',  idValue: ''});
    };
    
    $scope.addInstitution = function() {
        $scope.newInstitution.name = [{text:$scope.newInstitution.nameStr,'lang':'en'}];
        $scope.saveInstitution($scope.newInstitution);
        
        $scope.showAddInstitutionForm = false;
        $scope.newInstitution = {};
    };
    
    $scope.addOrganizationUnit = function() {
        $scope.newOrganizationUnit.name = [{text:$scope.newOrganizationUnit.nameStr,'lang':'en'}];
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
            $scope.getAllInstitutions();
        });
    };
    
    $scope.cancelAddForm = function() {
        $scope.showAddInstitutionForm = false;
        $scope.showAddOrganizationUnitForm = false;
        $scope.newInstitution = {};
        $scope.newOrganizationUnit = {};
    };
    
    $scope.newInstitution = {};
    $scope.getAllInstitutions();
});
