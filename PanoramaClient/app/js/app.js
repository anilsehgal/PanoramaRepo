angular.module( 'panorama', [ 'ui.router', 'angular.filter', 'relativeDate', 'ngSanitize','angularTrix', 'ngToast' ] )
.config( function( $stateProvider, $urlRouterProvider, $httpProvider ) {
        $httpProvider.defaults.withCredentials = true;
        $stateProvider
            // route for the home page
            .state('app', {
                url:'/',
                views: {
                    'header': {
                        templateUrl : 'views/header.html',
                        controller  : 'LoginController'
                    },
                    'content': {
                        templateUrl : 'views/home.html',
                        controller  : 'IndexController'
                    },
                    'widgets': {
                        templateUrl : 'views/widgets.html',
                        controller  : 'IndexController'
                    },
                    'footer': {
                        templateUrl : 'views/footer.html'
                    }
                }

            })
            .state('app.welcome', {
                url:'welcome',
                views: {
                    'header@': {
                        templateUrl : 'views/header.html',
                        controller  : 'LoginController'
                    },
                    'content@': {
                        templateUrl : 'views/home.html',
                        controller  : 'IndexController'
                    }
                }

            })
            .state('app.viewDiscussion', {
                url:'viewDiscussion/:discussionId',
                views: {
                    'header@': {
                        templateUrl : 'views/header.html',
                        controller  : 'LoginController'
                    },
                    'content@': {
                        templateUrl : 'views/discussion_detail.html',
                        controller  : 'IndexController'
                    }
                }

            })
            .state('app.createDiscussion', {
                url:'createDiscussion',
                views: {
                    'header@': {
                        templateUrl : 'views/header.html',
                        controller  : 'LoginController'
                    },
                    'content@': {
                        templateUrl : 'views/create_discussion.html',
                        controller  : 'IndexController'
                    }
                }

            })
            .state('app.users', {
                url:'users',
                views: {
                    'header@': {
                        templateUrl : 'views/header.html',
                        controller  : 'LoginController'
                    },
                    'content@': {
                        templateUrl : 'views/users.html',
                        controller  : 'IndexController'
                    }
                }

            })
            .state('app.discussionbytag', {
                url:'discussionbytag/:tagId/:tagName',
                views: {
                    'header@': {
                        templateUrl : 'views/header.html',
                        controller  : 'LoginController'
                    },
                    'content@': {
                        templateUrl : 'views/discussion_by_tags.html',
                        controller  : 'IndexController'
                    }
                }

            })
            .state('app.home', {
                url:'home',
                views: {
                    'header@': {
                        templateUrl : 'views/header.html',
                        controller  : 'LoginController'
                    },
                    'content@': {
                        templateUrl : 'views/home.html',
                        controller  : 'IndexController'
                    }
                }

            })
            .state('app.stats', {
                url:'stats',
                views: {
                    'header@': {
                        templateUrl : 'views/header.html',
                        controller  : 'LoginController'
                    },
                    'content@': {
                        templateUrl : 'views/widgets.html',
                        controller  : 'IndexController'
                    }
                }

            })
            .state('app.profile', {
                url:'profile',
                views: {
                    'header@': {
                        templateUrl : 'views/header.html',
                        controller  : 'LoginController'
                    },
                    'content@': {
                        templateUrl : 'views/profile.html',
                        controller  : 'IndexController'
                    }
                }

            })
            .state('app.search', {
                url:'search/:searchText',
                views: {
                    'header@': {
                        templateUrl : 'views/header.html',
                        controller  : 'LoginController'
                    },
                    'content@': {
                        templateUrl : 'views/search_results.html',
                        controller  : 'IndexController'
                    }
                }

            })
            .state('app.user', {
                url:'user/:userId',
                views: {
                    'header@': {
                        templateUrl : 'views/header.html',
                        controller  : 'LoginController'
                    },
                    'content@': {
                        templateUrl : 'views/user.html',
                        controller  : 'IndexController'
                    }
                }

            })
            .state('app.info', {
                url:'info',
                views: {
                    'header@': {
                        templateUrl : 'views/header.html',
                        controller  : 'LoginController'
                    },
                    'content@': {
                        templateUrl : 'views/info.html',
                        controller  : 'IndexController'
                    }
                }

            });
        $urlRouterProvider.otherwise('welcome');
    });
