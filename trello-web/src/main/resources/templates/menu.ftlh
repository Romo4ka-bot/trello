<section class="menu__section">
    <header class="menu__header">
        Учетная запись
        <svg class="symbol__close">
            <use xlink:href="#close"></use>
        </svg>
    </header>
    <div class="menu__user">
        <#if user??>
        <button class="main__header--username menu__user--avatar">
            ${user.initials}
        </button>
        <div class="menu__user--info">
            <div class="menu__user-name">${user.firstName} ${user.secondName}</div>
            <div class="menu__user-email">${user.email}</div>
        </div>
    </div>
    <ul class="menu__user--ul">
        <li class="menu__user--li"><a href="/logout" style="text-decoration: none; color: #172b4d;">Выйти</a></li>
    </ul>
    <#else>
        <button class="main__header--username menu__user--avatar">
            <img src="https://openmoji.org/data/color/svg/E000.svg">
        </button>
        <ul class="menu__user--ul">
            <li class="menu__user--li"><a href="/login" style="text-decoration: none; color: #172b4d;">Войти</a></li>
        </ul>
    </#if>
</section>