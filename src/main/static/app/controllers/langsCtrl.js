app
		.controller(
				"LangsController",
				[
						"$scope", "$state", "$location", "httpService", "$rootScope", "$mdDialog", "$mdMedia",
						function($scope, $state, $location, httpService, $rootScope, $mdDialog, $mdMedia) {
							
							// Callback de changeLang para selectLang
							$scope.callSelectLang = function() {
								//console.log("callSelectLang");
								if ($state.get("booking.home")) {
									urlLocalTask = protocol_url + appHost + "/localTask/listCombi";
							     	data = {localId:$rootScope.local.id};
							     	var promiseLocalTask = httpService.GET(urlLocalTask,data);
							     	promiseLocalTask.then(function (response) {
									 	combiTasks = __Utils.sortByPropChar(response.data, "lotName", true);
									 	$rootScope.combiTasks = combiTasks;
									 	//__FacadeCore.Cache_set(appName + "combiTasks", combiTasks);
									 	var path = $location.path();
										if (path != "/booking") {
											return $state.go("/booking");
										} else {
											return $rootScope.currentScope.initBook(1);
										}
									 	//return $state.go('booking.home');
							     	});
								} else if ($state.get("localTasks.home")) {
									return $state.go('localTasks.home');
								} else if ($state.get("reportSales.home")) {
									return $state.go('reportSales.home');
								}
							};
							
							// Cambiamos el idioma
							$scope.selectLang = function(lang) {
								//console.log("selectLang", lang, $rootScope.langApp);
								if (lang && lang !== $rootScope.langApp) {
									if (!$scope.screenIsSmall){
										$rootScope.showLang = !$rootScope.showLang;
									}	
									$rootScope.isViewLoading = true;
									$rootScope.changeLang(lang,$scope.callSelectLang);
									$rootScope.openNotif('Has seleccionado el idioma '+ lang, 2, null);
								}
							};
															
							$scope.toggleLangTools = function() {
								//console.log('$rootScope.showLang',$rootScope.showLang);
								$scope.screenIsSmall = !$mdMedia('gt-sm') && $rootScope.langs.length>3;
								if ($scope.screenIsSmall){
									$scope.showLangs($rootScope.findLangTextElement("label.aside.langs"), /*$rootScope.findLangTextElement("label.aside.user.title")*/'Seleccione');
								} else {
									$rootScope.showLang = !$rootScope.showLang;
								}	
							};
														
							$scope.showLangs = function(titleDialog, titleContent) {
								//console.log ("titleDialog, titleContent",titleDialog, titleContent);
							    $mdDialog.show({
							      controller: DialogController,
							      templateUrl: 'views/modalDialogLangs.html',
							      parent: angular.element(document.body),
							      clickOutsideToClose:true,
						          locals: { titleDialog: titleDialog, titleContent: titleContent }
							    })
							    .then(function(obj) {
							    	$scope.selectLang(obj);
							    });
							};
							
							var DialogController = function ($scope, $mdDialog, titleDialog, titleContent) {
								
								$scope.acceptText = $rootScope.findLangTextElement("form.accept");
								$scope.cancelText = $rootScope.findLangTextElement("form.cancel");

								$scope.titleDialog = titleDialog;
							    $scope.titleContent = titleContent;

							    $scope.returnObj = $rootScope.langApp;
							    
							    $scope.selectObj = function(obj) {
							    	$scope.returnObj = obj;
							    }
							    
								$scope.hide = function() {
									$mdDialog.hide();
								};
								  
								$scope.cancel = function() {
								    $mdDialog.cancel();
								};
								  
								$scope.answer = function() {
								    $mdDialog.hide($scope.returnObj);
								};

							}

							
							
						} ]);

app.directive('showLangs', function() {
	return {
		restrict : 'E',
		templateUrl : 'views/langs.html',
		controller: 'LangsController',
	};
});