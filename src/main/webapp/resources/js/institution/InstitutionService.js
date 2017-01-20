angular.module('institution').service('InstitutionService', function ($http) {
    return {
        getLocal: function (callback) {
            $http.get('gui/institution/get_all').success(callback);
        },
        save: function (institution, callback) {
            $http.post('gui/institution/save', institution).success(callback);
        },
        getInstitutions: function (institutionRequest, callback) {
            $http.post('gui/institution/heis', institutionRequest).success(callback);
        },
        getHeis: function (callback) {
            $http.get('gui/institution/heis').success(callback);
        }
    };
});
