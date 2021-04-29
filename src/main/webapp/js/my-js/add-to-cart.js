/*
* JQUERY CHECK CÁC CASE CÓ THỂ CÓ TRONG QUÁ TRÌNH THÊM SẢN PHẨM VÀO GIỎ HÀNG
*/

/*
 * Kiểm tra sản phẩm đã có trong giỏ hay chưa?
 * Dùng ajax kiểm tra và trả về kết quả là true/false, đồng thời server (có/không) thêm vào csdl
 * Chổ này hiển thị notifications cho người dùng biết là họ nhận được gì sau khi yêu cầu
 */
$(function () {
    $('.btnAddToCart').click(function () {
        var productID = $(this).val();
        console.log(JSON.stringify({productID: productID, amount: 1}));
        $.ajax({
            type: 'POST',
            data: JSON.stringify({productID: productID, amount: 1}),
            contentType: 'application/json',
            url: 'add-to-cart',
            success: function (result) {
                if (result === 'error') {
                    showError();
                } else if (result === 'warning') {
                    showWarning();
                } else {
                    let sum = parseInt($("#sumOfCart").text(), 10) + 1;
                    $('#sumOfCart').text(sum);
                    showSuccess();
                    $('.cart-list').append(result);
                }
            }
        })
    })
})
