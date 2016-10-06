angular.module('iia').controller('IiaController', function ($scope, $location, IiaService) {
    IiaService.getAll(
        function(result) {
            $scope.iias = result;
        });
    
    $scope.createNewIia = function() {
       $location.path('/newIia');
    };
    
    $scope.cancelNewIia = function() {
       $location.path('/iia');
       $scope.resetAll();
    };
    
    $scope.saveNewIia = function() {
        IiaService.add($scope.newIia, 
            function(result) {
                $scope.iias = result;
                $location.path('/iia');
                $scope.resetAll();
            });
    };
    
    $scope.resetAll = function() {
        $scope.newIia = {};
        $scope.newIia.partner = {};
        $scope.newIia.partner.condition = [];
        
        $scope.resetNewCondition();
    };
    
    $scope.resetNewCondition = function() {
        $scope.newCondition = {term: {title:[{lang:'',title:''}]}};
        $scope.showConditionForm=false;
    };
    
    $scope.addCondition = function() {
        $scope.newIia.partner.condition.push($scope.newCondition);
        $scope.resetNewCondition();
    };
    
    $scope.addNewTitle = function() {
        $scope.newCondition.term.title.push({lang:'',title:''});
    };
    
    $scope.resetAll();
    $scope.institutions = ['hei1','hei2','hei3','hei4'];
    
});
