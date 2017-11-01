angular.module('iia').controller('IiaController', function ($scope, IiaService) {
    $scope.getAllIias = function(){
        IiaService.getAll(
            function(result) {
                $scope.iiaList = result;
        });
    };
    
    $scope.getMobilityNumberVariants = function(){
        IiaService.getMobilityNumberVariants(
            function(result) {
                $scope.mobilityNumberVariants = result;
        });
    };
   
    $scope.getDurationUnitVariants = function(){
        IiaService.getDurationUnitVariants(
            function(result) {
                $scope.durationUnits = result;
        });
    };
    
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
        $scope.cancelAddCondition();
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
        $scope.showAddConditionForm = false;
        $scope.cancelAddCondition();
    };
 
    $scope.cancelAddCondition = function(){
        $scope.showAddConditionForm = false;
        $scope.newCondition = {eqfLevel:1};
    };
    
    $scope.eqfLevels=['1','2','3','4','5','6','7','8'];
    $scope.getMobilityNumberVariants();
    $scope.getDurationUnitVariants();
    $scope.getAllIias();
});
