'use strict';

describe('Controller Tests', function() {

    describe('Project Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockProject, MockSociety, MockTechnology;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockProject = jasmine.createSpy('MockProject');
            MockSociety = jasmine.createSpy('MockSociety');
            MockTechnology = jasmine.createSpy('MockTechnology');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Project': MockProject,
                'Society': MockSociety,
                'Technology': MockTechnology
            };
            createController = function() {
                $injector.get('$controller')("ProjectDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'zomzogworldApp:projectUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
