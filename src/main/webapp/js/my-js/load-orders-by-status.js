

$('#statusOrderSelect').change(function () {
    var statusOrder = $(this).val();
    $.ajax({
        type: 'GET',
        data: {statusOrder: statusOrder},
        url: 'ajax-load-by-status',
        success: function (rs) {
            if(rs==="") {
                $('.table-body').html('<td colspan="6">Không có đơn hàng ở trạng thái '+ statusOrder +'!</td>');
            }else{
                $('.table-body').html(rs);

            }
        }
    })
})

$('#btnSearchByOrderID').click(function () {
    var searchParams = $('#searchByIdOrder').val()===""?'null':$('#searchByIdOrder').val();
    $.ajax({
        type: 'GET',
        data: {orderID: searchParams},
        url: 'ajax-load-by-status',
        success: function (rs) {
            if(rs==="") {
                $('.table-body').html('<td colspan="6">Không tồn tại đơn hàng có mã '+ searchParams +'!</td>');
            }else{
                $('.table-body').html(rs);

            }
        }
    })

})
