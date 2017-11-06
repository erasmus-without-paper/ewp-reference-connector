angular.module('notifications').service('NotificationService', function ($http) {
    return {
        getAll: function (callback) {
            $http.get('gui/notification/get_all').success(callback);
        },
        getCount: function (callback) {
            $http.get('gui/notification/count').success(callback);
        },
        getIiaCnrHeis: function (callback) {
            $http.get('gui/notification/iia-cnr-heis').success(callback);
        },
        getOmobilityCnrHeis: function (callback) {
            $http.get('gui/notification/omobility-cnr-heis').success(callback);
        },
        getImobilityCnrHeis: function (callback) {
            $http.get('gui/notification/imobility-cnr-heis').success(callback);
        },
        getTorsCnrHeis: function (callback) {
            $http.get('gui/notification/tors-cnr-heis').success(callback);
        },
        sendNotification: function (notifyRequest, callback) {
            $http.post('gui/notification/notify', notifyRequest).success(callback);
        }
    };
});
