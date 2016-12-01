angular.module('iiapartner').service('IiaPartnerService', function ($http) {
    return {
        getAll: function (callback) {
            $http.get('gui/iiapartner/get_all').success(callback);
        }
    };
});
