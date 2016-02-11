'use strict';

angular.module('zomzogworldApp')
    .factory('Technology', function ($resource, DateUtils) {
        return $resource('api/technologys/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    });
