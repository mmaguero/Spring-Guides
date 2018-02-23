$(document).ready(function() {
    $.ajax({
        url: "https://dog.ceo/api/breeds/image/random"
    }).then(function(data) {
       //$('.greeting-id').append(data.status);
       $('.greeting-content').append("<img height='auto' width='auto' src='" + data.message + "'>");
    });
});