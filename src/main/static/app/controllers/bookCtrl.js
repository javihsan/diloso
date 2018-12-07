app
		.controller(
				"BookController",
				[
						"$scope",
						"$state",
						"httpService",
						'$anchorScroll',
						"$rootScope",
						"$filter",
						"$interval",
						function($scope, $state, httpService, $anchorScroll, $rootScope, $filter, $interval) {
							// Se está cargando la página
							$rootScope.isViewLoading = true;
							
							// Para evitar error en el html angular, ancura de tabla de dias
							$scope.appo = {};
							$scope.appo.cols = 1;							
							
							$scope.toggleSelectDate = function() {
								//console.log ("toggleSelectDate");
								$scope.showSelectedDate = !$scope.showSelectedDate;
							};
							
							
							$scope.formatDateSelected = function() {
								if($rootScope.selectedDate){
									date = __Utils.stringToDate($rootScope.selectedDate);
									return __Utils.dateToStringFormat(date);
								}
							};
							
							$scope.extractYear = function() {
								if($rootScope.selectedDate){
									date = __Utils.stringToDate($rootScope.selectedDate);
									return date.getFullYear();
								}
							};
							
							$scope.extractDayWeekMonth = function(sel) {
								if($rootScope.selectedDate){
									date = $rootScope.selectedDate;
									date = __Utils.stringToDate(date);
									if (sel){
										date.setDate(date.getDate() + sel);
									}
									return __Utils.dateToDayWeekMonthFormat(date);
								}
							};
							
							$scope.extractDayMonth = function(sel) {
								if($rootScope.selectedDate){
									date = $rootScope.selectedDate;
									date = __Utils.stringToDate(date);
									if (sel){
										date.setDate(date.getDate() + sel);
									}
									return date.getDate();
								}
							};
							
							$scope.extractSemDay = function(sel) {
								if($rootScope.selectedDate){
									date = $rootScope.selectedDate;
									date = __Utils.stringToDate(date);
									if (sel){
										date.setDate(date.getDate() + sel);
									}
									return __Utils.dateToDayWeekFormat(date);
								}
							};
							
							$scope.range = function(n) {
						        return new Array(n);
						    };
						    
						    $scope.icons_num = function(n) {
						        var icoArray = ['one','two','3','4','5','6'];
						    	return icoArray[n];
						    };
							
							$scope.goToBookingHome = function() {
								//console.log("goToBookingHome");
								$state.go('booking.home');
							};
							
							
							// Al iniciar la pantalla de Booking
							$scope.initBook = function(reset) {
								//console.log("initBookingIntento");
								if ($rootScope.firm) {
									//console.log("initBook",reset);
									
									$rootScope.isViewLoading = true;
									$rootScope.existsMenu = true;
				
									if (reset){
										
										$scope.selectedTabIndex = 0;
										$scope.tabsBook[1].disabled = true;
										$scope.tabsBook[2].disabled = true;
										
										$rootScope.selectedDate = undefined;
										$scope.showSelectedDate = true;
										
										$scope.personscope = undefined;
										
										$scope.appo = {};
										$scope.appo.cols = 1;	
										
										$scope.client = {};

									}
									
									var a, newDayAux;
				
									if ($rootScope.firm.firBilledModule === 0) {
										// this.footerInvoice.hide();
									}

									if ($rootScope.operatorRead) {
										// this.footerBook.hide();
									}

									if($rootScope.selectedDate){
										a = $rootScope.selectedDate.split('-');
										newDayAux = new Date(a[0], a[1] - 1, a[2]);
									} else {
										newDayAux = new Date();
									}	

									var stopCal = $interval(function() {
										if ($("#table-month")) {
											$scope.createCalendar(newDayAux);
											// impedimos que se vuelva a ejecutar esta función
											$interval.cancel(stopCal);
										}
									}, 250);
								}
							};

							$scope.createCalendar = function(newDayAux) {
								//console.log("createCalendar",newDayAux);
								var openDaysAux, weekDaysClosed, tableMonth;

								tableMonth = $("#table-month");
								openDaysAux = $rootScope.local.locOpenDays;
								weekDaysClosed = $rootScope.local.locSemanalDiary.closedDiary;
								create($scope, httpService, tableMonth, weekDaysClosed, {
										date : newDayAux,
										openDays : openDaysAux
									});
								return $scope.initSelectTask();
							};

							$scope.onToday = function() {
								//console.log("onToday");
								var newDayAux = new Date();
								return $scope.createCalendar(newDayAux);
							};

							$scope.loadMonth = function(direction) {
								//console.log("loadMonth",direction);
								return loadMonthDelta(direction);
							};
							
							$scope.onSelectDate= function(event) {
								//console.log("onSelectDate",event);
								var elemen = $(event.currentTarget).find("span#date");
								if (!elemen.parent().hasClass('date_closed')) {
									if ($rootScope.adminOption
											|| !elemen.parent().hasClass('date_not_enabled')) {
										var selectedDate = elemen.attr("datetime");
										$rootScope.selectedDate = new String(selectedDate);
										$scope.toggleSelectDate();
									}
								}
							};
							
							// Al iniciar la pantalla de SelectTask
							$scope.initSelectTask = function() {
								//console.log("initSelectTask", $scope.personscope);
								
								if (!$scope.personscope){
									
									$scope.tasMultiple = true;
									if ($rootScope.local.locMulServices == 0) {
										$scope.tasMultiple = false;
									}
									
									$scope.personscope = {};
									$scope.personscope.persons = [];
									for ( var i = 1; i <= $rootScope.local.locNumPersonsApo; i++) {
										var optObj =  { id: i, name: i.toString() };
										$scope.personscope.persons.push(optObj);
									}
									$scope.personscope.numPersons = 1;
									$scope.personscope.selectedTasksPersons = [];
									if (!$scope.tasMultiple) {
										$scope.personscope.selectedTasksPersonsCombo = [];
									} else {
										$scope.personscope.selectedTasksPersonsStr = [];
									}	
									
									$scope.selCalendar = {};
									$scope.selCalendar.calendars = [];
									$scope.selCalendar.selectedCalendar = [];
								}
								
								return $scope.changeNumPersons();

							};

							$scope.changeNumPersons = function() {
								//console.log("changeNumPersons");

								var defaultTask, selectedTasksPer, taskSelPer, h, i, ind;

								defaultTask = __Utils.findByProp($rootScope.combiTasks, "id", $rootScope.local.locTaskDefaultId);
								taskSelPer = {
									id : defaultTask.id,
									tasName : defaultTask.lotName
								};
								if (appFirmDomain === 'adveo') {
									taskSelPer.numLines = 1;
									taskSelPer.numPallets = 1;
								}
								ind = 0;
								for (h = 0; h < $scope.personscope.numPersons; h++) {
									selectedTasksPer = $scope.personscope.selectedTasksPersons[h];
									if (!selectedTasksPer) {
										if ($rootScope.selectedTasksCount
												&& $rootScope.selectedTasksCount[h]) {
											selectedTasksPer = new Array();
											for (i = 0; i <= $rootScope.selectedTasksCount[h]; i++) {
												selectedTasksPer[i] = $rootScope.selectedTasks[ind];
												i++;
												ind++;
											}
										} else {
											selectedTasksPer = new Array();
											selectedTasksPer[0] = taskSelPer;
										}
										$scope.personscope.selectedTasksPersons[h] = selectedTasksPer;
										if (!$scope.tasMultiple) {
											$scope.personscope.selectedTasksPersonsCombo[h] = selectedTasksPer[0].id;
										} 
									}	
								}

								if (appFirmDomain == 'adveo') {
									$scope.isAdveo = true;
									//return this.showTasksGoods();
								} else {
									$scope.isAdveo = false;
									//return this.showTasks();
								}
								return $scope.showTasks();
							};

							$scope.showTasks = function() {
								//console.log("showTasks");
								var h, i, selectedTasksPer, strTask, tasksSelect;
								if ($scope.tasMultiple) {
									for (h = 0; h < $scope.personscope.numPersons; h++) {
										selectedTasksPer = $scope.personscope.selectedTasksPersons[h];
										strTask = "";
										for (i = 0; i < selectedTasksPer.length; i++) {
											if (i > 0) {
												strTask += " , ";
											}
											strTask += selectedTasksPer[i].tasName;
										}
										$scope.personscope.selectedTasksPersonsStr[h] = strTask; 
									}
								}
								if ($rootScope.local.locSelCalendar == 1) {
									$scope.fillSelCalendar();
								}
								//console.log("showTasks fin antes de loading");
								return $rootScope.isViewLoading = false;
							};

							$scope.changeTaskSelect = function(taskNumPerson) {
								//console.log("changeTaskSelect", taskNumPerson);
								var selectedTasksPer, taskSelPer, taskcombo;
								taskcombo = __Utils.findByProp($rootScope.combiTasks, "id", $scope.personscope.selectedTasksPersonsCombo[taskNumPerson]);
								taskSelPer = {
									id : $scope.personscope.selectedTasksPersonsCombo[taskNumPerson],
									tasName : taskcombo.lotName
								};
								selectedTasksPer = new Array();
								selectedTasksPer[0] = taskSelPer;
								$scope.personscope.selectedTasksPersons[taskNumPerson] = selectedTasksPer;
								if ($rootScope.local.locSelCalendar == 1) {
									return $scope.fillSelCalendar();
								}
							};

							$scope.fillSelCalendar = function() {
								//console.log("fillSelCalendar");
								var data, h, j, i, selectTaskParam, selectedTasksPer, url;
								j = 0;
								selectTaskParam = new Array()
								for (h = 0; h < $scope.personscope.numPersons; h++) {
									selectedTasksPer = $scope.personscope.selectedTasksPersons[h];
									for (i = 0; i < selectedTasksPer.length; i++) {
										if (selectTaskParam.indexOf(selectedTasksPer[i].id) == -1) {
											selectTaskParam[j] = selectedTasksPer[i].id;
											j++;
										}
									}
								}
								url = protocol_url + appHost + "/calendar/listCandidate";
								data = {
									localId : $rootScope.local.id,
									selectedTasks : selectTaskParam
								};
								return httpService
										.GET(url, data)
										.then(
												function(response) {
													$scope.selCalendar.calendars = __Utils.sortByPropChar(response.data, "calName", true);
													$scope.selCalendar.selectedCalendar = [];
													for (i = 0; i < $scope.selCalendar.calendars.length; i++) {
														if ($scope.selCalendar.calendars[i].calName
																.toLowerCase()
																.indexOf("pere") !== -1) {
															$scope.selCalendar.selectedCalendar[0] = $scope.selCalendar.calendars[i].id;
														} else {
															calAux = {id:-1};
															$scope.selCalendar.selectedCalendar[0] = calAux;
														}
													}
												});
							};
							
							$scope.saveTaskSelect = function() {
								//console.log("saveTaskSelect");
								
								$rootScope.isViewLoading = true;
								
								$scope.tabsBook[1].disabled = false;
								$scope.selectedTabIndex = 1;

								
								var h, i, s, selectedTasks, selectedTasksCount, selectedTasksPer;
								selectedTasks = new Array();
						        selectedTasksCount = new Array();
						        s = 0;
								for (h = 0; h < $scope.personscope.numPersons; h++) {
									selectedTasksPer = $scope.personscope.selectedTasksPersons[h];
									for (i = 0; i < selectedTasksPer.length; i++) {
										selectedTasks[s] = selectedTasksPer[i];
										s++;
									}
							        selectedTasksCount[h] = selectedTasksPer.length;
								}
								$rootScope.selectedTasks = selectedTasks;
								$rootScope.selectedTasksCount = selectedTasksCount;

								$scope.initDayAppos();
							};
							
							/* SelectTaskPerson  ********************************************/
							
							// Ir a la pantalla de SelectTaskPerson
							$scope.goToSelectTaskPerson = function(numPerson) {
								//console.log("goToSelectTaskPerson", numPerson);
								$scope.personscope.selectedTasksNumPerson = numPerson;
								$state.go('booking.selectTasksPerson');
							}
							
						   // Al iniciar la pantalla de SelectTaskPerson
							$scope.initSelectTaskPerson = function() {
								//console.log("initSelectTaskPersonIntento");
								if ($rootScope.firm) {
									//console.log("initSelectTaskPerson");
									var selectedTasksPer = $scope.personscope.selectedTasksPersons[$scope.personscope.selectedTasksNumPerson-1];
									$scope.personscope.selectedTasksPersonsChecks = [];
									for (i = 0; i < selectedTasksPer.length; i++) {
										$scope.personscope.selectedTasksPersonsChecks.push(selectedTasksPer[i].id);
									}
									$scope.personscope.cabText = $rootScope.findLangTextElement("label.template.job");
								    if ($rootScope.local.locNumPersonsApo > 1) {
								    	$scope.personscope.cabText += " " + $rootScope.findLangTextElement("label.template.jobForPerson") + " " + $scope.personscope.selectedTasksNumPerson;
								    }
								}
							}	
						     
							// Al seleccionar/des un servicio en Multiple Person
							$scope.selectTaskPerson = function(taskId) {
								//console.log("selectTaskPerson", taskId);
								var idx = $scope.personscope.selectedTasksPersonsChecks.indexOf(taskId);
								// is currently selected
							    if (idx > -1) {
							      $scope.personscope.selectedTasksPersonsChecks.splice(idx, 1);
							    }
							    // is newly selected
							    else {
							      $scope.personscope.selectedTasksPersonsChecks.push(taskId);
							    }
							}
							
							// Limpiamos servicios en Multiple Person
							$scope.cleanTaskPerson = function() {
								//console.log("cleanTaskPerson");
								$scope.personscope.selectedTasksPersonsChecks = [];
							}
							
							// Salvamos seleccion de servicios en Multiple Person
							$scope.saveTaskPerson = function() {
								//console.log("saveTaskPerson");
								
								$rootScope.isViewLoading = true;
								
								var taskSelPer = undefined;
								var taskAux = undefined;
								var selectedTasksPerIndiv = [];
								var selectedTasksPer = [];
								i = 0;
						        c = 0;
								angular.forEach($scope.personscope.selectedTasksPersonsChecks, function (id) {
									taskAux = __Utils.findByProp($rootScope.combiTasks, "id", id);
									taskSelPer = {
										id : taskAux.id,
										tasName : taskAux.lotName
									};
									selectedTasksPer[i] = taskSelPer;
									i++;
									if (taskAux.lotTaskCombiId && taskAux.lotTaskCombiId.length > 0) {
										 var combiIds = taskAux.lotTaskCombiId;
										 for (j = 0; j<combiIds.length; j++) {
											 selectedTasksPerIndiv[c] = combiIds[j];
								             c++;
								         }
									} else {
										selectedTasksPerIndiv[c] = id;
							            c++;
									}
								});
								
							    if (selectedTasksPer.length > 0) {
						          var duplicate = __Utils.arrHasDupes(selectedTasksPerIndiv);
						          if (duplicate) {
						            var duplicateText = __Utils.findByProp($rootScope.combiTasks, "id", duplicate).lotName;
						            return $rootScope
									.openNotif(
											//$rootScope.findLangTextElement("label.notification.localTaskCombiDiferent.title"),
											$rootScope.findLangTextElement("label.notification.localTaskCombiDiferent.text") + " " + duplicateText + " " + $rootScope.findLangTextElement("label.notification.localTaskCombiDiferent.text2"),
											3,
											null);
						          } else {
						        	  $scope.personscope.selectedTasksPersons[$scope.personscope.selectedTasksNumPerson-1] = selectedTasksPer;
						        	  return $state.go('booking.home');
						          }
						        } else {
						        	 return $rootScope
										.openNotif(
												//$rootScope.findLangTextElement("label.notification.localTaskSelectOne.title"),
												$rootScope.findLangTextElement("label.notification.localTaskSelectOne.text"),
												2,
												null);
						        }
							}
							
							/* AposDay ********************************************/
							
							// date_closed or date_not_enabled
							$scope.isNotSel = function(dateApos) {
								//console.log("isNotSel", dateApos);
								if ($rootScope.selectedDate){
									var date = __Utils.stringToDate($rootScope.selectedDate);
									date.setDate(date.getDate() + dateApos);
									var weekDaysClosed = $rootScope.local.locSemanalDiary.closedDiary;
									var dayWeek = date.getDay();
									if (dayWeek==0){
										dayWeek = 6;
									} else {
										dayWeek = dayWeek-1;
									}
									for (i=0;i<weekDaysClosed.length;i++){
										if (dayWeek==weekDaysClosed[i]) {
											return true;
										}
									}
									var today = new Date();
									today.setHours(0);
									today.setMinutes(0);
									today.setSeconds(0);
									today.setMilliseconds(0)
									var oneDay = 1000 * 60 * 60 * 24;
									var maxDate = new Date();
									maxDate.setTime(today.getTime() + ((_this.settings.openDays-1)*oneDay) );
									if (date<today || date>maxDate){
										return true;
									}
									
//									var url = protocol_url+appHost+"/annual/listByMonth";
//									var data = {localId:$scope.local.id,selectedDate:date};
//								
//									var promiseAnnuals = httpService.GET(url,data);
//									promiseAnnuals.then(function (response) {

								
									
				//								isClosed = 0;
				//		    	    			$.each(_this.annuals,		
				//		    	    				function(){ 
				//		    	    			    	day = new Date(this.anuDate);
				//		    	    					strDay = __Utils.dateToString(day);
				//		    	    					if (date==strDay){
				//		    	    						isClosed = this.anuClosed;
				//		        	    					if (isClosed==1){ 
				//		        	    						return true;
				//		        	    					}
				//		    	    					}
				//		    	    				}	
				//		                		);
//									});
								}	
							}
							
							// Al iniciar la pantalla de Booking Days
							$scope.initDayAppos = function(dateApos,event) {
								//console.log("initDayAppos", dateApos, event);
								if (event){
									var elemen = $(event.currentTarget);//.find("span#date");
									if (elemen.hasClass('date_closed') ||
										 (!$rootScope.adminOption && elemen.hasClass('date_not_enabled')) ){
										return;
									}
								}	
								
								$rootScope.isViewLoading = true;
								
								$scope.appo = {};
								$scope.appo.appointments = new Array();
								$scope.appo.appoSel = undefined;
								$scope.appo.cols = 1;
								
								var data, selectTaskParam, selectedCalendars, selectedCalendarsParam, selectedTasks, selectedTasksCount, h, i;

								selectedTasks = $rootScope.selectedTasks;
								if (selectedTasks) {
									selectedTasksCount = $rootScope.selectedTasksCount;
									selectedCalendars = $scope.selCalendar.selectedCalendar;
									if ($scope.local.locSelCalendar == 1 && selectedCalendars[0].id ==-1) {
										selectedCalendarsParam = "";
									} else {
										selectedCalendarsParam = new Array();
										for (i in selectedCalendars){
											selectedCalendarsParam[i] = selectedCalendars[i].id;
										}
									}
									
									if (dateApos){
										date = __Utils.stringToDate($rootScope.selectedDate);
										date.setDate(date.getDate() + dateApos);
										$rootScope.selectedDate = __Utils.dateToString(date); 
									}

									selectTaskParam = new Array();
									for (h in selectedTasks){	
										selectTaskParam[h] = selectedTasks[h].id;
										h++;
									}
									
									data = {
										localId : $rootScope.local.id,
										selectedDate : $rootScope.selectedDate.toString(),
										selectedTasks : selectTaskParam,
										selectedTasksCount : selectedTasksCount,
										selectedCalendars : selectedCalendarsParam
									};
									if (selectedTasks[0].numLines) {
										data.numLines = selectedTasks[0].numLines;
										data.numPallets = selectedTasks[0].numPallets;
									}

									var promiseAposDay = httpService.GET($rootScope.urlListApoByDay, data);
									return promiseAposDay.then(
										function(response) {
											return $scope.showCalendarDay(response, data);
										}
									);
								} 
							};
							
							$scope.showCalendarDay = function(response, data) {
								//console.log("showCalendarDay");
								var a, appointment, bgColor, cal, h, hourAux, hours, newDayAux, num_apo, resultAppos, todayAux, top_hour, view, x, _j, _l, _len, _len1, _len2, _m;

								resultAppos = __Utils.sortByPropChar(response.data,"apoName", false);
																
								if (resultAppos.length > 0) {
									hours = [];
									for (h =  0; h <= 23; h++) {
										hours[h] = [ 0, 0 ];
									}

									//Buscamos las horas que tienen citas
									for (_j = 0, _len = resultAppos.length; _j < _len; _j++) {
										appointment = resultAppos[_j];
										hourAux = parseInt(appointment.apoName.split(":")[0]);
										hours[hourAux][1] = 1;
									}

									//Reordenamos el orden de las horas que tienen citas
									x = -1;
									for (h = 0; h <= 23; h++) {
										if (hours[h][1] == 1) {
											x++;
											hours[h][1] = x;
										}
									}
									// Al final x+1 determina el num de horas visibles, lo que determina la altura: 60 base + numLineas * altura linea
									$scope.appo.height = 60+((x+1)*85);
									$scope.appo.cols = 1;
									
									bgColor = 0;
									$scope.appo.appointments = new Array();
									for (_l = 0, _len1 = resultAppos.length; _l < _len1; _l++) {
										appointment = resultAppos[_l];
										hourAux = parseInt(appointment.apoName.split(":")[0]);
										num_apo = hours[hourAux][0];
										if (num_apo+1>$scope.appo.cols){
											$scope.appo.cols = num_apo+1;
										}
										hours[hourAux][0] = hours[hourAux][0] + 1;
										if ($rootScope.numCals > 1
												&& $rootScope.firm.firConfig.configLocal.configLocSelCalAfter) {
											bgColor = 1;
											for (_m = 0, _len2 = $rootScope.calCandidates.length; _m < _len2; _m++) {
												cal = $rootScope.calCandidates[_m];
												if (cal.id !== appointment.apoCalendarId) {
													bgColor++;
												} else {
													break;
												}
											}
										}
										appointment.enabled = true;
										appointment.bgColor = bgColor;
										$scope.appo.appointments.push(appointment);
									}
									//console.log("$scope.appo.appointments",$scope.appo.appointments);
									return $rootScope.isViewLoading = false;
								} //else {
//									if (!adminOption) {
//										return Lungo.Notification
//												.success(
//														findLangTextElement("label.notification.notavailable.title"),
//														findLangTextElement("label.notification.notavailable.text"),
//														null,
//														5,
//														function(response) {
//															return __FacadeCore
//																	.Router_article(
//																			"taskSelect",
//																			"task-form");
//														});
//									} else {
//										Lungo.Notification
//												.success(
//														findLangTextElement("label.notification.notavailableAdmin.title"),
//														findLangTextElement("label.notification.notavailableAdmin.text"),
//														null, 5);
//										a = data.selectedDate.split('-');
//										newDayAux = new Date(a[0], a[1] - 1,
//												a[2]);
//										todayAux = new Date();
//										newDayAux
//												.setHours(todayAux.getHours()
//														- (todayAux
//																.getTimezoneOffset() / 60));
//										newDayAux.setMinutes(todayAux
//												.getMinutes());
//										appointment = new __Model.Appointment({
//											enabled : true,
//											apoStartTime : newDayAux
//										});
//										__FacadeCore.Cache_remove(appName
//												+ "newApo");
//										__FacadeCore.Cache_set(appName
//												+ "newApo", appointment);
//										return __FacadeCore
//												.Router_section("#newEvent");
//									}
//								}
							};
							
							/* New Appo ***************************************************/
							$scope.onSelectDayAppo = function(appointment) {
								//console.log("onSelectDayAppo",appointment);
								
								$scope.appo.appoSel = appointment;
								
								if ($scope.local.locSelCalendar == 1){
									$scope.selCalendar.selectedCalendar[0].calName = "";
									calSel = __Utils.findByProp($scope.selCalendar.calendars, "id", $scope.selCalendar.selectedCalendar[0].id);
									if (calSel){
										$scope.selCalendar.selectedCalendar[0].calName = calSel.calName;
									}
								}
								
								$scope.client = {
									name: "namef",
									email: "sdfs@df.sdfsdf",
									telf: "66767sdfsdf",
									observ: "ovserddd"
							    };
								
								$scope.tabsBook[2].disabled = false;
								return $scope.selectedTabIndex = 2;
								
								/*__FacadeCore.Cache_remove appName+"newApo"
						    	__FacadeCore.Cache_set appName+"newApo", @model
						    	
						    	if @model.apoCalendarId!=null
							    	selectedCalendars = new Array()
							    	calendarSel = {id:@model.apoCalendarId,name:@model.apoCalendarName}
							    	selectedCalendars[0] = calendarSel
							    	__FacadeCore.Cache_remove appName+"selectedCalendars"
							    	__FacadeCore.Cache_set appName+"selectedCalendars", selectedCalendars
						    	
						    	__FacadeCore.Router_section "#newEvent"*/
							};
							
							
							$scope.sendNewAppo = function() {
								//console.log("sendNewAppo");
							};
							
							/* Tabs ********************************************/
						 	var tabsBook = [
								   	          { title: 'Datos cita', disabled: false},
								   	          { title: 'Hora cita', disabled: true},
								   	          { title: 'Tus datos', disabled: true}
							   	            ];
							   	$scope.tabsBook = tabsBook;
							   	$scope.selectedTabIndex = 0;

//							   	$scope.addTab = function (title, view) {
//							        view = view || title + " Content View";
//							        tabs.push({ title: title, content: view, disabled: false});
//							      };
//							   $scope.removeTab = function (tab) {
//							        var index = tabs.indexOf(tab);
//							        tabs.splice(index, 1);
//							      };
							

						} ]);

app.directive('showMonth', function() {
	return {
		templateUrl : 'views/tableMonthBooking.html',
		restrict : 'E'
	};
});

app.directive('showTasks', function() {
	return {
		templateUrl : 'views/bookingSelectTasks.html',
		restrict : 'E'
	};
});

app.directive('showAposDay', function() {
	return {
		templateUrl : 'views/bookingAposDay.html',
		restrict : 'E'
	};
});

app.directive('showAposNew', function() {
	return {
		templateUrl : 'views/bookingAposNew.html',
		restrict : 'E'
	};
});

//TaskSelectCtrl.prototype.validateForm = function() {
//    var error_numLines, error_numPallets, numLinesSel, numPalletsSel, result;
//
//    result = true;
//    numLinesSel = $$("#numLines");
//    error_numLines = $$("#error_numLines");
//    error_numLines.html("");
//    numPalletsSel = $$("#numPallets");
//    error_numPallets = $$("#error_numPallets");
//    error_numPallets.html("");
//    if (numLinesSel[0] && !numLinesSel[0].checkValidity()) {
//      error_numLines.html(getMessageValidity(numLinesSel[0]));
//      numLinesSel[0].focus();
//      result = false;
//    } else if (numPalletsSel[0] && !numPalletsSel[0].checkValidity()) {
//      error_numPallets.html(getMessageValidity(numPalletsSel[0]));
//      numPalletsSel[0].focus();
//      result = false;
//    }
//    return result;
//  };
