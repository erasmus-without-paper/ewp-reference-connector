angular.module('iia').service('IiaService', function ($http) {
    return {
        getAll: function (callback) {
            $http.get('rest/iia/get',
                { method: 'GET',
                  params: {hei_id: 'hei-id', iia_id: ['iia-id-1', 'iia-id-2']}
                }).success(callback);
        }
    };
});
