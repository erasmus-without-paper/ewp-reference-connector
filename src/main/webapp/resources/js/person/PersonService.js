angular.module('person').service('PersonService', function ($http) {
    return {
        getAll: function (callback) {
            $http.get('gui/person/get_all').success(callback);
        },
        addNew: function ($person, callback) {
            $http.post('gui/person/add', $person).success(callback);
        }
    };
});
