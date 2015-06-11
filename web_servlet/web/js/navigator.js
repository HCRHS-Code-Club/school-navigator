var rooms;

$.getJSON("js/rooms.json", function(data) {
    rooms = data;
    var options = {
        plugins: ['restore_on_backspace'],
        persist: false,
        maxItems: 1,
        valueField: 'name',
        labelField: 'name',
        searchField: 'name',
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

$('#start').on("keyup keypress", function(e) {
  var code = e.keyCode || e.which; 
  if (code  === 13) {               
    e.preventDefault();
    return false;
  }
});

$('#end').on("keyup keypress", function(e) {
  var code = e.keyCode || e.which; 
  if (code  === 13) {               
    e.preventDefault();
    return false;
  }
});
