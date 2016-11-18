angular.module('academicterm').service('AcademicTermService', function ($http) {
    return {
        getAll: function (callback) {
            $http.get('gui/academicterm/list').success(callback);
        },
        addNew: function ($academicterm, callback) {
            $http.post('gui/academicterm/add', $academicterm).success(callback);
        }
    };
});
