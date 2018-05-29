//wrapped

(function () {
    'use strict';

    angular.module('app').controller('UploadController', UploadController);

    UploadController.$inject = ['$mdDialog', 'productService'];

    function UploadController($mdDialog, productService) {

        var vm = this;
        vm.dataFile = [];
        vm.invalid = false;
        vm.uploadFile = uploadFile;
        vm.cancelForm = cancelForm;

        function cancelForm() {
            $mdDialog.cancel();
        }

        function uploadFile() {
            console.log(vm);
            var file = vm.dataFile[0];
            console.log(file);
            if (file !== undefined && file.name.split('.').pop() === 'csv') {
                vm.invalid = false;
                productService.upload(file)
                    .then(function () {
                        $mdDialog.hide();
                    })
            } else {
                vm.invalid = true;
            }

        }
    }
}());
