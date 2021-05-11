<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Карточка</title>
    <link rel="stylesheet" href="/css/style.css">
</head>
<body>
<div class="card__content--window active__card--content">
    <div class="card__content">
        <header class="card__content--header">
            <svg class="symbol__monitor">
                <use xlink:href="#monitor"></use>
            </svg>
            <div class="card__content--infoCard">
                <span class="card__content--title">${card.title}</span>
                <span class="card__content--subtitle">в колонке
                    <span class="notext-decoration">${listCard.title}</span>
          </span>
            </div>

            <svg class="symbol__close--content">
                <use xlink:href="#close"></use>
            </svg>
        </header>

        <div class="card__content--content">
            <div class="card__window--content">
                <div id="card__attachments--title" class="card__attachments--title">
                    <svg class="symbol__attachments symbol__about">
                        <use xlink:href="#about"></use>
                    </svg>
                    <span id="description-span" class="card__attachments--text">Описание</span>
                    <#--          <div class="header__board--members">-->
                    <#--            <#list participants as participants>-->
                    <#--              <#if participants.id == board.userId>-->
                    <#--                <div class="header__board--member-block admin">-->
                    <#--                  <button class="board__avatar" id="${participants.id}">-->
                    <#--                    ${participants.initials}-->
                    <#--                  </button>-->
                    <#--                  <span class="admin__symbol"></span>-->
                    <#--                </div>-->
                    <#--              <#else>-->
                    <#--                <button class="board__avatar" id="${participants.id}">-->
                    <#--                  ${participants.initials}-->
                    <#--                </button>-->
                    <#--              </#if>-->
                    <#--            </#list>-->
                    <#--          </div>-->
                    <#if card.description??>
                        <button id="change-description-btn" onclick="updateDescription()" class="card__about--btn">
                            Изменить
                        </button>
                    </#if>
                </div>

                <div id="card__about" class="card__about">
                    <div id="card-about-text" class="card__about--text">
                        <#if card.description??>
                            ${card.description}
                        <#else>
                            Здесь должно быть описание
                        </#if>
                    </div>
                    <#if !(card.description??)>
                        <textarea id="card__about--text" placeholder="Введите описание" class="card__about--field"
                                  cols="30" rows="10"></textarea>
                        <span id="save-description-span" class="card__about--action">
                <button id="save-description-btn" onclick="aa()" class="card__about--save">Сохранить</button>
                  <svg class="symbol__close--about">
                    <use xlink:href="#close"></use>
                  </svg>
            </span>
                    </#if>
                </div>
                <div class="card__attachments--title">
                    <svg class="symbol__attachments">
                        <use xlink:href="#attachments"></use>
                    </svg>
                    <span class="card__attachments--text">Вложения</span>
                </div>

                <div class="card__attachments--items">
                    <#if card.attachments?size != 0>
                        <#list card.attachments as attachment>
                            <div class="card__attachments--item">
                                <div class="card__attachments--icon">
                                    <img src="${attachment.contentPath}"
                                         alt="../static/pictures/not-found-image-15383864787lu.jpg">
                                </div>
                                <div class="card__attachments--info">
                                    <#--                  <span class="card__attachments--name">${attachment.title}</span>-->
                                    <div class="card__attachments--time">
                                        Добавлено ${attachment.createdAt}
                                    </div>
                                    <a href="#" class="card__attachments--delete">Удалить</a>
                                </div>
                            </div>
                        </#list>
                    </#if>
                    <#--                    <form action="" method="post" class="block__write-tweet" enctype="multipart/form-data">-->
                    <button type="button" id="fileBtn" onclick="activeAttach()" class="card__attachments--add">
                        Добавить вложение
                    </button>
                    <input class="inp_file_card" id="file" type="file" name="file">
                    <#--                    </form>-->
                </div>

                <#if card.checkLists?size != 0>
                    <#list card.checkLists as checkList>
                        <div class="card__attachments--title" id="divCh${checkList.id}">
                            <svg class="symbol__attachments">
                                <use xlink:href="#checkList"></use>
                            </svg>
                            <span class="card__attachments--text">${checkList.title}</span>
                            <button class="card__about--btn" id="${checkList.id}" onclick="deleteCheckList(this)">
                                Удалить
                            </button>
                        </div>

                        <div class="card__about">
                            <div class="card__check--items">
                                <#list checkList.checkListTasks as task>
                                    <div class="card__check--item">
                                        <input class="card__check--input" type="checkbox"
                                               <#if task.done == true>checked</#if> id="dfff">
                                        <label for="dffff" class="card__check--label">${task.title}</label>
                                    </div>
                                </#list>

                                <textarea style="display: none" id="card__check--text" placeholder="Введите элемент"
                                          class="card__check--field" cols="30" rows="10"></textarea>
                                <span style="display: none" class="card__about--action">
                  <button class="card__about--save">Сохранить</button>
                  <svg class="symbol__close--about">
                    <use xlink:href="#close"></use>
                  </svg>
                </span>
                            </div>
                        </div>
                    </#list>
                </#if>

                <div class="card__attachments--title">
                    <svg class="symbol__attachments">
                        <use xlink:href="#chat"></use>
                    </svg>
                    <span class="card__attachments--text">Комментарии</span>
                </div>

                <div class="card__chat">
                    <div class="card__chat--field">
                        <div class="card__chat--avatar">
                            <#if user??>
                                ${user.initials}
                            </#if>
                        </div>
                        <div class="card__chat--comment">
                            <textarea id="add-comment-textarea" class="card__chat--textarea"
                                      placeholder="Напишите комментарий" cols="30" rows="10"></textarea>
                            <span class="card__about--action card__chat--action">
                <button id="add-comment-btn" type="button" onclick="addComment()"
                        class="card__about--save">Отправить</button>
                <svg class="symbol__close--about">
                  <use xlink:href="#alias"></use>
                </svg>
              </span>
                        </div>
                    </div>
                    <div id="card__comments" class="card__comments">
                        <#list card.comments as comment>
                            <div class="card__comment" id="div${comment.id}">
                                <div class="card__comment--descr">
                                    <div class="card__chat--avatar card__comment--avatar">
                                        ${user.initials}
                                    </div>
                                    <div class="card__comment--info">
                                        <span class="card__comment--username">${comment.userFirstName}</span>
                                        <span class="card__comment--date">${comment.createdAt}</span>
                                    </div>
                                </div>
                                <div class="card__comment--text">
                                    ${comment.text}
                                </div>
                                <div class="card__comment--action">
                                    <button class="card__comment--link notext-decoration" id="${comment.id}"
                                            onclick="deleteComment(this)">Удалить
                                    </button>
                                </div>
                            </div>
                        </#list>
                    </div>
                </div>

            </div>
            <div class="card__window--sidebar">
                <div class="card__sidebar--title">Добавить на карточку</div>
                <div class="card__sidebar--item">
                    <form action="/workspace/${workspaceId}/board/${boardId}/list_card/${listCard.id}/card/${card.id}/check_list"
                          method="get">
                        <button>Чек-лист</button>
                    </form>
                </div>
                <div class="card__sidebar--item">
                    <button>Срок</button>
                </div>
                <div class="card__sidebar--item">
                    <form action="/workspace/${workspaceId}/board/${boardId}/list_card/${listCard.id}/card/${card.id}/archive" method="post">
                        <button>Архивация</button>
                    </form>
                </div>
            </div>
        </div>

    </div>
</div>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>

<script>

    async function saveDescriptionRequest(text) {
        let body = {
            'description': text
        };
        return await fetch("/card/${card.id}/desc",
            {
                headers: {
                    'Content-Type': 'application/json;charset=utf-8'
                },
                method: 'POST',
                body: JSON.stringify(body)
            }
        )
    }

    let aa = async function saveDescription() {

        let text = document.getElementById("card__about--text").value;

        let response = await saveDescriptionRequest(text);
        if (response.ok) {

            document.getElementById("save-description-span").remove();
            document.getElementById("card__about--text").remove();

            let parent = document.getElementById("card__attachments--title");

            let changeDescriptionButton = document.createElement("div");
            changeDescriptionButton.innerHTML = `<button id="change-description-btn"
        onclick="updateDescription()" class="card__about--btn">Изменить</button>`;
            parent.appendChild(changeDescriptionButton);

            let cardAboutText = document.getElementById("card-about-text");
            if (cardAboutText != null) {
                cardAboutText.innerText = text
            } else {
                let cardAbout = document.getElementById("card__about");
                cardAboutText = document.createElement("div");
                cardAboutText.innerHTML = `<div id="card-about-text" class="card__about--text">` + text + `</div>`
                cardAbout.appendChild(cardAboutText)
            }
        }

    };

    async function updateDescription() {

        document.getElementById("change-description-btn").remove();
        document.getElementById("card-about-text").remove();

        let parent = document.getElementById("card__about");

        let cardAboutText = document.createElement("div");
        cardAboutText.innerHTML = `<textarea id="card__about--text" placeholder="Введите описание" class="card__about--field" cols="30" rows="10"></textarea>`

        let saveDescriptionSpan = document.createElement("div");
        saveDescriptionSpan.innerHTML = `<span id="save-description-span" class="card__about--action">
              <button id="save-description-btn" onclick=aa() class="card__about--save">Сохранить</button>
                <svg class="symbol__close--about">
                  <use xlink:href="#close"></use>
                </svg>
          </span>`;

        parent.appendChild(cardAboutText);
        parent.appendChild(saveDescriptionSpan);
    }

    async function addComment() {

        let text = document.getElementById("add-comment-textarea").value;

        let body = {
            'userId': `${user.id}`.replaceAll(',', ''),
            'text': text,
            'cardId': `${card.id}`
        };

        let response = await fetch('/card/comment',
            {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json;charset=utf-8'
                },
                body: JSON.stringify(body)
            }
        );

        if (response.ok) {

            let result = await response.json();
            let createdAt = await result.createdAt;

            let authorAvatar = `${user.initials}`;
            let authorName = `${user.firstName}`;

            let comment = document.createElement("div");
            comment.innerHTML = `<div class="card__comment">
                <div class="card__comment--descr">
                  <div class="card__chat--avatar card__comment--avatar">

                      <div>` + authorAvatar + `</div>

                  </div>
                  <div class="card__comment--info">
                    <span class="card__comment--username">` + authorName + `</span>
                    <span class="card__comment--date">` + createdAt + `</span>
                  </div>
                </div>
                <div class="card__comment--text">` +
                text +
                `</div>
                <div class="card__comment--action">
                  <a href="#" class="card__comment--link notext-decoration">Удалить</a>
                </div>
              </div>`;

            let parent = document.getElementById("card__comments");
            parent.appendChild(comment)
        }
    }

    let activeAttach = function () {
        let btn_file = $("#fileBtn");
        let inp_file = $("#file");
        console.log(1);
        btn_file.click(function () {
            console.log(1);
            inp_file.removeClass("inp_file_card");
            inp_file.addClass("inp_file_card-active");
        })
    };
    let deleteComment = async function (a) {

        let id = a.id;

        let response = await fetch('/card/comment/' + id + '/delete',
            {
                method: 'POST',
            }
        );

        if (response.ok) {
            $("#div" + id).detach();
        }
    };

    let deleteCheckList = async function (a) {
        let id = a.id;

        let response = await fetch('/check_list/' + id + '/delete',
            {
                method: 'POST',
            }
        );

        if (response.ok) {
            $("#divCh" + id).detach();
        }
    }
</script>
</body>
</html>