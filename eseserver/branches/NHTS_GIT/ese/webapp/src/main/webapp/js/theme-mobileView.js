function openNav() {
  document.getElementById("mySidepanel").style.width = "250px";
// $("#mySidepanel").prepend($(".breadCrumb-wrapper.div-desktop"));
// $(".breadCrumb-wrapper.div-desktop").removeClass("div-desktop");
}

function closeNav() {
  document.getElementById("mySidepanel").style.width = "0";
  //$(".breadCrumb-wrapper").addClass("div-desktop");
  //$(".language-control.div-desktop").before($(".breadCrumb-wrapper.div-desktop"));
}

var col_height=0;
var col_elem;

$('.col').each(function () {
    $this = $(this);
    if ( $this.outerHeight() > col_height ) {
        col_elem=this;
        col_height=$this.outerHeight();
    }
});

$('.col').height(col_height);

if (e.cancelable) {
	   e.preventDefault();
}

if (this.isSwipe(swipeThreshold) && e.cancelable) {
	   e.preventDefault();
	   e.stopPropagation();
	   swiping = true;
	}