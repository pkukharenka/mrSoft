//wrapped

(function () {
    'use strict';

    angular.module('app').controller('CategoryController', CategoryController);

    CategoryController.$inject = ['$mdDialog', '$q', 'productService', 'categories'];

    function CategoryController($mdDialog, $q, productService, categories) {

        var vm = this;
        vm.categories = categories;
        vm.category = {};
        vm.checkedCategory = false;
        vm.categoryKeyword = '';

        vm.manageCategory = manageCategory;
        vm.saveCategory = saveCategory;
        vm.deleteCategories = deleteCategories;
        vm.checkAllCategories = checkAllCategories;

        vm.cancelForm = function () {
            $mdDialog.cancel();
        };

        function manageCategory(category) {
            vm.category = category;
        }

        function saveCategory() {
            productService.saveCategory(vm.category)
                .then(function () {
                    productService.getCategories()
                        .then(function (response) {
                            vm.categories = response;
                            vm.category = {};
                        });
                })
        }

        function checkAllCategories() {
            if (vm.checkedCategory) {
                vm.categories.forEach(function (el) {
                    el.checked = true;
                })
            } else {
                vm.categories.forEach(function (el) {
                    el.checked = false;
                })
            }
        }

        function deleteCategories() {
            var promises = [];
            vm.categories.forEach(function (category) {
                if (category.checked) {
                    promises.push(productService.deleteCategory(category.id))
                }
            });
            $q.all(promises).then(function () {
                productService.getCategories()
                    .then(function (response) {
                        vm.categories = response;
                    });
            })
        }
    }
}());

