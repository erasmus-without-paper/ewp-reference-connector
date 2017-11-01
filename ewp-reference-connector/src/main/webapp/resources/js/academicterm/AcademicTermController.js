angular.module('academicterm').controller('AcademicTermController', function ($scope, AcademicTermService, InstitutionService) {
    $scope.getAllAcademicTerms = function(){
        AcademicTermService.getAll(
            function(result) {
                $scope.academicTerms = result;
        });
    };
    
    $scope.addAcademicTerm = function(){
        var selectedAcademicYear;
        angular.forEach($scope.academicYears, function(item) {
            if (item.id === $scope.newAcademicTerm.academicYearId) {
                selectedAcademicYear = item;
            }
        });
        $scope.newAcademicTerm.academicYear = selectedAcademicYear;
        var academicYearText = $scope.newAcademicTerm_selectedTerm === 0 ? selectedAcademicYear.startYear : selectedAcademicYear.endYear;
        var enAcademicTermText = $scope.terms[$scope.newAcademicTerm_selectedTerm].en + ' ' + academicYearText;
        var seAcademicTermText = $scope.terms[$scope.newAcademicTerm_selectedTerm].se + ' ' + academicYearText;
        $scope.newAcademicTerm.dispName = [{text:enAcademicTermText,'lang':'en'}, {text:seAcademicTermText,'lang':'se'}];
        $scope.saveAcademicTerm($scope.newAcademicTerm);
        
        $scope.cancelAddAcademicTerm();
    };
    
    $scope.cancelAddAcademicTerm = function(){
        $scope.newAcademicTerm = {};
        $scope.newAcademicTerm_selectedTerm = 0;
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
    
    $scope.terms = [{label:'Fall', en:'Fall semester', se:'Hösttermin'}, {label:'Spring', en:'Spring semester', se:'Vårtermin'}];
    $scope.cancelAddAcademicTerm();
    $scope.getAllAcademicTerms();
});
