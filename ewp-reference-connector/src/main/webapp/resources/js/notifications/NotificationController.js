angular.module('notifications').controller('NotificationController', function ($scope, NotificationService) {
    NotificationService.getAll(
        function(notifications) {
            $scope.notifications = notifications;
        });
});