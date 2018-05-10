angular.module('app').controller('SecondController', SecondController);

SecondController.$inject = ['$scope', '$filter', 'getDataFromMrSoft', 'secondService'];

function SecondController($scope, $filter, getDataFromMrSoft) {

    $scope.byLenght = byLenght;
    $scope.bySubstring = bySubstring;
    $scope.searchKey = '';
    $scope.sensitive = '';
    $scope.filtredData = [];
    var testValues = getDataFromMrSoft;


    function byLenght() {
        console.log($scope.filtredData);
        $scope.filtredData = $filter('lengthFilter')(testValues.data, $scope.searchKey);
    }

    function bySubstring() {
        if ($scope.sensitive) {
            $scope.filtredData = $filter('sensitiveFilter')(testValues.data, $scope.searchKey);

        } else {
            $scope.filtredData = $filter('filter')(testValues.data, $scope.searchKey);

        }

    }
}