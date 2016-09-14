angular.module('iia').service('IiaService', function ($http) {
    return {
        getAll: function (callback) {
            $http.get('rest/iia/get',
                { method: 'GET',
                  params: {hei_id: 'institutionId1', iia_id: ['id1', 'id2']}
                }).success(callback);
        }
    };
});
