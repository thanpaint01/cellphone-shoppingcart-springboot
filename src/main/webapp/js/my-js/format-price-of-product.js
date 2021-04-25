function format2(n, currency) {
    return currency + n.toFixed(2).replace(/(\d)(?=(\d{3})+\.)/g, '$1,');
}

let x = document.getElementsByClassName(".product-price");
for (let i = 0, len = x.length; i < len; i++) {
    let num = Number(x[i].innerHTML)
        .toLocaleString('vi-VN');
    x[i].innerHTML = format2(num, 'VND');
}
