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
        
        $scope.lostypes = ['DEGREE_PROGRAMME', 'MODULE', 'COURSE'];
        
        $scope.showAddLearningOppSpecForm = true;
        $scope.currentLos = undefined;
    };

    $scope.viewAddLearningOppSpecForm = function(los) {
        if (los.type === 'DEGREE_PROGRAMME') {
            $scope.lostypes = ['MODULE', 'COURSE'];
        } else if (los.type === 'MODULE') {
            $scope.lostypes = ['COURSE'];
        } else if (los.type === 'COURSE') {
            $scope.lostypes = ['CLASS'];
        }
        
        $scope.institutions = [];
        $scope.showAddLearningOppSpecForm = true;
        $scope.currentLos = los;
    };
    
    $scope.addLearningOppSpec = function(){
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
        $scope.newLearningOppSpec = {name:[{text:'', lang: ''}]};
        $scope.currentLos = '';
    };
    
    $scope.saveLearningOppSpec = function(learningOppSpec) {
        LosService.save(learningOppSpec, function(result) {
            $scope.getAllTopLevelLosParents();
        });
    };
    
    $scope.cancelAddLearningOppSpec = function(){
        $scope.newLearningOppSpec = {};
        $scope.newLearningOppSpec = {name:[{text:'', lang: ''}]};
        $scope.showAddLearningOppSpecForm = false;
    };
    
    $scope.addNameForLearningOppSpec = function() {
        $scope.newLearningOppSpec.name.push({text: '',  lang: ''});
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
    $scope.cancelAddLearningOppSpec();
});
