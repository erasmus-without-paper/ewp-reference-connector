angular.module('institution').controller('InstitutionController', function ($scope, InstitutionService) {
    $scope.getAllInstitutions = function() {
        InstitutionService.getLocal(
            function(result) {
                $scope.institutions = result;
                var oldBrowseList = $scope.browseInstOrgObjectList;
                $scope.browseInstOrgObjectList = [];
                $scope.rebuildBrowseList(result, oldBrowseList);
        });
    };
    
    $scope.rebuildBrowseList = function(itemList, oldBrowseList) {
        var oldItem = oldBrowseList.shift();
        var newItem;
        if (oldItem) {
            angular.forEach(itemList, function(item) {
                if (item.id === oldItem.id) {
                    newItem = item;
                }
            });
        }
        
        if (newItem) {
            $scope.browseInstOrgObjectList.push(newItem);
            $scope.rebuildBrowseList(newItem.organizationUnits, oldBrowseList);
        } else {
            $scope.currentInstOrgObject = $scope.browseInstOrgObjectList[$scope.browseInstOrgObjectList.length - 1];
        }
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
        $scope.viewStreetAdressForm = false;
        $scope.viewMailingAdressForm = false;
        $scope.viewPhoneNumberForm = false;
        $scope.viewFaxNumberForm = false;
    };
    
    $scope.addInstitution = function() {
        $scope.saveInstitution($scope.newInstitution);
        
        $scope.showAddInstitutionForm = false;
        $scope.cancelAddForm();
    };
    
    $scope.addOrganizationUnit = function() {
        if (!$scope.currentInstOrgObject.organizationUnits) {
            $scope.currentInstOrgObject.organizationUnits = [];
        }
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
