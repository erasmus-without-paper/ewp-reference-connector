angular.module('department').controller('DepartmentController', function ($scope, DepartmentService) {
    DepartmentService.getLocal(
        function(result) {
            $scope.departments = result;
        });
});
