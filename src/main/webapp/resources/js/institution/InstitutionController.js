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

    $scope.addNameForInstitution = function() {
        $scope.newInstitution.name.push({text: '',  lang: ''});
    };

    $scope.addNameForOrganizationUnit = function() {
        $scope.newOrganizationUnit.name.push({text: '',  lang: ''});
    };
    
    $scope.addInstitution = function() {
        $scope.newInstitution.streetAddress.addressLine = [$scope.newInstitution.streetAddressLine];
        $scope.saveInstitution($scope.newInstitution);
        
        $scope.showAddInstitutionForm = false;
        $scope.cancelAddForm();
    };
    
    $scope.addOrganizationUnit = function() {
        if (!$scope.currentInstOrgObject.organizationUnits) {
            $scope.currentInstOrgObject.organizationUnits = [];
        }
        $scope.newOrganizationUnit.streetAddress.addressLine = [$scope.newOrganizationUnit.streetAddressLine];
        $scope.currentInstOrgObject.organizationUnits.push($scope.newOrganizationUnit);
        $scope.saveInstitution($scope.browseInstOrgObjectList[0]);
        
        $scope.showAddOrganizationUnitForm = false;
        $scope.cancelAddForm();
    };
    
    $scope.saveInstitution = function(institution) {
        InstitutionService.save(institution, function(result) {
            $scope.getAllInstitutions();
        });
    };
    
    $scope.cancelAddForm = function() {
        $scope.showAddInstitutionForm = false;
        $scope.showAddOrganizationUnitForm = false;
        $scope.newInstitution = {name:[{text:'', lang: ''}]};
        $scope.newOrganizationUnit = {};
        $scope.newOrganizationUnit = {name:[{text:'', lang: ''}]};
    };
    
    $scope.cancelAddForm();
    $scope.getAllInstitutions();
});
