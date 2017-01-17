angular.module('iia').controller('IiaController', function ($scope, IiaService) {
    $scope.getAllIias = function(){
        IiaService.getAll(
            function(result) {
                $scope.iiaList = result;
        });
    };
    
    $scope.durationUnits = ['Hours', 'Days', 'Weeks', 'Months', 'Years'];
    $scope.mobilityNumberVariants = ['Total', 'Average'];
    
    $scope.setSelectedIia = function(iia) {
        $scope.selectedIia = iia;
    };

    $scope.setSelectedCoopCond = function(coop) {
        $scope.selectedCoopCond = coop;
    };
    
    $scope.backIia = function() {
            $scope.selectedIia = '';
    };

    $scope.backCoopCond = function() {
            $scope.selectedCoopCond = '';
    };
    
    $scope.viewAddCooperationConditionForm = function() {
        IiaService.getMobilityTypes(
            function(result) {
            $scope.mobilityTypes = result;
        });
        
        $scope.showAddConditionForm = true;
    };
    
    $scope.addIia = function(){
        $scope.newIia.cooperationConditions = $scope.conditions;
        $scope.conditions = [];
        IiaService.addNewIia($scope.newIia,
            function(result) {
                $scope.newIia = {};
                $scope.showAddIiaForm = false;
                $scope.getAllIias();
        });
    };
    
    $scope.cancelAddIia = function(){
        $scope.newIia = {};
        $scope.showAddIiaForm = false;
    };
    
    $scope.conditions = [];
    $scope.addCondition = function(){
        var selectedMobilityType;
        angular.forEach($scope.mobilityTypes, function(mobType) {
            if (mobType.id === $scope.newCondition.mobilityTypeId) {
                selectedMobilityType = mobType;
            }
        });
        $scope.newCondition.mobilityType = selectedMobilityType;
        $scope.conditions.push($scope.newCondition);
        $scope.newCondition = {};
        $scope.showAddConditionForm = false;
    };
 
    $scope.cancelAddCondition = function(){
        $scope.showAddConditionForm = false;
    };
    
    $scope.getAllIias();
});
