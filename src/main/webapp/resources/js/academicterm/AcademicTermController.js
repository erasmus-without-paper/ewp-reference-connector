angular.module('academicterm').controller('AcademicTermController', function ($scope, AcademicTermService, InstitutionService) {
    $scope.getAllAcademicTerms = function(){
        AcademicTermService.getAll(
            function(result) {
                $scope.academicTerms = result;
        });
    };
    
    $scope.addAcademicTerm = function(){
        var selectedAcademicYear;
        var selectedAcademicYearId = Number($scope.newAcademicTerm.academicYearId);
        angular.forEach($scope.academicYears, function(item) {
            if (item.id === selectedAcademicYearId) {
                selectedAcademicYear = item;
            }
        });
        $scope.newAcademicTerm.academicYear = selectedAcademicYear;
        $scope.newAcademicTerm.dispName = [{text:$scope.newAcademicTerm.dispNameStr,'lang':'en'}];
        $scope.saveAcademicTerm($scope.newAcademicTerm);
        
        $scope.showAddAcademicTermForm = false;
        $scope.newAcademicTerm = {};
    };
    
    $scope.cancelAddAcademicTerm = function(){
        $scope.newAcademicTerm = {};
        $scope.showAddAcademicTermForm = false;
    };
    
    $scope.saveAcademicTerm = function(academicterm) {
        AcademicTermService.addNew(academicterm, function(result) {
            $scope.getAllAcademicTerms();
        });
    };
    
    $scope.addOrganizationUnitsToList = function(obj) {
        angular.forEach(obj.organizationUnits, function(item) {
            $scope.organizations.push(item);
            $scope.addOrganizationUnitsToList(item);
        });
    };
    
    $scope.viewAddAcademicTermForm = function() {
        InstitutionService.getLocal(
            function(result) {
            $scope.institutions = result;
        });
        
        AcademicTermService.getAcademicYears(
            function(result) {
            $scope.academicYears = result;
        });

        $scope.organizations = [];
        $scope.showAddAcademicTermForm = true;
    };
    
    $scope.institutionChanged = function() {
        var currentInst;
        angular.forEach($scope.institutions, function(item) {
            if (item.institutionId === $scope.newAcademicTerm.institutionId) {
                currentInst = item;
            }
        });
        
        $scope.organizations = [];
        $scope.addOrganizationUnitsToList(currentInst);
    };
    
    $scope.getAllAcademicTerms();
});
