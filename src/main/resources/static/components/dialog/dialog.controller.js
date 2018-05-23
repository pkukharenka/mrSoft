angular.module('app').controller('DialogController', DialogController);

DialogController.$inject = ['$scope', '$mdDialog', 'dataProduct', 'categories', 'productService'];

function DialogController($scope, $mdDialog, dataProduct, categories, productService) {
    $scope.product = dataProduct;
    $scope.categories = categories;
    $scope.saveProduct = saveProduct;

    $scope.cancelForm = function () {
        $mdDialog.cancel();
    };

    function saveProduct(product) {
        if ($scope.productForm.$valid) {
            productService.saveProduct(product)
                .then(function () {
                    $mdDialog.hide();
                });
        }
    }
}