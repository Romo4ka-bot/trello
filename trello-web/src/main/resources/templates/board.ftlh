<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <link rel="stylesheet" href="/css/style.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <link href="/webjars/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <script src="/webjars/jquery/jquery.min.js"></script>
    <script src="/webjars/sockjs-client/sockjs.min.js"></script>
    <script src="/webjars/stomp-websocket/stomp.min.js"></script>
    <style>
        body {
            background-color: #f5f5f5;
        }

        #main-content {
            max-width: 940px;
            padding: 2em 3em;
            margin: 0 auto 20px;
            background-color: #fff;
            border: 1px solid #e5e5e5;
            -webkit-border-radius: 5px;
            -moz-border-radius: 5px;
            border-radius: 5px;
        }
    </style>
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

<section class="main__section board__section">
    <#include "header.ftlh">
    <main class="main__content--board">
        <header class="header__board">
            <div class="header__board--right">
                <div class="header__board-item">
                    ${board.title}
                </div>
                <div class="vertical__line"></div>
                <div class="header__board-team">${workspace.title}</div>
                <div class="vertical__line"></div>
                <div class="header__board--members">
                    <#list participants as participants>
                        <#if participants.id == board.userId>
                            <div class="header__board--member-block admin">
                                <button class="board__avatar" id="${participants.id}">
                                    ${participants.initials}
                                </button>
                                <span class="admin__symbol"></span>
                            </div>
                        <#else>
                            <button class="board__avatar" id="${participants.id}">
                                ${participants.initials}
                            </button>
                        </#if>
                    </#list>
                </div>

                <div class="vertical__line"></div>
                <div class="header__board-team" id="btnInvitation">Пригласить</div>
            </div>
            <div class="header__board--left">
                <button class="header__board-team header__board--menu" id="archiveBtn">Архив</button>
            </div>
        </header>

        <div class="lists__card" id="cards">
            <#list listsCard as list>
                <div class="card-list-container">
                    <header class="card__list--title">
                        ${list.title}
                    </header>
                    <#if user.id==board.userId>
                        <form action="/workspace/${workspace.id}/board/${board.id}/list_card/${list.id}/delete" method="post">
                            <button>Удалить колонку</button>
                        </form>
                    </#if>
                    <div class="card__list--cards">
                        <#if list.cards??>
                            <#list list.cards as list2>
                                <form action="/workspace/${workspace.id}/board/${board.id}/list_card/${list.id}/card/${list2.id}"
                                      method="get">
                                    <div class="drag_start" draggable="true">
                                        <div class="card-container">
                                            <div class="card-body">
                                                <p>
                                                    <button>${list2.title}</button>
                                                </p>
                                            </div>
                                        </div>
                                    </div>
                                </form>
                                <#if user.id==board.userId>
                                    <form action="/workspace/${workspace.id}/board/${board.id}/list_card/${list.id}/card/${list2.id}/delete" method="post">
                                        <button>Удалить карточку</button>
                                    </form>
                                </#if>
                            </#list>
                        </#if>
                    </div>
                    <div class="card__list--footer notext-decoration">
                        <#--                        <a href="#" class="notext-decoration btn__add--card">-->
                        <#--                            <svg class="symbol__plus">-->
                        <#--                                <use xlink:href="#plus"></use>-->
                        <#--                            </svg>-->
                        <#--                            <div class="notext-decoration add__card">Добавить карточку</div>-->
                        <#--                        </a>-->
                        <div class="card__textarea">
                            <form action="/workspace/${workspace.id}/board/${board.id}/list_card/${list.id}/card"
                                  method="post">
                                <textarea class="card__add--textarea" name="title" id="card__text" cols="30" rows="10"
                                          placeholder="Введите название карточки"></textarea>
                                <div class="action__card">
                                    <button class="card__add--btn">Добавить карточку</button>
                                    <svg class="symbol__close--cardAdd">
                                        <use xlink:href="#close"></use>
                                    </svg>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </#list>
            <div class="card-list-container--add">
                <div class="card__textarea">
                    <form action="/workspace/${workspace.id}/board/${board.id}/list_card" method="post">
        <textarea class="card__add--textarea" name="title" id="card__text--list" cols="30" rows="10"
                  placeholder="Введите название колонки"></textarea>
                        <div class="action__card">
                            <button id="create-list-button" class="card__add--btn">Добавить колонку</button>
                            <svg class="symbol__close--cardAdd">
                                <use xlink:href="#close"></use>
                            </svg>
                        </div>
                    </form>
                </div>
            </div>
        </div>

        <div id="main-content" class="container">
            <div class="row">
                <div class="col-md-6">
                    <form class="form-inline">
                        <div class="form-group">
                            <label for="connect">Сreate connection:</label>
                            <button id="connect" class="btn btn-default" type="submit">Connect</button>
                            <button id="disconnect" class="btn btn-default" type="submit" disabled="disabled">Disconnect
                            </button>
                        </div>
                    </form>
                </div>
                <div class="col-md-6">
                    <form class="form-inline">
                        <div class="form-group">
                            <label for="name">What is your name?</label>
                            <input type="text" id="name" class="form-control" placeholder="Your name here...">
                        </div>
                        <button id="send" class="btn btn-default" type="submit">Send</button>
                    </form>
                </div>
            </div>
            <div class="row">
                <div class="col-md-12">
                    <table id="conversation" class="table table-striped">
                        <thead>
                        <tr>
                            <th>Greetings</th>
                        </tr>
                        </thead>
                        <tbody id="greetings">
                        </tbody>
                    </table>
                </div>
            </div>
        </div>

        <div class="menu__board" id="archiveBoard">
            <div class="menu__board--title">
                Архив
                <svg class="symbol__close--menu">
                    <use xlink:href="#close"></use>
                </svg>
            </div>
            <div class="menu__board--content">
                <#list archivedCards as list>
                    <div class="menu__board--item">
                        <a href="/workspace/${workspace.id}/board/${board.id}/list_card//card/${list.cardId}"
                           class="menu__board--card notext-decoration">
                            <span class="archive__name">${list.cardTitle}</span>
                        </a>
                        <p class="menu__board--action">
                            <a href="#" class="link__back">Вернуть на доску</a>
                            <span class="notext-decoration delitel"> - </span>
                            <a href="#" class="link__delete">Удалить</a>
                        </p>
                    </div>
                </#list>
            </div>
        </div>

    </main>
    <!--    <app-card-content></app-card-content>-->

    <div class="header__board--popup" id="popup_member">
        <div class="header__board--title">
            <button class="board__avatar--popup" id="popup_userInitial">
            </button>
            <div class="header__popup--info">
                <span class="header__popup--username" id="popup_userFullName"></span>
                <span class="header__popup--email" id="popup_userEmail"></span>
            </div>

            <svg class="symbol__close--popup">
                <use xlink:href="#close"></use>
            </svg>
        </div>
        <#if user.id == board.userId>
            <div class="header__popup--content">
                <form action="" method="post">
                    <button class="header__popup--a">Удалить
                        с доски
                    </button>
                </form>
            </div>
        </#if>
    </div>

    <div class="popup__section--invitation">
        <div class="popup__invitation">
            <svg class="symbol__close--invitation">
                <use xlink:href="#close"></use>
            </svg>
            <div class="popup__invitation--title">
                Пригласить на доску
            </div>
            <form action="/workspace/${workspace.id}/board/${board.id}/participants" method="post"
                  class="popup__invitation--form">
                <div class="popup__invitation--group">
                    <input type="text" placeholder="Адрес электронной почты" class="popup__invitation--input"
                           name="email">
                </div>
                <button class="popup__invitation--btn">Отправить приглашение</button>
            </form>
        </div>
    </div>
</section>

<#include "menu.ftlh">
<script src="https://www.jsviews.com/download/jsrender.min.js"></script>
<script src="/javascripts/board.js"></script>

<script>
    const menu = $('.menu__section');
    const btnMenu = $('.main__header--username');
    const closeBtnMenu = $('.symbol__close');
    const memberBoard = $('#popup_member');
    const btnMember = $('.board__avatar');
    const closePopupMember = $('.symbol__close--popup');
    const popupInvitation = $('.popup__section--invitation');
    const btnPopupInvitation = $('#btnInvitation');
    const closePopupInvitation = $('.symbol__close--invitation');
    const archive = $('#archiveBoard');
    const btnArchive = $('#archiveBtn');
    const closeArchive = $('.symbol__close--menu');
    const exData = {
        address: {
            street: 'Main Street'
        }
    };

    btnMenu.click(function () {
        memberBoard.removeClass('popup__active');
        menu.toggleClass('menu__active');
    });

    closeBtnMenu.click(function () {
        menu.removeClass('menu__active');
    });

    btnMember.click(function () {
        $.ajax({
            type: "GET", url: "/workspace/1/board/1/participants/" + this.id,
            dataType: 'json',
            success: (res) => {
                document.querySelector("#popup_userInitial").innerText = res.initials;
                document.querySelector("#popup_userEmail").innerText = res.email;
                document.querySelector("#popup_userFullName").innerText = res.firstName + " " + res.secondName;
            },
            error: (err) => {
                console.log(err)
            }
        });
        memberBoard.toggleClass('popup__active');
    });

    closePopupMember.click(function () {
        memberBoard.removeClass('popup__active');
    });

    btnPopupInvitation.click(function () {
        memberBoard.removeClass('popup__active');
        menu.removeClass('menu__active');
        popupInvitation.addClass('active__invitation');
    });
    closePopupInvitation.click(function () {
        popupInvitation.removeClass('active__invitation');
    });

    btnArchive.click(function () {
        archive.addClass('active__menu');
    });

    closeArchive.click(function () {
        archive.removeClass('active__menu');
    });

    var stompClient = null;

    var stompClient = null;

    function setConnected(connected) {
        $("#connect").prop("disabled", connected);
        $("#disconnect").prop("disabled", !connected);
        if (connected) {
            $("#conversation").show();
        }
        else {
            $("#conversation").hide();
        }
        $("#greetings").html("");
    }

    function connect() {
        var socket = new SockJS('/chat');
        stompClient = Stomp.over(socket);
        stompClient.connect({}, function (frame) {
            setConnected(true);
            console.log('Connected: ' + frame);
            stompClient.subscribe('/topic/greetings', function (greeting) {
                showGreeting(JSON.parse(greeting.body).content);
            });
        });
    }

    function disconnect() {
        if (stompClient !== null) {
            stompClient.disconnect();
        }
        setConnected(false);
        console.log("Disconnected");
    }

    function sendName() {
        stompClient.send("/app/hello", {}, JSON.stringify({'name': $("#name").val()}));
    }

    function showGreeting(message) {
        $("#greetings").append("<tr><td>" + message + "</td></tr>");
    }

    $(function () {
        $("form").on('submit', function (e) {
            e.preventDefault();
        });
        $( "#connect" ).click(function() { connect(); });
        $( "#disconnect" ).click(function() { disconnect(); });
        $( "#send" ).click(function() { sendName(); });
    });

</script>
</body>
</html>