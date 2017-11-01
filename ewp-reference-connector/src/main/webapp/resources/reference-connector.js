angular
    .module('connector', ['menu', 'home', 'echo', 'iia', 'institution', 'ngResource', 'ngRoute', 'person', 'contact', 'mobilityParticipant', 'academicterm', 'los', 'loi', 'mobility'])
    .config(function ($routeProvider) {
        $routeProvider.when('/iia', {
                templateUrl: 'partials/iia.html',
                controller: 'IiaController'
            }).when('/newIia', {
                templateUrl: 'partials/new_iia_form.html',
                controller: 'IiaController'
            }).when('/home', {
                templateUrl: 'partials/home.html',
                controller: 'HomeController'
            }).when('/institution', {
                templateUrl: 'partials/institution.html',
                controller: 'InstitutionController'
            }).when('/echo', {
                templateUrl: 'partials/echo.html',
                controller: 'EchoController'
            }).when('/academicterm', {
                templateUrl: 'partials/academicterm.html',
                controller: 'AcademicTermController'
            }).when('/los', {
                templateUrl: 'partials/los.html',
                controller: 'LosController'
            }).when('/loi', {
                templateUrl: 'partials/loi.html',
                controller: 'LoiController'
            }).when('/contact', {
                templateUrl: 'partials/contact.html',
                controller: 'ContactController'
            }).when('/mobility-participant', {
                templateUrl: 'partials/mobility_participant.html',
                controller: 'MobilityParticipantController'
            }).when('/person', {
                templateUrl: 'partials/person.html',
                controller: 'PersonController'
            }).when('/mobility', {
                templateUrl: 'partials/mobility.html',
                controller: 'MobilityController'
            }).when('/institution-client', {
                templateUrl: 'partials/institution_client.html',
                controller: 'InstitutionClientController'
            }).when('/ounit-client', {
                templateUrl: 'partials/ounit_client.html',
                controller: 'OrganizationUnitClientController'
            }).when('/courses-client', {
                templateUrl: 'partials/courses_client.html',
                controller: 'CoursesClientController'
            }).when('/course-replication-client', {
                templateUrl: 'partials/course_replication_client.html',
                controller: 'CourseReplicationClientController'
            }).when('/iia-client', {
                templateUrl: 'partials/iia_client.html',
                controller: 'IiaClientController'
            }).otherwise({
                redirectTo: '/home'
            });
    });

/**
* vkBeautify - javascript plugin to pretty-print or minify text in XML, JSON, CSS and SQL formats.
*  
* Version - 0.99.00.beta 
* Copyright (c) 2012 Vadim Kiryukhin
* vkiryukhin @ gmail.com
* http://www.eslinstructor.net/vkbeautify/
* 
* Dual licensed under the MIT and GPL licenses:
*   http://www.opensource.org/licenses/mit-license.php
*   http://www.gnu.org/licenses/gpl.html
*
*   Pretty print
*
*        vkbeautify.xml(text [,indent_pattern]);
*        vkbeautify.json(text [,indent_pattern]);
*        vkbeautify.css(text [,indent_pattern]);
*        vkbeautify.sql(text [,indent_pattern]);
*
*        @text - String; text to beatufy;
*        @indent_pattern - Integer | String;
*                Integer:  number of white spaces;
*                String:   character string to visualize indentation ( can also be a set of white spaces )
*   Minify
*
*        vkbeautify.xmlmin(text [,preserve_comments]);
*        vkbeautify.jsonmin(text);
*        vkbeautify.cssmin(text [,preserve_comments]);
*        vkbeautify.sqlmin(text);
*
*        @text - String; text to minify;
*        @preserve_comments - Bool; [optional];
*                Set this flag to true to prevent removing comments from @text ( minxml and mincss functions only. )
*
*   Examples:
*        vkbeautify.xml(text); // pretty print XML
*        vkbeautify.json(text, 4 ); // pretty print JSON
*        vkbeautify.css(text, '. . . .'); // pretty print CSS
*        vkbeautify.sql(text, '----'); // pretty print SQL
*
*        vkbeautify.xmlmin(text, true);// minify XML, preserve comments
*        vkbeautify.jsonmin(text);// minify JSON
*        vkbeautify.cssmin(text);// minify CSS, remove comments ( default )
*        vkbeautify.sqlmin(text);// minify SQL
*
*/

(function() {

function createShiftArr(step) {

	var space = '    ';
	
	if ( isNaN(parseInt(step)) ) {  // argument is string
		space = step;
	} else { // argument is integer
		switch(step) {
			case 1: space = ' '; break;
			case 2: space = '  '; break;
			case 3: space = '   '; break;
			case 4: space = '    '; break;
			case 5: space = '     '; break;
			case 6: space = '      '; break;
			case 7: space = '       '; break;
			case 8: space = '        '; break;
			case 9: space = '         '; break;
			case 10: space = '          '; break;
			case 11: space = '           '; break;
			case 12: space = '            '; break;
		}
	}

	var shift = ['\n']; // array of shifts
	for(ix=0;ix<100;ix++){
		shift.push(shift[ix]+space); 
	}
	return shift;
}

function vkbeautify(){
	this.step = '    '; // 4 spaces
	this.shift = createShiftArr(this.step);
};

vkbeautify.prototype.xml = function(text,step) {

	var ar = text.replace(/>\s{0,}</g,"><")
				 .replace(/</g,"~::~<")
				 .replace(/\s*xmlns\:/g,"~::~xmlns:")
				 .replace(/\s*xmlns\=/g,"~::~xmlns=")
				 .split('~::~'),
		len = ar.length,
		inComment = false,
		deep = 0,
		str = '',
		ix = 0,
		shift = step ? createShiftArr(step) : this.shift;

		for(ix=0;ix<len;ix++) {
			// start comment or <![CDATA[...]]> or <!DOCTYPE //
			if(ar[ix].search(/<!/) > -1) { 
				str += shift[deep]+ar[ix];
				inComment = true; 
				// end comment  or <![CDATA[...]]> //
				if(ar[ix].search(/-->/) > -1 || ar[ix].search(/\]>/) > -1 || ar[ix].search(/!DOCTYPE/) > -1 ) { 
					inComment = false; 
				}
			} else 
			// end comment  or <![CDATA[...]]> //
			if(ar[ix].search(/-->/) > -1 || ar[ix].search(/\]>/) > -1) { 
				str += ar[ix];
				inComment = false; 
			} else 
			// <elm></elm> //
			if( /^<\w/.exec(ar[ix-1]) && /^<\/\w/.exec(ar[ix]) &&
				/^<[\w:\-\.\,]+/.exec(ar[ix-1]) == /^<\/[\w:\-\.\,]+/.exec(ar[ix])[0].replace('/','')) { 
				str += ar[ix];
				if(!inComment) deep--;
			} else
			 // <elm> //
			if(ar[ix].search(/<\w/) > -1 && ar[ix].search(/<\//) == -1 && ar[ix].search(/\/>/) == -1 ) {
				str = !inComment ? str += shift[deep++]+ar[ix] : str += ar[ix];
			} else 
			 // <elm>...</elm> //
			if(ar[ix].search(/<\w/) > -1 && ar[ix].search(/<\//) > -1) {
				str = !inComment ? str += shift[deep]+ar[ix] : str += ar[ix];
			} else 
			// </elm> //
			if(ar[ix].search(/<\//) > -1) { 
				str = !inComment ? str += shift[--deep]+ar[ix] : str += ar[ix];
			} else 
			// <elm/> //
			if(ar[ix].search(/\/>/) > -1 ) { 
				str = !inComment ? str += shift[deep]+ar[ix] : str += ar[ix];
			} else 
			// <? xml ... ?> //
			if(ar[ix].search(/<\?/) > -1) { 
				str += shift[deep]+ar[ix];
			} else 
			// xmlns //
			if( ar[ix].search(/xmlns\:/) > -1  || ar[ix].search(/xmlns\=/) > -1) { 
				str += shift[deep]+ar[ix];
			} 
			
			else {
				str += ar[ix];
			}
		}
		
	return  (str[0] == '\n') ? str.slice(1) : str;
}

vkbeautify.prototype.json = function(text,step) {

	var step = step ? step : this.step;
	
	if (typeof JSON === 'undefined' ) return text; 
	
	if ( typeof text === "string" ) return JSON.stringify(JSON.parse(text), null, step);
	if ( typeof text === "object" ) return JSON.stringify(text, null, step);
		
	return text; // text is not string nor object
}

vkbeautify.prototype.css = function(text, step) {

	var ar = text.replace(/\s{1,}/g,' ')
				.replace(/\{/g,"{~::~")
				.replace(/\}/g,"~::~}~::~")
				.replace(/\;/g,";~::~")
				.replace(/\/\*/g,"~::~/*")
				.replace(/\*\//g,"*/~::~")
				.replace(/~::~\s{0,}~::~/g,"~::~")
				.split('~::~'),
		len = ar.length,
		deep = 0,
		str = '',
		ix = 0,
		shift = step ? createShiftArr(step) : this.shift;
		
		for(ix=0;ix<len;ix++) {

			if( /\{/.exec(ar[ix]))  { 
				str += shift[deep++]+ar[ix];
			} else 
			if( /\}/.exec(ar[ix]))  { 
				str += shift[--deep]+ar[ix];
			} else
			if( /\*\\/.exec(ar[ix]))  { 
				str += shift[deep]+ar[ix];
			}
			else {
				str += shift[deep]+ar[ix];
			}
		}
		return str.replace(/^\n{1,}/,'');
}

//----------------------------------------------------------------------------

function isSubquery(str, parenthesisLevel) {
	return  parenthesisLevel - (str.replace(/\(/g,'').length - str.replace(/\)/g,'').length )
}

function split_sql(str, tab) {

	return str.replace(/\s{1,}/g," ")

				.replace(/ AND /ig,"~::~"+tab+tab+"AND ")
				.replace(/ BETWEEN /ig,"~::~"+tab+"BETWEEN ")
				.replace(/ CASE /ig,"~::~"+tab+"CASE ")
				.replace(/ ELSE /ig,"~::~"+tab+"ELSE ")
				.replace(/ END /ig,"~::~"+tab+"END ")
				.replace(/ FROM /ig,"~::~FROM ")
				.replace(/ GROUP\s{1,}BY/ig,"~::~GROUP BY ")
				.replace(/ HAVING /ig,"~::~HAVING ")
				//.replace(/ SET /ig," SET~::~")
				.replace(/ IN /ig," IN ")
				
				.replace(/ JOIN /ig,"~::~JOIN ")
				.replace(/ CROSS~::~{1,}JOIN /ig,"~::~CROSS JOIN ")
				.replace(/ INNER~::~{1,}JOIN /ig,"~::~INNER JOIN ")
				.replace(/ LEFT~::~{1,}JOIN /ig,"~::~LEFT JOIN ")
				.replace(/ RIGHT~::~{1,}JOIN /ig,"~::~RIGHT JOIN ")
				
				.replace(/ ON /ig,"~::~"+tab+"ON ")
				.replace(/ OR /ig,"~::~"+tab+tab+"OR ")
				.replace(/ ORDER\s{1,}BY/ig,"~::~ORDER BY ")
				.replace(/ OVER /ig,"~::~"+tab+"OVER ")

				.replace(/\(\s{0,}SELECT /ig,"~::~(SELECT ")
				.replace(/\)\s{0,}SELECT /ig,")~::~SELECT ")
				
				.replace(/ THEN /ig," THEN~::~"+tab+"")
				.replace(/ UNION /ig,"~::~UNION~::~")
				.replace(/ USING /ig,"~::~USING ")
				.replace(/ WHEN /ig,"~::~"+tab+"WHEN ")
				.replace(/ WHERE /ig,"~::~WHERE ")
				.replace(/ WITH /ig,"~::~WITH ")
				
				//.replace(/\,\s{0,}\(/ig,",~::~( ")
				//.replace(/\,/ig,",~::~"+tab+tab+"")

				.replace(/ ALL /ig," ALL ")
				.replace(/ AS /ig," AS ")
				.replace(/ ASC /ig," ASC ")	
				.replace(/ DESC /ig," DESC ")	
				.replace(/ DISTINCT /ig," DISTINCT ")
				.replace(/ EXISTS /ig," EXISTS ")
				.replace(/ NOT /ig," NOT ")
				.replace(/ NULL /ig," NULL ")
				.replace(/ LIKE /ig," LIKE ")
				.replace(/\s{0,}SELECT /ig,"SELECT ")
				.replace(/\s{0,}UPDATE /ig,"UPDATE ")
				.replace(/ SET /ig," SET ")
							
				.replace(/~::~{1,}/g,"~::~")
				.split('~::~');
}

vkbeautify.prototype.sql = function(text,step) {

	var ar_by_quote = text.replace(/\s{1,}/g," ")
							.replace(/\'/ig,"~::~\'")
							.split('~::~'),
		len = ar_by_quote.length,
		ar = [],
		deep = 0,
		tab = this.step,//+this.step,
		inComment = true,
		inQuote = false,
		parenthesisLevel = 0,
		str = '',
		ix = 0,
		shift = step ? createShiftArr(step) : this.shift;;

		for(ix=0;ix<len;ix++) {
			if(ix%2) {
				ar = ar.concat(ar_by_quote[ix]);
			} else {
				ar = ar.concat(split_sql(ar_by_quote[ix], tab) );
			}
		}
		
		len = ar.length;
		for(ix=0;ix<len;ix++) {
			
			parenthesisLevel = isSubquery(ar[ix], parenthesisLevel);
			
			if( /\s{0,}\s{0,}SELECT\s{0,}/.exec(ar[ix]))  { 
				ar[ix] = ar[ix].replace(/\,/g,",\n"+tab+tab+"")
			} 
			
			if( /\s{0,}\s{0,}SET\s{0,}/.exec(ar[ix]))  { 
				ar[ix] = ar[ix].replace(/\,/g,",\n"+tab+tab+"")
			} 
			
			if( /\s{0,}\(\s{0,}SELECT\s{0,}/.exec(ar[ix]))  { 
				deep++;
				str += shift[deep]+ar[ix];
			} else 
			if( /\'/.exec(ar[ix]) )  { 
				if(parenthesisLevel<1 && deep) {
					deep--;
				}
				str += ar[ix];
			}
			else  { 
				str += shift[deep]+ar[ix];
				if(parenthesisLevel<1 && deep) {
					deep--;
				}
			} 
			var junk = 0;
		}

		str = str.replace(/^\n{1,}/,'').replace(/\n{1,}/g,"\n");
		return str;
}


vkbeautify.prototype.xmlmin = function(text, preserveComments) {

	var str = preserveComments ? text
							   : text.replace(/\<![ \r\n\t]*(--([^\-]|[\r\n]|-[^\-])*--[ \r\n\t]*)\>/g,"")
									 .replace(/[ \r\n\t]{1,}xmlns/g, ' xmlns');
	return  str.replace(/>\s{0,}</g,"><"); 
}

vkbeautify.prototype.jsonmin = function(text) {

	if (typeof JSON === 'undefined' ) return text; 
	
	return JSON.stringify(JSON.parse(text), null, 0); 
				
}

vkbeautify.prototype.cssmin = function(text, preserveComments) {
	
	var str = preserveComments ? text
							   : text.replace(/\/\*([^*]|[\r\n]|(\*+([^*/]|[\r\n])))*\*+\//g,"") ;

	return str.replace(/\s{1,}/g,' ')
			  .replace(/\{\s{1,}/g,"{")
			  .replace(/\}\s{1,}/g,"}")
			  .replace(/\;\s{1,}/g,";")
			  .replace(/\/\*\s{1,}/g,"/*")
			  .replace(/\*\/\s{1,}/g,"*/");
}

vkbeautify.prototype.sqlmin = function(text) {
	return text.replace(/\s{1,}/g," ").replace(/\s{1,}\(/,"(").replace(/\s{1,}\)/,")");
}

window.vkbeautify = new vkbeautify();

})();


angular.module('academicterm', []);
angular.module('academicterm').controller('AcademicTermController', function ($scope, AcademicTermService, InstitutionService) {
    $scope.getAllAcademicTerms = function(){
        AcademicTermService.getAll(
            function(result) {
                $scope.academicTerms = result;
        });
    };
    
    $scope.addAcademicTerm = function(){
        var selectedAcademicYear;
        angular.forEach($scope.academicYears, function(item) {
            if (item.id === $scope.newAcademicTerm.academicYearId) {
                selectedAcademicYear = item;
            }
        });
        $scope.newAcademicTerm.academicYear = selectedAcademicYear;
        var academicYearText = $scope.newAcademicTerm_selectedTerm === 0 ? selectedAcademicYear.startYear : selectedAcademicYear.endYear;
        var enAcademicTermText = $scope.terms[$scope.newAcademicTerm_selectedTerm].en + ' ' + academicYearText;
        var seAcademicTermText = $scope.terms[$scope.newAcademicTerm_selectedTerm].se + ' ' + academicYearText;
        $scope.newAcademicTerm.dispName = [{text:enAcademicTermText,'lang':'en'}, {text:seAcademicTermText,'lang':'se'}];
        $scope.saveAcademicTerm($scope.newAcademicTerm);
        
        $scope.cancelAddAcademicTerm();
    };
    
    $scope.cancelAddAcademicTerm = function(){
        $scope.newAcademicTerm = {};
        $scope.newAcademicTerm_selectedTerm = 0;
        $scope.showAddAcademicTermForm = false;
    };
    
    $scope.saveAcademicTerm = function(academicterm) {
        AcademicTermService.addNew(academicterm, function(result) {
            $scope.getAllAcademicTerms();
        });
    };
    
    $scope.addOrganizationUnitsToList = function(obj) {
        angular.forEach(obj.organizationUnits, function(item) {
            $scope.organizations.push(item);
        });
    };
    
    $scope.viewAddAcademicTermForm = function() {
        InstitutionService.getLocal(
            function(result) {
            $scope.institutions = result;
        });
        
        AcademicTermService.getAcademicYears(
            function(result) {
            $scope.academicYears = result;
        });

        $scope.organizations = [];
        $scope.showAddAcademicTermForm = true;
    };
    
    $scope.institutionChanged = function() {
        var currentInst;
        angular.forEach($scope.institutions, function(item) {
            if (item.institutionId === $scope.newAcademicTerm.institutionId) {
                currentInst = item;
            }
        });
        
        $scope.organizations = [];
        $scope.addOrganizationUnitsToList(currentInst);
    };
    
    $scope.terms = [{label:'Fall', en:'Fall semester', se:'Hösttermin'}, {label:'Spring', en:'Spring semester', se:'Vårtermin'}];
    $scope.cancelAddAcademicTerm();
    $scope.getAllAcademicTerms();
});

angular.module('academicterm').service('AcademicTermService', function ($http) {
    return {
        getAll: function (callback) {
            $http.get('gui/academic_term/get_all').success(callback);
        },
        addNew: function (academicterm, callback) {
            $http.post('gui/academic_term/add', academicterm).success(callback);
        },
        getAcademicYears: function (callback) {
            $http.get('gui/academic_term/list_academic_years').success(callback);
        }
    };
});

angular.module('connector').service('ClientCacheService', function ($http) {
    var cache = {};
    return {
        add: function (client, key, value) {
            if (!cache[client]) {
                cache[client] = {};
            }
            cache[client][key] = value;
        },
        get: function (client, key) {
            return cache[client] ? cache[client][key] : undefined;
        },
        keys: function (client) {
            return Object.keys(cache[client]);
        }
    };
});

angular.module('connector').directive('clientResult', function() {
        return {
            restrict: 'A',
            scope: {
                clientResult: '=',
                formattedTemplate: '=formattedTemplate'
            },
            templateUrl: 'templates/client_result.html'
        };
    });

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
angular.module('connector').directive('contact', function() {
        return {
            restrict: 'A',
            scope: {
                contact: '='
            },
            templateUrl: 'templates/contact.html'
        };
    });
angular.module('connector').directive('contactDetails', function() {
        return {
            restrict: 'A',
            scope: {
                details: '=contactDetails'
            },
            templateUrl: 'templates/contact_details.html'
        };
    });

angular.module('connector').directive('convertToNumber', function() {
  return {
    require: 'ngModel',
    link: function(scope, element, attrs, ngModel) {
      ngModel.$parsers.push(function(val) {
        return parseInt(val, 10);
      });
      ngModel.$formatters.push(function(val) {
        return '' + val;
      });
    }
  };
});
angular.module('connector').directive('flexibleAddress', function() {
        return {
            restrict: 'A',
            scope: {
                address: '=flexibleAddress'
            },
            templateUrl: 'templates/flexible_address.html'
        };
    });
angular.module('connector').directive('flexibleAddressForm', function() {
        return {
            restrict: 'A',
            scope: {
                address: '=flexibleAddressForm'
            },
            templateUrl: 'templates/flexible_address_form.html',
            controller: function($scope){
                $scope.addAdressLine = function() {
                    if (!$scope.address) {
                        $scope.address = {};
                    }
                    if (!$scope.address.addressLine) {
                        $scope.address.addressLine = [];
                    }
                    if ($scope.address.addressLine.length < 4) {
                        $scope.address.addressLine.push('');
                    }
                };
                $scope.addDeliveryPointCode = function() {
                    if (!$scope.address) {
                        $scope.address = {};
                    }
                    if (!$scope.address.deliveryPointCode) {
                        $scope.address.deliveryPointCode = [];
                    }
                    $scope.address.deliveryPointCode.push('');
                };
                $scope.addRecipientName = function() {
                    if (!$scope.address) {
                        $scope.address = {};
                    }
                    if (!$scope.address.recipientName) {
                        $scope.address.recipientName = [];
                    }
                    $scope.address.recipientName.push('');
                };
            }
        };
    });
angular.module('connector').directive('instOrgUnitForm', function() {
        return {
            restrict: 'A',
            scope: {
                instOrgUnit: '=instOrgUnitForm',
                objectType: '=',
                add: '&addFkn',
                cancel: '&cancelFkn'
            },
            templateUrl: 'templates/inst_org_unit_form.html',
            controller: function($scope) {
                $scope.addOtherId = function() {
                    if (!$scope.instOrgUnit.otherId) {
                        $scope.instOrgUnit.otherId = [];
                    }
                    $scope.instOrgUnit.otherId.push({idType: '',  idValue: ''});
                };
                $scope.addName = function() {
                    $scope.instOrgUnit.name.push({text: '',  lang: ''});
                };
                $scope.addFactSheetUrl = function() {
                    if (!$scope.instOrgUnit.factSheet) {
                        $scope.instOrgUnit.factSheet = {};
                    }
                    if (!$scope.instOrgUnit.factSheet.url) {
                        $scope.instOrgUnit.factSheet.url = [];
                    }
                    $scope.instOrgUnit.factSheet.url.push({text: '',  lang: ''});
                };
                
                $scope.addWebsiteUrl = function() {
                    if (!$scope.instOrgUnit.factSheet) {
                        $scope.instOrgUnit.factSheet = {};
                    }
                    if (!$scope.instOrgUnit.factSheet.contactDetails) {
                        $scope.instOrgUnit.factSheet.contactDetails = {};
                    }
                    if (!$scope.instOrgUnit.factSheet.contactDetails.url) {
                        $scope.instOrgUnit.factSheet.contactDetails.url = [];
                    }
                    $scope.instOrgUnit.factSheet.contactDetails.url.push({text: '',  lang: ''});
                };
            }
        };
    });

angular.module('connector').directive('phoneNumber', function() {
        return {
            restrict: 'A',
            scope: {
                phoneNumber: '=',
                numberTitle: '='
            },
            templateUrl: 'templates/phone_number.html'
        };
    });

angular.module('connector').directive('phoneNumberForm', function() {
        return {
            restrict: 'A',
            scope: {
                phoneNumber: '=phoneNumberForm'
            },
            templateUrl: 'templates/phone_number_form.html'
        };
    });

angular.module('echo', []);
angular.module('echo').controller('EchoController', function ($scope, EchoService) {
    $scope.echoRequest = {};
    $scope.echoRequest.method = 'GET';
    $scope.echoRequest.httpsec = true;
    $scope.echoRequest.params = {echo:['']};
    
    $scope.addEchoItem = function() {
        $scope.echoRequest.params.echo.push('');
    };
    
    $scope.sendEcho = function() {
        $scope.echoResult = '';
        EchoService.echo($scope.echoRequest,
            function(result) {
                $scope.echoResult = result;
            });
    };
    
    EchoService.echoHeis(
        function(result) {
            $scope.echoHeis = result;
        });
        
});
angular.module('echo').service('EchoService', function ($http) {
    return {
        echo: function (echoRequest, callback) {
            $http.post('gui/echo',
                echoRequest
                ).success(callback);
        },
        echoHeis: function (callback) {
            $http.get('gui/echo').success(callback);
        }
    };
});
angular.module('contact', []);
angular.module('contact').controller('ContactController', function ($scope, ContactService, PersonService, InstitutionService) {

    $scope.getAllContacts = function() {
       ContactService.getAll(
            function(result) {
            $scope.contacts = result;
        });
    };
    
    $scope.viewAddContactForm = function() {
        InstitutionService.getLocal(
            function(result) {
            $scope.institutions = result;
        });

        PersonService.getAll(
            function(result) {
            $scope.persons = result;
        });
        
        ContactService.getContactRoles(function(result) {
            $scope.roles = result;
        });
        $scope.organizations = [];
        $scope.showAddContactForm = true;
    };

    $scope.addOrganizationUnitsToList = function(obj) {
        angular.forEach(obj.organizationUnits, function(item) {
            $scope.organizations.push(item);
            $scope.addOrganizationUnitsToList(item);
        });
    };
    
    $scope.institutionChanged = function() {
        var currentInst;
        angular.forEach($scope.institutions, function(item) {
            if (item.institutionId === $scope.newContact.institutionId) {
                currentInst = item;
            }
        });
        
        $scope.organizations = [];
        $scope.addOrganizationUnitsToList(currentInst);
    };
    
    $scope.addContact  = function(){
        var currentPerson;
        angular.forEach($scope.persons, function(item) {
            if (item.personId === $scope.newContact.personId) {
                currentPerson = item;
            }
        });
        $scope.newContact.person = currentPerson;
        ContactService.addNew($scope.newContact,
            function(result) {
                $scope.newContact = {};
                $scope.showAddContactForm = false;
                $scope.getAllContacts();
        });
    };
    
    $scope.cancelAddContact = function(){
        $scope.newContact = {};
        $scope.showAddContactForm = false;
    };
    
    $scope.getAllContacts();
});
angular.module('contact').service('ContactService', function ($http) {
    return {
        getAll: function (callback) {
            $http.get('gui/contact/get_all',
                { method: 'GET'
                }).success(callback);
        },
        addNew: function (contact, callback) {
            $http.post('gui/contact/add', contact).success(callback);
        },
        getContactRoles: function (callback) {
            $http.get('gui/contact/contact_roles').success(callback);
        }
    };
});

angular.module('home', []);
angular.module('home').controller('HomeController', function ($scope, HomeService) {
    HomeService.name(
        function(result) {
            $scope.universityName = result;
        });

    HomeService.hostname(
        function(result) {
            $scope.hostname = result;
        });
});
angular.module('home').service('HomeService', function ($http) {
    return {
        name: function (callback) {
            $http.get('gui/home/name').success(callback);
        },
        hostname: function (callback) {
            $http.get('gui/home/hostname').success(callback);
        }
    };
});
angular.module('iia', []);
angular.module('iia').controller('IiaClientController', function ($scope, IiaService, ClientCacheService) {
    IiaService.getIiaHeis(function(result) {
        $scope.heis = result;
        if ($scope.heis) {
            $scope.indexUrls = [];
            $scope.getUrls = [];
            angular.forEach($scope.heis, function(hei) {
                if ($scope.indexUrls.indexOf(hei.urls['index-url']) === -1) {
                    $scope.indexUrls.push(hei.urls['index-url']);
                }
                if ($scope.getUrls.indexOf(hei.urls['get-url']) === -1) {
                    $scope.getUrls.push(hei.urls['get-url']);
                }
            });
            
            $scope.urls = $scope.indexUrls;
        }
    });
    $scope.urlChanged = function() {
        $scope.urlHeis = [{name:'<Missing Institution>', id:'missing'}];
        angular.forEach($scope.heis, function(hei) {
            if (hei.urls[$scope.urlKey] === $scope.request.url) {
                $scope.urlHeis.push(hei);
            }
        });
    };
    
    $scope.institutionChanged = function() {
        $scope.heiIiaIds = ['<Missing IIA Id>'];
        
        $scope.currentHei = '';
        angular.forEach($scope.heis, function(hei) {
            if (hei.id === $scope.request.heiId) {
                $scope.currentHei = hei;
            }
        });
        
        var iiaIds = ClientCacheService.get('Iia', $scope.request.heiId);
        angular.forEach(iiaIds, function(iiaId) {
            $scope.heiIiaIds.push(iiaId);
        });
    };
    
    $scope.sendIiaRequest = function() {
        if ($scope.clientView === 'index') {
            IiaService.getIiaIndex($scope.request, function(result) {
                $scope.iiaIndexResult = result;
                $scope.result = result;
                if (result && result.result && result.result.iiaId) {
                    ClientCacheService.add('Iia', $scope.request.heiId, result.result.iiaId);
                }
            });
        } else {
            if ($scope.cachedIds) {
                $scope.request.params = {'iia_id': $scope.cachedIiaIds};
            } else {
                var iiaIds = $scope.manuallyIiaIds.split(',');
                $scope.request.params = {'iia_id': iiaIds};
            }
            IiaService.getIia($scope.request, function(result) {
                $scope.iiaResult = result;
                $scope.result = result;
            });
        }
    };
    
    $scope.setClientView = function(viewName) {
        $scope.clientView = viewName;
        if (viewName === 'index') {
            $scope.urls = $scope.indexUrls;
            $scope.urlKey = 'index-url';
        } else {
            $scope.urls = $scope.getUrls;
            $scope.urlKey = 'get-url';
            $scope.institutionChanged();
        }
        if ($scope.currentHei) {
            $scope.request.url = $scope.currentHei.urls[$scope.urlKey];
        }
    };
    
    $scope.clientView = 'index';
    $scope.urlKey = 'index-url';
    $scope.request = {method: "GET"};
});

angular.module('iia').controller('IiaController', function ($scope, IiaService) {
    $scope.getAllIias = function(){
        IiaService.getAll(
            function(result) {
                $scope.iiaList = result;
        });
    };
    
    $scope.getMobilityNumberVariants = function(){
        IiaService.getMobilityNumberVariants(
            function(result) {
                $scope.mobilityNumberVariants = result;
        });
    };
   
    $scope.getDurationUnitVariants = function(){
        IiaService.getDurationUnitVariants(
            function(result) {
                $scope.durationUnits = result;
        });
    };
    
    $scope.setSelectedIia = function(iia) {
        $scope.selectedIia = iia;
    };

    $scope.setSelectedCoopCond = function(coop) {
        $scope.selectedCoopCond = coop;
    };
    
    $scope.backIia = function() {
            $scope.selectedIia = '';
    };

    $scope.backCoopCond = function() {
            $scope.selectedCoopCond = '';
    };
    
    $scope.viewAddCooperationConditionForm = function() {
        $scope.cancelAddCondition();
        IiaService.getMobilityTypes(
            function(result) {
            $scope.mobilityTypes = result;
        });
        
        $scope.showAddConditionForm = true;
    };
    
    $scope.addIia = function(){
        $scope.newIia.cooperationConditions = $scope.conditions;
        $scope.conditions = [];
        IiaService.addNewIia($scope.newIia,
            function(result) {
                $scope.newIia = {};
                $scope.showAddIiaForm = false;
                $scope.getAllIias();
        });
    };
    
    $scope.cancelAddIia = function(){
        $scope.newIia = {};
        $scope.showAddIiaForm = false;
    };
    
    $scope.conditions = [];
    $scope.addCondition = function(){
        var selectedMobilityType;
        angular.forEach($scope.mobilityTypes, function(mobType) {
            if (mobType.id === $scope.newCondition.mobilityTypeId) {
                selectedMobilityType = mobType;
            }
        });
        $scope.newCondition.mobilityType = selectedMobilityType;
        $scope.conditions.push($scope.newCondition);
        $scope.showAddConditionForm = false;
        $scope.cancelAddCondition();
    };
 
    $scope.cancelAddCondition = function(){
        $scope.showAddConditionForm = false;
        $scope.newCondition = {eqfLevel:1};
    };
    
    $scope.eqfLevels=['1','2','3','4','5','6','7','8'];
    $scope.getMobilityNumberVariants();
    $scope.getDurationUnitVariants();
    $scope.getAllIias();
});

angular.module('iia').service('IiaService', function ($http) {
    return {
        getAll: function (callback) {
            $http.get('gui/iia/get_all').success(callback);
        },
        addNewIia: function ($iia, callback) {
            $http.post('gui/iia/add', $iia).success(callback);
        },
        getMobilityTypes: function (callback) {
            $http.get('gui/iia/mobility_types').success(callback);
        },
        getMobilityNumberVariants: function (callback) {
            $http.get('gui/iia/mobility_unit_variants').success(callback);
        },
        getDurationUnitVariants: function (callback) {
            $http.get('gui/iia/duration_unit_variants').success(callback);
        },
        getIiaHeis: function (callback) {
            $http.get('gui/iia/heis').success(callback);
        },
        getIiaIndex: function (courseRequest, callback) {
            $http.post('gui/iia/iias-index', courseRequest).success(callback);
        },
        getIia: function (courseRequest, callback) {
            $http.post('gui/iia/iias', courseRequest).success(callback);
        }
    };
});

angular.module('institution', []);
angular.module('institution').controller('InstitutionClientController', function ($scope, InstitutionService, ClientCacheService) {
    InstitutionService.getHeis(function(result) {
        $scope.heis = result;
        if ($scope.heis) {
            $scope.urls = [];
            angular.forEach($scope.heis, function(hei) {
                if ($scope.urls.indexOf(hei.urls['url']) === -1) {
                    $scope.urls.push(hei.urls['url']);
                }
            });
        }
    });
    
    $scope.urlChanged = function() {
        $scope.urlHeis = [{name:'<Missing Institution>', id:'missing'}];
        angular.forEach($scope.heis, function(hei) {
            if (hei.urls['url'] === $scope.institutionRequest.url) {
                $scope.urlHeis.push(hei);
            }
        });
    };
    
    $scope.sendInstitutionRequest = function() {
        InstitutionService.getInstitutions($scope.institutionRequest, function(result) {
            $scope.institutionResult = result;
            if (result && result.result && result.result.hei) {
                angular.forEach(result.result.hei, function(hei) {
                    if (hei.ounitId) {
                        ClientCacheService.add('Institution', hei.heiId, hei.ounitId);
                    }
                });
            }
        });
    };
    
    $scope.institutionRequest = {method: "GET"};
});

angular.module('institution').controller('InstitutionController', function ($scope, InstitutionService) {
    $scope.getAllInstitutions = function() {
        InstitutionService.getLocal(
            function(result) {
                $scope.institutions = result;
                var oldBrowseList = $scope.browseInstOrgObjectList;
                $scope.browseInstOrgObjectList = [];
                $scope.rebuildBrowseList(result, oldBrowseList);
        });
    };
    
    $scope.rebuildBrowseList = function(itemList, oldBrowseList) {
        var oldItem = oldBrowseList.shift();
        var newItem;
        if (oldItem) {
            angular.forEach(itemList, function(item) {
                if (item.id === oldItem.id) {
                    newItem = item;
                }
            });
        }
        
        if (newItem) {
            $scope.browseInstOrgObjectList.push(newItem);
            $scope.rebuildBrowseList(newItem.organizationUnits, oldBrowseList);
        } else {
            $scope.currentInstOrgObject = $scope.browseInstOrgObjectList[$scope.browseInstOrgObjectList.length - 1];
        }
    };
        
    $scope.browseInstOrgObjectList = [];
    $scope.setCurrentInstOrgObject = function(obj) {
        $scope.currentInstOrgObject = obj;
        $scope.browseInstOrgObjectList.push(obj);
        $scope.cancelAddForm();
    };
    
    $scope.backCurrentInstOrgObject = function() {
        $scope.browseInstOrgObjectList.pop();
        if($scope.browseInstOrgObjectList.length > 0) {
            $scope.currentInstOrgObject = $scope.browseInstOrgObjectList[$scope.browseInstOrgObjectList.length-1];
        } else {
            $scope.currentInstOrgObject = '';
        }
        $scope.cancelAddForm();
    };
    $scope.viewAddForm = function() {
        if ($scope.currentInstOrgObject) {
            $scope.showAddInstitutionForm = false;
            $scope.showAddOrganizationUnitForm = true;
        } else {
            $scope.showAddInstitutionForm = true;
            $scope.showAddOrganizationUnitForm = false;
        }
        $scope.viewStreetAdressForm = false;
        $scope.viewMailingAdressForm = false;
        $scope.viewPhoneNumberForm = false;
        $scope.viewFaxNumberForm = false;
    };
    
    $scope.addInstitution = function() {
        $scope.saveInstitution($scope.newInstitution);
        
        $scope.showAddInstitutionForm = false;
        $scope.cancelAddForm();
    };
    
    $scope.addOrganizationUnit = function() {
        if (!$scope.currentInstOrgObject.organizationUnits) {
            $scope.currentInstOrgObject.organizationUnits = [];
        }
        $scope.currentInstOrgObject.organizationUnits.push($scope.newOrganizationUnit);
        $scope.saveInstitution($scope.browseInstOrgObjectList[0]);
        
        $scope.showAddOrganizationUnitForm = false;
        $scope.cancelAddForm();
    };
    
    $scope.saveInstitution = function(institution) {
        InstitutionService.save(institution, function(result) {
            $scope.getAllInstitutions();
        });
    };
    
    $scope.cancelAddForm = function() {
        $scope.showAddInstitutionForm = false;
        $scope.showAddOrganizationUnitForm = false;
        $scope.newInstitution = {name:[{text:'', lang: ''}]};
        $scope.newOrganizationUnit = {};
        $scope.newOrganizationUnit = {name:[{text:'', lang: ''}]};
    };
    
    $scope.cancelAddForm();
    $scope.getAllInstitutions();
});

angular.module('institution').service('InstitutionService', function ($http) {
    return {
        getLocal: function (callback) {
            $http.get('gui/institution/get_all').success(callback);
        },
        save: function (institution, callback) {
            $http.post('gui/institution/save', institution).success(callback);
        },
        getInstitutions: function (institutionRequest, callback) {
            $http.post('gui/institution/heis', institutionRequest).success(callback);
        },
        getHeis: function (callback) {
            $http.get('gui/institution/heis').success(callback);
        },
        getOrganizationUnits: function (institutionRequest, callback) {
            $http.post('gui/institution/ounits-heis', institutionRequest).success(callback);
        },
        getOrganizationUnitHeis: function (callback) {
            $http.get('gui/institution/ounits-heis').success(callback);
        }
    };
});

angular.module('institution').controller('OrganizationUnitClientController', function ($scope, InstitutionService, ClientCacheService) {
    InstitutionService.getOrganizationUnitHeis(function(result) {
        $scope.heis = result;
        if ($scope.heis) {
            $scope.urls = [];
            angular.forEach($scope.heis, function(hei) {
                if ($scope.urls.indexOf(hei.urls['url']) === -1) {
                    $scope.urls.push(hei.urls['url']);
                }
            });
        }
    });
    
    $scope.urlChanged = function() {
        $scope.urlHeis = [{name:'<Missing Institution>', id:'missing'}];
        angular.forEach($scope.heis, function(hei) {
            if (hei.urls['url'] === $scope.organizationUnitRequest.url) {
                $scope.urlHeis.push(hei);
            }
        });
    };
    
    $scope.institutionChanged = function() {
        $scope.heiOrganizationUnits = ['<Missing Organization Unit Id>'];
        var organizationUnitIds = ClientCacheService.get('Institution', $scope.organizationUnitRequest.heiId);
        angular.forEach(organizationUnitIds, function(ounitId) {
            $scope.heiOrganizationUnits.push(ounitId);
        });
    };
    
    $scope.sendOrganizationUnitRequest = function() {
        if ($scope.cachedIds) {
            $scope.organizationUnitRequest.params = {'ounit_id': $scope.cachedOrgUnitIds};
        } else {
            var orgUnitIds = $scope.manuallyOrgUnitIds.split(',');
            $scope.organizationUnitRequest.params = {'ounit_id': orgUnitIds};
        }
        InstitutionService.getOrganizationUnits($scope.organizationUnitRequest, function(result) {
            $scope.organizationUnitResult = result;
        });
    };
    
    $scope.organizationUnitRequest = {method: "GET"};
});

angular.module('loi', []);
angular.module('loi').controller('LoiController', function ($scope, LoiService, LosService, AcademicTermService) {
    $scope.getAllTopLevelLosParents = function(){
        LosService.getAllTopLevelParents(
            function(result) {
                $scope.losList = result;
        });
    };
    
    $scope.showLos = function(los) {
        $scope.currentLos=los;
    };

    $scope.toggleLosMenuItem = function(los) {
        event.preventDefault();
        event.stopPropagation();
        los.expanded = !los.expanded;
    };
    
    $scope.viewAddLearningOppInstanceForm = function() {
        $scope.creditLevels = ['Bachelor', 'Master', 'PhD'];
        LoiService.getGradingSchemes(
            function(result) {
                $scope.gradingSchemes = result;
        });
        
        AcademicTermService.getAll(
            function(result) {
                $scope.academicTerms = result;
        });
        
        $scope.organizations = [];
        $scope.showAddLearningOppInstanceForm = true;
    };
    
    $scope.cancelAddLearningOppInstance = function(){
        $scope.newLearningOppInstance = {credits: [{value: '',  scheme: '', level: ''}]};
        $scope.showAddLearningOppInstanceForm = false;
        $scope.selectedGradingSchemeId = '';
    };
    
    $scope.addLearningOppInstance = function(){
        var selectedAcademicTerm;
        angular.forEach($scope.academicTerms, function(item) {
            if (item.id === $scope.newLearningOppInstance.academicTermId) {
                selectedAcademicTerm = item;
            }
        });
        $scope.newLearningOppInstance.academicTerm = selectedAcademicTerm;
        
        var selectedGradingScheme;
        angular.forEach($scope.gradingSchemes, function(item) {
            if (item.id === $scope.selectedGradingSchemeId) {
                selectedGradingScheme = item;
            }
        });
        $scope.newLearningOppInstance.gradingScheme = selectedGradingScheme;
        
        $scope.currentLos.learningOpportunityInstances.push($scope.newLearningOppInstance);
        $scope.saveLearningOppSpec($scope.currentLos);
        
        $scope.showAddLearningOppInstanceForm = false;
        $scope.newLearningOppInstance = {credits: [{value: '',  scheme: '', level: ''}]};
        $scope.currentLos = '';
    };
    
    $scope.addResultDistributionCategory = function() {
        if (!$scope.newLearningOppInstance.resultDistribution) {
            $scope.newLearningOppInstance.resultDistribution = {};
        }
        if (!$scope.newLearningOppInstance.resultDistribution.resultDistributionCategory) {
            $scope.newLearningOppInstance.resultDistribution.resultDistributionCategory = [];
        }
        $scope.newLearningOppInstance.resultDistribution.resultDistributionCategory.push({label:'', count:0});
    };

    $scope.addResultDistributionDescription = function() {
        if (!$scope.newLearningOppInstance.resultDistribution) {
            $scope.newLearningOppInstance.resultDistribution = {};
        }
        if (!$scope.newLearningOppInstance.resultDistribution.description) {
            $scope.newLearningOppInstance.resultDistribution.description = [];
        }
        $scope.newLearningOppInstance.resultDistribution.description.push({text:'', lang:''});
    };
    
    $scope.saveLearningOppSpec = function(learningOppSpec) {
        LosService.save(learningOppSpec, function(result) {
            $scope.getAllTopLevelLosParents();
        });
    };
    
    $scope.addCredit = function() {
        $scope.newLearningOppInstance.credits.push({value: '',  scheme: '', level: ''});
    };
    
    $scope.getAllTopLevelLosParents();
    $scope.cancelAddLearningOppInstance();
});

angular.module('loi').service('LoiService', function ($http) {
    return {
        getGradingSchemes: function (callback) {
            $http.get('gui/loi/grading_schemes').success(callback);
        }
    };
});

angular.module('los', []);
angular.module('los').controller('CourseReplicationClientController', function ($scope, LosService, ClientCacheService) {
    LosService.getCourseReplicationHeis(function(result) {
        $scope.heis = result;
        if ($scope.heis) {
            $scope.urls = [];
            angular.forEach($scope.heis, function(hei) {
                if ($scope.urls.indexOf(hei.urls['url']) === -1) {
                    $scope.urls.push(hei.urls['url']);
                }
            });
        }
    });
    $scope.urlChanged = function() {
        $scope.urlHeis = [{name:'<Missing Institution>', id:'missing'}];
        angular.forEach($scope.heis, function(hei) {
            if (hei.urls['url'] === $scope.courseReplicationRequest.url) {
                $scope.urlHeis.push(hei);
            }
        });
    };
    $scope.sendCourseReplicationRequest = function() {
        LosService.getCourseReplication($scope.courseReplicationRequest, function(result) {
            $scope.losResult = result;
            if (result && result.result && result.result.losId) {
                ClientCacheService.add('CourseReplication', $scope.courseReplicationRequest.heiId, result.result.losId);
            }
        });
    };
    
    $scope.courseReplicationRequest = {method: "GET"};
});

angular.module('los').controller('CoursesClientController', function ($scope, LosService, ClientCacheService) {
    LosService.getCourseHeis(function(result) {
        $scope.heis = result;
        if ($scope.heis) {
            $scope.urls = [];
            angular.forEach($scope.heis, function(hei) {
                if ($scope.urls.indexOf(hei.urls['url']) === -1) {
                    $scope.urls.push(hei.urls['url']);
                }
            });
        }
    });
    $scope.urlChanged = function() {
        $scope.urlHeis = [{name:'<Missing Institution>', id:'missing'}];
        angular.forEach($scope.heis, function(hei) {
            if (hei.urls['url'] === $scope.coursesRequest.url) {
                $scope.urlHeis.push(hei);
            }
        });
    };
    
    $scope.institutionChanged = function() {
        $scope.heiLosIds = ['<Missing Los Id>'];
        var losIds = ClientCacheService.get('CourseReplication', $scope.coursesRequest.heiId);
        angular.forEach(losIds, function(losId) {
            $scope.heiLosIds.push(losId);
        });
    };
    
    $scope.sendCoursesRequest = function() {
        if ($scope.cachedIds) {
            $scope.coursesRequest.params = {'los_id': $scope.cachedLosIds};
        } else {
            var losIds = $scope.manuallyLosIds.split(',');
            $scope.coursesRequest.params = {'los_id': losIds};
        }
        LosService.getCourse($scope.coursesRequest, function(result) {
            $scope.losResult = result;
        });
    };
    
    $scope.coursesRequest = {method: "GET"};
});

angular.module('los').controller('LosController', function ($scope, LosService, InstitutionService) {
    $scope.getAllTopLevelLosParents = function(){
        LosService.getAllTopLevelParents(
            function(result) {
                $scope.losList = result;
        });
    };
    
    $scope.viewAddLearningOppSpecRootForm = function() {
        InstitutionService.getLocal(
            function(result) {
            $scope.institutions = result;
        });
        
        $scope.lostypes = ['DEGREE_PROGRAMME', 'MODULE', 'COURSE'];
        
        $scope.showAddLearningOppSpecForm = true;
        $scope.currentLos = undefined;
    };
    
    $scope.institutionChanged = function() {
        var currentInst;
        angular.forEach($scope.institutions, function(item) {
            if (item.institutionId === $scope.newLearningOppSpec.institutionId) {
                currentInst = item;
            }
        });
        
        $scope.organizations = [];
        $scope.addOrganizationUnitsToList(currentInst);
    };
    
    $scope.addOrganizationUnitsToList = function(obj) {
        angular.forEach(obj.organizationUnits, function(item) {
            $scope.organizations.push(item);
            $scope.addOrganizationUnitsToList(item);
        });
    };

    $scope.viewAddLearningOppSpecForm = function(los) {
        if (los.type === 'DEGREE_PROGRAMME') {
            $scope.lostypes = ['MODULE', 'COURSE'];
        } else if (los.type === 'MODULE') {
            $scope.lostypes = ['COURSE'];
        } else if (los.type === 'COURSE') {
            $scope.lostypes = ['CLASS'];
        }
        
        $scope.institutions = [];
        $scope.showAddLearningOppSpecForm = true;
        $scope.currentLos = los;
    };
    
    $scope.addLOSUrl = function() {
        if (!$scope.newLearningOppSpec.url) {
            $scope.newLearningOppSpec.url = [];
        }
        $scope.newLearningOppSpec.url.push({text:'', lang:''});
    };
    
    $scope.addLearningOppSpec = function() {
        if ($scope.currentLos) {
            $scope.newLearningOppSpec.institutionId = $scope.currentLos.institutionId;
            $scope.currentLos.learningOpportunitySpecifications.push($scope.newLearningOppSpec);
            $scope.saveLearningOppSpec($scope.currentLos);
        } else {
            $scope.newLearningOppSpec.topLevelParent = true;
            $scope.saveLearningOppSpec($scope.newLearningOppSpec);
        }
        
        $scope.showAddLearningOppSpecForm = false;
        $scope.newLearningOppSpec = {};
        $scope.newLearningOppSpec = {name:[{text:'', lang: ''}]};
        $scope.currentLos = '';
    };
    
    $scope.saveLearningOppSpec = function(learningOppSpec) {
        LosService.save(learningOppSpec, function(result) {
            $scope.getAllTopLevelLosParents();
        });
    };
    
    $scope.cancelAddLearningOppSpec = function(){
        $scope.newLearningOppSpec = {name:[{text:'', lang: ''}], eqfLevel:1};
        $scope.showAddLearningOppSpecForm = false;
    };
    
    $scope.addNameForLearningOppSpec = function() {
        $scope.newLearningOppSpec.name.push({text: '',  lang: ''});
    };
    
    $scope.addDescriptionForLearningOppSpec = function() {
        if (!$scope.newLearningOppSpec.description) {
            $scope.newLearningOppSpec.description = [];
        }
        $scope.newLearningOppSpec.description.push({text: '',  lang: ''});
    };
    
    $scope.showLos = function(los) {
        $scope.currentLos=los;
    };
    $scope.toggleLosMenuItem = function(los) {
        event.preventDefault();
        event.stopPropagation();
        los.expanded = !los.expanded;
    };
    
    $scope.eqfLevels=['1','2','3','4','5','6','7','8'];
    $scope.getAllTopLevelLosParents();
    $scope.cancelAddLearningOppSpec();
});

angular.module('los').service('LosService', function ($http) {
    return {
        getAll: function (callback) {
            $http.get('gui/los/get_all').success(callback);
        },
        getAllTopLevelParents: function (callback) {
            $http.get('gui/los/get_top_level_parents').success(callback);
        },
        save: function (learningOppSpec, callback) {
            $http.post('gui/los/save', learningOppSpec).success(callback);
        },
        getByInstitutionId: function (institutionId, callback) {
            $http.get('gui/los/get_by_institution_id', {params: {institutionId: institutionId}}).success(callback);
        },
        getCourseReplicationHeis: function (callback) {
            $http.get('gui/los/course-replication').success(callback);
        },
        getCourseReplication: function (courseReplicationRequest, callback) {
            $http.post('gui/los/course-replication', courseReplicationRequest).success(callback);
        },
        getCourseHeis: function (callback) {
            $http.get('gui/los/courses').success(callback);
        },
        getCourse: function (courseRequest, callback) {
            $http.post('gui/los/courses', courseRequest).success(callback);
        }
    };
});

angular.module('menu', []);
angular.module('menu').controller('MenuController', function ($scope, $location) {
    $scope.rootMenu = [{name:'Organization',
                        subItems:[{name:'Institution', page:'institution'},
                                  {name:'Contact', page:'contact'},
                                  {name:'Mobility participant', page:'mobility-participant'},
                                  {name:'Person', page:'person'}]},
                       {name:'Course', 
                        subItems:[{name:'Academic term', page:'academicterm'},
                                  {name:'Learning opp. spec.', page:'los'},
                                  {name:'Learning opp. instance', page:'loi'}]},
                       {name:'IIA',
                        subItems:[{name:'IIA', page:'iia'}]},
                       {name:'Mobility', page:'mobility'},
                       {name:'Other Connectors',
                        subItems:[{name:'Echo', page:'echo'},
                                  {name:'Institution', page:'institution-client'},
                                  {name:'Organization unit', page:'ounit-client'},
                                  {name:'Course-replication', page:'course-replication-client'},
                                  {name:'Course', page:'courses-client'},
                                  {name:'Iia', page:'iia-client'}]}];

    $scope.selectedRootItem = '';
    $scope.selectHomeItem = function() {
        $scope.selectedRootItem = '';
        $location.path('home');
    };
    $scope.selectRootItem = function(item) {
        if ($scope.selectedRootItem !== item) {
            $scope.selectedRootItem = item;
            if (item.page) {
                $location.path(item.page);
                $scope.selectedSubItem = '';
            }
        }
    };

    $scope.selectSubItem = function(item) {
        $scope.selectedSubItem = item;
        $location.path(item.page);
    };
});

angular.module('mobility', []);
angular.module('mobility').controller('MobilityController', function ($scope, $filter, MobilityService, IiaService, MobilityParticipantService, LosService) {
    $scope.getAllMobilities = function(){
        MobilityService.getAll(
            function(result) {
                $scope.mobilities = result;
        });
    };
    
    $scope.addMobility = function(){
        $scope.newMobility.mobilityRevision = 1;
        MobilityService.addNew($scope.newMobility,
            function(result) {
                $scope.newMobility = {};
                $scope.laComponentsDescription = [];
                $scope.showAddMobilityForm = false;
                $scope.getAllMobilities();
        });
    };
    
    $scope.viewAddMobilityForm = function(){
        IiaService.getAll(
            function(result) {
                $scope.iiaList = result;
        });
        
        MobilityParticipantService.getAll(
            function(result) {
                $scope.mobilityParticipants = result;
        });
        
        $scope.cooperationConditionList = [];
        MobilityService.getMobilityStatuses(function(result) {
            $scope.mobilityStatuses = result;
        });
        $scope.showAddMobilityForm = true;
    };
    
    $scope.iiaChanged = function() {
        var selectedIia;
        angular.forEach($scope.iiaList, function(item) {
            if (item.id === $scope.newMobility.iiaId) {
                selectedIia = item;
            }
        });
        
        $scope.cooperationConditionList = [];
        $scope.addCooperationConditionsToList(selectedIia);
    };

    $scope.cooperationConditionChanged = function() {
        var selectedCooperationCondition;
        angular.forEach($scope.cooperationConditionList, function(item) {
            if (item.id === $scope.newMobility.cooperationConditionId) {
                selectedCooperationCondition = item;
            }
        });
        
        $scope.newMobility.sendingInstitutionId = selectedCooperationCondition.sendingPartner.institutionId;
        if(selectedCooperationCondition.sendingPartner.organizationUnitId !== null){
            $scope.newMobility.sendingOrganizationUnitId = selectedCooperationCondition.sendingPartner.organizationUnitId;
        }
        
        $scope.newMobility.receivingInstitutionId = selectedCooperationCondition.receivingPartner.institutionId;
        if(selectedCooperationCondition.receivingPartner.organizationUnitId !== null){
            $scope.newMobility.receivingOrganizationUnitId = selectedCooperationCondition.receivingPartner.organizationUnitId;
        }
        
        $scope.newMobility.mobilityType = selectedCooperationCondition.mobilityType;
        $scope.newMobility.eqfLevel = selectedCooperationCondition.eqfLevel;
    };
    
    $scope.addCooperationConditionsToList = function(obj) {
        angular.forEach(obj.cooperationConditions, function(item) {
            $scope.cooperationConditionList.push(item);
        });
    };
   
    $scope.cancelAddMobility = function(){
        $scope.newMobility = {};
        $scope.laComponentsDescription = [];
        $scope.showAddMobilityForm = false;
    };

    $scope.createSelectOptionsForLos = function(losList, parentText) {
        angular.forEach(losList, function(los) {
            var losName = $filter('languageItem')(los.name, 'en').text;
            var losText = (parentText ? parentText + " -> " : "") + los.losCode + " - " + losName + " (" + los.type + ")";
            $scope.losOptions.push({id:los.id, text:losText, ref:los});
            if (los.learningOpportunitySpecifications && los.learningOpportunitySpecifications.length > 0) {
                $scope.createSelectOptionsForLos(los.learningOpportunitySpecifications, los.losCode);
            }
        });
    };

    $scope.getAllTopLevelLosParents = function(){
        $scope.losOptions = [];
        LosService.getAllTopLevelParents(
            function(result) {
                $scope.createSelectOptionsForLos(result);
        });
    };
    
    $scope.viewAddStudiedLaComponentsForm = function() {
        MobilityService.getLaComponentStatuses(function(result) {
            $scope.laComponentStatuses = result;
        });
        $scope.showAddStudiedLaConditionForm = true;
        $scope.showAddRecognizedLaConditionForm = false;
        $scope.getAllTopLevelLosParents();
    };
    
    $scope.viewAddRecognizedLaComponentsForm = function() {
        $scope.showAddRecognizedLaConditionForm = true;
        $scope.showAddStudiedLaConditionForm = false;
        $scope.getAllTopLevelLosParents();
    };
    
    $scope.getLosOption = function(losId) {
        var los;
        angular.forEach($scope.losOptions, function(losOption) {
            if (losOption.id === losId) {
                los = losOption;
            }
        });
        return los;
    };
    
    $scope.getLoiOption = function(loiId) {
        var loi;
        angular.forEach($scope.loiOptions, function(loiOption) {
            if (loiOption.id === loiId) {
                loi = loiOption;
            }
        });
        return loi;
    };
    
    $scope.losChanged = function() {
        $scope.loiOptions = [];
        var los = $scope.getLosOption($scope.newLaComponent.losId);
        if (los) {
            angular.forEach(los.ref.learningOpportunityInstances, function(loi) {
                var startDate = $filter('date')(loi.academicTerm.startDate, 'yyyy-MM-dd');
                var endDate = $filter('date')(loi.academicTerm.endDate, 'yyyy-MM-dd');
                var loiText = startDate + " --- " + endDate;
                $scope.loiOptions.push({id:loi.id, text:loiText});
            });
        }
    };
    
    $scope.cancelAddLaComponents = function() {
        $scope.showAddRecognizedLaConditionForm = false;
        $scope.showAddStudiedLaConditionForm = false;
        $scope.newLaComponent = {};
    };
    
    $scope.addStudiedLaComponent = function() {
        if (!$scope.newMobility.learningAgreement) {
            $scope.newMobility.learningAgreement = {};
        }
        if (!$scope.newMobility.learningAgreement.studiedLaComponents) {
            $scope.newMobility.learningAgreement.studiedLaComponents = [];
        }
        
        $scope.newMobility.learningAgreement.studiedLaComponents.push($scope.newLaComponent);
        $scope.newMobility.learningAgreement.learningAgreementRevision = 1;
        $scope.cancelAddLaComponents();
    };
    
    $scope.addRecognizedLaComponent = function() {
        if (!$scope.newMobility.learningAgreement) {
            $scope.newMobility.learningAgreement = {};
        }
        if (!$scope.newMobility.learningAgreement.recognizedLaComponents) {
            $scope.newMobility.learningAgreement.recognizedLaComponents = [];
        }
        
        $scope.laComponentsDescription[$scope.newLaComponent.losId + "." + $scope.newLaComponent.loiId] = 
                $scope.getLosOption($scope.newLaComponent.losId).text + ", " + 
                $scope.getLoiOption($scope.newLaComponent.loiId).text;
        
        $scope.newMobility.learningAgreement.recognizedLaComponents.push($scope.newLaComponent);
        $scope.newMobility.learningAgreement.learningAgreementRevision = 1;
        $scope.cancelAddLaComponents();
    };
    
    $scope.getAllMobilities();
    $scope.cancelAddMobility();
});
angular.module('mobility').service('MobilityService', function ($http) {
    return {
        getAll: function (callback) {
            $http.get('gui/mobility/get_all').success(callback);
        },
        getMobilityStatuses: function (callback) {
            $http.get('gui/mobility/mobility_statuses').success(callback);
        },
        getLaComponentStatuses: function (callback) {
            $http.get('gui/mobility/lacomponent_statuses').success(callback);
        },
        addNew: function ($person, callback) {
            $http.post('gui/mobility/add', $person).success(callback);
        }
    };
});

angular.module('person', []);
angular.module('person').controller('PersonController', function ($scope, PersonService) {
    $scope.getAllPersons = function(){
        PersonService.getAll(
            function(result) {
                $scope.persons = result;
        });
    };
    
    $scope.showAddForm = function(){
        $scope.showAddPersonForm = true;
        PersonService.getGenderNames(
            function(result) {
                $scope.genders = result;
                if ($scope.genders && $scope.genders.length > 0) {
                    $scope.newPerson.gender = $scope.genders[0];
                }
        });
    };
    
    $scope.addPerson = function(){
        if($scope.newPerson.countryCode){
            $scope.newPerson.countryCode = $scope.newPerson.countryCode.toUpperCase();
        }
        PersonService.addNew($scope.newPerson,
            function(result) {
                $scope.cancelAddPerson();
                $scope.getAllPersons();
        });
    };
    $scope.cancelAddPerson = function(){
        $scope.newPerson = {};
        $scope.showAddPersonForm = false;
    };
    
    $scope.getAllPersons();
    $scope.cancelAddPerson();
});
angular.module('person').service('PersonService', function ($http) {
    return {
        getAll: function (callback) {
            $http.get('gui/person/get_all').success(callback);
        },
        addNew: function ($person, callback) {
            $http.post('gui/person/add', $person).success(callback);
        },
        getGenderNames: function (callback) {
            $http.get('gui/person/get_gender_names').success(callback);
        }
    };
});

angular.module('mobilityParticipant', []);
angular.module('mobilityParticipant').controller('MobilityParticipantController', function ($scope, MobilityParticipantService, PersonService) {

    $scope.getAllMobilityParticipants = function() {
       MobilityParticipantService.getAll(
            function(result) {
            $scope.mobilityParticipants = result;
        });
    };
    
    $scope.viewAddMobilityParticipantForm = function() {
        PersonService.getAll(
            function(result) {
            $scope.persons = result;
        });
        
        $scope.showAddMobilityParticipantForm = true;
    };

    $scope.addMobilityParticipant = function(){
        var currentPerson;
        angular.forEach($scope.persons, function(item) {
            if (item.personId === $scope.newMobilityParticipant.personId) {
                currentPerson = item;
            }
        });
        $scope.newMobilityParticipant.person = currentPerson;
        MobilityParticipantService.addNew($scope.newMobilityParticipant,
            function(result) {
                $scope.newMobilityParticipant = {};
                $scope.showAddMobilityParticipantForm = false;
                $scope.getAllMobilityParticipants();
        });
    };
    
    $scope.cancelAddMobilityParticipant = function(){
        $scope.newMobilityParticipant = {};
        $scope.showAddMobilityParticipantForm = false;
    };
    
    $scope.getAllMobilityParticipants();
});
angular.module('mobilityParticipant').service('MobilityParticipantService', function ($http) {
    return {
        getAll: function (callback) {
            $http.get('gui/participant/get_all',
                { method: 'GET'
                }).success(callback);
        },
        addNew: function (contact, callback) {
            $http.post('gui/participant/add', contact).success(callback);
        }
    };
});
