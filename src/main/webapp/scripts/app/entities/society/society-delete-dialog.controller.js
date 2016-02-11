'use strict';

angular.module('zomzogworldApp')
	.controller('SocietyDeleteController', function($scope, $uibModalInstance, entity, Society) {

        $scope.society = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Society.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
