$(document).ready(function () {
    $(".load-more").simpleLoadMore({
        item: "div",
        btnHTML:
            '<p class="font-weight-bold text-center mt-3"><button class="btn hvr-hover text-light">Xem thêm</button></p>',
        count: 1,
        itemsToLoad: 5,
    });
    $(".reply-popup").click(function (e) {
        e.preventDefault();
        $(".reply-box").toggle();
    });
    $("textarea").autoResize();
});
