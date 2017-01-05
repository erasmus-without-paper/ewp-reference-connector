angular.module('loi').controller('LoiController', function ($scope, LosService, AcademicTermService) {
    $scope.getAllTopLevelLosParents = function(){
        LosService.getAllTopLevelParents(
            function(result) {
                $scope.losList = result;
        });
    };
    
    $scope.showLos = function(los) {
        $scope.currentLos=los;
    };

    $scope.toggleLosMenuItem = function(los) {
        event.preventDefault();
        event.stopPropagation();
        los.expanded = !los.expanded;
    };
    
    $scope.viewAddLearningOppInstanceForm = function() {
        $scope.creditLevels = ['Bachelor', 'Master', 'PhD'];
        AcademicTermService.getAll(
            function(result) {
                $scope.academicTerms = result;
        });
        
        $scope.organizations = [];
        $scope.showAddLearningOppInstanceForm = true;
    };
    
    $scope.cancelAddLearningOppInstance = function(){
        $scope.newLearningOppInstance = {credits: [{value: '',  scheme: '', level: ''}]};
        $scope.showAddLearningOppInstanceForm = false;
    };
    
    $scope.addLearningOppInstance = function(){
        var selectedAcademicTerm;
        var selectedAcademicTermId = Number($scope.newLearningOppInstance.academicTermId);
        angular.forEach($scope.academicTerms, function(item) {
            if (item.id === selectedAcademicTermId) {
                selectedAcademicTerm = item;
            }
        });
        
        $scope.newLearningOppInstance.academicTerm = selectedAcademicTerm;
        $scope.currentLos.learningOpportunityInstances.push($scope.newLearningOppInstance);
        $scope.saveLearningOppSpec($scope.currentLos);
        
        $scope.showAddLearningOppInstanceForm = false;
        $scope.newLearningOppInstance = {credits: [{value: '',  scheme: '', level: ''}]};
        $scope.currentLos = '';
    };
    
    $scope.saveLearningOppSpec = function(learningOppSpec) {
        LosService.save(learningOppSpec, function(result) {
            $scope.getAllTopLevelLosParents();
        });
    };
    
    $scope.addCredit = function() {
        $scope.newLearningOppInstance.credits.push({value: '',  scheme: '', level: ''});
    };
    
    $scope.getAllTopLevelLosParents();
    $scope.cancelAddLearningOppInstance();
});
