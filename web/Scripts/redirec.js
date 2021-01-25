$(document).ready(function () {
            var i = 3;
            setInterval(function () {
                i--;
                if (i < 1) {
                    window.location = "Login";
                }
                $("#r i").text(i);
            }, 1000);
});