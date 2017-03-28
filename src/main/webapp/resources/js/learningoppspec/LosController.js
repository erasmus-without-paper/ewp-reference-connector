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
    
    $scope.institutionChanged = function() {
        var currentInst;
        angular.forEach($scope.institutions, function(item) {
            if (item.institutionId === $scope.newLearningOppSpec.institutionId) {
                currentInst = item;
            }
        });
        
        $scope.organizations = [];
        $scope.addOrganizationUnitsToList(currentInst);
    };
    
    $scope.addOrganizationUnitsToList = function(obj) {
        angular.forEach(obj.organizationUnits, function(item) {
            $scope.organizations.push(item);
            $scope.addOrganizationUnitsToList(item);
        });
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
    
    $scope.addLOSUrl = function() {
        if (!$scope.newLearningOppSpec.url) {
            $scope.newLearningOppSpec.url = [];
        }
        $scope.newLearningOppSpec.url.push({text:'', lang:''});
    };
    
    $scope.addLearningOppSpec = function() {
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
        $scope.newLearningOppSpec = {name:[{text:'', lang: ''}], eqfLevel:1};
        $scope.showAddLearningOppSpecForm = false;
    };
    
    $scope.addNameForLearningOppSpec = function() {
        $scope.newLearningOppSpec.name.push({text: '',  lang: ''});
    };
    
    $scope.addDescriptionForLearningOppSpec = function() {
        if (!$scope.newLearningOppSpec.description) {
            $scope.newLearningOppSpec.description = [];
        }
        $scope.newLearningOppSpec.description.push({text: '',  lang: ''});
    };
    
    $scope.showLos = function(los) {
        $scope.currentLos=los;
    };
    $scope.toggleLosMenuItem = function(los) {
        event.preventDefault();
        event.stopPropagation();
        los.expanded = !los.expanded;
    };
    
    $scope.eqfLevels=['1','2','3','4','5','6','7','8'];
    $scope.getAllTopLevelLosParents();
    $scope.cancelAddLearningOppSpec();
});
