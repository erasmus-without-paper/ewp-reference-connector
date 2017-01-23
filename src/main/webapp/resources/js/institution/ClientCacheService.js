angular.module('institution').service('ClientCacheService', function ($http) {
    var cache = {};
    return {
        add: function (key, value) {
            cache[key] = value;
        },
        get: function (key) {
            return cache[key];
        },
        keys: function () {
            return Object.keys(cache);
        }
    };
});
