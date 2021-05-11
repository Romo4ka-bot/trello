<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <link rel="stylesheet" href="/css/style.css">
</head>
<body>

<svg style="display: none">
    <symbol id="plus" viewBox="0 0 426.66667 426.66667">
        <g>
            <path
                    d="m405.332031 192h-170.664062v-170.667969c0-11.773437-9.558594-21.332031-21.335938-21.332031-11.773437 0-21.332031 9.558594-21.332031 21.332031v170.667969h-170.667969c-11.773437 0-21.332031 9.558594-21.332031 21.332031 0 11.777344 9.558594 21.335938 21.332031 21.335938h170.667969v170.664062c0 11.777344 9.558594 21.335938 21.332031 21.335938 11.777344 0 21.335938-9.558594 21.335938-21.335938v-170.664062h170.664062c11.777344 0 21.335938-9.558594 21.335938-21.335938 0-11.773437-9.558594-21.332031-21.335938-21.332031zm0 0"/>
        </g>
    </symbol>

    <symbol id="close" viewBox="0 0 14 14">
        <path fill-rule="evenodd" clip-rule="evenodd"
              d="M5.58579 7L0.292893 1.70711C-0.0976311 1.31658 -0.0976311 0.683418 0.292893 0.292893C0.683418 -0.0976311 1.31658 -0.0976311 1.70711 0.292893L7 5.58579L12.2929 0.292893C12.6834 -0.0976311 13.3166 -0.0976311 13.7071 0.292893C14.0976 0.683418 14.0976 1.31658 13.7071 1.70711L8.41421 7L13.7071 12.2929C14.0976 12.6834 14.0976 13.3166 13.7071 13.7071C13.3166 14.0976 12.6834 14.0976 12.2929 13.7071L7 8.41421L1.70711 13.7071C1.31658 14.0976 0.683418 14.0976 0.292893 13.7071C-0.0976311 13.3166 -0.0976311 12.6834 0.292893 12.2929L5.58579 7Z"
        ></path>
    </symbol>
</svg>
<section class="main__section">
    <#include "header.ftlh">
    <section class="main__content">
        <div class="main__content--left">
            <button class="main__content--item">
                <span class="main__content--text">Доски</span>
            </button>
            <div class="main__content--block">
                <div class="main__content--title">
                    <span class="main__content--titleText">Рабочие пространства</span>
                    <button class="main__content--add">
                        <svg class="symbol__plus">
                            <use xlink:href="#plus"></use>
                        </svg>
                    </button>
                </div>
                <#if workspaces??>
                    <#list workspaces as list>
                        <button class="main__content--item">
                            <span class="main__content--text">${list.title}</span>
                        </button>
                    </#list>
                </#if>
            </div>
        </div>


        <div class="main__content--right">
            <#if workspaces??>
                <#list workspaces as list>
                    <div class="main__content--group">
                        <div class="content__group--title">
                            <span class="content__group--text">${list.title}</span>
                        </div>
                        <div class="content__group--cards">
                            <#list list.boards as list2>
                                <input type="hidden" id="boardId" value="${list2.id}">
                                <a href="/workspace/${list.id}/board/${list2.id}"
                                   class="content__group--card notext-decoration">
                                    ${list2.title}
                                </a>
                            </#list>
                            <a href="#"
                               class="content__group--card notext-decoration content__group_create__card main__header--create" id="${list.id}">
                                Создать доску
                            </a>
                        </div>
                    </div>
                </#list>
            <#else>
                <p>У вас нет рабочих пространств</p>
            </#if>
        </div>
    </section>
</section>

<#include "menu.ftlh">

<div id="createTeam" class="create_team--window">
    <div class="create_team--modal">
        <svg class="symbol__close--modal">
            <use xlink:href="#close"></use>
        </svg>
        <div class="team__modal--info">
            <form action="/workspace" method="post" class="team__modal--form">
                <span class="team__modal--title">Создайте команду</span>
                <span class="team__modal--text">Повысьте производительность: участники команды смогут получать удобный доступ ко всем доскам.</span>
                <div class="modal__group">
                    <label for="name_team" class="modal__team--label">Имя команды</label>
                    <input type="text" name="title" id="name_team" class="modal__team--input"
                           placeholder="Введите название команды">
                    <div class="modal__team-undertext">Укажите название вашей команды, компании или организации.</div>
                </div>
                <div class="modal__group">
                    <label for="name_team" class="modal__team--label">Описание команды</label>
                    <input type="text" name="description" id="name_team" class="modal__team--input"
                           placeholder="Введите описание команды">
                    <div class="modal__team-undertext">Укажите описание вашей команды, компании или организации.</div>
                </div>
                <button class="modal__team--btn">Создать команду</button>
            </form>
        </div>
    </div>
</div>

<div class="create_team--window" id="createBoard">
    <div class="create_team--modal">
        <svg class="symbol__close--modal">
            <use xlink:href="#close"></use>
        </svg>
        <div class="team__modal--info">
            <form action="" method="post" id="form_create_board" class="team__modal--form">
                <span class="team__modal--title">Создайте доску</span>
                <span class="team__modal--text">Повысьте удобство: создайте доску для комфортной работы.</span>
                <div class="modal__group">
                    <label for="name_board" class="modal__team--label">Название доски</label>
                    <input type="text" id="name_board" class="modal__team--input" name="title"
                           placeholder="Введите название доски">
                    <div class="modal__team-undertext">Укажите название доски.</div>
                </div>
                <button class="modal__team--btn">Создать доску</button>
            </form>
        </div>
    </div>
</div>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
<script>
    const menu = $('.menu__section');
    const btnMenu = $('.main__header--username');
    const btnPlus = $('.main__content--add');
    const teamWindow = $('#createTeam');
    const closeBtn = $('.symbol__close--modal');
    const closeBtnMenu = $('.symbol__close');
    const boardModal = $('#createBoard');
    const btnCreateBoard = $('.main__header--create');

    btnMenu.click(function () {
        menu.toggleClass('menu__active');
    });

    btnPlus.click(function () {
        menu.removeClass('menu__active');
        teamWindow.addClass('team__window--active');
    });

    closeBtn.click(function () {
        teamWindow.removeClass('team__window--active');
        boardModal.removeClass('team__window--active')
    });

    closeBtnMenu.click(function () {
        menu.removeClass('menu__active');
    });

    btnCreateBoard.click(function () {
        menu.removeClass('menu__active');
        boardModal.addClass('team__window--active');
        let workspaceId = this.id;
        document.querySelector('#form_create_board').setAttribute('action', 'workspace/' + workspaceId + '/board');
    });
</script>
<script src="/javascripts/app.js"></script>
</body>
</html>