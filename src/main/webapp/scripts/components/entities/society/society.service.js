'use strict';

angular.module('zomzogworldApp')
    .factory('Society', function ($resource, DateUtils) {
        return $resource('api/societys/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.enter = DateUtils.convertLocaleDateFromServer(data.enter);
                    data.quit = DateUtils.convertLocaleDateFromServer(data.quit);
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    data.enter = DateUtils.convertLocaleDateToServer(data.enter);
                    data.quit = DateUtils.convertLocaleDateToServer(data.quit);
                    return angular.toJson(data);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    data.enter = DateUtils.convertLocaleDateToServer(data.enter);
                    data.quit = DateUtils.convertLocaleDateToServer(data.quit);
                    return angular.toJson(data);
                }
            }
        });
    });
