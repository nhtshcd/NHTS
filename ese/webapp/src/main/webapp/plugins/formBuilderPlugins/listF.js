// configure the class for runtime loading
if (!window.fbControls) window.fbControls = new Array();
window.fbControls.push(function (controlClass) {

  /**
   * listFControl
   */
  class listFControl extends controlClass {

    configure() {
      this.js = 'dependencies/jquery.rateyo.min.js';
      this.css = 'css/jquery.rateyo.min.css';
    }

    /**
     * build a text DOM element, supporting other jquery text form-control's
     * @return DOM Element to be injected into the form.
     */
    build() {
      return this.markup('span', null, {id: this.config.name});
    }

    onRender() {
      let value = this.config.value || 3.6;
      $('#'+this.config.name).rateYo({rating: value});
    }
  }

  // register this control for the following types & text subtypes
  controlClass.register('listF', listFControl);
  return listFControl;
});