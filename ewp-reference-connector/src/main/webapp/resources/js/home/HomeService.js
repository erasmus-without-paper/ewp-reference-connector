angular.module('home').service('HomeService', function ($http) {
    return {
        name: function (callback) {
            $http.get('gui/home/name').success(callback);
        },
        hostname: function (callback) {
            $http.get('gui/home/hostname').success(callback);
        }
    };
});