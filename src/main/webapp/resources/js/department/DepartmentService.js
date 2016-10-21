angular.module('department').service('DepartmentService', function ($http) {
    return {
        getLocal: function (callback) {
            $http.get('rest/department/get',
                { method: 'GET',
                  params: {hei_id: 'hei-id', department_id: ['department-id']}
                }).success(callback);
        }
    };
});
