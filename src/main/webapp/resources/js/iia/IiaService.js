angular.module('iia').service('IiaService', function ($http) {
    return {
        getAll: function (callback) {
            $http.get('gui/iia/get_all').success(callback);
        },
        addNewIia: function ($iia, callback) {
            $http.post('gui/iia/add', $iia).success(callback);
        },
        getMobilityTypes: function (callback) {
            $http.get('gui/iia/mobility_types').success(callback);
        },
        getMobilityNumberVariants: function (callback) {
            $http.get('gui/iia/mobility_unit_variants').success(callback);
        },
        getDurationUnitVariants: function (callback) {
            $http.get('gui/iia/duration_unit_variants').success(callback);
        }
    };
});
