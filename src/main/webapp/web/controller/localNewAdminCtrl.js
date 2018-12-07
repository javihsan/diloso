// Generated by CoffeeScript 1.6.2
(function() {
  var LocalNewCtrl, _ref,
    __hasProp = {}.hasOwnProperty,
    __extends = function(child, parent) { for (var key in parent) { if (__hasProp.call(parent, key)) child[key] = parent[key]; } function ctor() { this.constructor = child; } ctor.prototype = parent.prototype; child.prototype = new ctor(); child.__super__ = parent.prototype; return child; };

  LocalNewCtrl = (function(_super) {
    __extends(LocalNewCtrl, _super);

    function LocalNewCtrl() {
      _ref = LocalNewCtrl.__super__.constructor.apply(this, arguments);
      return _ref;
    }

    LocalNewCtrl.prototype.events = {
      "singleTap a[data-action=save]": "onSave",
      "singleTap a[data-action=cancel]": "onCancel"
    };

    LocalNewCtrl.prototype.elements = {
      "a[data-action=save]": "buttonSave",
      "a[data-action=cancel]": "buttonCancel",
      "#locName": "locName",
      "#locAddress": "locAddress",
      "#locCity": "locCity",
      "#locState": "locState",
      "#locCP": "locCP",
      "#locResponName": "locResponName",
      "#locResponSurname": "locResponSurname",
      "#locResponEmail": "locResponEmail",
      "#locResponTelf1": "locResponTelf1",
      "#locApoDuration": "locApoDuration",
      "#locTimeRestricted": "locTimeRestricted",
      "#locOpenDays": "locOpenDays",
      "#locNumPersonsApo": "locNumPersonsApo",
      "#locNumUsuDays": "locNumUsuDays",
      "#locNewClientDefault": "locNewClientDefault",
      "#locMulServices": "locMulServices",
      "#locCacheTasks": "locCacheTasks",
      "#locSelCalendar": "locSelCalendar"
    };

    LocalNewCtrl.prototype.onSave = function(event) {
      var data, url, _this;

      __FacadeCore.Cache_remove(appName + "elementSave");
      __FacadeCore.Cache_remove(appName + "elementCancel");
      __FacadeCore.Cache_set(appName + "elementSave", this.buttonSave.html());
      __FacadeCore.Cache_set(appName + "elementCancel", this.buttonCancel.html());
      Lungo.Element.loading(this.buttonSave.selector, "black");
      Lungo.Element.loading(this.buttonCancel.selector, "black");
      url = "http://" + appHost + "/local/admin/new";
      data = {
        domain: AppAdmin.firmDomain,
        locName: this.locName.val(),
        locAddress: this.locAddress.val(),
        locCity: this.locCity.val(),
        locState: this.locState.val(),
        locCP: this.locCP.val(),
        locApoDuration: this.locApoDuration.val(),
        locTimeRestricted: this.locTimeRestricted.val(),
        locOpenDays: this.locOpenDays.val(),
        locNumPersonsApo: this.locNumPersonsApo.val(),
        locNumUsuDays: this.locNumUsuDays.val(),
        locNewClientDefault: this.locNewClientDefault.val(),
        locResponName: this.locResponName.val(),
        locResponSurname: this.locResponSurname.val(),
        locResponEmail: this.locResponEmail.val(),
        locResponTelf1: this.locResponTelf1.val(),
        locMulServices: this.locMulServices.val(),
        locCacheTasks: this.locCacheTasks.val(),
        locSelCalendar: this.locSelCalendar.val()
      };
      _this = this;
      return $$.post(url, data, function(response) {
        return Lungo.Notification.success(findLangTextElement("label.notification.salvedData.title"), findLangTextElement("label.notification.salvedData.text"), null, 3, function(response) {
          __FacadeCore.Router_article("admin", "list-local");
          return _this.resetArticle();
        });
      });
    };

    LocalNewCtrl.prototype.onCancel = function(event) {
      __FacadeCore.Cache_remove(appName + "elementSave");
      __FacadeCore.Cache_remove(appName + "elementCancel");
      __FacadeCore.Cache_set(appName + "elementSave", this.buttonSave.html());
      __FacadeCore.Cache_set(appName + "elementCancel", this.buttonCancel.html());
      Lungo.Element.loading(this.buttonSave.selector, "black");
      Lungo.Element.loading(this.buttonCancel.selector, "black");
      this.resetArticle();
      return __FacadeCore.Router_back();
    };

    LocalNewCtrl.prototype.resetArticle = function() {
      this.buttonSave.html(__FacadeCore.Cache_get(appName + "elementSave"));
      this.buttonCancel.html(__FacadeCore.Cache_get(appName + "elementCancel"));
      this.locName.val("");
      this.locAddress.val("");
      this.locCity.val("");
      this.locState.val("");
      this.locCP.val("");
      this.locResponName.val("");
      this.locResponSurname.val("");
      this.locResponEmail.val("");
      this.locResponTelf1.val("");
      this.locApoDuration.val("");
      this.locTimeRestricted.val("");
      this.locOpenDays.val("");
      this.locNumPersonsApo.val("");
      this.locNumUsuDays.val("");
      this.locNewClientDefault.val("");
      this.locMulServices.val("");
      this.locCacheTasks.val("");
      return this.locSelCalendar.val("");
    };

    return LocalNewCtrl;

  })(Monocle.Controller);

  __Controller.LocalNew = new LocalNewCtrl("section#newLocal");

}).call(this);