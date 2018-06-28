angular.module('panorama')
    .filter('plain_preview', function () {
        return function (text) {
            result = text ? String(text).replace(/<[^>]+>/gm, '') : '';
            length = text.length > 150 ? 150 : text.length;
            result = result.substring(0, length) + "...";
            return result;
        };
    })
    .filter('addTargetBlank', function () {
        return function (x) {
            var tree = angular.element('<div>' + x + '</div>'); //defensively wrap in a div to avoid 'invalid html' exception
            tree.find('a').attr('target', '_blank'); //manipulate the parse tree
            return angular.element('<div>').append(tree).html(); //trick to have a string representation
        }
    })
    .controller('LoginController', ['$scope', '$sessionStorage', '$state', 'discussionForumService', 'messageService', function ($scope, $sessionStorage, $state, discussionForumService, messageService) {
        
        $scope.user = {
            username: "",
            password: ""
        };

        $scope.newUser = {
            userName: "",
            fullName: "",
            password: "",
            avatar: "",
            email: ""
        };

        $scope.register = function () {
 
            console.log($scope.newUser);
            if ($scope.newUser.avatar.trim() === '') {
                messageService.error('Please choose an avatar or upload your own');
                return;
            }
            discussionForumService.register($scope.newUser).then(function (res) {

                $("#registerModal").modal('hide');
                messageService.success('Thankyou for signing up! ' + $scope.newUser.userName + '. Please login to post content.');
            }, function (err) {
                
                messageService.error(err.data.message);
            });
        };

        $scope.login = function () {
            discussionForumService.login($scope.user).then(function (res) {

                $sessionStorage.store("token", res.data.token);
                $sessionStorage.store("accomodation", res.data.accomodation);
                $sessionStorage.store("identifier", res.data.identifier);
                $("#loginModal").modal('hide');
                messageService.success('Welcome ' + $scope.user.username);
            }, function (err) {

                messageService.error(err.data.message);
            });
        };
        $scope.getAvatars = function () {
            discussionForumService.getAvatars().then(function (res) {

                $scope.avatars = res.data;
            }, function (err) {

                messageService.error(err.data.message);
            });

        };

        $scope.selectAvatar = function (avatar) {
            discussionForumService.fakeCall().then(function (res) {

                var avatarChoices = document.getElementsByName("avatarChoice");
                avatarChoices.forEach(function (element) {

                    angular.element(element).removeClass('avatar-image-selected');
                });
                angular.element(document.getElementById("avatarChoice" + avatar.avatarId)).addClass('avatar-image-selected');
                $scope.newUser.avatar = avatar.avatarURL;
            }, function (err) {

                messageService.error(err.data.message);
            });

        };

        $scope.isUserLoggedIn = function () {
            return angular.isDefined($sessionStorage.get("token")) && $sessionStorage.get("token").trim().length > 0;
        };
        $scope.getAccomodation = function () {
            return $sessionStorage.get("accomodation");
        };
        $scope.getIdentifier = function () {
            return $sessionStorage.get("identifier");
        };
        $scope.logout = function () {
            discussionForumService.logout().then(function (res) {

                $sessionStorage.remove("accomodation");
                $sessionStorage.remove("token");
                $sessionStorage.remove("identifier");
                messageService.success("Logout Successful");
                $state.go('app.welcome');
            }, function (err) {

                messageService.error(err.data.message);
            });

        };
        
        $scope.searchText = "";
        $scope.performSearch = function () {
            $state.go('app.search', {
                "searchText": $scope.searchText
            });
        };
        
        $scope.uploadImage = function (files) {

            if (files.length > 0) {
                var file = files[0];

                if (file.size > 600000) { // 600kb?
                    messageService.error("Max File Size allowed is 500kb");
                } else {
                    var reader = new FileReader();
                    reader.onload = function (readerEvt) {
                        var binaryString = readerEvt.target.result;
                        $scope.newUser.avatar = binaryString;
                    };
                    reader.readAsDataURL(file);
                }
            }
        };

}])
    .controller('IndexController', ['$scope', 'discussionForumService', '$sce', '$stateParams', '$state', 'messageService', '$sessionStorage', '$filter', function ($scope, discussionForumService, $sce, $stateParams, $state, messageService, $sessionStorage, $filter) {
        
        $scope.title = 'Discussions';
        $scope.activeDiscussionId = $stateParams.discussionId;
        $scope.userId = $stateParams.userId;
        $scope.activeTagId = $stateParams.tagId;
        $scope.activeTagName = $stateParams.tagName;
        $scope.searchText = $stateParams.searchText;
        $scope.sce = $sce;
        
        $scope.currentUser = {
            userId: "",
            userName: "",
            fullName: "",
            newPassword: "",
            password: "",
            createdOn: "",
            role: "",
            isActive: "",
            topicCount: "",
            replyCount: "",
            email: "",
            avatar: ""
        };
        $scope.newUser = {
            "userId": "",
            "userName": "",
            "fullName": "",
            "newPassword": "",
            "password": "",
            "createdOn": "",
            "role": "",
            "isActive": "",
            "topicCount": "",
            "replyCount": "",
            "email": "",
            "avatar": ""
        };
        $scope.getCurrentUser = function() {
            discussionForumService.getCurrentUser().then(function (res) {

                $scope.currentUser = res.data;
                console.log(res.data);
            }, function (err) {

                messageService.error(err.data.message);
            });
        }
        $scope.updateUser = function() {

            $scope.newUser.fullName = $('#userFullName').val();
            $scope.newUser.email = $('#userEmail').val();
            $scope.newUser.password = $('#userPassword').val();
            $scope.newUser.newPassword = $('#userNewPassword').val();

            discussionForumService.updateUser($scope.newUser).then(function (res) {

                messageService.success('Profile updated successfully');
            }, function (err) {

                messageService.error(err.data.message);
            });
        }
        
        $scope.isUserLoggedIn = function () {
            return angular.isDefined($sessionStorage.get("token")) && $sessionStorage.get("token").trim().length > 0;
        };
        
        $scope.getUserRole = function() {
            return $sessionStorage.get('accomodation');
        }
        
        $scope.getLatest50Discussions = function () {
            discussionForumService.getLatest50Discussions().then(function (res) {

                $scope.discussions = res.data;
                $scope.title = 'Latest Discussions';
            }, function (err) {

                messageService.error(err.data.message);
            });
        };  
        $scope.getDiscussionsByCurrentUser = function () {
            discussionForumService.getCurrentUser().then(function (res) {

                discussionForumService.getDiscussionsByUser(res.data.userId).then(function (res) {

                    $scope.discussions = res.data;
                    $scope.title = 'Your Discussions';
                }, function (err) {

                    messageService.error(err.data.message);
                });
            }, function (err) {

                messageService.error(err.data.message);
            });
            
        };        
        $scope.getTrendingDiscussions = function () {
            discussionForumService.getTrendingDiscussions().then(function (res) {
                
                $scope.discussions = res.data;
                $scope.title = 'Trending Discussions';
            }, function (err) {

                messageService.error(err.data.message);
            });
        };
        $scope.getHot50OpenDiscussions = function () {
            discussionForumService.getHot50OpenDiscussions().then(function (res) {

                $scope.discussions = res.data;
            }, function (err) {

                messageService.error(err.data.message);
            });
        };
        $scope.getLatest50ClosedDiscussions = function () {
            discussionForumService.getLatest50ClosedDiscussions().then(function (res) {


                $scope.discussions = res.data;
            }, function (err) {

                messageService.error(err.data.message);
            });
        };
        $scope.getDiscussionsCreatedSinceWeek = function () {
            discussionForumService.getDiscussionsCreatedSinceWeek().then(function (res) {

                $scope.discussions = res.data;
            }, function (err) {

                messageService.error(err.data.message);
            });
        };
        
        $scope.getUsers = function () {
            discussionForumService.getUsers().then(function (res) {
                
                $scope.users = res.data;
            }, function (err) {

                messageService.error(err.data.message);
            });
        };
        
        $scope.getUserById = function () {
            discussionForumService.getUserById($scope.userId).then(function (res) {
                
                $scope.user = res.data;
                discussionForumService.getDiscussionsByUser(res.data.userId).then(function (res) {

                    $scope.discussions = res.data;
                    $scope.title = 'Discussions by ' + $scope.user.fullName;
                }, function (err) {

                    messageService.error(err.data.message);
                });
            }, function (err) {

                messageService.error(err.data.message);
            });
        };


        $scope.getDiscussionsByTagId = function (tagId) {
            discussionForumService.getDiscussionsByTagId(tagId).then(function (res) {

                $scope.discussions = res.data;
                $scope.title = 'Discussions tagged: ' + $scope.activeTagName;
            }, function (err) {

                messageService.error(err.data.message);
            });
        };
        $scope.getDiscussionsCreatedSinceMonth = function () {
            discussionForumService.getDiscussionsCreatedSinceMonth().then(function (res) {


                $scope.discussions = res.data;
            }, function (err) {

                messageService.error(err.data.message);
            });
        };
        $scope.searchDiscussionsByText = function () {
            discussionForumService.searchDiscussionsByText($scope.searchText).then(function (res) {

                $scope.discussions = res.data;
                $scope.title = 'Search results for text: ' + $scope.searchText;
            }, function (err) {

                messageService.error(err.data.message);
            });
        };

        $scope.getDiscussionById = function () {
            discussionForumService.getDiscussionById($scope.activeDiscussionId).then(function (res) {

                $scope.discussion = res.data;
            }, function (err) {

                messageService.error(err.data.message);
            });
        };

        $scope.getRepliesForDiscussion = function (discussionId) {

            discussionForumService.getRepliesForDiscussion(discussionId).then(function (res) {

                $scope.replies = res.data;
            }, function (err) {

                messageService.error(err.data.message);
            });
        };

        $scope.getTopContributor = function () {
            discussionForumService.getTopContributor().then(function (res) {

                $scope.topContributor = res.data;
            }, function (err) {

                messageService.error(err.data.message);
            });
        };
        
        $scope.getLatestUser = function () {
            discussionForumService.getLatestUser().then(function (res) {

                $scope.latestUser = res.data;
            }, function (err) {

                messageService.error(err.data.message);
            });
        };

        $scope.getTopicTags = function (topicId, topic) {
            discussionForumService.getTopicTags(topicId).then(function (res) {
                if (topic) {

                    topic.tags = res.data;
                } else {

                    $scope.topicTags = res.data;
                }

            }, function (err) {

                messageService.error(err.data.message);
            });
        };

        $scope.getReplyTags = function (replyId, reply) {
            discussionForumService.getReplyTags(replyId).then(function (res) {

                reply.tags = res.data;
            }, function (err) {

                messageService.error(err.data.message);
            });
        };

        $scope.searchTagsByName = function () {

            var value = $("#tagSearchText").val();
            if (value.trim().length === 0) {
                $scope.foundTags = [];
            } else {

                discussionForumService.searchTagsByName(value).then(function (res) {

                    $scope.foundTags = res.data;
                }, function (err) {

                    messageService.error(err.data.message);
                });
            }
        };
        
        $scope.getUserTagsByUserId = function (user) {
            discussionForumService.getUserTagsByUserId(user.userId).then(function (res) {

                user.tags = res.data;
            }, function (err) {

                messageService.error(err.data.message);
            });
        };
        
        $scope.getUserTags = function () {
            discussionForumService.getUserTags().then(function (res) {

                $scope.topContributorTags = res.data;
            }, function (err) {

                messageService.error(err.data.message);
            });
        };
        $scope.getTrendingTags = function () {

            discussionForumService.getTrendingTags().then(function (res) {

                $scope.trendingTags = res.data;
            }, function (err) {

                messageService.error(err.data.message);
            });
        };

        $scope.newReply = {};
        $scope.newReply.replyText = '';
        $scope.newReply.tags = [];
        $scope.postReplyToTopic = function () {
            
            var plainText = $filter('plain_preview')($scope.newReply.replyText);
            if (plainText.trim() === '...') {
                messageService.error('Please provide some text in your reply');
                return;
            }
            
            $scope.newReply.discussionId = $scope.activeDiscussionId;
            $scope.newReply.createdBy = "1";
            
            discussionForumService.createReply($scope.newReply).then(function (res) {

                $scope.getRepliesForDiscussion($scope.activeDiscussionId);
                $scope.newReply.replyText = '';
                messageService.success("Reply successfully posted!");
            }, function (err) {

                messageService.error(err.data.message);
            });
        };

        $scope.newDiscussion = {};
        $scope.newDiscussion.description = '';
        $scope.newDiscussion.tags = [];
        $scope.insertDiscussion = function () {
            var plainText = $filter('plain_preview')($scope.newDiscussion.description);
            if (plainText.trim() === '...' || $scope.newDiscussion.topic.trim() === '') {
                messageService.error('Please provide a topic and description');
                return;
            }
            $scope.newDiscussion.createdBy = "1";
            discussionForumService.insertDiscussion($scope.newDiscussion).then(function (res) {
                
                messageService.success('Discussion created successfully');
                $state.go('app.viewDiscussion', {
                    "discussionId": res.data.discussionId
                });
            }, function (err) {

                messageService.error(err.data.message);
            });
        }; 
        
        $scope.markReplyAsAnswer = function (reply) {
            
            discussionForumService.markReplyAsAnswer(reply).then(function (res) {
                
                $state.reload();
                // reload current state
            }, function (err) {

                messageService.error(err.data.message);
            });
        };
        $scope.deleteDiscussion = function (discussion) {
            
            discussionForumService.deleteDiscussion(discussion).then(function (res) {
                
                 messageService.success("Discussion deleted successfully");
                if ($state.current.name === 'app.welcome') {
                    $state.reload();
                } else {
                    $state.go('app.welcome');
                }
                
            }, function (err) {

                messageService.error(err.data.message);
            });
        };
        $scope.deleteReply = function (reply) {
            
            discussionForumService.deleteReply(reply).then(function (res) {
                
                messageService.success("Reply deleted successfully");
                $state.reload();
                // reload current state
            }, function (err) {

                messageService.error(err.data.message);
            });
        };

        $scope.closeDiscussion = function (discussion) {
            
            discussionForumService.closeDiscussion(discussion).then(function (res) {
                
                $state.reload();
                // reload current state
            }, function (err) {

                messageService.error(err.data.message);
            });
        };


        $scope.selectTagForAddition = function (tag, object) {
            discussionForumService.fakeCall().then(function (res) {

                if (object.tags.indexOf(tag) === -1) {

                    object.tags.push(tag);
                }
            }, function (err) {

                messageService.error(err.data.message);
            });

        };

        $scope.selectTagForRemoval = function (tag, object) {

            discussionForumService.fakeCall().then(function (res) {

                var index = object.tags.indexOf(tag);
                if (index !== -1) {

                    object.tags.splice(index, 1);
                }
            }, function (err) {

                messageService.error(err.data.message);
            });

        };

}]);