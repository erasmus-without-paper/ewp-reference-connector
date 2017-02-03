angular.module('loi').service('LoiService', function ($http) {
    return {
        getGradingSchemes: function (callback) {
            $http.get('gui/loi/grading_schemes').success(callback);
        }
    };
});
