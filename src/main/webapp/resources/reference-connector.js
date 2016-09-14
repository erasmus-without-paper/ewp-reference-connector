angular
    .module('connector', ['echo', 'iia', 'institution', 'ngResource', 'ngRoute'])
    .config(function ($routeProvider) {
        $routeProvider.when('/iia', {
                templateUrl: 'partials/iia.html',
                controller: 'IiaController'
            }).when('/home', {
                templateUrl: 'partials/home.html',
                controller: 'HomeController'
            }).when('/institution', {
                templateUrl: 'partials/institution.html',
                controller: 'InstitutionController'
            }).when('/echo', {
                templateUrl: 'partials/echo.html',
                controller: 'EchoController'
            }).otherwise({
                redirectTo: '/home'
            });
    })
    .controller('HomeController', function ($scope) {
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
angular.module('iia').controller('IiaController', function ($scope, IiaService) {
    IiaService.getAll(
        function(result) {
            $scope.iias = result;
        });
});

angular.module('iia').service('IiaService', function ($http) {
    return {
        getAll: function (callback) {
            $http.get('rest/iia/get',
                { method: 'GET',
                  params: {hei_id: 'institutionId1', iia_id: ['id1', 'id2']}
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
});

angular.module('institution').service('InstitutionService', function ($http) {
    return {
        getLocal: function (callback) {
            $http.get('rest/institution/get',
                { method: 'GET',
                  params: {hei_id: ['hei-id']}
                }).success(callback);
        }
    };
});
