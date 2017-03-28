angular.module('los').service('LosService', function ($http) {
    return {
        getAll: function (callback) {
            $http.get('gui/los/get_all').success(callback);
        },
        getAllTopLevelParents: function (callback) {
            $http.get('gui/los/get_top_level_parents').success(callback);
        },
        save: function (learningOppSpec, callback) {
            $http.post('gui/los/save', learningOppSpec).success(callback);
        },
        getByInstitutionId: function (institutionId, callback) {
            $http.get('gui/los/get_by_institution_id', {params: {institutionId: institutionId}}).success(callback);
        },
        getCourseReplicationHeis: function (callback) {
            $http.get('gui/los/course-replication').success(callback);
        },
        getCourseReplication: function (courseReplicationRequest, callback) {
            $http.post('gui/los/course-replication', courseReplicationRequest).success(callback);
        },
        getCourseHeis: function (callback) {
            $http.get('gui/los/courses').success(callback);
        },
        getCourse: function (courseRequest, callback) {
            $http.post('gui/los/courses', courseRequest).success(callback);
        }
    };
});
