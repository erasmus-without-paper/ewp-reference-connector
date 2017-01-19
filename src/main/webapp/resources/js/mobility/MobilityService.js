angular.module('mobility').service('MobilityService', function ($http) {
    return {
        getAll: function (callback) {
            $http.get('gui/mobility/get_all').success(callback);
        },
        getMobilityStatuses: function (callback) {
            $http.get('gui/mobility/mobility_statuses').success(callback);
        },
        getLaComponentStatuses: function (callback) {
            $http.get('gui/mobility/lacomponent_statuses').success(callback);
        },
        addNew: function ($person, callback) {
            $http.post('gui/mobility/add', $person).success(callback);
        }
    };
});
