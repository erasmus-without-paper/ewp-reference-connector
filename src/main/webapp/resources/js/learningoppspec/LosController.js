angular.module('los').controller('LosController', function ($scope, LosService, InstitutionService) {
    $scope.getAllTopLevelLosParents = function(){
        LosService.getAllTopLevelParents(
            function(result) {
                $scope.losList = result;
        });
    };
    
    $scope.viewAddLearningOppSpecRootForm = function() {
        InstitutionService.getLocal(
            function(result) {
            $scope.institutions = result;
        });
        
        $scope.lostypes = ['Degree Programme', 'Module', 'Course'];
        
        $scope.showAddLearningOppSpecForm = true;
        $scope.currentLos = undefined;
    };

    $scope.viewAddLearningOppSpecForm = function(los) {
        if (los.type === 'Degree Programme') {
            $scope.lostypes = ['Module', 'Course'];
        } else if (los.type === 'Module') {
            $scope.lostypes = ['Course'];
        } else if (los.type === 'Course') {
            $scope.lostypes = ['Class'];
        }
        
        $scope.institutions = [];
        $scope.showAddLearningOppSpecForm = true;
        $scope.currentLos = los;
    };
    
    $scope.addLearningOppSpec = function(){
        $scope.newLearningOppSpec.name = [{text:$scope.newLearningOppSpec.nameStr,'lang':'en'}];
        $scope.newLearningOppSpec.url = [{text:$scope.newLearningOppSpec.urlStr,'lang':'en'}];
        
        if ($scope.currentLos) {
            $scope.newLearningOppSpec.institutionId = $scope.currentLos.institutionId;
            $scope.currentLos.learningOpportunitySpecifications.push($scope.newLearningOppSpec);
            $scope.saveLearningOppSpec($scope.currentLos);
        } else {
            $scope.newLearningOppSpec.topLevelParent = true;
            $scope.saveLearningOppSpec($scope.newLearningOppSpec);
        }
        
        $scope.showAddLearningOppSpecForm = false;
        $scope.newLearningOppSpec = {};
        $scope.currentLos = '';
    };
    
    $scope.saveLearningOppSpec = function(learningOppSpec) {
        LosService.save(learningOppSpec, function(result) {
            $scope.getAllTopLevelLosParents();
        });
    };
    
    $scope.cancelAddLearningOppSpec = function(){
        $scope.newLearningOppSpec = {};
        $scope.showAddLearningOppSpecForm = false;
    };
    
    
    
    
    $scope.showLos = function(los) {
        $scope.currentLos=los;
    };
    $scope.toggleLosMenuItem = function(los) {
        event.preventDefault();
        event.stopPropagation();
        los.expanded = !los.expanded;
    };
    
    $scope.getAllTopLevelLosParents();
});
