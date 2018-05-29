//wrapped

(function () {

    'use strict';

    angular.module('app').controller('DialogController', DialogController);

    DialogController.$inject = ['$mdDialog', 'dataProduct', 'categories', 'productService'];

    function DialogController($mdDialog, dataProduct, categories, productService) {

        var vm = this;

        vm.product = dataProduct;
        vm.categories = categories;
        vm.saveProduct = saveProduct;
        vm.cancelForm = cancelForm;

        function cancelForm() {
            $mdDialog.cancel();
        }

        function saveProduct() {
            if (vm.productForm.$valid) {
                productService.saveProduct(vm.product)
                    .then(function () {
                        $mdDialog.hide();
                    });
            }
        }
    }
}());
