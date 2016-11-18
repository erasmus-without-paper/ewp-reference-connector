angular.module('academicterm').controller('AcademicTermController', function ($scope, AcademicTermService) {
 $scope.getAllAcademicTerms = function(){
        AcademicTermService.getAll(
            function(result) {
                $scope.academicTerms = result;
        });
    };
    
    $scope.getAllAcademicTerms();
  
});
