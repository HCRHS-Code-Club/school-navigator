$('#sumbit').click(function() {
    $.ajax({url: "navigator.html",
        data: {
            "start": $('#start').val(),
            "end": $('#end').val()
        },
        success: function (data, textStatus, jqXHR) {
            $('#output').html(data);
        }
    });
    $('html, body').stop().animate({
                scrollTop: $($('a[href=#directions]').attr('href')).offset().top
            }, 1500, 'easeInOutExpo');
});

//$('#start').keypress(function(event) {
//    if(event.keyCode === 13) {
//        $('#sumbit').click();
//    }
//});
//
//$('#end').keypress(function(event) {
//    if(event.keyCode === 13) {
//        $('#sumbit').click();
//    }
//});