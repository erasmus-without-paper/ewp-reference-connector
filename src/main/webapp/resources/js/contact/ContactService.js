angular.module('contact').service('ContactService', function ($http) {
    return {
        getAll: function (callback) {
            $http.get('gui/contact/get_all',
                { method: 'GET'
                }).success(callback);
        },
        addNew: function (contact, callback) {
            $http.post('gui/contact/add', contact).success(callback);
        }
    };
});
