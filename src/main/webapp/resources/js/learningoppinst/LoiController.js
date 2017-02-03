angular.module('loi').controller('LoiController', function ($scope, LoiService, LosService, AcademicTermService) {
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
        LoiService.getGradingSchemes(
            function(result) {
                $scope.gradingSchemes = result;
        });
        
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
        $scope.selectedGradingSchemeId = '';
    };
    
    $scope.addLearningOppInstance = function(){
        var selectedAcademicTerm;
        angular.forEach($scope.academicTerms, function(item) {
            if (item.id === $scope.newLearningOppInstance.academicTermId) {
                selectedAcademicTerm = item;
            }
        });
        $scope.newLearningOppInstance.academicTerm = selectedAcademicTerm;
        
        var selectedGradingScheme;
        angular.forEach($scope.gradingSchemes, function(item) {
            if (item.id === $scope.selectedGradingSchemeId) {
                selectedGradingScheme = item;
            }
        });
        $scope.newLearningOppInstance.gradingScheme = selectedGradingScheme;
        
        $scope.currentLos.learningOpportunityInstances.push($scope.newLearningOppInstance);
        $scope.saveLearningOppSpec($scope.currentLos);
        
        $scope.showAddLearningOppInstanceForm = false;
        $scope.newLearningOppInstance = {credits: [{value: '',  scheme: '', level: ''}]};
        $scope.currentLos = '';
    };
    
    $scope.addResultDistributionCategory = function() {
        if (!$scope.newLearningOppInstance.resultDistribution) {
            $scope.newLearningOppInstance.resultDistribution = {};
        }
        if (!$scope.newLearningOppInstance.resultDistribution.resultDistributionCategory) {
            $scope.newLearningOppInstance.resultDistribution.resultDistributionCategory = [];
        }
        $scope.newLearningOppInstance.resultDistribution.resultDistributionCategory.push({label:'', count:0});
    };

    $scope.addResultDistributionDescription = function() {
        if (!$scope.newLearningOppInstance.resultDistribution) {
            $scope.newLearningOppInstance.resultDistribution = {};
        }
        if (!$scope.newLearningOppInstance.resultDistribution.description) {
            $scope.newLearningOppInstance.resultDistribution.description = [];
        }
        $scope.newLearningOppInstance.resultDistribution.description.push({text:'', lang:''});
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
