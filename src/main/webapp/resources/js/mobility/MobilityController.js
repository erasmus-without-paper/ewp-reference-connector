angular.module('mobility').controller('MobilityController', function ($scope, $filter, MobilityService, IiaService, PersonService, LosService) {
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
        
        PersonService.getAll(
            function(result) {
                $scope.persons = result;
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
            if (item.iiaId === $scope.newMobility.iiaId) {
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
    
    $scope.viewAddLaComponentsForm = function() {
        MobilityService.getLaComponentStatuses(function(result) {
            $scope.laComponentStatuses = result;
        });
        $scope.showAddLaConditionForm = true;
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
        $scope.showAddLaConditionForm = false;
        $scope.newLaComponent = {};
    };
    
    $scope.addLaComponent = function() {
        if (!$scope.newMobility.learningAgreement) {
            $scope.newMobility.learningAgreement = {};
        }
        if (!$scope.newMobility.learningAgreement.laComponents) {
            $scope.newMobility.learningAgreement.laComponents = [];
        }
        
        $scope.laComponentsDescription[$scope.newLaComponent.losId + "." + $scope.newLaComponent.loiId] = 
                $scope.getLosOption($scope.newLaComponent.losId).text + ", " + 
                $scope.getLoiOption($scope.newLaComponent.loiId).text;
        
        $scope.newMobility.learningAgreement.laComponents.push($scope.newLaComponent);
        $scope.newMobility.learningAgreement.learningAgreementRevision = 1;
        $scope.cancelAddLaComponents();
    };
    
    $scope.getAllMobilities();
    $scope.cancelAddMobility();
});