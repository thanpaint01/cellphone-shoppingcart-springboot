
function redirect() {
    var show = document.getElementById("show-message");
    var seconds = 5;
    setInterval(function () {
        show.textContent = "Bạn sẽ được chuyển hướng sang \"Quản lý đơn hàng\" sau " + seconds + " giây";
        if (seconds == 0) {
            window.location.href = '/user/my-order';
        }
        seconds--;
    }, 1000);


}
window.onload = function () {
    //console.log('loading....')
    $.ajax({
        type: 'POST',
        data: {paypalResponse: 'success', totalPrice: 0.0},
        url: 'order',
        success: function () {
            //alert("abc xyz")
            //do nothing
        }
    })
    redirect()
}
// window.addEventListener('load', redirect(), false);