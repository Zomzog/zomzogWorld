'use strict';

angular.module('zomzogworldApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('technology', {
                parent: 'entity',
                url: '/technologys',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'zomzogworldApp.technology.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/technology/technologys.html',
                        controller: 'TechnologyController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('technology');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('technology.detail', {
                parent: 'entity',
                url: '/technology/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'zomzogworldApp.technology.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/technology/technology-detail.html',
                        controller: 'TechnologyDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('technology');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Technology', function($stateParams, Technology) {
                        return Technology.get({id : $stateParams.id});
                    }]
                }
            })
            .state('technology.new', {
                parent: 'technology',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/technology/technology-dialog.html',
                        controller: 'TechnologyDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    name: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('technology', null, { reload: true });
                    }, function() {
                        $state.go('technology');
                    })
                }]
            })
            .state('technology.edit', {
                parent: 'technology',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/technology/technology-dialog.html',
                        controller: 'TechnologyDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Technology', function(Technology) {
                                return Technology.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('technology', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('technology.delete', {
                parent: 'technology',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/technology/technology-delete-dialog.html',
                        controller: 'TechnologyDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Technology', function(Technology) {
                                return Technology.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('technology', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
