angular.module('mobilityParticipant').service('MobilityParticipantService', function ($http) {
    return {
        getAll: function (callback) {
            $http.get('gui/participant/get_all',
                { method: 'GET'
                }).success(callback);
        },
        addNew: function (contact, callback) {
            $http.post('gui/participant/add', contact).success(callback);
        }
    };
});
