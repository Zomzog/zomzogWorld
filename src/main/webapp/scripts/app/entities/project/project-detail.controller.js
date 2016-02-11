'use strict';

angular.module('zomzogworldApp')
    .controller('ProjectDetailController', function ($scope, $rootScope, $stateParams, entity, Project, Society, Technology) {
        $scope.project = entity;
        $scope.load = function (id) {
            Project.get({id: id}, function(result) {
                $scope.project = result;
            });
        };
        var unsubscribe = $rootScope.$on('zomzogworldApp:projectUpdate', function(event, result) {
            $scope.project = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
