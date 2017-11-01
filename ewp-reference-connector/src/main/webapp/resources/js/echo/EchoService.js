angular.module('echo').service('EchoService', function ($http) {
    return {
        echo: function (echoRequest, callback) {
            $http.post('gui/echo',
                echoRequest
                ).success(callback);
        },
        echoHeis: function (callback) {
            $http.get('gui/echo').success(callback);
        }
    };
});