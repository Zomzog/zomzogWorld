'use strict';

angular.module('zomzogworldApp')
    .factory('Register', function ($resource) {
        return $resource('api/register', {}, {
        });
    });


