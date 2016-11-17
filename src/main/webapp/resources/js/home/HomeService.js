angular.module('home').service('HomeService', function ($http) {
    return {
        name: function (callback) {
            $http.get('gui/home').success(callback);
        }
    };
});