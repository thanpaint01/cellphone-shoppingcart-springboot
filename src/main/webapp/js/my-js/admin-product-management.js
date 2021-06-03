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
window.addEventListener('load', function() {
    document.querySelector('input[type="file"]').addEventListener('change', function() {
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
    CKEDITOR.replace('inputProductImgDes1', {
        filebrowserBrowseUrl : 'classpath:/ckfinder/ckfinder.html',
        filebrowserImageBrowseUrl : 'classpath:/ckfinder/ckfinder.html?type=Images',
        filebrowserFlashBrowseUrl : '/ckfinder/ckfinder.html?type=Flash',
        filebrowserUploadUrl : '/ckfinder/core/connector/java/connector.java?command=QuickUpload&type=Files',
        filebrowserImageUploadUrl : '/ckfinder/core/connector/java/connector.java?command=QuickUpload&type=Images',
        filebrowserFlashUploadUrl : '/ckfinder/core/connector/java/connector.java?command=QuickUpload&type=Flash'
    });
})
BrowseServer( 'Images:/', 'xImagePath' );

function BrowseServer(startupPath, functionData) {
    // You can use the "CKFinder" class to render CKFinder in a page:
    var finder = new CKFinder();

    // The path for the installation of CKFinder (default = "/ckfinder/").
    finder.basePath = '../';

    //Startup path in a form: "Type:/path/to/directory/"
    finder.startupPath = startupPath;

    // Name of a function which is called when a file is selected in CKFinder.
    finder.selectActionFunction = SetFileField;

    // Additional data to be passed to the selectActionFunction in a second argument.
    // We'll use this feature to pass the Id of a field that will be updated.
    finder.selectActionData = functionData;

    // Name of a function which is called when a thumbnail is selected in CKFinder. Preview img
    // finder.selectThumbnailActionFunction = ShowThumbnails;

    // Launch CKFinder
    finder.popup();
}

// This is a sample function which is called when a file is selected in CKFinder.
function SetFileField(fileUrl, data) {
    document.getElementById(data["selectActionData"]).innerHTML = this
        .getSelectedFile().name;
    document.getElementById("imgpreview").src = fileUrl;
}