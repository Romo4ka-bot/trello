// $('#create-list-button').click(function (event) {
//     event.preventDefault();
//     const title = document.getElementsByName('title');
//     const url = window.location.href.substr(21) + '/list_card';
//     console.log(url);
//     $.ajax({
//         type: "POST", url: "/workspace/1/board/1/list_card",
//         data: {
//             title: title
//         },
//         dataType: 'json',
//         success: (res) => {
//             console.log(res);
//             console.log('hello');
//             $('#cards').append($(`
//                                 <div class="card-list-container">
//                     <header class="card__list--title">
//                         ${title}
//                     </header>
//                     </div>
//                  `));
//         },
//         error: (err) => {
//             console.log(err)
//         }
//     })
// });