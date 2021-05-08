function convert(currency) {

    // Using replace() method
    // to make currency string suitable
    // for parseFloat() to convert
    var temp = currency.replace(/[^0-9.-]+/g, "");

    // Converting string to float
    // or double and return
    return parseFloat(temp);
}

$('.tr-update').change(function () {
    //tính giá theo số lượng khi có sự cập nhật
    var cartItemID = $(this).prop('id');
    var productID = $(this).find('p.product-price.current-price-product').prop('id');
    var amount = $(this).find('input.c-input-text.qty.text').val();
    var currentPrice = $(this).find('p.product-price.current-price-product').text();
    var total = amount * convert(reverseFormatNumber(currentPrice, "vi-VN"));
    $(this).find('p.product-price.total-by-product').html(formatter.format(total));

    $.ajax({
        type: 'POST',
        contentType: 'application/json',
        data: JSON.stringify({id: cartItemID, amount: amount, totalPrice: total, productID: productID}),
        url: 'add-to-cart',
        success: function (result) {
            //không làm gì hết
        }
    })

    //tổng cộng giá theo số lượng
    var allTotalPriceByProductID = document.querySelectorAll('.total-by-product');
    let result = 0;
    for (var i = 0, len = allTotalPriceByProductID.length | 0; i < len; i = i + 1 | 0) {
        result += convert(reverseFormatNumber(allTotalPriceByProductID[i].textContent, "vi-VN"));
    }
    $('.total-price').html(formatter.format(result));

    //giá cuối cùng (Thành tiền)
    var lastPrice = result - convert(reverseFormatNumber($('.discount-price').text(), "vi-VN"));
    $('.last-price').html(formatter.format(lastPrice));
})

var sumAmount = $('#sumOfCart').text();
//xóa sản phẩm
$('.remove-action').click(function () {
    var cartID = $(this).attr('value');
    var amountRemove = $(this).attr('name');
    let resetAmount = parseInt(sumAmount - amountRemove);
    let totalPriceBefore = convert(reverseFormatNumber($('.total-price').text(), "vi-VN"));
    let priceRemove = convert($(this).attr('alt'));
    let resetTotalPrice = totalPriceBefore - priceRemove;
    $('#btnDeleteModalConfirm').click(function () {
        $.ajax({
            type: 'DELETE',
            data: {id: cartID},
            url: 'cart',
            success: function (result) {
                if (result !== "") {
                    $('#removeCartItemModal').modal('toggle');
                    $('#' + cartID).remove();
                    $('#sumOfCart').html(resetAmount);
                    $('#li' + cartID).remove();
                    $('.total-price').html(formatter.format(resetTotalPrice));
                    $('.last-price').html(formatter.format(resetTotalPrice - convert($('.discount-price').text())));
                    deleteSuccess();
                } else {
                    showFailDelete();
                }
            }
        })
    })
})

function deleteSuccess() {
    toast({
        type: 'success',
        title: "Xóa thành công!",
        message: "Sản phẩm vừa chọn đã được xóa khỏi giỏ hàng.",
        duration: 3000
    })
}

function showFailDelete() {
    toast({
        title: 'Thất bại!',
        type: "error",
        message: "Hệ thống đang xảy ra lỗi. Không thể xóa.",
        duration: 3000
    })
}