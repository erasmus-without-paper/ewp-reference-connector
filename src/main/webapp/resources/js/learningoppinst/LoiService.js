angular.module('loi').service('LoiService', function ($http) {
    return {
        getAll: function (callback) {
            $http.get('gui/loi/get_all').success(callback);
        },
        addNew: function (learningOppInstance, callback) {
            $http.post('gui/loi/add', learningOppInstance).success(callback);
        }
    };
});
