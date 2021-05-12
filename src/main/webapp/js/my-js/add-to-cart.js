/*
* JQUERY CHECK CÁC CASE CÓ THỂ CÓ TRONG QUÁ TRÌNH THÊM SẢN PHẨM VÀO GIỎ HÀNG
*/

/*
 * Kiểm tra sản phẩm đã có trong giỏ hay chưa?
 * Dùng ajax kiểm tra và trả về kết quả là true/false, đồng thời server (có/không) thêm vào csdl
 * Chổ này hiển thị notifications cho người dùng biết là họ nhận được gì sau khi yêu cầu
 */
$('.btnAddToCart').click(function () {
    let productID = $(this).val();
    console.log(JSON.stringify({productID: productID, amount: 1}));
    $.ajax({
        type: 'POST',
        data: {id: 0, productID: productID, amount: 1},
        url: 'add-to-cart',
        success: function (result) {

            if (result === 'error') {
                showError();
                // }
                // else if (result === 'warning') {
                //showWarning();
            } else {
                var userSessionForCart = document.getElementsByClassName('userSessionForCart');
                // if(userSessionForCart !== null)
                let sum = parseInt($("#sumOfCart").text(), 10) + 1;
                $('#sumOfCart').text(sum);
                showSuccess();
                $('.cart-list').append(result);
                var allLiPrice = document.getElementsByClassName('li-price');
                var total = 0;
                if (allLiPrice.length > 0) {
                    $('.li-price').each(function (index) {
                        console.log($(this).text());
                        total += convert(reverseFormatNumber($(this).text(), "vi-VN"));
                    })
                }
                $('.total-cart').html(formatter.format(total));
            }
        }
    })
})

