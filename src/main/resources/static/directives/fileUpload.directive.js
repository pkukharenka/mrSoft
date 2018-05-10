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

        element.bind('change', function(){
            var files = [];
            angular.forEach(element[0].files, function (file) {
                files.push(file)
            });
            scope.$apply(function(){
                modelSetter(scope, files);
            });
        });
    }
}