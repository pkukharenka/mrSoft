angular.module('app', ['ngRoute', 'ngMaterial', 'ngMessages', 'pascalprecht.translate']);

angular.module('app').config(config);

config.$inject = ['$routeProvider', '$locationProvider', '$translateProvider'];

function config($routeProvider, $locationProvider, $translateProvider) {
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
        });
    var translationRu = {
        'LANGUAGE': 'Изменить язык',
        'REQUIRED': 'Данное поле обязательно для заполнения',
        'CATEGORY_HEADER': 'Категории',
        'UPLOAD': {
            'HEADER': 'Форма импорта',
            'NAME': 'Наименование файла',
            'INVALID': 'Файл должен иметь расширение .csv'
        },

        'CATEGORY_UPDATE_HEADER': 'Редактирование категории',
        'SEARCH': 'Поиск...',
        'SENSITIVE': 'Чувствительность регистра',
        'VALUE_PLACEHOLDER': 'Введите значение',
        'FIND_VALUES': 'Найденные значения',
        'BUTTON': {
            'TOOLTIP': {
                'DELETE': 'Удалить',
                'ADD_CAT': 'Добавить категорию',
                'ADD_PRODUCT': 'Добавить продукт',
                'DOWNLOAD': 'Экспорт данных',
                'UPLOAD': 'Импорт данных'
            },
            'SUBMIT': 'Ok',
            'CANCEL': 'Отмена',
            'LENGTH': 'По длине',
            'SUBSTRING': 'По подстроке'
        },

        'TH': {
            'CATEGORY': 'Категория',
            'PRODUCT': 'Наименование продукта',
            'PRICE': 'Цена',
            'COUNT': 'Количество',
            'DESC': 'Описание'

        },
        'DIALOG': {
            'HEADER': 'Форма добавления (обновления) продукта'

        },
        'CATEGORY': {
            'NAME': 'Категория товара:',
            'PLACEHOLDER': 'Категория'
        },
        'PRODUCT': {
            'NAME': 'Название товара:',
            'PRICE': 'Цена товара:',
            'COUNT': 'Количество товара',
            'DESC': 'Описание товара',
            'NAME_LENGTH': 'Наименование товара должно быть не более 255 символов',
            'DESC_LENGTH': 'Описание не должно быть более 1000 символов.'
        }
    };

    var translationEn = {
        'LANGUAGE': 'Change language',
        'REQUIRED': 'This field is required.',
        'CATEGORY_HEADER': 'Category',
        'UPLOAD': {
            'HEADER': 'Import form',
            'NAME': 'File name',
            'INVALID': 'The file must have the extension .csv'
        },

        'CATEGORY_UPDATE_HEADER': 'Category editing',
        'SEARCH': 'Search...',
        'SENSITIVE': 'Sensitivity of the register',
        'VALUE_PLACEHOLDER': 'Enter value',
        'FIND_VALUES': 'The values ​​that were found',
        'BUTTON': {
            'TOOLTIP': {
                'DELETE': 'Delete',
                'ADD_CAT': 'Add category',
                'ADD_PRODUCT': 'Add product',
                'DOWNLOAD': 'Exporting data',
                'UPLOAD': 'Importing data'
            },
            'SUBMIT': 'Submit',
            'CANCEL': 'Cancel',
            'LENGTH': 'By length',
            'SUBSTRING': 'By substring'
        },

        'TH': {
            'CATEGORY': 'Category',
            'PRODUCT': 'Product name',
            'PRICE': 'Price',
            'COUNT': 'Count',
            'DESC': 'Description'

        },
        'DIALOG': {
            'HEADER': 'The form of adding (updating) the product'

        },
        'CATEGORY': {
            'NAME': 'Product category:',
            'PLACEHOLDER': 'Category'
        },
        'PRODUCT': {
            'NAME': 'Product name:',
            'PRICE': 'Product price:',
            'COUNT': 'Product count',
            'DESC': 'Product description',
            'NAME_LENGTH': 'The name of the product must be no more than 255 characters.',
            'DESC_LENGTH': 'The description can not be more than 1000 characters.'
        }
    };

    $translateProvider.translations('ru', translationRu);
    $translateProvider.translations('en', translationEn);
    $translateProvider.preferredLanguage('en');
    $translateProvider.useSanitizeValueStrategy('sceParameters');

}