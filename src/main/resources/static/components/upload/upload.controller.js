angular.module('app').controller('UploadController', UploadController);

UploadController.$inject = ['$scope', '$mdDialog', 'productService'];

function UploadController($scope, $mdDialog, productService) {

    $scope.dataFile = [];
    $scope.invalid = false;
    $scope.uploadFile = uploadFile;
    $scope.cancelForm = cancelForm;

    function cancelForm() {
        $mdDialog.cancel();
    }

    function uploadFile() {
        if ($scope.dataFile[0] !== undefined && $scope.dataFile[0].name.split('.').pop() === 'csv') {
            $scope.invalid = false;
            productService.upload($scope.dataFile)
                .then(function () {
                    $mdDialog.hide();
                })
        } else {
            $scope.invalid = true;
        }

    }
}