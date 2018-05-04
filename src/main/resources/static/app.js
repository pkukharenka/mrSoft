angular.module('app', ['ngRoute', 'ngMaterial', 'ngMessages']);

angular.module('app').config(config);

config.$inject = ['$routeProvider', '$locationProvider'];

function config($routeProvider, $locationProvider) {
    $locationProvider.hashPrefix('');
    $routeProvider
        .when('/product/', {
            templateUrl: './components/main/main.html',
            controller: MainController,
            resolve: {
                'getAllProducts': function (productService) {
                    return productService.getProducts()
                },
                'getCategories': function (productService) {
                    return productService.loadCategories()
                }
            }
        })
        .when('/second/', {
            templateUrl: './components/second/second.html',
            controller: SecondController,
            resolve: {
                'getDataFromMrSoft': function (secondService) {
                    return secondService.findValues()
                }
            }
        })


}