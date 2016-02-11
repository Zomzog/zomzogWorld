'use strict';

angular.module('zomzogworldApp').controller('TechnologyDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Technology',
        function($scope, $stateParams, $uibModalInstance, entity, Technology) {

        $scope.technology = entity;
        $scope.load = function(id) {
            Technology.get({id : id}, function(result) {
                $scope.technology = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('zomzogworldApp:technologyUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.technology.id != null) {
                Technology.update($scope.technology, onSaveSuccess, onSaveError);
            } else {
                Technology.save($scope.technology, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
