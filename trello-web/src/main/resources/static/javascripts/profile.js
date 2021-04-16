const getWorkspace = function () {
    $.ajax({
        type: "GET", url: "/api/workspace",
        data_type: 'json',
        success: (result) => {
            console.log(result);
            if (result.content.length !== 0) {
                $('#not_workspaces').detach();
                $("<ul id=\"child\">").appendTo($("#list_workspaces"));
                const child = $('#child');
                child.append($(`
                    <h2>Список ваших рабочих пространств</h2>
                 `)
                );
                for (let i = 0; i < result.content.length; i++) {
                    child.append($(`
                                <li><h4>${result.content[i]['title']}</h4></li>
                 `)
                    );
                }
            }
        }
    })
};

$('#create-team-button').click(function (event) {
    event.preventDefault();
    const title = document.getElementsByName('title');
    const desc = document.getElementsByName('desc');
    console.log('Privet');
    $.ajax({
        type: "POST", url: "/api/workspace",
        data: {
            workspaceDTO: {
                title: title,
                description: desc
            }
        },
        dataType: 'json',
        success: (result) => {
            $('#child').append($(`
                                <li><h4>${result['title']}</h4></li>
                 `));
        }
    })
});