angular.module('los').controller('LosController', function ($scope, LosService, InstitutionService) {
    $scope.getAllLearningOppSpecs = function(){
        LosService.getAll(
            function(result) {
                $scope.losList = result;
        });
    };
    
    $scope.viewAddLearningOppSpecForm = function() {
        InstitutionService.getLocal(
            function(result) {
            $scope.institutions = result;
        });
        
        $scope.lostypes = ['Degree Programme', 'Module', 'Course', 'Class'];
        
        $scope.showAddLearningOppSpecForm = true;
    };
    
    $scope.addLearningOppSpec = function(){
        $scope.newLearningOppSpec.name = [{text:$scope.newLearningOppSpec.nameStr,'lang':'en'}];
        $scope.newLearningOppSpec.url = [{text:$scope.newLearningOppSpec.urlStr,'lang':'en'}];
        $scope.saveLearningOppSpec($scope.newLearningOppSpec);
        $scope.showAddLearningOppSpecForm = false;
        $scope.newLearningOppSpec = {};
    };
    
    $scope.saveLearningOppSpec = function(learningOppSpec) {
        LosService.addNew(learningOppSpec, function(result) {
            $scope.getAllLearningOppSpecs();
        });
    };
    
    $scope.cancelAddLearningOppSpec = function(){
        $scope.newLearningOppSpec = {};
        $scope.showAddLearningOppSpecForm = false;
    };
    
    $scope.getAllLearningOppSpecs();
});
