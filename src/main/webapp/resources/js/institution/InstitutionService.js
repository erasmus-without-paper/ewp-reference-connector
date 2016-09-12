angular.module('institution').service('InstitutionService', function ($http) {
    return {
        getLocal: function (callback) {
            $http.get('rest/institution/get',
                { method: 'GET',
                  params: {hei_id: ['hei-id']}
                }).success(callback);
        }
    };
});
