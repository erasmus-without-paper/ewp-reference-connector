angular.module('mobility').service('MobilityService', function ($http) {
    return {
        getAll: function (callback) {
            $http.get('gui/omobility/get_all').success(callback);
        },
        getMobilityStatuses: function (callback) {
            $http.get('gui/omobility/mobility_statuses').success(callback);
        },
        getLaComponentStatuses: function (callback) {
            $http.get('gui/omobility/lacomponent_statuses').success(callback);
        },
        addNew: function ($person, callback) {
            $http.post('gui/omobility/add', $person).success(callback);
        },
        getOmobilityHeis: function (callback) {
            $http.get('gui/omobility/omobilities-heis').success(callback);
        },
        getOmobilityIndex: function (courseRequest, callback) {
            $http.post('gui/omobility/omobilities-index', courseRequest).success(callback);
        },
        getOmobility: function (courseRequest, callback) {
            $http.post('gui/omobility/omobilities-get', courseRequest).success(callback);
        },
        getAllMobilityUpdateRequests: function (callback) {
            $http.get('gui/omobility/update/get_all').success(callback);
        },
        getCountMobilityUpdateRequests: function (callback) {
            $http.get('gui/omobility/update/count').success(callback);
        },
        getImobilityHeis: function (callback) {
            $http.get('gui/omobility/imobilities-heis').success(callback);
        },
        getImobility: function (courseRequest, callback) {
            $http.post('gui/omobility/imobilities-get', courseRequest).success(callback);
        }
    };
});
