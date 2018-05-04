angular.module('app').directive('fileModel', fileModel);

fileModel.$injetc = ['$parse'];

function fileModel($parse) {
    return {
        restrict: 'A',
        link: link
    };

    function link(scope, element, attrs) {
        var model = $parse(attrs.fileModel);
        var modelSetter = model.assign;

        element.bind('change', function () {
            scope.$apply(function () {
                modelSetter(scope, element[0].files[0]);
            });
        });
    }
}
