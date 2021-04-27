var formatter = new Intl.NumberFormat('vi-VN', {
    style: 'currency',
    currency: 'VND',
});

const price = $('.product-price').html(function () {
    $(this).html(formatter.format($(this).text()));
})
