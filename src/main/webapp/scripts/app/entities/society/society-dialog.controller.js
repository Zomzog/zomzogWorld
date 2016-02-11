'use strict';

angular.module('zomzogworldApp').controller('SocietyDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Society', 'Project',
        function($scope, $stateParams, $uibModalInstance, entity, Society, Project) {

        $scope.society = entity;
        $scope.projects = Project.query();
        $scope.load = function(id) {
            Society.get({id : id}, function(result) {
                $scope.society = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('zomzogworldApp:societyUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.society.id != null) {
                Society.update($scope.society, onSaveSuccess, onSaveError);
            } else {
                Society.save($scope.society, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.datePickerForEnter = {};

        $scope.datePickerForEnter.status = {
            opened: false
        };

        $scope.datePickerForEnterOpen = function($event) {
            $scope.datePickerForEnter.status.opened = true;
        };
        $scope.datePickerForQuit = {};

        $scope.datePickerForQuit.status = {
            opened: false
        };

        $scope.datePickerForQuitOpen = function($event) {
            $scope.datePickerForQuit.status.opened = true;
        };
}]);
