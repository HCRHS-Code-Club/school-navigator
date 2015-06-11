var rooms;

$.getJSON("js/rooms.json", function(data) {
    rooms = data;
    var options = {
        persist: true,
        maxItems: 1,
        valueField: 'name',
        labelField: 'name',
        options: rooms
    };
    
    $('#start').selectize(options);
    $('#end').selectize(options);
});

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





