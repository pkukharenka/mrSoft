angular.module('app', ['ngRoute', 'ngMaterial', 'ngMessages']);

angular.module('app').config(config);

config.$inject = ['$routeProvider', '$locationProvider', '$httpProvider'];

function config($routeProvider, $locationProvider, $httpProvider) {
    $httpProvider.defaults.useXDomain = true;
    delete $httpProvider.defaults.headers.common['X-Requested-With'];
    $locationProvider.hashPrefix('');
    $routeProvider
        .when('/', {
            templateUrl: './components/main/main.html',
            controller: MainController,
            resolve: {
                'getAllProducts': function (productService) {
                    return productService.getProducts()
                },
                'getCategories': function (productService) {
                    return productService.getCategories()
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