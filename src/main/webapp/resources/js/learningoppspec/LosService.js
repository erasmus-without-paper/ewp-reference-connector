angular.module('los').service('LosService', function ($http) {
    return {
        getAll: function (callback) {
            $http.get('gui/los/get_all').success(callback);
        },
        addNew: function (learningOppSpec, callback) {
            $http.post('gui/los/add', learningOppSpec).success(callback);
        },
        getByInstitutionId: function (institutionId, callback) {
            $http.get('gui/los/get_by_institution_id', {params: {institutionId: institutionId}}).success(callback);
        }
    };
});
