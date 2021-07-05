
$(document).ready(function () {


})
//brands
$('.btnRemoveBrand').click(function () {
    let id = $(this).attr('value');
    deleteByName(id, 'brands');
})

function deleteByName(id, name){
    $.ajax({
        type: 'DELETE',
        url: '/admin/tags-manage/'+name+'/'+id,
        success: function (rs) {
            if(rs === "fail") showErrorMessage("Thất bại!", "Thông tin tag không hợp lệ. Không thể tìm thấy!");
            else{
                showSuccessMessage("Thành công", "Hệ thống thay đổi hiển thị thành công.")
                var seconds = 1;
                setInterval(function () {
                    if (seconds == 0) {
                        window.location.href = '/admin/tags-manage/'+name;
                    }
                    seconds--;
                }, 1000);
            }
        }
    })
}

//rams
$('.btnRemoveRams').click(function () {
    let id = $(this).attr('value');
    deleteByName(id, 'rams');
})
//roms
$('.btnRemoveRoms').click(function () {
    let id = $(this).attr('value');
    deleteByName(id, 'roms');
})

//pins
$('.btnRemovePins').click(function () {
    let id = $(this).attr('value');
    deleteByName(id, 'pins');
})
