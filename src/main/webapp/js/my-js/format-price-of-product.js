var formatter = new Intl.NumberFormat('en-US');
// , {
//     style: 'currency',
//         currency: 'VND',
// });

const price = $('.product-price').html(function () {
    $(this).html(formatter.format($(this).text()));
})
