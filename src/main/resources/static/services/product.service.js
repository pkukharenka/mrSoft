angular.module('app').factory('productService', productService);

productService.$inject = ['$http', '$log'];

function productService($http, $log) {

    return {
        getProducts: getProducts,
        loadCategories: loadCategories,
        saveProduct: saveProduct,
        deleteProduct: deleteProduct,
        download: download,
        upload: upload
    };

    function getProducts() {
        return $http.get('/product/')
            .then(completeFunc)
            .catch(errorFunc)
    }

    function loadCategories() {
        return $http.get('/category/')
            .then(completeFunc)
            .catch(errorFunc)
    }

    function saveProduct(product) {
        return $http.post('/product/', product)
            .then(completeFunc)
            .catch(errorFunc)
    }

    function deleteProduct(id) {
        return $http.post('/product/delete/', id)
            .then(completeFunc)
            .catch(errorFunc)
    }

    function download() {
        return $http.get('/product/download/')
            .then(completeFunc)
            .catch(errorFunc)
    }

    function upload(files) {
        var fd = new FormData();
        fd.append('file', files);
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