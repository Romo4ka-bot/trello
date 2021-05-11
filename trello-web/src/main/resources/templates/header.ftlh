<header class="main__header">
    <div class="main__header--right">
        <button class="main__header--block">
            Дом
        </button>
        <button class="main__header--dashboard">
            <span class="header__dashboard-text">Доски</span>
        </button>
    </div>
    <div class="main__header--middle">
        Trello
    </div>
    <div class="main__header--left">
        <button class="main__header--username">
            <#if user??>
                ${user.initials}
            <#else>
                <img src="https://openmoji.org/data/color/svg/E000.svg">
            </#if>
        </button>
    </div>
</header>
