'use strict';
var MyTabModel = Backbone.Collection.extend({
    url: 'data/coverage.json'
})

allure.api.addTranslation('en', {
    tab: {
        coverage: {
            name: 'Coverage'
        }
    }
});


allure.api.addTab('coverage', {
    title: 'tab.coverage.name', icon: 'fa fa-bar-chart',
    route: 'coverage',
    onEnter: (function () {
        return new MyLayout()
    })
});


const template = function (data) {
    let html = '<h3 class="pane__title">Coverage</h3>';
    html += "<p>"
    html += "<ul>"
    for (const en of data.items[0].attributes.documented_endpoints){
    html += `<li>${en.url}</li>`
    }
    html += "</ul>"
    html += "</p>"
    return html;
}

var MyView = Backbone.Marionette.View.extend({
    template: template,

    render: function () {
        this.$el.html(this.template(this.options));
        return this;
    }
})

class MyLayout extends allure.components.AppLayout {

    initialize() {
        this.model = new MyTabModel();
    }

    loadData() {
        return this.model.fetch();
    }

    getContentView() {
        return new MyView({items: this.model.models});
    }
}