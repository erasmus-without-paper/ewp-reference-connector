angular.module('menu').controller('MenuController', function ($scope, $location) {
    $scope.rootMenu = [{name:'Organization',
                        subItems:[{name:'Institution', page:'institution'},
                                  {name:'Contact', page:'contact'},
                                  {name:'Mobility participant', page:'mobility-participant'},
                                  {name:'Person', page:'person'}]},
                       {name:'Course', 
                        subItems:[{name:'Academic term', page:'academicterm'},
                                  {name:'Learning opp. spec.', page:'los'},
                                  {name:'Learning opp. instance', page:'loi'}]},
                       {name:'IIA',
                        subItems:[{name:'IIA', page:'iia'}]},
                       {name:'Mobility', page:'mobility'},
                       {name:'Other Connectors',
                        subItems:[{name:'Echo', page:'echo'},
                                  {name:'Discovery', page:'manifest'},
                                  {name:'Institution', page:'institution-client'},
                                  {name:'Organization unit', page:'ounit-client'}]}];

    $scope.selectedRootItem = '';
    $scope.selectHomeItem = function() {
        $scope.selectedRootItem = '';
        $location.path('home');
    };
    $scope.selectRootItem = function(item) {
        if ($scope.selectedRootItem !== item) {
            $scope.selectedRootItem = item;
            if (item.page) {
                $location.path(item.page);
                $scope.selectedSubItem = '';
            }
        }
    };

    $scope.selectSubItem = function(item) {
        $scope.selectedSubItem = item;
        $location.path(item.page);
    };
});
