angular.module('iia').service('IiaService', function ($http) {
    return {
        getAll: function (callback) {
            $http.get('gui/iia/get_all').success(callback);
        }
    };
    
    
//    return {
//        getAll: function (callback) {
//            $http.get('rest/iia/get-all',
//                { method: 'GET'
//                }).success(callback);
//        },
//        get: function (heiId, iiaIdList, callback) {
//            $http.get('rest/iia/get',
//                { method: 'GET',
//                  params: {hei_id: heiId, iia_id: iiaIdList}
//                }).success(callback);
//        },
//        add: function (iia, callback) {
//            $http.post('rest/iia/add', iia,
//                { method: 'POST',
//                  headers: {'Content-Type': 'application/json'}
//                }).success(callback);
//        }
//    };
});
