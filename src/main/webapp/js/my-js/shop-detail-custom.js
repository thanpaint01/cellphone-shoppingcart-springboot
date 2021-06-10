// $(".load-more").simpleLoadMore({
//     item: "div",
//     btnHTML:
//         '<p class="font-weight-bold text-center mt-3"><button class="btn hvr-hover text-light">Xem thêm</button></p>',
//     count: 1,
//     itemsToLoad: 5,
// });
// $(".reply-popup").click(function (e) {
//     e.preventDefault();
//     $(".reply-box").toggle();
// });
// $("textarea").autoResize();
var sampleComment = $("#sample-comment-reply").clone();
$("#sample-comment-reply").remove();
$("button.reply-popup").on("click", function () {
  var parent = $(this).parent();
  var newCommentForm = parent.next();
  if (parent.next().hasClass("reply-box")) {
    newCommentForm.remove();
  } else {
    newCommentForm = sampleComment.clone();
    parent.after(newCommentForm);
    newCommentForm.find("textarea").autoResize();
    var cancelBtn = newCommentForm.find(".btn-comment-cancel");
    var submitBtn = newCommentForm.find(".btn-comment-submit");
    $(cancelBtn).on("click", function () {
      newCommentForm.remove();
    });
    $(submitBtn).on("click", function (e) {
      e.preventDefault();
      $.ajax({
        type: "POST",
        url: "/user/comment/create",
        data: "data",
        success: function (response) {},
      });
    });
  }
});
