angular.module('academicterm').service('AcademicTermService', function ($http) {
    return {
        getAll: function (callback) {
            $http.get('gui/academic_term/get_all').success(callback);
        },
        addNew: function (academicterm, callback) {
            $http.post('gui/academic_term/add', academicterm).success(callback);
        },
        getAcademicYears: function (callback) {
            $http.get('gui/academic_term/list_academic_years').success(callback);
        }
    };
});
