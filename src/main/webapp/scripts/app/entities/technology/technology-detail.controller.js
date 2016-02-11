'use strict';

angular.module('zomzogworldApp')
    .controller('TechnologyDetailController', function ($scope, $rootScope, $stateParams, entity, Technology) {
        $scope.technology = entity;
        $scope.load = function (id) {
            Technology.get({id: id}, function(result) {
                $scope.technology = result;
            });
        };
        var unsubscribe = $rootScope.$on('zomzogworldApp:technologyUpdate', function(event, result) {
            $scope.technology = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
