angular.module('institution').service('InstitutionService', function ($http) {
    return {
        getLocal: function (callback) {
            $http.get('gui/institution/list').success(callback);
        },
        save: function (institution, callback) {
            $http.post('gui/institution/save', institution).success(callback);
        }
    };
});
