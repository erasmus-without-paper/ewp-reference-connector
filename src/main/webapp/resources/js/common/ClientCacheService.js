angular.module('connector').service('ClientCacheService', function ($http) {
    var cache = {};
    return {
        add: function (client, key, value) {
            if (!cache[client]) {
                cache[client] = {};
            }
            cache[client][key] = value;
        },
        get: function (client, key) {
            return cache[client] ? cache[client][key] : undefined;
        },
        keys: function (client) {
            return Object.keys(cache[client]);
        }
    };
});
