angular.module('connector')
    .filter('toDate', function() {
        return function(date) {
            if (date) {
                var dt = new Date(date.replace(/\+.*/,''));
                return dt ? dt : date;
            }
            return date;
        };
    })
    .filter('languageItem', function() {
        return function(languageList, lang) {
            var languageObject;
            angular.forEach(languageList, function(item) {
                if (item.lang === lang) {
                    languageObject = item;
                }
            });

            if (languageObject) {
                return languageObject;
            }
            return languageList && languageList.length > 0 ? languageList[0] : '';
        };
    })
    .filter('capitalize', function() {
        return function(text) {
            if(text){
                var firstLetter = text[0];
                var otherLetters = text.substring(1);
                return firstLetter.toUpperCase() + otherLetters.toLowerCase();
            }
        };
    })
    .filter('prettyprint', function() {
        return function(xml) {
            return xml ? vkbeautify.xml(xml) : "";
        };
    })
    .filter('enumToReadable', function() {
        return function(enumName) {
            if(enumName) {
                var readable = enumName.replace(/_/g, ' ');
                var firstLetter = readable[0];
                var otherLetters = readable.substring(1);
                return firstLetter.toUpperCase() + otherLetters.toLowerCase();
            }
            return '';
        };
    });