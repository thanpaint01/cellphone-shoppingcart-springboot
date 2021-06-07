$(function () {
    $.ajax({
        type: 'GET',
        url: '/api/order',
        success: function (rs) {
            if (rs === null) {
                showErrorMessage("Lỗi", "Hệ thống đang gặp sự cố!");
            }
            // $('#tbody-order').html(JSON.stringify(rs))
            renderDATAOrder(rs);
        }
    })
})

function renderDATAOrder(data) {
    var items = data.map(function (order) {
        return `
        <tr class="order-item">
            <td class="data-fields col1 align-middle">
                <b class="order-id">${order.id}</b>
            </td>
            <td class="data-fields col3">
                <a class="go-detail-mobile" href="/product/detail/${order.listOrders[0].productID}">
                    <div class="d-flex">
                        <div class="img-product">
                             <img src="${order.listOrders[0].productImg}" height="80" width="80">
                        </div>
                        <div class="text-left">
                             <p>Đơn hàng bao gồm <b class="first-product-name">${order.listOrders[0].productName}</b>
                                 và <b class="size-1-order-amount">${order.amountItems - 1}</b> sản
                                 phẩm khác
                             </p>
                        </div>
                    </div>
                 </a>
             </td>
             <td class="data-fields col2 order-date">${order.createdDate}</td>
             <td class="data-fields col2"><b class="product-price order-total-price">${formatter.format(order.lastPrice)}</b></td>
             <td class="data-fields col2"><span class="status-order-${order.id}">${order.status}</span>
             </td>
             <td class="data-fields col1 text-center">
                <a href="#" class="view" title="" data-toggle="tooltip" data-original-title="Xem chi tiết"><i data-toggle="modal" data-target="#exampleModal${order.id}" class="fas fa-arrow-circle-right icon modal-del" id="modal${order.id}"></i></a>
             </td>
         </tr>
        `
    })
    $('#tbody-order').html(items);
}

$('#btnOrder').click(function () {
    if ($('#province').val() === null || $('#district').val() === null || $('#ward').val() === null) {
        showMessageSelectProvince();
        $('#address').css('border', 'red 1px solid')
        $('.invalid-feedback').css('display', 'initial');
    } else {
        $('.invalid-feedback').css('display', 'none');
        var address = ($('#address').val() + ", " + $('#ward').val() + ", " + $('#district').val() + ", " + $('#province').val()).trim();
        var nameClient = $('#fullName').val();
        var phoneNumber = $('#phoneNumber').val();
        var totalPrice = convert(reverseFormatNumber($('#lastPrice').text(), "vi-VN"));
        var payment = $('#cash').prop('checked') === true ? 'cash' : 'paypal';
        var order = {
            "address": address,
            "nameOfClient": nameClient,
            "phoneNumberOfClient": phoneNumber,
            "totalPrice": totalPrice,
            "payment": payment
        }
        if (payment === "cash") {
            postOrder(order);
        } else {
            postOrderForPayPal(order);
        }
    }
});

function postOrder(order) {
    $.ajax({
        type: 'POST',
        contentType: 'application/json',
        accept: 'application/json',
        url: '/api/order',
        data: JSON.stringify(order),
        success: function (rs) {
            if (rs == null) alert("HỆ THỐNG ĐANG GẶP SỰ CỐ! VUI LÒNG THỬ LẠI.")
            if (rs.id == 0) {
                showErrorMessage("Lỗi", "Hệ thống đang xảy ra lỗi. Thử lại sau!");
            } else {
                $('div.loader').prop('display', 'block');
                $(window).on('load', function (event) {
                    $('.loader').delay(1000).fadeOut('fast');
                });
                showSuccessMessage("Đặt hàng thành công!", "Chúng tôi đã nhận được đơn hàng của bạn.\n Cảm ơn bạn đã mua hàng.")
                window.location.href = "/user/my-order";
            }
        }
    })
}

function postOrderForPayPal(order) {
    $.ajax({
        type: 'POST',
        contentType: 'application/json',
        accept: 'application/json',
        url: '/api/order/post-order-paypal',
        data: JSON.stringify(order),
        success: function (rs) {
            if (rs == null) alert("HỆ THỐNG ĐANG GẶP SỰ CỐ! VUI LÒNG THỬ LẠI.")
            if (rs === "error") {
                showErrorMessage("Lỗi", "Hệ thống đang xảy ra lỗi. Thử lại sau!");
            } else {
                postOrderPayPal(order);
            }
        }
    })
}

function postOrderPayPal(order) {
    $.ajax({
        type: 'POST',
        contentType: 'application/json',
        accept: 'application/json',
        data: JSON.stringify(order),
        url: '/pay',
        success: function (rs) {
            if (rs.id == 0) {
                showErrorMessage("Lỗi", "Hệ thống đang xảy ra lỗi. Thử lại sau!");
            } else {
                $('div.loader').prop('display', 'block');
                $(window).on('load', function (event) {
                    $('.loader').delay(5000).fadeOut('fast');
                });
                window.location.href = rs;
                console.log("LINK RESP BY PAYPAL = " + rs)

            }
        }
    })
}

function showMessageSelectProvince() {
    toast({
        title: "Thông báo!",
        type: "warning",
        message: "Vui lòng chọn địa chỉ nhận hàng. Bỏ qua những trường đã nhập dữ liệu!",
        duration: 5000
    })
}


//load by status
$('#statusOrderSelect').change(function () {
    var statusOrder = $(this).val();
    let id = $('#searchByIdOrder').val() === "" ? 0 : parseInt($('#searchByIdOrder').val());
    $.ajax({
        type: 'GET',
        url: '/api/order?status=' + statusOrder,
        success: function (rs) {
            if (rs.length == 0) {
                $('.table-body').html('<td colspan="6">Không có đơn hàng ở trạng thái ' + statusOrder + '!</td>');
            } else {
                renderDATAOrder(rs);
            }
        }
    })

})

var currentURL = 'http://localhost/api/order'
$('#btnSearchByOrderID').click(function () {
    var status = $('#statusOrderSelect').val();
    var urlFind = currentURL;
    let id = $('#searchByIdOrder').val() === "" ? 0 : parseInt($('#searchByIdOrder').val());
    if (isNaN(id) === true) {
        showErrorMessage("Thông báo!", "Mã đơn hàng tìm kiếm phải là số.")
        urlFind = currentURL;
    } else {
        urlFind += '?status=' + status + '&id=' + id;
    }
    alert(urlFind)
    $.ajax({
        type: 'GET',
        url: urlFind,
        success: function (rs) {
            if (rs === "") {
                $('.table-body').html('<td colspan="6">Không tồn tại đơn hàng có mã ' + id + ' !</td>');
            } else {
                renderDATAOrder(rs)
            }
        }
    })
})
$(".table-body").on("click", ".fas.fa-arrow-circle-right.icon.modal-del", function () {
    var idDeny = $(this).prop('id').split('-')[1]
    $('#btnDenyOrder-' + idDeny).click(function () {
        $.ajax({
            type: 'POST',
            data: {orderID: idDeny},
            url: '/api/order/deny',
            success: function (rs) {
                if (rs !== 'error') {
                    $('#exampleModal' + idDeny).modal('toggle');
                    $('.status-order-' + idDeny).text("Đã hủy");
                    showDenySuccess();
                } else {
                    alert("Hệ thống đang xảy ra lỗi! Thực hiện sau.")
                }
            }
        })
    })
})

function showDenySuccess() {
    toast({
        title: "Hủy thành công đơn hàng!",
        type: 'success',
        message: 'Đơn hàng đã được hủy.\nHãy tiếp tục mua hàng bạn nhé.',
        duration: 5000
    })
}