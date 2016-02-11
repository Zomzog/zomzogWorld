 'use strict';

angular.module('zomzogworldApp')
    .factory('notificationInterceptor', function ($q, AlertService) {
        return {
            response: function(response) {
                var alertKey = response.headers('X-zomzogworldApp-alert');
                if (angular.isString(alertKey)) {
                    AlertService.success(alertKey, { param : response.headers('X-zomzogworldApp-params')});
                }
                return response;
            }
        };
    });
