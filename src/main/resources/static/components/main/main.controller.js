//wrapped

(function () {
    'use strict';

    angular.module('app').controller('MainController', MainController);

    MainController.$injetc = ['$mdDialog', '$route', '$q', '$window',
        'getAllProducts', 'getCategories', 'productService'];

    function MainController($mdDialog, $route, $q, $window,
                            getAllProducts, getCategories, productService) {

        var vm = this;

        vm.products = getAllProducts;
        vm.categories = getCategories;
        vm.checked = false;
        vm.searchKeyword = '';

        vm.manageProduct = manageProduct;
        vm.deleteProducts = deleteProducts;
        vm.check = check;
        vm.download = download;
        vm.upload = upload;
        vm.addCategory = addCategory;

        function manageProduct(ev, product) {
            $mdDialog.show({
                locals: {
                    dataProduct: product,
                    categories: vm.categories
                },
                controller: 'DialogController as vm',
                templateUrl: './components/dialog/save.dialog.html',
                parent: angular.element(document.body),
                targetEvent: ev,
                onRemoving: updateTable,
                clickOutsideToClose: true
            })
        }

        function updateTable() {
            $route.reload();
        }

        function check() {
            if (vm.checked) {
                vm.products.forEach(function (el) {
                    el.checked = true;
                })
            } else {
                vm.products.forEach(function (el) {
                    el.checked = false;
                })
            }
        }

        function deleteProducts() {
            var promises = [];
            vm.products.forEach(function (product) {
                if (product.checked) {
                    promises.push(productService.deleteProduct(product.id))
                }
            });
            $q.all(promises).then(function () {
                $route.reload();
            })
        }

        function download() {
            $window.location = '/product/download';
        }

        function upload() {
            $mdDialog.show({
                controller: 'UploadController as vm',
                templateUrl: './components/upload/upload.dialog.html',
                parent: angular.element(document.body),
                onRemoving: updateTable,
                clickOutsideToClose: true
            })
        }

        function addCategory() {
            $mdDialog.show({
                controller: 'CategoryController as vm',
                locals: {
                    categories: vm.categories
                },
                templateUrl: './components/category/category.html',
                parent: angular.element(document.body),
                onRemoving: updateTable,
                clickOutsideToClose: true
            })
        }

    }
}());
