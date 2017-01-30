angular.module('connector').directive('instOrgUnitForm', function() {
        return {
            restrict: 'A',
            scope: {
                instOrgUnit: '=instOrgUnitForm',
                objectType: '=',
                add: '&addFkn',
                cancel: '&cancelFkn'
            },
            templateUrl: 'templates/inst_org_unit_form.html',
            controller: function($scope) {
                $scope.addOtherId = function() {
                    if (!$scope.instOrgUnit.otherId) {
                        $scope.instOrgUnit.otherId = [];
                    }
                    $scope.instOrgUnit.otherId.push({idType: '',  idValue: ''});
                };
                $scope.addName = function() {
                    $scope.instOrgUnit.name.push({text: '',  lang: ''});
                };
                $scope.addFactSheetUrl = function() {
                    if (!$scope.instOrgUnit.factSheet) {
                        $scope.instOrgUnit.factSheet = {};
                    }
                    if (!$scope.instOrgUnit.factSheet.url) {
                        $scope.instOrgUnit.factSheet.url = [];
                    }
                    $scope.instOrgUnit.factSheet.url.push({text: '',  lang: ''});
                };
            }
        };
    });
