angular.module('app').controller('CategoryController', CategoryController);

CategoryController.$inject = ['$scope', '$mdDialog', '$q', 'productService', 'categories'];

function CategoryController($scope, $mdDialog, $q, productService, categories) {

    $scope.categories = categories;
    $scope.category = {};
    $scope.checkedCategory = false;
    $scope.categoryKeyword = '';

    $scope.manageCategory = manageCategory;
    $scope.saveCategory = saveCategory;
    $scope.deleteCategories = deleteCategories;
    $scope.checkAllCategories = checkAllCategories;

    $scope.cancelForm = function () {
        $mdDialog.cancel();
    };

    function manageCategory(category) {
        $scope.category = category;
    }

    function saveCategory(category) {
        productService.saveCategory(category)
            .then(function () {
                productService.getCategories()
                    .then(function (response) {
                        $scope.categories = response;
                        $scope.category = {};
                    });
            })
    }

    function checkAllCategories() {
        if ($scope.checkedCategory) {
            $scope.categories.forEach(function (el) {
                el.checked = true;
            })
        } else {
            $scope.categories.forEach(function (el) {
                el.checked = false;
            })
        }
    }

    function deleteCategories() {
        var promises = [];
        $scope.categories.forEach(function (category) {
            if (category.checked) {
                promises.push(productService.deleteCategory(category.id))
            }
        });
        $q.all(promises).then(function () {
            productService.getCategories()
                .then(function (response) {
                    $scope.categories = response;
                });
        })
    }
}