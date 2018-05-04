angular.module('app').controller('UploadController', UploadController);

UploadController.$inject = ['$scope', '$mdDialog', 'productService'];

function UploadController($scope, $mdDialog, productService) {
    $scope.filename = [];
    $scope.uploadFile = uploadFile;

    $scope.cancelForm = function () {
        $mdDialog.cancel();
    };

    function uploadFile() {
        console.log($scope.filename);
        productService.upload($scope.filename)
    }
}