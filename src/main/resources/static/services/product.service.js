angular.module('app').factory('productService', productService);

productService.$inject = ['$http', '$log'];

function productService($http, $log) {

    return {
        getProducts: getProducts,
        getCategories: getCategories,
        saveProduct: saveProduct,
        saveCategory: saveCategory,
        deleteProduct: deleteProduct,
        deleteCategory: deleteCategory,
        upload: upload
    };

    function getProducts() {
        return $http.get('/product/')
            .then(completeFunc)
            .catch(errorFunc)
    }

    function getCategories() {
        return $http.get('/category/')
            .then(completeFunc)
            .catch(errorFunc)
    }

    function saveProduct(product) {
        return $http.post('/product/', product)
            .then(completeFunc)
            .catch(errorFunc)
    }

    function saveCategory(category) {
        return $http.post('/category/', category)
            .then(completeFunc)
            .catch(errorFunc)
    }

    function deleteProduct(id) {
        return $http.post('/product/delete/', id)
            .then(completeFunc)
            .catch(errorFunc)
    }

    function deleteCategory(id) {
        return $http.post('/category/delete/', id)
            .then(completeFunc)
            .catch(errorFunc)
    }

    // function download() {
    //     return
    // }

    function upload(file) {
        var fd = new FormData();
        fd.append('file', file[0]);
        return $http.post('/product/upload/', fd, {
            headers: {'Content-Type': undefined},
            transformRequest: angular.identity
        })
            .then(completeFunc)
            .catch(errorFunc)
    }

    function completeFunc(response) {
        return response.data;
    }

    function errorFunc(error) {
        $log.error(error);
    }
}