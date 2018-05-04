angular.module('app').factory('productService', productService);

productService.$inject = ['$http', '$log'];

function productService($http, $log) {

    const domain = 'http://localhost:8080';

    return {
        getProducts: getProducts,
        loadCategories: loadCategories,
        saveProduct: saveProduct,
        deleteProduct: deleteProduct,
        download: download,
        upload: upload
    };

    function getProducts() {
        return $http.get(domain + '/product/')
            .then(completeFunc)
            .catch(errorFunc)
    }

    function loadCategories() {
        return $http.get(domain + '/category/')
            .then(completeFunc)
            .catch(errorFunc)
    }

    function saveProduct(product) {
        return $http.post(domain + '/product/', product)
            .then(completeFunc)
            .catch(errorFunc)
    }

    function deleteProduct(id) {
        return $http.post(domain + '/product/delete/', id)
            .then(completeFunc)
            .catch(errorFunc)
    }

    function download() {
        return $http.get(domain + '/product/download/')
            .then(completeFunc)
            .catch(errorFunc)
    }

    function upload(files) {
        var fd = new FormData();
        fd.append('file', files);
        return $http.post(domain + '/product/upload/', fd, {
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