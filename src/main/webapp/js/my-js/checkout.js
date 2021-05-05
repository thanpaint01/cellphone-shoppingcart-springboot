$('#btnOrder').click(function () {
    if ($('#province').val() === null || $('#district').val() === null || $('#ward').val() === null) {
        showMessageSelectProvince();
        $('#address').css('border', 'red 1px solid')
        $('.invalid-feedback').css('display', 'initial');
    } else {
        $('.invalid-feedback').css('display', 'none');
        var address = $('#address').val()+", "+$('#ward').val()+", "+$('#district').val()+", "+$('#province').val();
        var nameClient = $('#fullName').val();
        var phoneNumber = $('#phoneNumber').val();
        var totalPrice = convert(reverseFormatNumber($('#lastPrice').text(), "vi-VN"));
        alert(totalPrice);
        $.ajax({
            type: 'POST',
            url: "order",
            data: {address: address, nameClient: nameClient, phoneNumber: phoneNumber, totalPrice: totalPrice},

            success: function (rs) {
                alert(rs)
            }

        })
    }
})

function showMessageSelectProvince() {
    toast({
        title: "Thông báo!",
        type: "warning",
        message: "Vui lòng chọn địa chỉ nhận hàng. Bỏ qua những trường đã nhập dữ liệu!",
        duration: 5000
    })
}