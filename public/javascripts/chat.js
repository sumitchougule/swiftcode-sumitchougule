var app = angular.module('chatApp', ['ngMaterial']);

app.config(function ($mdThemingProvider) {
    $mdThemingProvider.theme('default')
        .primaryPalette('purple')
        .accentPalette('green');
});

app.controller('chatController', function ($scope) {
    $scope.messages = [{
            'sender': 'USER',
            'text': 'hello'
		},
        {
            'sender': 'BOT',
            'text': 'hii what can i do for u?'
 		}
 ];

});