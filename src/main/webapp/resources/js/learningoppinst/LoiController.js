angular.module('loi').controller('LoiController', function ($scope, LoiService, InstitutionService, LosService, AcademicTermService) {
    $scope.getAllLearningOppInstances = function(){
        LoiService.getAll(
            function(result) {
                $scope.loiList = result;
        });
    };
    
    $scope.viewAddLearningOppInstanceForm = function() {
        InstitutionService.getLocal(
            function(result) {
            $scope.institutions = result;
        });
        
        AcademicTermService.getAll(
            function(result) {
                $scope.academicTerms = result;
        });
        
        $scope.organizations = [];
        $scope.showAddLearningOppInstanceForm = true;
    };
    
    $scope.institutionChanged = function(){
        var currentInst;
        angular.forEach($scope.institutions, function(item) {
            if (item.institutionId === $scope.newLearningOppInstance.institutionId) {
                currentInst = item;
            }
        });
        
        $scope.organizations = [];
        $scope.addOrganizationUnitsToList(currentInst);
        
        LosService.getByInstitutionId($scope.newLearningOppInstance.institutionId,
            function(result) {
                $scope.losList = result;
        });
    };
    
    $scope.addOrganizationUnitsToList = function(obj) {
        angular.forEach(obj.organizationUnits, function(item) {
            $scope.organizations.push(item);
            $scope.addOrganizationUnitsToList(item);
        });
    };

    $scope.addLearningOppInstance = function(){
        var currentLearningOppSpec;
        var selectedLosId = Number($scope.newLearningOppInstance.learningOppSpecId);
        angular.forEach($scope.losList, function(item) {
            if (item.id === selectedLosId) {
                currentLearningOppSpec = item;
            }
        });
        $scope.newLearningOppInstance.learningOppSpec = currentLearningOppSpec;
        
        var currentAcademicTerm;
        var selectedAcademicTermId = Number($scope.newLearningOppInstance.academicTermId);
        angular.forEach($scope.academicTerms, function(item) {
            if (item.id === selectedAcademicTermId) {
                currentAcademicTerm = item;
            }
        });
        $scope.newLearningOppInstance.academicTerm = currentAcademicTerm;
        
        $scope.saveLearningOppInstance($scope.newLearningOppInstance);
        $scope.showAddLearningOppInstanceForm = false;
        $scope.newLearningOppInstance = {};
    };

    $scope.saveLearningOppInstance = function(learningOppInstance) {
        LoiService.addNew(learningOppInstance, function(result) {
            $scope.getAllLearningOppInstances();
        });
    };
    
    $scope.cancelAddLearningOppInstance = function(){
        $scope.newLearningOppInstance = {};
        $scope.showAddLearningOppInstanceForm = false;
    };
    
    $scope.getAllLearningOppInstances();
});
