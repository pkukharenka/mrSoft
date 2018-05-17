angular.module('app').controller('LayoutController', LayoutController);

LayoutController.$inject = ['$scope'];

function LayoutController($scope) {

    $scope.currentNavItem = 'main';

}