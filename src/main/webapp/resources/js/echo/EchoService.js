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