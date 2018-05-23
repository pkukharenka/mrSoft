angular.module('app').controller('LayoutController', LayoutController);

LayoutController.$inject = ['$scope', '$translate'];

function LayoutController($scope, $translate) {

    $scope.currentNavItem = 'main';
    $scope.changeLanguage = changeLanguage;
    $scope.languageIcon = 'icons/USA.svg';

    function changeLanguage(type) {
        if (type === "ru") {
            $scope.languageIcon = 'icons/RU.svg';
        } else {
            $scope.languageIcon = 'icons/USA.svg';
        }
        $translate.use(type)
    }

}