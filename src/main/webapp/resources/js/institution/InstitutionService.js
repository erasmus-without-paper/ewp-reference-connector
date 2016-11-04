angular.module('institution').service('InstitutionService', function ($http) {
    return {
        getLocal: function (callback) {
            $http.get('gui/institution/list',
                { method: 'GET',
                }).success(callback);
        }
    };
});
