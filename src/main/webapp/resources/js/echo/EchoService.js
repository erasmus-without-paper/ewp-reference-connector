angular.module('echo').service('EchoService', function ($http) {
    return {
        echo: function (echoList, callback) {
            $http.get('rest/echo',
                {
                    params: {echo: echoList}
                }).success(callback);
        }
    };
});