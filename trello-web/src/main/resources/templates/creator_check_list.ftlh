<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="/css/style.css">
    <link rel="stylesheet" href="/css/style.min.css">

    <!-- Useful meta tags -->
    <meta http-equiv="x-ua-compatible" content="ie=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="robots" content="index, follow">
    <meta name="google" content="notranslate">
    <meta name="format-detection" content="telephone=no">
    <meta name="description" content="">

    <title>Trello</title>
</head>
<body>
<div class="container">
<svg display="none">
    <symbol id="close" viewBox="0 0 511.995 511.995">
        <g>
            <g>
                <path d="M437.126,74.939c-99.826-99.826-262.307-99.826-362.133,0C26.637,123.314,0,187.617,0,256.005
			s26.637,132.691,74.993,181.047c49.923,49.923,115.495,74.874,181.066,74.874s131.144-24.951,181.066-74.874
			C536.951,337.226,536.951,174.784,437.126,74.939z M409.08,409.006c-84.375,84.375-221.667,84.375-306.042,0
			c-40.858-40.858-63.37-95.204-63.37-153.001s22.512-112.143,63.37-153.021c84.375-84.375,221.667-84.355,306.042,0
			C493.435,187.359,493.435,324.651,409.08,409.006z"/>
            </g>
        </g>
        <g>
            <g>
                <path d="M341.525,310.827l-56.151-56.071l56.151-56.071c7.735-7.735,7.735-20.29,0.02-28.046
			c-7.755-7.775-20.31-7.755-28.065-0.02l-56.19,56.111l-56.19-56.111c-7.755-7.735-20.31-7.755-28.065,0.02
			c-7.735,7.755-7.735,20.31,0.02,28.046l56.151,56.071l-56.151,56.071c-7.755,7.735-7.755,20.29-0.02,28.046
			c3.868,3.887,8.965,5.811,14.043,5.811s10.155-1.944,14.023-5.792l56.19-56.111l56.19,56.111
			c3.868,3.868,8.945,5.792,14.023,5.792c5.078,0,10.175-1.944,14.043-5.811C349.28,331.117,349.28,318.562,341.525,310.827z"/>
            </g>
        </g>
    </symbol>

    <symbol id="arrow-left" viewBox="0 0 443.52 443.52">
        <g>
            <path d="M143.492,221.863L336.226,29.129c6.663-6.664,6.663-17.468,0-24.132c-6.665-6.662-17.468-6.662-24.132,0l-204.8,204.8
			c-6.662,6.664-6.662,17.468,0,24.132l204.8,204.8c6.78,6.548,17.584,6.36,24.132-0.42c6.387-6.614,6.387-17.099,0-23.712
			L143.492,221.863z"/>
        </g>
    </symbol>
</svg>
<div class="page" id="page">

    <section class="createRecipe">
        <div class="createRecipe__bg"></div>
        <div class="form__bg-link">
            <a href="/" class="link__back">
                <svg class="arrow__left-symbol">
                    <use xlink:href="#arrow-left"></use>
                </svg>
            </a>

            <form action="/workspace/${workspaceId}/board/${boardId}/list_card/${listCardId}/card/${cardId}/check_list" class="create__recipe-form" method="post">
                <div class="recipe__title-inner-form">
                    <h2 class="recipe__title-form">Создай чек-лист</h2>
                </div>

                <div class="form__group">
                    <label for="title" class="form__label">Выбери название</label>
                    <input type="text" id="title" name="title" class="form__field">
                </div>

                <div class="form__group">
                    <label for="description" class="form__label">Добавь задания</label>
                    <div class="form__btns">
                        <button type="button" class="btn__change-ingredient" id="add">Добавить</button>
                        <button type="button" class="btn__change-ingredient" id="delete">Удалить</button>
                    </div>
                    <div class="ingredients__field">

                    </div>

                </div>
                <div class="form__group">
                    <button type="submit" class="create__btn">
                        Создать
                    </button>
                </div>
            </form>
        </div>
    </section>
</div>
</div>
<script>
    const addBtn = document.querySelector('#add');
    const deleteBtn = document.querySelector('#delete');
    const form = document.querySelector('.ingredients__field');
    const formRecipe = document.querySelector('#recipe-step');

    const addInput = () => {
        let inputField = document.createElement('input');
        inputField.className = 'form__field form__field-ingredient';
        inputField.placeholder = 'Введи задачу';
        inputField.name = 'tasks';
        inputField.id = 'ingredient';
        inputField.type = 'text';
        form.append(inputField);
    };

    const deleteInput = () => {
        const ingredients = document.querySelectorAll('.form__field-ingredient');
        if (ingredients.length !== 0) {
            ingredients.item(ingredients.length - 1).remove();
        }
    };

    addBtn.addEventListener('click', addInput);
    deleteBtn.addEventListener('click', deleteInput);
</script>
</body>
</html>