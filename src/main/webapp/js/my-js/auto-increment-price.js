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
    //check số lượng nếu == 0 thì cảnh báo với người dùng là đang muốn xóa sản phẩm khỏi giỏ?
    if(amount == 0) {
        alert("Bạn muốn xóa sản phẩm này khỏi giỏ hàng!")
    }

    var currentPrice = $(this).find('p.product-price.current-price-product').text();
    var total = amount * convert(currentPrice);
    $(this).find('p.product-price.total-by-product').html(total.toLocaleString("en-US"),
        {
            style: 'currency',
            currency: 'VND'
        }
    );

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
    for (var i=0, len=allTotalPriceByProductID.length|0; i<len; i=i+1|0) {
        result += convert(allTotalPriceByProductID[i].textContent);
    }
    $('.total-price').html(result.toLocaleString("en-US")+" đ");

    //giá cuối cùng (Thành tiền)
    var lastPrice = result- convert($('.discount-price').text());
    $('.last-price').html(lastPrice.toLocaleString("en-US")+" đ");

})
