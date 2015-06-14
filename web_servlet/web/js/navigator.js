var $start;
var $end;

function loadRooms() {
    $.getJSON("js/rooms.json", function(data) {
        var rooms = data;
        var options = {
            plugins: ['restore_on_backspace'],
            persist: false,
            maxItems: 1,
            valueField: 'name',
            labelField: 'name',
            searchField: 'name',
            options: rooms
        };

        $start = $('#start').selectize(options);
        $end = $('#end').selectize(options);
    });
}

function getData(f) {
    $.ajax({url: "navigator.html",
        data: {
            "start": $('#start').val(),
            "end": $('#end').val()
        },
        success: function (data, textStatus, jqXHR) {
            console.log(data);
            f(data);
        }
    });
}

function scroll(id) {
    $('html, body').stop().animate({
                scrollTop: $($('a[href=' + id +']').attr('href')).offset().top
            }, 1500, 'easeInOutExpo');
}

function output(data) {
    $('#output').html(data);
}

function reverse() {
    var start = $('#start').val();
    var end = $('#end').val();
    if(start !== "" && end !== "") {
        $('#start').val(end);
        $('#end').val(start);
        $start[0].selectize.setValue(end);
        $end[0].selectize.setValue(start);
        getData(output);
    } else {
        output("Please enter a start and end room");
        scroll("#getdirections");
    }
}

$(document).ready(function() {
    loadRooms();
    $('#sumbit').click(function() {
        getData(output);
        scroll("#directions");
    });
    
    $('#reverse').click(function() {
        reverse();
    });
    
    $('#toDir').click(function() {
        scroll("#getdirections");
        console.log("top");
    });
    
    $('#toTop').click(function() {
        scroll("#page-top");
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
});