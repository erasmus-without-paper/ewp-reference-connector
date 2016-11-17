angular.module('coordinator').service('CoordinatorService', function ($http) {
    return {
        getAll: function (callback) {
            $http.get('gui/coordinator/list',
                { method: 'GET'
                }).success(callback);
        },
        addNew: function ($coordinator, callback) {
            $http.post('gui/coordinator/add', $coordinator).success(callback);
        }
    };
});
