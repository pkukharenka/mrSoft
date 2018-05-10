angular.module('app').controller('UploadController', UploadController);

UploadController.$inject = ['$scope', '$mdDialog', 'productService'];

function UploadController($scope, $mdDialog, productService) {

    $scope.dataFiles = [];

    $scope.uploadFile = uploadFile;

    $scope.cancelForm = function () {
        $mdDialog.cancel();
    };

    function uploadFile() {
        productService.upload($scope.dataFiles)
            .then(function () {
                $mdDialog.hide();
            })
    }
}