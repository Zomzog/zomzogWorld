'use strict';

angular.module('zomzogworldApp').controller('ProjectDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Project', 'Society', 'Technology',
        function($scope, $stateParams, $uibModalInstance, entity, Project, Society, Technology) {

        $scope.project = entity;
        $scope.societys = Society.query();
        $scope.technologys = Technology.query();
        $scope.load = function(id) {
            Project.get({id : id}, function(result) {
                $scope.project = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('zomzogworldApp:projectUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.project.id != null) {
                Project.update($scope.project, onSaveSuccess, onSaveError);
            } else {
                Project.save($scope.project, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
