angular.module('institution').controller('InstitutionController', function ($scope, InstitutionService) {
    InstitutionService.getLocal(
        function(result) {
            $scope.institutions = result;
        });
});
