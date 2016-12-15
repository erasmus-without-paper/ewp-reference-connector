angular.module('iia').service('IiaService', function ($http) {
    return {
        getAll: function (callback) {
            $http.get('gui/iia/get_all').success(callback);
        },
        addNewIia: function ($iia, callback) {
            $http.post('gui/iia/add', $iia).success(callback);
        },
        getMobilityTypes: function (callback) {
            $http.get('gui/iia/get_mobility_types').success(callback);
        }
    };
});
