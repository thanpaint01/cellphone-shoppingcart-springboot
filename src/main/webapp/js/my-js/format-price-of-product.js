const formatter = new Intl.NumberFormat('vi-VN', {style: 'currency', currency: 'VND'});
$.fn.formatCurrency = function () {
    this.each(function () {
        $(this).text(formatter.format($(this).text()));
    });
    return this;
};
$('.product-price').formatCurrency();

