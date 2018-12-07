// Generated by CoffeeScript 1.6.2
(function() {
  var FirmListAdminCtrl, _ref,
    __hasProp = {}.hasOwnProperty,
    __extends = function(child, parent) { for (var key in parent) { if (__hasProp.call(parent, key)) child[key] = parent[key]; } function ctor() { this.constructor = child; } ctor.prototype = parent.prototype; child.prototype = new ctor(); child.__super__ = parent.prototype; return child; };

  FirmListAdminCtrl = (function(_super) {
    __extends(FirmListAdminCtrl, _super);

    function FirmListAdminCtrl() {
      _ref = FirmListAdminCtrl.__super__.constructor.apply(this, arguments);
      return _ref;
    }

    FirmListAdminCtrl.prototype.events = {
      "load article#list-firm": "loadFirm",
      "singleTap a[data-action=new]": "onNewFirm"
    };

    FirmListAdminCtrl.prototype.elements = {
      "#list-firm": "listFirm",
      "#header_list_text": "header"
    };

    FirmListAdminCtrl.prototype.onNewFirm = function(event) {
      if (this.listFirm.hasClass("active")) {
        return __FacadeCore.Router_section("newFirm");
      }
    };

    FirmListAdminCtrl.prototype.loadFirm = function(event) {
      var url, _this;

      this.header.html(AppAdmin.firmDomain + " - " + findLangTextElement("label.aside.firms"));
      Lungo.Element.loading("#list-firm ul", "black");
      url = "http://" + appHost + "/firm/admin/list";
      _this = this;
      return $$.json(url, null, function(response) {
        return _this.showFirm(response);
      });
    };

    FirmListAdminCtrl.prototype.showFirm = function(response) {
      var firEnabled, firm, firmAux, result, textsTemplates, view, _i, _len, _results;

      if (response.length > 0) {
        result = Lungo.Core.toArray(response);
        result = Lungo.Core.orderByProperty(result, "firName", "asc");
        this.listFirm.children().empty();
        textsTemplates = {
          enabled: findLangTextElement("form.enabled")
        };
        _results = [];
        for (_i = 0, _len = result.length; _i < _len; _i++) {
          firmAux = result[_i];
          firEnabled = false;
          textsTemplates = {
            enabled: findLangTextElement("form.closed")
          };
          if (firmAux.enabled === 1) {
            firEnabled = true;
            textsTemplates.enabled = findLangTextElement("form.enabled");
          }
          firm = new __Model.Firm({
            enabled: firEnabled,
            firId: firmAux.id,
            firName: firmAux.firName,
            firDomain: firmAux.firDomain,
            firServer: firmAux.firServer,
            firResponName: firmAux.firRespon.whoName,
            firResponSurname: firmAux.firRespon.whoSurname,
            firResponEmail: firmAux.firRespon.whoEmail,
            firResponTelf1: firmAux.firRespon.whoTelf1,
            firAddress: firmAux.firWhere.wheAddress,
            firCity: firmAux.firWhere.wheCity,
            firState: firmAux.firWhere.wheState,
            firCP: firmAux.firWhere.wheCP,
            firCountry: firmAux.firWhere.wheCountry,
            firBookingClient: firmAux.firBookingClient,
            firClassTasks: firmAux.firClassTasks,
            firGwtUsers: firmAux.firGwtUsers,
            firBilledModule: firmAux.firBilledModule,
            firTaskMulClients: firmAux.firTaskMulClients,
            texts: textsTemplates
          });
          view = new __View.FirmListAdminView({
            model: firm
          });
          _results.push(view.append(firm));
        }
        return _results;
      } else {
        this.listFirm.children().empty();
        return Lungo.Notification.success(findLangTextElement("label.notification.noData.title"), findLangTextElement("label.notification.noData.text"), null, 3);
      }
    };

    return FirmListAdminCtrl;

  })(Monocle.Controller);

  __Controller.FirmListAdmin = new FirmListAdminCtrl("section#admin");

}).call(this);
