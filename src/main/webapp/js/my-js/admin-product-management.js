var apiAllProducts = 'http://localhost:8080/admin/products-manage'
var currentAPI = 'http://localhost:8080/admin/products-manage'
var textValueFindBy = $('#textValueFindBy').val();

$('#btnFindBy').click(function () {
    textValueFindBy = $('#textValueFindBy').val();
    if (textValueFindBy !== "") {
        apiAllProducts += "?find=" + textValueFindBy;
    }
    $.ajax({
        url: apiAllProducts,
        success: function (rs) {
            window.location.href = apiAllProducts
            apiAllProducts = currentAPI;
        }
    })
})
if (textValueFindBy !== "") {
    $(".page-link").each(function () {
        $(this).prop('href', $(this).prop('href') + "&find=" + textValueFindBy)
    });
}

//upload product img
window.addEventListener('load', function () {
    document.querySelector('input[type="file"]').addEventListener('change', function () {
        if (this.files && this.files[0]) {
            var img = document.querySelector('#fileImgNewProduct');
            img.onload = () => {
                URL.revokeObjectURL(img.src);  // no longer needed, free memory
            }

            img.src = URL.createObjectURL(this.files[0]); // set src to blob url
            $('#fileImgNewProduct').text(img.src);
            $('#fileImgNewProduct').prop('href', img.src);
        }
    });
});


$(document).ready(function () {
    CKEDITOR
        .replace(
            'inputProductImgDes1',
            {
                height: 400,
                filebrowserUploadUrl: '/admin/upload_ckeditor',
                filebrowserBrowseUrl: 'filebrowser',
                filebrowserWindowWidth: 800,
                filebrowserWindowHeight: 500
                // filebrowserBrowseUrl : '/ckfinder/ckfinder.html',
                // filebrowserImageBrowseUrl : '/ckfinder/ckfinder.html?type=Images',
                // filebrowserFlashBrowseUrl : '/ckfinder.html?type=Flash',
                // filebrowserUploadUrl : '/ckfinder/core/connector/java/connector.java?command=QuickUpload&type=Files',
                // filebrowserImageUploadUrl : '/ckfinder/core/connector/java/connector.java?command=QuickUpload&type=Images',
                // filebrowserFlashUploadUrl : '/ckfinder/core/connector/java/connector.java?command=QuickUpload&type=Flash'
            });

})

