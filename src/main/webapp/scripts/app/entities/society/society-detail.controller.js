'use strict';

angular.module('zomzogworldApp')
    .controller('SocietyDetailController', function ($scope, $rootScope, $stateParams, entity, Society, Project) {
        $scope.society = entity;
        $scope.load = function (id) {
            Society.get({id: id}, function(result) {
                $scope.society = result;
            });
        };
        var unsubscribe = $rootScope.$on('zomzogworldApp:societyUpdate', function(event, result) {
            $scope.society = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
