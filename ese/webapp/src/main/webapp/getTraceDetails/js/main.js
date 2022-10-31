$(document).ready(function(){    
    /*alert($(window).innerHeight());
    alert($('.row1').innerHeight());
    alert($('.row2').innerHeight());*/
    var w = $(window).innerHeight();
    var r1 = $('.row1').innerHeight();
    var r2 = $('.row2').innerHeight();
    $('.map-container').height(w-(r1+r2));
});