'use strict';

angular.module('zomzogworldApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('society', {
                parent: 'entity',
                url: '/societys',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'zomzogworldApp.society.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/society/societys.html',
                        controller: 'SocietyController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('society');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('society.detail', {
                parent: 'entity',
                url: '/society/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'zomzogworldApp.society.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/society/society-detail.html',
                        controller: 'SocietyDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('society');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Society', function($stateParams, Society) {
                        return Society.get({id : $stateParams.id});
                    }]
                }
            })
            .state('society.new', {
                parent: 'society',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/society/society-dialog.html',
                        controller: 'SocietyDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    name: null,
                                    enter: null,
                                    quit: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('society', null, { reload: true });
                    }, function() {
                        $state.go('society');
                    })
                }]
            })
            .state('society.edit', {
                parent: 'society',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/society/society-dialog.html',
                        controller: 'SocietyDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Society', function(Society) {
                                return Society.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('society', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('society.delete', {
                parent: 'society',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/society/society-delete-dialog.html',
                        controller: 'SocietyDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Society', function(Society) {
                                return Society.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('society', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
