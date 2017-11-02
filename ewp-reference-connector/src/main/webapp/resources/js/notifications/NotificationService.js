angular.module('notifications').service('NotificationService', function ($http) {
    return {
        getAll: function (callback) {
            $http.get('gui/notification/get_all').success(callback);
        },
        getCount: function (callback) {
            $http.get('gui/notification/count').success(callback);
        }
    };
});
