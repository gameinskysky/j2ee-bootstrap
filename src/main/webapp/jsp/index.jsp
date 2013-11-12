<!DOCTYPE html>
<html>
<head>
    <title>J2EE Bootstrap</title>
    <link rel="stylesheet" href="static/css/reset.css">
    <link rel="stylesheet" href="static/css/style.css">

    <script src="//ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
</head>
<body>
<h1>Simple TODO-list app</h1>

<input id="newTaskInput" type="text" placeholder="Enter new task here"/>

<ul id="tasksList">
</ul>

<script>

    $(function () {
        $("#newTaskInput").keyup(function (event) {
            if (event.keyCode == 13) {
                submitTask($("#newTaskInput").val());
            }
        });
        loadTasks();
    });

    function loadTasks() {
        $("#tasksList").empty();
        $.get("api/tasks/", function(response) {
            response.map(function(task) {
                $("#tasksList").append("<li><div class=\"clearfix\"><div class=\"left\">" + task.task + "</div><div class=\"right delete\"><a href=\"javascript:void(0);\" data-ref=\"" + task.id + "\">Delete</a></div></div></li>");
            });
            updateListeners();
        });
    }

    function submitTask(task) {
        console.log('Submitting: ' + task);
        $.post("api/tasks/add", {task: task}, function (response) {
                    $("#tasksList").append("<li><div class=\"clearfix\"><div class=\"left\">" + response.task + "</div><div class=\"right delete\"><a href=\"javascript:void(0);\" data-ref=\"" + response.id + "\">Delete</a></div></div></li>");
                    updateListeners();
                }
        );
    }

    function updateListeners() {
        $("li").find("div[class='right delete']").hide();
        $("li").unbind().mouseover(function(e) {
                    $(this).find("div[class='right delete']").show();
                })
                .mouseout(function(e) {
                    $(this).find("div[class='right delete']").hide();
                });
        $("li").find("a").unbind().click(function (e) {
            var taskId = $(this).attr("data-ref");
            console.log('deleting task: ' + taskId);
            $.post("api/tasks/delete", {id: taskId}, function(response) {
                loadTasks();
            });
        });
    }

</script>
</body>
</html>
