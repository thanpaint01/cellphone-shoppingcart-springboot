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
        $.ajax({
            type: 'POST',
            data: JSON.stringify({productID: productID, amount: 1}),
            contentType: 'application/json',
            accept: 'text/plain',
            url: 'add-to-cart',
            success: function (result) {
                if (result === false) {
                    showWarning();
                } else {
                    let sum = parseInt($("#sumOfCart").text(), 10) + 1;
                    $('#sumOfCart').text(sum);
                    showSuccess();
                }
            }
        })
    })
})

//Toast thông báo lỗi khi sản phẩm đã tồn tại trong giỏ hàng
var toast = function ({title = "", type = "", message = "", duration = 3000}) {
    const main = document.getElementById('toast');
    //tao ra cac element trong toast
    const icon = {
        success: 'fas fa-check-circle',
        warning: 'fas fa-info-circle',
        error: 'fas fa-times-circle',
    }
    const iconToast = icon[type];

    if (main) {
        //tao ra mot div chua noi dung toast
        const toast = document.createElement('div');

        const autoRemove = setTimeout(function () {
            main.removeChild(toast);
        }, duration);

        toast.onclick = function (e) {
            if (e.target.closest('.fas.fa-times')) {
                main.removeChild(toast);
                clearTimeout(autoRemove);
            }
        }
        toast.innerHTML = `
            <div class="toast toast--${type}">
                    <div class="toast__icon">
                        <i class="${iconToast}"></i>
                    </div>
                    <div class="toast__content">
                        <b>${title}</b><br/>
                        <span><small>${message}</small></span>
                    </div>
                    <div class="toast__icon">
                        <i class="fas fa-times"></i>
                    </div>
             </div>
            `;
        main.appendChild(toast);
    }

}

function showSuccess() {
    toast({
        type: "success",
        title: "Thành công!",
        message: 'Sản phẩm đã được thêm vào giỏ hàng.',
        duration: 5000
    })
}

function showWarning() {
    toast({
        type: "error",
        title: "Thông báo!",
        message: 'Sản phẩm vừa chọn đã có trong giỏ hàng.',
        duration: 5000
    })
}
