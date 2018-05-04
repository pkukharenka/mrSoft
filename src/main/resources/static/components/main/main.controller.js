angular.module('app').controller('MainController', MainController);

MainController.$injetc = ['$scope', '$mdDialog', '$route', '$q', 'getAllProducts', 'getCategories', 'productService'];

function MainController($scope, $mdDialog, $route, $q, getAllProducts, getCategories, productService) {

    $scope.products = getAllProducts;
    $scope.categories = getCategories;
    $scope.checked = false;
    $scope.searchKeyword = '';

    $scope.manageProduct = manageProduct;
    $scope.deleteProducts = deleteProducts;
    $scope.check = check;
    $scope.download = download;
    $scope.upload = upload;

    function manageProduct(ev, product) {
        $mdDialog.show({
            locals: {
                dataProduct: product,
                categories: $scope.categories
            },
            controller: DialogController,
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
        if ($scope.checked) {
            $scope.products.forEach(function (el) {
                el.checked = true;
            })
        } else {
            $scope.products.forEach(function (el) {
                el.checked = false;
            })
        }

    }

    function deleteProducts() {
        var promises = [];
        $scope.products.forEach(function (product) {
            if (product.checked) {
                promises.push(productService.deleteProduct(product.id))
            }
        });
        $q.all(promises).then(function () {
            $route.reload();
        })
    }

    function download() {
        productService.download();
    }

    function upload() {
        $mdDialog.show({
            controller: UploadController,
            templateUrl: './components/upload/upload.dialog.html',
            parent: angular.element(document.body),
            clickOutsideToClose: true
        })
    }


}