angular.module('panorama')
    /*
    getLatest50Discussions
    getHot50OpenDiscussions
    getLatest50ClosedDiscussions
    getDiscussionsCreatedSinceWeek
    getDiscussionsCreatedSinceMonth
    searchDiscussionsByText
    getDiscussionsByUser
    getRepliesForDiscussion

    insertDiscussion
    updateDiscussion
    closeDiscussion
    createReply
    updateReply
    markReplyAsAnswer
    voteReply
    createUser
    */

.service('discussionForumService', ['panoramaService', function (panoramaService) {

        /*Service Getters*/
        this.getLatest50Discussions = function () {
            return panoramaService.fetchFromServer('getLatest50Discussions');
        };
        this.getHot50OpenDiscussions = function () {
            return panoramaService.fetchFromServer('getHot50OpenDiscussions');
        };        
        this.getTrendingDiscussions = function () {
            return panoramaService.fetchFromServer('getTrendingDiscussions');
        };
        this.getLatest50ClosedDiscussions = function () {
            return panoramaService.fetchFromServer('getLatest50ClosedDiscussions');
        };
        this.getDiscussionsCreatedSinceWeek = function () {
            return panoramaService.fetchFromServer('getDiscussionsCreatedSinceWeek');
        };
        this.getDiscussionsCreatedSinceMonth = function () {
            return panoramaService.fetchFromServer('getDiscussionsCreatedSinceMonth');
        };
        this.searchDiscussionsByText = function (text) {
            return panoramaService.fetchFromServer('searchDiscussionsByText/' + text);
        };
        this.getDiscussionsByUser = function (userId) {
            return panoramaService.fetchFromServerWithCredentials('getDiscussionsByUser/' + userId);
        };
        this.getRepliesForDiscussion = function (discussionId) {
            return panoramaService.fetchFromServer('getRepliesForDiscussion/' + discussionId);
        };
        this.getTopContributor = function () {
            return panoramaService.fetchFromServer('getTopContributor');
        };
        this.getTopicTags = function (topicId) {
            return panoramaService.fetchFromServer('getTopicTags/' + topicId);
        };
        this.getReplyTags = function (replyId) {
            return panoramaService.fetchFromServer('getReplyTags/' + replyId);
        };
        this.getUserTags = function () {
            return panoramaService.fetchFromServer('getUserTags');
        };
        this.getUserTagsByUserId = function (userId) {
            return panoramaService.fetchFromServer('getUserTagsByUserId/' + userId);
        };
        this.getAvatars = function () {
            return panoramaService.fetchFromServer('getAvatars');
        };
        this.getCurrentUser = function () {
            return panoramaService.fetchFromServerWithCredentials('getCurrentUser');
        };
        this.getTrendingTags = function () {
            return panoramaService.fetchFromServer('getTrendingTags');
        };
        this.getUsers = function () {
            return panoramaService.fetchFromServer('getUsers');
        };
        this.getDiscussionById = function (discussionId) {
            return panoramaService.fetchFromServer('getDiscussionById/' + discussionId);
        };
        this.getUserById = function (userId) {
            return panoramaService.fetchFromServer('getUserById/' + userId);
        };
        this.getDiscussionsByTagId = function (tagId) {
            return panoramaService.fetchFromServer('getDiscussionsByTagId/' + tagId);
        };
        this.searchTagsByName = function (tagName) {
            return panoramaService.fetchFromServer('searchTagsByName/' + tagName);
        };
        this.getLatestUser = function () {
            return panoramaService.fetchFromServer('getLatestUser');
        };
        this.fakeCall = function () {
            return panoramaService.fetchFromServer('fakeCall');
        };

        /*Service Posters*/
        this.insertDiscussion = function (discussion) {
            return panoramaService.postToServer('insertDiscussion', discussion);
        };
        this.updateDiscussion = function (discussion) {
            return panoramaService.postToServer('updateDiscussion', discussion);
        };
        this.closeDiscussion = function (discussion) {
            return panoramaService.postToServer('closeDiscussion', discussion);
        };
        this.createReply = function (reply) {
            return panoramaService.postToServer('createReply', reply);
        };
        this.updateReply = function (reply) {
            return panoramaService.postToServer('updateReply', reply);
        };
        this.markReplyAsAnswer = function (reply) {
            return panoramaService.postToServer('markReplyAsAnswer', reply);
        };
        this.deleteDiscussion = function (discussion) {
            return panoramaService.postToServer('deleteDiscussion', discussion);
        };
        this.deleteReply = function (reply) {
            return panoramaService.postToServer('deleteReply', reply);
        };
        this.voteReply = function (replyVote) {
            return panoramaService.postToServer('voteReply', replyVote);
        };
        this.createUser = function (user) {
            return panoramaService.postToServer('createUser', user);
        };
        this.login = function (user) {
            return panoramaService.postFormToServer('login', user);
        };
        this.logout = function (user) {
            return panoramaService.postToServer('logout', {});
        };
        this.register = function (user) {
            return panoramaService.postFormToServer('createUser', user);
        };
        this.updateUser = function (user) {
            return panoramaService.postFormToServerWithCreds('updateUser', user);
        };
}])
    .service('panoramaService', ['$http', '$sessionStorage', function ($http, $sessionStorage) {

        var url = 'http://localhost:8080/';
        /**
         * calls the PanoramaServices web services to fetch data in JSON Rest Format using GET method
         * parameters:
         * funct: the ws function with parameters
         * return:
         * response as JSON array in a $promise
         */
        this.fetchFromServer = function (funct) {

            return $http.get(url + 'PanoramaServices/json/' + funct);
        };
        this.fetchFromServerWithCredentials = function (funct) {
            return $http({
                method: 'GET',
                url: url + 'PanoramaServices/json/' + funct,
                headers: {
                    'Authorization': "Bearer " + $sessionStorage.get("token")
                }
            });
        };

        this.fetchFileFromServer = function (funct) {

            return $http.get(url + 'PanoramaServices/json/' + funct, {
                responseType: "arraybuffer"
            });
        };

        /**
         * calls the PanoramaServices web services to push data in JSON Rest Format using POST method
         * parameters:
         * funct: the ws function
         * params: query parameters
         * return:
         * response as JSON array/object in a $promise
         */
        this.postToServer = function (funct, params) {

            if (angular.isDefined($sessionStorage.get("token"))) {
                return $http.post(url + 'PanoramaServices/json/' + funct, params, {
                    headers: {
                        'Authorization': "Bearer " + $sessionStorage.get("token")
                    }
                });
            } else {

                return $http.post(url + 'PanoramaServices/json/' + funct, params);
            }

        };

        this.postFormToServer = function (funct, params) {
            
            return $http({
                method: 'POST',
                url: url + 'PanoramaServices/json/' + funct,
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded'
                },
                transformRequest: function (obj) {
                    var str = [];
                    for (var p in obj)
                        str.push(encodeURIComponent(p) + "=" + encodeURIComponent(obj[p]));
                    return str.join("&");
                },
                data: params
            });
        };
        this.postFormToServerWithCreds = function (funct, params) {
            return $http({
                method: 'POST',
                url: url + 'PanoramaServices/json/' + funct,
                headers: {
                    'Authorization': "Bearer " + $sessionStorage.get("token"),
                    'Content-Type': 'application/x-www-form-urlencoded'
                },
                transformRequest: function (obj) {
                    var str = [];
                    for (var p in obj)
                        str.push(encodeURIComponent(p) + "=" + encodeURIComponent(obj[p]));
                    return str.join("&");
                },
                data: params
            });
        };
    }])
    .service('messageService', ['ngToast', '$sce', function (ngToast, $sce) {

        this.success = function (message) {

            ngToast.create({
                className: 'success',
                horizontalPosition: 'center',
                verticalPosition: 'bottom',
                content: $sce.trustAsHtml('<i style="font-size: 1.5em;line-height: 1.5em;" class="fa fa-check-circle" aria-hidden="true"></i>&nbsp;&nbsp;<span style="line-height: 1.5em;">' + message + '</span>')
            });
        };
        this.warn = function (message) {

            ngToast.create({
                className: 'warning',
                horizontalPosition: 'center',
                verticalPosition: 'bottom',
                content: $sce.trustAsHtml('<i style="font-size: 1.5em;line-height: 1.5em;" class="fa fa-exclamation-triangle" aria-hidden="true"></i>&nbsp;&nbsp;<span style="line-height: 1.5em;">' + message + '</span>')
            });
        };
        this.error = function (message) {

            ngToast.create({
                className: 'danger',
                horizontalPosition: 'center',
                verticalPosition: 'bottom',
                content: $sce.trustAsHtml('<i style="font-size: 1.5em;line-height: 1.5em;" class="fa fa-times" aria-hidden="true"></i>&nbsp;&nbsp;<span style="line-height: 1.5em;">' + message + '</span>')
            });
        };
    }])
    .factory('$sessionStorage', ['$window', function ($window) {
        return {

            store: function (key, value) {

                $window.sessionStorage[key] = value;
            },
            get: function (key, defaultValue) {

                return $window.sessionStorage[key] || defaultValue;
            },
            storeObject: function (key, value) {

                $window.sessionStorage[key] = JSON.stringify(value);
            },
            getObject: function (key, defaultValue) {

                return JSON.parse($window.sessionStorage[key] || defaultValue);
            },
            remove: function (key) {

                $window.sessionStorage.removeItem(key);
            }
        }
}]);