angular
    .module('connector', ['echo', 'iia', 'institution', 'department', 'ngResource', 'ngRoute'])
    .config(function ($routeProvider) {
        $routeProvider.when('/iia', {
                templateUrl: 'partials/iia.html',
                controller: 'IiaController'
            }).when('/newIia', {
                templateUrl: 'partials/new_iia_form.html',
                controller: 'IiaController'
            }).when('/home', {
                templateUrl: 'partials/home.html',
                controller: 'HomeController'
            }).when('/institution', {
                templateUrl: 'partials/institution.html',
                controller: 'InstitutionController'
            }).when('/department', {
                templateUrl: 'partials/department.html',
                controller: 'DepartmentController'
            }).when('/echo', {
                templateUrl: 'partials/echo.html',
                controller: 'EchoController'
            }).otherwise({
                redirectTo: '/home'
            });
    })
    .filter('toDate', function() {
        return function(date) {
            if (date) {
                var dt = new Date(date.replace(/\+.*/,''));
                return dt ? dt : date;
            }
            return date;
        };
    })
    .controller('HomeController', function ($scope) {
    });

angular.module('department', []);
angular.module('department').controller('DepartmentController', function ($scope, DepartmentService) {
    DepartmentService.getLocal(
        function(result) {
            $scope.departments = result;
        });
});

angular.module('department').service('DepartmentService', function ($http) {
    return {
        getLocal: function (callback) {
            $http.get('rest/department/get',
                { method: 'GET',
                  params: {hei_id: 'hei-id', department_id: ['department-id']}
                }).success(callback);
        }
    };
});

angular.module('echo', []);
angular.module('echo').controller('EchoController', function ($scope, EchoService) {
    $scope.echoList = [];
    $scope.addEchoToList = function() {
        if ($scope.echoItem) {
            $scope.echoList.push($scope.echoItem);
            $scope.echoItem = '';
        }
    };
    $scope.sendEcho = function() {
        EchoService.echo($scope.echoList,
            function(result) {
                $scope.echoResult = result;
            });
    };
});
angular.module('echo').service('EchoService', function ($http) {
    return {
        echo: function (echoList, callback) {
            $http.get('rest/echo',
                { method: 'GET',
                    params: {echo: echoList}
                }).success(callback);
        }
    };
});
angular.module('iia', []);
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

angular.module('iia').service('IiaService', function ($http) {
    return {
        getAll: function (callback) {
            $http.get('rest/iia/get-all',
                { method: 'GET'
                }).success(callback);
        },
        get: function (heiId, iiaIdList, callback) {
            $http.get('rest/iia/get',
                { method: 'GET',
                  params: {hei_id: heiId, iia_id: iiaIdList}
                }).success(callback);
        },
        add: function (iia, callback) {
            $http.post('rest/iia/add', iia,
                { method: 'POST',
                  headers: {'Content-Type': 'application/json'}
                }).success(callback);
        }
    };
});

angular.module('institution', []);
angular.module('institution').controller('InstitutionController', function ($scope, InstitutionService) {
    InstitutionService.getLocal(
        function(result) {
            $scope.institutions = result;
        });
        
    $scope.browseInstOrgObjectList = [];
    $scope.setCurrentInstOrgObject = function(obj) {
        $scope.currentInstOrgObject = obj;
        $scope.browseInstOrgObjectList.push(obj);
    };
    
    $scope.backCurrentInstOrgObject = function() {
        $scope.browseInstOrgObjectList.pop();
        if($scope.browseInstOrgObjectList.length > 0) {
            $scope.currentInstOrgObject = $scope.browseInstOrgObjectList[$scope.browseInstOrgObjectList.length-1];
        } else {
            $scope.currentInstOrgObject = '';
        }
    };
});

angular.module('institution').service('InstitutionService', function ($http) {
    return {
        getLocal: function (callback) {
            $http.get('gui/institution/list',
                { method: 'GET',
                }).success(callback);
        }
    };
});
