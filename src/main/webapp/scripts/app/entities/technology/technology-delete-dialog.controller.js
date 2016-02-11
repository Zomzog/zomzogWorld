'use strict';

angular.module('zomzogworldApp')
	.controller('TechnologyDeleteController', function($scope, $uibModalInstance, entity, Technology) {

        $scope.technology = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Technology.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
